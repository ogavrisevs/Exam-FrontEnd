package com.bla.laa.client;


import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.SortedMap;
import java.util.logging.Logger;

public class ParagraphMoreAsyncCallback implements AsyncCallback<SortedMap<Integer, SafeHtml>> {
    private static final Logger logger = Logger.getLogger(ParagraphMoreAsyncCallback.class.getName());
    Main main = null;

    public ParagraphMoreAsyncCallback(Main main) {
        this.main = main;
    }

    public void onFailure(Throwable caught) {
        logger.severe("ParagraphMoreAsyncCallback.onFailure(" + caught.getMessage() + ");");
    }

    public void onSuccess(SortedMap<Integer, SafeHtml> htmlMap) {
        logger.info(ParagraphMoreAsyncCallback.class.getName()+ ".onSuccess()");
        main.paragHtml.addMap(htmlMap);
        main.paragHtml. republish();

    }
}
