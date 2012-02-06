package com.bla.laa.client.handlers;

import com.bla.laa.client.Main;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

import java.util.logging.Logger;

public class ImgSClickHandler implements ClickHandler{
    private static final Logger logger = Logger.getLogger(ImgSClickHandler.class.getName());
    Main main;

    public ImgSClickHandler(Main main) {
        this.main = main;
    }

    public void onClick(ClickEvent event) {
        logger.info("ImgSClickHandler.onClick()");
        if (!main.imageLarge.getUrl().isEmpty()) {
            Widget source = (Widget) event.getSource();
            int left = source.getAbsoluteLeft();
            int top = source.getAbsoluteTop();
            main.popupImage.setPopupPosition(left - 100, top - 118);
            main.popupImage.show();
        }


    }
}

