package com.bla.laa.client.handlers;

import com.bla.laa.client.Main;
import com.bla.laa.client.ParagraphMoreAsyncCallback;
import com.bla.laa.client.RPC;
import com.bla.laa.client.comp.ScrollPanelUpDown;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.logging.Logger;


public class VertScrollHandler implements ScrollHandler {
    private static final Logger logger = Logger.getLogger(VertScrollHandler.class.getName());
    Main main;

    public VertScrollHandler(Main main) {
        this.main = main;
    }

    public void onScroll(ScrollEvent event) {
        logger.info("ScrollHandler().onScroll();");
        Widget source = (Widget) event.getSource();
        if (source instanceof ScrollPanel){
            ScrollPanelUpDown scrollPanel =  (ScrollPanelUpDown) source;

            Integer curPoz = scrollPanel.getVerticalScrollPosition();
            Integer minPoz = scrollPanel.getMinimumVerticalScrollPosition();
            Integer maxPoz = scrollPanel.getMaximumVerticalScrollPosition();

            scrollPanel.printPoz();
            ScrollPanelUpDown.Direction direction = scrollPanel.getDirection();

            if ((curPoz == minPoz) || (curPoz == maxPoz)){
                Integer paragId = 0;
                if ((ScrollPanelUpDown.Direction.DOWN == direction) || (curPoz == maxPoz)){
                    paragId = main.paragHtml.getLast();
                    paragId++;
                } else if ((ScrollPanelUpDown.Direction.UP == direction) || (curPoz == minPoz)) {
                    paragId = main.paragHtml.getFirst();
                    paragId--;
                }else if (ScrollPanelUpDown.Direction.NO_CHANGE == direction){
                    logger.severe( direction.name());
                }

                logger.info(String.valueOf(direction));
                RPC.App.getInstance().getParagraphMore(paragId, new ParagraphMoreAsyncCallback(main));
            }
        }
    }
}
