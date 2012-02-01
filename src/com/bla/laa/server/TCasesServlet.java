package com.bla.laa.server;

import com.bla.laa.server.TCases.Paragraph1;
import com.bla.laa.server.TCases.Paragraph2;
import com.bla.laa.server.TCases.Paragraph3;
import com.bla.laa.server.TCases.Paragraphs;
import com.bla.laa.shared.DAO.TCaseDAO;
import com.bla.laa.shared.PMF;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

public class TCasesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TCasesServlet.class.getName());
    public PersistenceManager pm = PMF.get().getPersistenceManager();
    String hostName = null;
    HttpServletRequest httpServletRequest = null;
    public HttpServletResponse httpServletResponse = null;

    enum urlParams {add, del, print};

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.httpServletRequest = request;
        this.httpServletResponse = response;

        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()){
            Object obj = enumeration.nextElement();
            if ( obj instanceof String){
                String paramName = (String)obj;
                if (paramName.contentEquals( urlParams.print.name()))
                    printAllTCases();
                if (paramName.contentEquals( urlParams.add.name())){
                    hostName = new Paragraphs(this).getHostName();
                    if ((hostName == null) || (hostName.isEmpty())) {
                        response.sendError(999, "host name not found !!! ");
                        return;
                    }
                    String respones = makeAllTcWithExCatch();
                    if (respones != null){
                        response.setContentType("text/html;charset=UTF-8");
                        httpServletResponse.getWriter().println(respones);
                    }
                } if (paramName.contentEquals( urlParams.del.name()))
                    delAllTCases();
            }
        }
    }

    private void delAllTCases() throws IOException {
        logger.info("delete all from TCaseDAO");
        StringBuffer sb = new StringBuffer("Delete all from TCaseDAO . . . \n   ");
        try {
            Query query = pm.newQuery(TCaseDAO.class);
            List<TCaseDAO> tCaseDAOs = (List<TCaseDAO>) query.execute();
            for (TCaseDAO tCaseDAO : tCaseDAOs){
                sb.append(tCaseDAO.toString() +" \n ");
                pm.deletePersistent(tCaseDAO);

            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            httpServletResponse.sendError(999, e.getMessage());
            return;
        }
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.getWriter().write( sb.toString());
    }

    private void printAllTCases() throws IOException {
        Query query = pm.newQuery(TCaseDAO.class);
        List<TCaseDAO> tCaseDAOs = (List<TCaseDAO>) query.execute();

        StringBuffer sb = new StringBuffer("Start printing .... \n");
        for (TCaseDAO tCaseDAO : tCaseDAOs) {
            sb.append(tCaseDAO.toString());
            sb.append("\n");
        }
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.getWriter().print(sb);
    }


    private String makeAllTcWithExCatch() {
        try {
            String rezStr = "";
            rezStr += new Paragraph1(this).makeAllTc();
            rezStr += new Paragraph2(this).makeAllTc();
            rezStr += new Paragraph3(this).makeAllTc();

            return rezStr;

        } catch (Exception e) {
            try {
                httpServletResponse.sendError(999, e.getMessage());
                return null;
            } catch (IOException e1) {
                logger.severe(e1.getMessage());
            }
        }
        return null;
    }

}
