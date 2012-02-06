package com.bla.laa.client.comp;

import com.bla.laa.client.Main;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAutoHorizontalAlignment;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

public class ParagHtml extends HTML{
    private static final Logger logger = Logger.getLogger(ParagHtml.class.getName());
    private SortedMap<Integer, String> htmlMap = new TreeMap<Integer, String>();
    Main main;

    public ParagHtml(Main main) {
        super();
        this.main = main;
        setAutoHorizontalAlignment(HasAutoHorizontalAlignment.ALIGN_CENTER);
    }

    public void addMap(SortedMap<Integer, String > htmlMapL) {
        logger.info(ParagHtml.class.getName() + " addMap() ");
        for (Integer keyL : htmlMapL.keySet()){
            htmlMap.put(keyL, htmlMapL.get(keyL));
        }
        logger.info("htmlMap.size() " + htmlMap.size());
    }

    public void republish() {
        logger.info(ParagHtml.class.getName() + "republish("+ htmlMap.size() +")");

        String htmlAll = "";
        for (Integer keyL : htmlMap.keySet()){
            htmlAll += htmlMap.get(keyL);
        }
        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(htmlAll) ;
        setHTML(safeHtml);

        main.scrollPaneParag.setScrollPosBack();
    }

    public Integer getFirst(){
        Integer key = 0;
        try{
            key = htmlMap.firstKey();
        }catch (Exception e){
            logger.severe(e.getMessage());
        }
        logger.info("getFirst() = "+ key);
        return key;
    }

    public Integer getLast(){
        Integer key = 0;
        try{
            key = htmlMap.lastKey();
        }catch (Exception e){
            logger.severe(e.getMessage());
        }
        logger.info("getLast() = " + key);
        return key;
    }
}

