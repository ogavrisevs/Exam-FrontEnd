package server;

import com.google.appengine.api.datastore.Key;
import shared.DAO.*;
import shared.PMF;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class ListServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ListServlet.class.getName());
    PersistenceManager pm = PMF.get().getPersistenceManager();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("ListServlet : doGet()");
        StringBuffer sb = new StringBuffer();
        initHtmlHead(sb);
        sb.append("<table>");

        Extent exents = (Extent) pm.getExtent(TCaseDAO.class, false);
        TCaseDAO t1 = null;
        for (Object obj : exents) {
            t1 = (TCaseDAO) obj;
            QuestionDAO questionDAO = (QuestionDAO) getByKey(QuestionDAO.class, t1.getQusetionKey());
            AnswersDAO answersDAO = (AnswersDAO) getByKey(AnswersDAO.class, t1.getAnswersKey());
            ImageDAO imageLargeDAO = null;
            if (t1.getImageLKey() != null) {
                Object objL = getByKey(ImageDAO.class, t1.getImageLKey());
                if (objL != null)
                    imageLargeDAO = (ImageDAO) objL;
            }

            ImageDAO imageSmallDAO = null;
            if (t1.getImageSKey() != null) {
                Object objS = getByKey(ImageDAO.class, t1.getImageSKey());
                if (objS != null)
                    imageSmallDAO = (ImageDAO) objS;
            }

            sb.append("<tr>");
            sb.append("<td>");
            sb.append(t1.getKey().toString());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(t1.getQusetionKey().toString());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(t1.getAnswersKey().toString());
            sb.append("</td>");
            sb.append("</tr>");

            // Question
            sb.append("<tr>");
            sb.append("<td>");
            sb.append(questionDAO.getQuestionText());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(questionDAO.getQuestionCsddId());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(questionDAO.getHash());
            sb.append("</td>");
            sb.append("</tr>");

            for (AnswerDAO answerDAO : answersDAO.getAnswers()) {
                sb.append("<tr>");
                sb.append("<td> _ _ _ _                    ");
                sb.append(answerDAO.getAnswerTxt());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(answerDAO.getCorrect());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(answerDAO.getHash());
                sb.append("</td>");
                sb.append("</tr>");
            }

            if ((imageSmallDAO != null) && (imageLargeDAO != null)) {
                sb.append("<tr>");
                sb.append("<td></td>");
                sb.append("<td>");
                sb.append(imageSmallDAO.getImageCsddId());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(imageSmallDAO.getHash());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(imageSmallDAO.getKey());
                sb.append("</td>");
                sb.append("</tr>");

                sb.append("<tr>");
                sb.append("<td></td>");
                sb.append("<td>");
                sb.append(imageLargeDAO.getImageCsddId());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(imageLargeDAO.getHash());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(imageLargeDAO.getKey());
                sb.append("</td>");
                sb.append("</tr>");
            }


        }
        if (t1 == null)
            sb.append("<tr> Not Rows found !!! </tr>");
        sb.append("</table>");
        initHtmlFoot(sb);

        String contentType = "text/html;charset=UTF-8";
        response.setContentType(contentType);
        response.getWriter().print(sb);
    }

    // risky
    private Object getByKey(Class c, Key key) {
        if (key == null)
            return "no Key !";
        try {
            return pm.getObjectById(c, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public void initHtmlHead(StringBuffer sb) {
        sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"          ");
        sb.append("                      \"http://www.w3.org/TR/html4/loose.dtd\">           ");
        sb.append("<html xmlns=\"http://www.w3.org/TR/xhtml1\" lang=\"lv\">                  ");
        sb.append("  <head>                                                                  ");
        //sb.append("    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" />  ");
        sb.append("    <title></title>                                                       ");
        sb.append("  </head>                                                                 ");
        sb.append("  <body>                                                                  ");
    }

    public void initHtmlFoot(StringBuffer sb) {
        sb.append("  </body>");
        sb.append("</html>");
    }
}
