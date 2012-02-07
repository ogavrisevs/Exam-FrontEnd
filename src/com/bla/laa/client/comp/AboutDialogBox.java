package com.bla.laa.client.comp;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

import java.util.logging.Logger;

public class AboutDialogBox extends DialogBox{
    private static final Logger logger = Logger.getLogger(AboutDialogBox.class.getName());

    public static final String OK_BUTTON = "Labi";

    public AboutDialogBox() {
        logger.info(AboutDialogBox.class.getName() +".Constructor() ");

        //setAnimationEnabled(true);
        setText("par progu  sdf sdf sdfsdf sdfsd fsd fsdf sdfsdfsdfsdfsdfsd\n asfsdf sdfsf sdfds fsdfd\nsdf sdf ");
        Button okButton = new Button(OK_BUTTON);
        okButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info( AboutDialogBox.class.getName() +" okButton.addClickHandler ");
                AboutDialogBox.this.hide();
            }
        });

        setWidget(okButton);

    }

}

