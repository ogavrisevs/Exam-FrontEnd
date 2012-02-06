package com.bla.laa.client.handlers;

import com.bla.laa.client.Main;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;

import java.util.logging.Logger;

public class AnswerButtonClickHandler implements ClickHandler{
    private static final Logger logger = Logger.getLogger(AnswerButtonClickHandler.class.getName());
    Main main;

    public AnswerButtonClickHandler(Main main) {
        this.main = main;
    }

    public void onClick(ClickEvent event) {
        logger.info("answerButtonClickHandler.onClick()");
        main.removeOldStyle();
        if (main.isAnswerSelected()) {
            if (main.isSelectedCorrectAnswer()) {
                RadioButton selectedRB = main.getCorrectAnswerRadioButton();
                selectedRB.addStyleName("gwt-RadioButton-Custom-Correct");
            } else {
                RadioButton correctRB = main.getCorrectAnswerRadioButton();
                correctRB.addStyleName("gwt-RadioButton-Custom-Border-Green");

                RadioButton currentRB = main.getCurrentAnswerRadioButton();
                currentRB.addStyleName("gwt-RadioButton-Custom-InCorrect");
            }
            HorizontalPanel horizontalPanel = main.getParagrPanel();
            horizontalPanel.setVisible(true);

            main.disableAllRadioButtons();
            main.answerButton.setEnabled(false);
        }

    }
}

