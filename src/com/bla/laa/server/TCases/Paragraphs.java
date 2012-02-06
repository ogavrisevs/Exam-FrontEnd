package com.bla.laa.server.TCases;

import com.bla.laa.server.TCasesServlet;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class Paragraphs {
    private static final Logger logger = Logger.getLogger(Paragraphs.class.getName());
    TCasesServlet tCasesServlet = null;
    static String hostName = null;

    public Paragraphs(TCasesServlet tCasesServlet) {
        this.tCasesServlet = tCasesServlet;
        hostName = getHostName();
    }

    public Blob getImage(String imgUrl) {
        try {
            URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
            HTTPResponse fetchResponse = fetchService.fetch(new URL(hostName + imgUrl));
            byte[] bytes = fetchResponse.getContent();
            return new Blob(bytes);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            try {
                tCasesServlet.httpServletResponse.sendError(999, e.getMessage());
            } catch (IOException e1) {
                logger.severe(e1.getMessage());
            }
        }
        return null;
    }

    public String getHostName() {
        String hostName = null;
        String environment = System.getProperty("com.google.appengine.runtime.environment");
        if (environment.contentEquals("Production")) {
            String applicationId = System.getProperty("com.google.appengine.application.id");
            String version = System.getProperty("com.google.appengine.application.version");
            hostName = "http://" + version + "." + applicationId + ".appspot.com/";
        } else if (environment.contentEquals("Development"))
            hostName = "http://localhost:8080/";

        logger.info("Host Name : " + hostName);
        return hostName;
    }


}

