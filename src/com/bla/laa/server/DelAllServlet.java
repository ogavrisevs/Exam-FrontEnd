package com.bla.laa.server;

import com.bla.laa.shared.DAO.TCaseDAO;
import com.bla.laa.shared.PMF;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class DelAllServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DelAllServlet.class.getName());
    PersistenceManager pm = PMF.get().getPersistenceManager();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("delete all from TCaseDAO");
        StringBuffer sb = new StringBuffer("Start of deliting ... \n");
        try {
            Extent exents = (Extent) pm.getExtent(TCaseDAO.class, false);
            for (Object obj : exents) {
                sb.append("del : " + ((TCaseDAO) obj).getKey());
                pm.deletePersistent(obj);
                sb.append("  = ok \n");
            }
        } catch (Exception e) {
            sb.append("\n Del err : " + e.getMessage());
            e.printStackTrace();
        }
        response.getWriter().println(sb);
    }
}
