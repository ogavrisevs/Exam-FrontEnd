package client;

import client.comp.AnswerRadioButton;
import client.comp.ParagLabel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import shared.Model.TCaseModel;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;


public class TcAsyncCallback implements AsyncCallback<TCaseModel> {
    private static final String IMG_BASE_URL = "/img?key=";
    private static final Logger logger = Logger.getLogger(TcAsyncCallback.class.getName());

    final Main main;


    public TcAsyncCallback(Main main) {
        this.main = main;
    }

    public void onSuccess(TCaseModel model) {
        logger.info("TcAsyncCallback.onSuccess()");
        main.initComponents();
        if (model == null) {
            main.messageLabel.setText("Questions not found !!!");
            main.messageLabel.setVisible(true);
            return;
        } else {
            main.messageLabel.setVisible(false);
        }

        // set question
        main.questionLabel.setText(model.getQuestionText());

        //rem old answers
        Iterator<Widget> iterator = main.answersVerticalPanel.iterator();
        while (iterator.hasNext()) {
            Object obj =  iterator.next();
            iterator.remove();
        }

        //add new
        for (String answer : model.getAnswers()) {
            HorizontalPanel horizontalPanel = new HorizontalPanel();

            AnswerRadioButton radioButton;
            Boolean correct  = answer.contentEquals(model.getCorectAnswer());
            if (correct)
                radioButton = new AnswerRadioButton("radioGroup", answer, correct);
            else
                radioButton = new AnswerRadioButton("radioGroup", answer);


            radioButton.addValueChangeHandler(main.radioButtonHandler);
            horizontalPanel.add(radioButton);

            if (correct){
                HorizontalPanel horizontalPanelParag = new HorizontalPanel();
                horizontalPanelParag.setVisible(false);
                Map<Integer, String> map =  model.getParagraphMap();
                for (Integer key : map.keySet()){
                    String paragName = map.get(key);
                    ParagLabel label = new ParagLabel(key, paragName);
                    label.addStyleName("gwt-LabelBlueUnderline");
                    label.addClickHandler(main.paragrLableClickHandler);
                    horizontalPanelParag.add(label);
                }
                horizontalPanel.add(horizontalPanelParag);
            }
            main.answersVerticalPanel.add(horizontalPanel);
        }

        main.imageSmall.setUrl("");
        main.imageLarge.setUrl("");
        if ((model.getImgSurl() != null) && (model.getImgSurl() != 0)) {
            main.imageSmall.setUrl(IMG_BASE_URL + model.getImgSurl());
            main.imageSmall.setVisible(true);
            main.imageLarge.setUrl(IMG_BASE_URL + model.getImgLurl());
            main.imageLarge.setVisible(true);
        } else {
            main.imageSmall.setVisible(false);
            main.imageLarge.setVisible(false);
        }

        //this.corectAnswer.setText(model.getCorectAnswer());
    }

    public void onFailure(Throwable throwable) {
        logger.severe("TcAsyncCallback.onFailure("+ throwable.toString() +")");
        main.initComponents();
        main.messageLabel.setText("Error Loading Questions !!!");
        main.messageLabel.setVisible(true);
    }
}
