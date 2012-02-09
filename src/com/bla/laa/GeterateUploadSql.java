package com.bla.laa;

import com.bla.laa.Common.MyCustException;
import com.bla.laa.Container.Answer;
import com.bla.laa.Container.TCase;
import com.bla.laa.shared.DAO.AnswerDAO;
import com.bla.laa.shared.DAO.ImageDAO;
import com.bla.laa.shared.DAO.QuestionDAO;
import com.bla.laa.shared.PMF;

import javax.imageio.ImageIO;
import javax.jdo.PersistenceManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GeterateUploadSql {
    private static Storage apacheDerbyClient = null;
    public static StorageFactory sf = null;
    private static final Logger logger = Logger.getLogger(GeterateUploadSql.class.getName());
    private static final PersistenceManager pm = PMF.get().getPersistenceManager();
    private static final String rootDirInFileSys = "web/public/imgC";
    private static final String rootDirWeb = "imgC";
    private static final String imgExt = "jpg";

    public static void main(String[] args) throws IOException, MyCustException {
        apacheDerbyClient = new Storage();
        sf = new StorageFactory(apacheDerbyClient);

        List<Integer> questionList = null;
        try {
            questionList = sf.getQuestionList();
        } catch (MyCustException e) {
            logger.severe(e.getMessage());
        }

        int counter =  0;
        List<String> sb = new ArrayList<String>();
        sb.add("List<AnswerDAO> answerDTOs = null;");
        sb.add("AnswersDAO answersDTO = null;");
        sb.add("QuestionDAO questionDTO = null;");
        sb.add("TCaseDAO tCaseDTO = null;");
        sb.add("ImageDAO imgLargeDTO = null;");
        sb.add("ImageDAO imgSmallDTO = null;");
        sb.add("Integer itmeIdx = 10;");

        for (Integer questionCSDDid : questionList) {
             logger.info("add : "+ counter);

            //if (counter == 100 )
            //    break;

            counter++;
            sb.add("sb.append(\"//------------------------------------  "+ counter + "\" );");

            List<TCase> tCases = null;
            try {
                tCases = sf.loadTicketFromDb(Integer.valueOf(questionCSDDid));
            } catch (MyCustException e) {
                e.printStackTrace();
            }
            TCase tc = tCases.get(0);

            sb.add("questionDAO = "+ new QuestionDAO(tc.getQuestion()).asObjStat());

            sb.add("answerDAOs = new ArrayList<AnswerDAO>();");
            for (Answer answer : tc.getAnswers()) {
                sb.add(new AnswerDAO(answer).asObjStat());
            }
            sb.add("answersDTO = new AnswersDAO(answerDTOs);");

            sb.add("pm.makePersistent(questionDTO); ");
            sb.add("pm.makePersistent(answersDTO ); ");
            if (tc.getPics() != null) {

                // small
                String fileSName = tc.getPics().getImageSmallHash() +"."+ imgExt;
                if (!chekFileExists(rootDirInFileSys +"/" +fileSName)){
                    File imgSmall = new File( rootDirInFileSys +"/" +fileSName);
                    ImageIO.write( tc.getPics().getImageSmall(), imgExt, imgSmall);
                }
                sb.add("imgSmallDTO ="+
                        new ImageDAO( rootDirWeb +"/"+ fileSName, tc.getPics().getImageCsddId(), tc.getPics().getImageSmallHash() ).asObjStat() );
                sb.add("pm.makePersistent(imgSmallDTO);");

                // large
                String fileLName = tc.getPics().getImageLargeHash() +"."+ imgExt;
                if(!chekFileExists( rootDirInFileSys +"/" +fileLName)){
                    File imgLarge = new File(rootDirInFileSys +"/" +fileLName);
                    ImageIO.write( tc.getPics().getImageLarge(), imgExt, imgLarge);
                }
                sb.add("imgLargeDTO = "+
                        new ImageDAO( rootDirWeb +"/"+ fileLName, tc.getPics().getImageCsddId(), tc.getPics().getImageLargeHash()).asObjStat());
                sb.add("pm.makePersistent(imgLargeDTO );");

                sb.add("tCaseDTO = new TCaseDAO(questionDTO.getKey(), answersDTO.getKey(), imgLargeDTO.getKey(), imgSmallDTO.getKey());");
            } else {
                sb.add("tCaseDTO = new TCaseDAO(questionDTO.getKey(), answersDTO.getKey());");
            }

            sb.add("tCaseDTO.setParagraphs(new ArrayList<String>(Arrays.asList(\"\"))); ");
            sb.add("tCaseDTO.settCaseTypeIdx( itmeIdx ); ");
            sb.add("pm.makePersistent(tCaseDTO); ");
            appendToFile(sb);
            sb.clear();
        }
    }

    private static void appendToFile(List<String> sb){
        try{
            File out = new File ("PrepAllTcases.java");
            FileWriter fileWriter = new FileWriter(out, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String s : sb)
                bufferedWriter.append(s + "\n");
            bufferedWriter.close();
        }catch (Exception e){
            logger.severe(e.getMessage());
        }
    }


    private static boolean chekFileExists(String fileName){
        try{
            File file = new File (fileName);
            return file.isFile();
        }catch (Exception e){
            logger.severe(e.getMessage());
        }
        return false;
    }
}
