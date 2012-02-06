package com.bla.laa.client.comp;

import com.bla.laa.client.Main;
import com.bla.laa.client.ParagraphMoreAsyncCallback;
import com.bla.laa.client.RPC;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

public class ParagHtml extends HTML{
    private static final Logger logger = Logger.getLogger(ParagHtml.class.getName());
    private SortedMap<Integer, String /*SafeHtml*/> htmlMap = new TreeMap<Integer, String /*SafeHtml*/>();
    Main main;

    public ParagHtml(Main main) {
        super();
        this.main = main;
    }

    public void addMap(SortedMap<Integer, String /*SafeHtml*/> htmlMapL) {
        logger.info(ParagHtml.class.getName() + " addMap() ");
        for (Integer keyL : htmlMapL.keySet()){
            htmlMap.put(keyL, htmlMapL.get(keyL));
        }
        logger.info("htmlMap.size() " + htmlMap.size());
    }

    public void republish() {
        logger.info(ParagHtml.class.getName() + "republish()");

        String htmlAll = "";
        for (Integer keyL : htmlMap.keySet()){
            htmlAll += htmlMap.get(keyL);
        }
        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(htmlAll) ;
        setHTML(safeHtml);

        //getOffsetHeight()

        logger.info(" paragHtml.getOffsetHeight() : "+ getOffsetHeight());
        logger.info(" popupParags.getOffsetHeight() : "+ main.popupParags.getOffsetHeight());

        // if not all space in windows ocupaid by html ask for more
        if  (getOffsetHeight() < main.popupParags.getOffsetHeight()){
            int paragId =  main.paragHtml.getLast();
            RPC.App.getInstance().getParagraphMore(paragId, new ParagraphMoreAsyncCallback(main));
        }

        main.scrollPaneParag.setScrollPosBack();
    }

    public Integer getFirst(){
        logger.info("getFirst()");
        logger.info("htmlMap.size() " + htmlMap.size());

        Integer key = 0;
        try{
            key = htmlMap.firstKey();
        }catch (Exception e){
            logger.severe(e.getMessage());
        }

        return key;
    }

    public Integer getLast(){
        logger.info("getLast()");
        Integer key = 0;
        try{
            key = htmlMap.lastKey();
        }catch (Exception e){
            logger.severe(e.getMessage());
        }
        return key;
    }
}

