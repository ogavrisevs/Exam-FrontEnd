package com.bla.laa.server;

import com.bla.laa.shared.DAO.AnswerDAO;
import com.bla.laa.shared.DAO.AnswersDAO;
import com.bla.laa.shared.DAO.QuestionDAO;
import com.bla.laa.shared.DAO.TCaseDAO;
import com.bla.laa.shared.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class MainServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MainServlet.class.getName());
    PersistenceManager pm = PMF.get().getPersistenceManager();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doGet ()");
        String id = request.getParameter("id");
        if (id == null) {
            response.sendError(999, "id not set !");
            logger.info("doGet.id == null");
            return;
        }
        logger.info("doGet.id == " + id);

        Key key = KeyFactory.createKey(TCaseDAO.class.getSimpleName(), Integer.valueOf(id));
        StringBuffer sb = new StringBuffer();
        try {
            setHeader(sb);
            Object testCaseObj = pm.getObjectById(TCaseDAO.class, key);
            if ((testCaseObj == null) || ((testCaseObj instanceof TCaseDAO) != true)) {
                response.sendError(999, "case not found !!");
                return;
            }
            TCaseDAO t1 = (TCaseDAO) testCaseObj;

            Object questionObj = pm.getObjectById(QuestionDAO.class, t1.getQusetionKey());
            if ((questionObj == null) || ((questionObj instanceof QuestionDAO) != true)) {
                response.sendError(999, "Question not set!");
                return;
            }
            QuestionDAO question = (QuestionDAO) questionObj;
            sb.append("<h2>" + question.getQuestionText() + "</h2>\n");

            Object answersObj = pm.getObjectById(AnswersDAO.class, t1.getAnswersKey());
            if ((answersObj == null) || ((answersObj instanceof AnswersDAO) != true)) {
                response.sendError(999, "Answers not set!");
                return;
            }
            AnswersDAO answers = (AnswersDAO) answersObj;
            for (AnswerDAO answer : answers.getAnswers())
                sb.append("<h3>" + (answer.getCorrect() == true ? "+" : "") + answer.getAnswerTxt() + "<h3> \n");

            if ((t1.getImageLKey() != null) && (t1.getImageSKey() != null)) {
                sb.append("<img  src=\"/img?key=" + t1.getImageSKey().getId() + " \"/>");
                sb.append("\n");
                sb.append("<img  src=\"/img?key=" + t1.getImageLKey().getId() + " \"/>");
            }
            setFooter(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String contentType = "text/html;charset=UTF-8";
        response.setContentType(contentType);
        response.getWriter().print(sb);
    }

    private void setHeader(StringBuffer sb) {
        sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \n");
        sb.append("        \"http://www.w3.org/TR/html4/loose.dtd\">                \n");
        sb.append("<html>                                                           \n");
        sb.append("  <head></head>                                                  \n");
        sb.append("<body>                                                           \n");
    }

    private void setFooter(StringBuffer sb) {
        sb.append("</body>                                                          \n");
        sb.append("</html>                                                          \n");
    }


}
