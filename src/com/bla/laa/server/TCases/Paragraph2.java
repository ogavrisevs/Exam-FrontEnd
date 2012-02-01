/*
 * @(#)Paragraph1.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.bla.laa.server.TCases;

import com.bla.laa.server.TCasesServlet;
import com.bla.laa.shared.DAO.*;

import javax.jdo.PersistenceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Paragraph2 extends Paragraphs{

    public Paragraph2(TCasesServlet tCasesServlet) {
        super(tCasesServlet);
    }

    public String makeAllTc() throws Exception {
        PersistenceManager pm = tCasesServlet.pm;

        StringBuffer sb = new StringBuffer("Start of loading ...");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        List<AnswerDAO> answerDAOs = null;
        AnswersDAO answersDAO = null;
        QuestionDAO questionDAO = null;
        TCaseDAO tCaseDAO = null;
        ImageDAO imgLargeDAO = null;
        ImageDAO imgSmallDAO = null;
        Integer caseTypeIdx = 12;

        sb.append("//------------------------------------  124" );
        questionDAO = new QuestionDAO(" Vai personai, kurai ir tiesības vadīt \"B\" kategorijas transportlīdzekļus, atļauts vadīt vieglo automobili ar piekabi, ja piekabes pilna masa pārsniedz 750 kg?", " 239e039aa0aed5d7ec9d47bc97e28165", 67771885 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Atļauts jebkurā gadījumā." , 57939 , " 6cf24b76af9efc9237e04b7526c85e4e",  false ));
        answerDAOs.add(new AnswerDAO(" Atļauts, ja transportlīdzekļu sastāva pilna masa nepārsniedz 3500 kg un piekabes pilna masa nepārsniedz vilcēja pašmasu." , 57969 , " affa12755d11e268d34020e3b9a275c7",  true ));
        answerDAOs.add(new AnswerDAO(" Aizliegts." , 57996 , " ff55fe017cafa7c4108c4d2eb73c703c",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1 pielikums ")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  913" );
        questionDAO = new QuestionDAO(" Vai vadītājam pēc policijas darbinieka pieprasījuma ir jāuzrāda apskatei transportlīdzekļa aprīkojums (avārijas zīme u. c. aprīkojums), kas atrodas bagāžas nodalījumā?", " b8f477aaeee063ab78daad7713d98333", 68199250 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Ir jāuzrāda." , 57551 , " c8d7f37d1efa79096091049e8c6a2e6a",  true ));
        answerDAOs.add(new AnswerDAO(" Nav jāuzrāda." , 57595 , " d14510eeb536b3ef268f7e31802a7103",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("38.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  533" );
        questionDAO = new QuestionDAO(" Vai vadītājam atļauts lietot alkoholiskos dzērienus pēc ceļu satiksmes negadījuma līdz alkoholisko dzērienu ietekmes pārbaudei?", " 1f3a6f1aa34850acc7e4e4651e6d2d66", 67786206 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Atļauts, ja policijas darbinieki ir jau atbraukuši." , 58218 , " d93ede7a6744d357c1195dca87b9b9e0",  false ));
        answerDAOs.add(new AnswerDAO(" Aizliegts." , 58254 , " ff55fe017cafa7c4108c4d2eb73c703c",  true ));
        answerDAOs.add(new AnswerDAO(" Atļauts jebkurā gadījumā." , 58281 , " 6cf24b76af9efc9237e04b7526c85e4e",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("39.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  266" );
        questionDAO = new QuestionDAO(" Vai pirms izbraukšanas vadītājam ir jāpārbauda, vai automobilis ir tehniskā kārtībā?", " 14eff2c8b33cb8225d618f9cc9233fac", 67777288 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Nav jāpārbauda, jo tehniskā stāvokļa pārbaudi veica valsts tehniskā apskate." , 57556 , " caf40eb3514e90e7bce5f2279cb41e7f",  false ));
        answerDAOs.add(new AnswerDAO(" Ir jāpārbauda." , 57600 , " 73e0135cedba209c18ccae233177f3b8",  true ));
        answerDAOs.add(new AnswerDAO(" Jāpārbauda tikai ārējās apgaismes ierīces." , 57637 , " 11a32dc9db48c53d6490f9a6f0c47db8",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("38.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  496" );
        questionDAO = new QuestionDAO(" Vai vadītājam atļauts vest vieglā automobiļa pakaļējā sēdeklī pasažieri, kas nav piesprādzējies?", " 667f69dff8b6b9a104d0c866f1b11236", 67783191 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Atļauts." , 57557 , " eceee4942cb6275542e314d9a8f4e8e2",  false ));
        answerDAOs.add(new AnswerDAO(" Aizliegts." , 57601 , " ff55fe017cafa7c4108c4d2eb73c703c",  true ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("38.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  1079" );
        questionDAO = new QuestionDAO(" Vai vadītājam ir tiesības uzzināt viņa vadītā transportlīdzekļa apstādināšanas iemeslu, ja transportlīdzeklis apstādināts pēc policijas darbinieka pieprasījuma?", " 66c4f563d48d848a17c6bca63557b93f", 68541918 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Ir tiesības." , 57558 , " 4ddf7cf30ceacbba0045e4fb38ce15ef",  true ));
        answerDAOs.add(new AnswerDAO(" Ir tiesības tikai gadījumos, ja vadītājs pārkāpis ceļu satiksmes noteikumu prasības." , 57602 , " b61b1832ebc90528c4f5df77138e73af",  false ));
        answerDAOs.add(new AnswerDAO(" Nav tiesības." , 57638 , " 0ac6519c06d06b4bcf78d090ad5c3f7d",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("9.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return sb.toString();


    }

}

