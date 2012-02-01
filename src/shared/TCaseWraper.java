package shared;

import shared.DAO.*;
import shared.Model.TCaseModel;
import shared.Services.ParagraphService;

import java.util.logging.Logger;

public class TCaseWraper {
    private static final Logger logger = Logger.getLogger(TCaseWraper.class.getName());
    ParagraphService paragraphService;

    public TCaseWraper() {
        paragraphService = new ParagraphService();

    }

    public TCaseModel getTCaseMod(TCaseDAO tc) {
        TCaseModel tcm = new TCaseModel();

        QuestionDAO questionDAO = (QuestionDAO) PMF.getByKey(QuestionDAO.class, tc.getQusetionKey());
        tcm.setQuestionText(questionDAO.getQuestionText());

        AnswersDAO answersDAO = (AnswersDAO) PMF.getByKey(AnswersDAO.class, tc.getAnswersKey());
        for (AnswerDAO answer : answersDAO.getAnswers()) {
            if (answer.getCorrect()) {
                tcm.addAnswers(answer.getAnswerTxt());
                tcm.setCorectAnswer(answer.getAnswerTxt());
            } else
                tcm.addAnswers(answer.getAnswerTxt());
        }

        if ((tc.getImageLKey() == null) || (tc.getImageSKey() == null)) {
            tcm.setImgLurl(0L);
            tcm.setImgSurl(0L);
        } else {
            tcm.setImgLurl(tc.getImageLKey().getId());
            tcm.setImgSurl(tc.getImageSKey().getId());
        }

        for (String paragName : tc.getParagraphs()){
            ParagraphDAO paragraphDAO = paragraphService.getParagByName(paragName);
            tcm.addParagraphDTOs(paragraphDAO.getNr(), paragraphDAO.getParagName());
        }

        return tcm;
    }
}
