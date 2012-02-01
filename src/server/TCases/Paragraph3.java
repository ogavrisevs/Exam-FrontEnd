/*
 * @(#)Paragraph1.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package server.TCases;

import server.TCasesServlet;
import shared.DAO.*;

import javax.jdo.PersistenceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Paragraph3 extends Paragraphs{

    public Paragraph3(TCasesServlet tCasesServlet) {
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
        Integer caseTypeIdx = 13;

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
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("282.23.", "1.1.", "1.2.", "1.3.", "1.4.", "1.5.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return sb.toString();


    }

}

