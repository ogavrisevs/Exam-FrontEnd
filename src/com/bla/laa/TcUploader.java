package com.bla.laa;

import com.bla.laa.Common.MyCustException;
import com.bla.laa.Container.Answer;
import com.bla.laa.Container.TCase;
import com.bla.laa.shared.DAO.*;
import com.bla.laa.shared.PMF;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

import javax.imageio.ImageIO;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TcUploader {

    private static Storage apacheDerbyClient = null;
    public static StorageFactory sf = null;
    private static final Logger logger = Logger.getLogger(TcUploader.class.getName());
    private static final PersistenceManager pm = PMF.get().getPersistenceManager();

    public static void main(String[] args) throws IOException {
        try {
            apacheDerbyClient = new Storage();
            sf = new StorageFactory(apacheDerbyClient);
        } catch (MyCustException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String username = "codon.dev@gmail.com";
        String password = "*****";
        RemoteApiOptions options = new RemoteApiOptions()
                .server("csn-exam.appspot.com", 443)
                        //.com.bla.laa.server("localhost", 8080)
                        //.com.bla.laa.server("127.0.0.1", 9997)
                .credentials(username, password);

        RemoteApiInstaller installer = new RemoteApiInstaller();
        installer.install(options);
        try {

            List<Integer> questionList = null;
            try {
                questionList = sf.getQuestionList();
            } catch (MyCustException e) {
                e.printStackTrace();
            }

            int counter = 0;
            for (Integer questionCSDDid : questionList) {
                List<TCase> tCases = null;
                try {
                    tCases = sf.loadTicketFromDb(Integer.valueOf(questionCSDDid));
                } catch (MyCustException e) {
                    e.printStackTrace();
                }
                TCase tc = tCases.get(0);

                QuestionDAO questionDAO = new QuestionDAO(tc.getQuestion());
                List<AnswerDAO> answers = new ArrayList<AnswerDAO>();

                for (Answer answer : tc.getAnswers()) {
                    AnswerDAO a = new AnswerDAO(answer);
                    answers.add(a);
                }
                AnswersDAO answersDAO = new AnswersDAO();
                answersDAO.setAnswers(answers);

                ImageDAO imgSmallDAO = null;
                ImageDAO imgLargeDAO = null;

                if (tc.getPics() != null) {
                    imgSmallDAO = new ImageDAO();
                    imgSmallDAO.setImage(getImgAsBytArray(tc.getPics().getImageSmall()));
                    imgSmallDAO.setImageCsddId(tc.getPics().getImageCsddId());
                    imgSmallDAO.setHash(tc.getPics().getImageSmallHash());

                    imgLargeDAO = new ImageDAO();
                    imgLargeDAO.setImage(getImgAsBytArray(tc.getPics().getImageLarge()));
                    imgLargeDAO.setImageCsddId(tc.getPics().getImageCsddId());
                    imgLargeDAO.setHash(tc.getPics().getImageLargeHash());
                }
                try {

                    if (chkForDubl(tc)) {
                        logger.info("TestCase are olreday on com.bla.laa.server : " + tc.getQuestion().getQuestionText());
                        continue;
                    }

                    //---- actual saving to remotu db
                    pm.makePersistent(questionDAO);
                    pm.makePersistent(answersDAO);

                    if ((imgLargeDAO != null) || (imgSmallDAO != null)) {
                        pm.makePersistent(imgLargeDAO);
                        pm.makePersistent(imgSmallDAO);
                    }

                    TCaseDAO tCaseDAO = null;
                    if ((imgLargeDAO != null) || (imgSmallDAO != null))
                        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey(), imgLargeDAO.getKey(), imgSmallDAO.getKey());
                    else
                        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());

                     tCaseDAO.settCaseTypeIdx(10);
                    pm.makePersistent(tCaseDAO);
                    counter++;
                    logger.info("added new TCaseDAO on com.bla.laa.server : " + tCaseDAO.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (counter > 100000)
                    break;
            }

        } finally {
            pm.close();
            installer.uninstall();
        }
    }

    /**
     * @return - false - new uniq rec.
     */
    private static Boolean chkForDubl(TCase tc) {
        if (chkForEntryByHash(QuestionDAO.class, tc.getQuestion().getQuestionHash())) {
            List<QuestionDAO> questionDAOs = getQuestionListByHash(tc.getQuestion().getQuestionHash());
            for (QuestionDAO questionDAO : questionDAOs) {
                TCaseDAO tCaseDAO = getTCaseDTOByQuestionKey(questionDAO.getKey());
                logger.info(tCaseDAO.toString());

                AnswersDAO answersDAO = (AnswersDAO) PMF.getByKey(AnswersDAO.class, tCaseDAO.getAnswersKey());

                List<String> answerHashLsitOnServer = new ArrayList<String>();
                for (AnswerDAO answerDAOItme : answersDAO.getAnswers()) {
                    AnswerDAO answerDAO = (AnswerDAO) PMF.getByKey(AnswerDAO.class, answerDAOItme.getKey());
                    answerHashLsitOnServer.add(answerDAO.getHash());
                }

                List<String> answerHashLsitLocas = new ArrayList<String>();
                for (Answer answer : tc.getAnswers())
                    answerHashLsitLocas.add(answer.getAnswerHash());

                //compare both lists
                if (answerHashLsitOnServer.containsAll(answerHashLsitLocas))
                    return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * @return - true already got this
     */
    private static Boolean chkForEntryByHash(Class classs, String hash) {
        Query query = pm.newQuery(classs);
        try {
            query.setFilter("hash == hashIN");
            query.declareParameters("String hashIN");
            List<Object> listDTOs = (List<Object>) query.execute(hash);
            if (listDTOs.isEmpty())
                return Boolean.FALSE;
            else
                return Boolean.TRUE;
        } catch (Exception e) {
            logger.severe(e.getMessage());
        } finally {
            query.closeAll();
        }
        return Boolean.FALSE;
    }

    private static List<QuestionDAO> getQuestionListByHash(String hash) {
        Query query = pm.newQuery(QuestionDAO.class);
        try {
            query.setFilter("hash == hashIN");
            query.declareParameters("String hashIN");
            List<QuestionDAO> listDAOs = (List<QuestionDAO>) query.execute(hash);
            return listDAOs;
        } catch (Exception e) {
            logger.severe(e.getMessage());
        } finally {
            query.closeAll();
        }
        return null;
    }

    private static TCaseDAO getTCaseDTOByQuestionKey(com.google.appengine.api.datastore.Key key) {
        Query query = pm.newQuery(TCaseDAO.class);
        try {
            query.setFilter("qusetionKey == keyParam");
            query.declareParameters(com.google.appengine.api.datastore.Key.class.getName() + " keyParam");
            List<TCaseDAO> listDAOs = (List<TCaseDAO>) query.execute(key);
            return listDAOs.get(0);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        } finally {
            query.closeAll();
        }
        return null;
    }


    static byte[] getImgAsBytArray(BufferedImage img) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}