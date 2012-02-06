package com.bla.laa.client;

import com.bla.laa.client.comp.AnswerRadioButton;
import com.bla.laa.client.comp.ParagHtml;
import com.bla.laa.client.comp.ParagLabel;
import com.bla.laa.client.comp.ScrollPanelUpDown;
import com.bla.laa.client.handlers.AnswerButtonClickHandler;
import com.bla.laa.client.handlers.ImgSClickHandler;
import com.bla.laa.client.handlers.VertScrollHandler;
import com.bla.laa.shared.Model.TCaseTypeModel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

//import com.google.gwt.user.com.bla.laa.client.ui.*;


public class Main implements EntryPoint {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private static final String CHK_ANSW_BUT = "Parbaudīt atbildi";
    private static final String NEXT_QUESTION_BUT = "Nākamais jautājums";
    private static final String QUESTION_TYPES = "Temati : ";
    private static final String CUSTOM_STYLE_NAME = "-Custom-";
    private static final String MAIL = "codon.dev@gmail.com";

    final HorizontalPanel panelMenuHorizontal = new HorizontalPanel();
    final VerticalPanel panelTextVertical = new VerticalPanel();
    final VerticalPanel panelAnswersVertical = new VerticalPanel();
    final HorizontalPanel panelButtonHorizontal = new HorizontalPanel();

    final Label messageLabel = new Label("");
    final Label questionLabel = new Label("");
    final Label questionTypeLable = new Label(QUESTION_TYPES);

    public final Button answerButton = new Button(CHK_ANSW_BUT);
    final Button nextButton = new Button(NEXT_QUESTION_BUT);

    public final Image imageLarge = new Image();
    final Image imageSmall = new Image();

    final ListBox tCaseTypeListBox = new ListBox(false);

    public PopupPanel popupImage = new PopupPanel(true);
    public PopupPanel popupParags = null;

    public ScrollPanelUpDown scrollPaneParag = null;

    //--- handlers
    ValueChangeHandler radioButtonHandler = null;
    ClickHandler nextButtonClickHandler = null;
    ClickHandler imgLClickHandler = null;
    //ClickHandler imgSClickHandler = null;
    ChangeHandler tCTListBoxChangeHandler = null;
    ClickHandler paragrLableClickHandler = null;
    ClickHandler paragPopUpClickHandler = null;

    //data
    List<TCaseTypeModel> tCaseTypeModels = new ArrayList<TCaseTypeModel>();

    // paragHtml bloc on top of scroll panel
    public ParagHtml paragHtml;

    Main main;

    public Main() {
    }

    public void onModuleLoad() {
        logger.info("Main.onModuleLoad()");
        this.main = this;

        initHandlers();
        initComponents();
        setHandlers();

        // load first
        RPC.App.getInstance().getTC( -1 , new TcAsyncCallback(this.main));

        panelMenuHorizontal.add(questionTypeLable);
        panelMenuHorizontal.add(tCaseTypeListBox);
        RootPanel.get("menu").add(panelMenuHorizontal);

        panelTextVertical.add(messageLabel);
        panelTextVertical.add(questionLabel);
        panelTextVertical.add(panelAnswersVertical);
        RootPanel.get("text").add(panelTextVertical);

        RootPanel.get("pic").add(imageSmall);

        panelButtonHorizontal.add(answerButton);
        panelButtonHorizontal.add(nextButton);
        RootPanel.get("butt").add(panelButtonHorizontal);
    }

    public void initComponents() {
        answerButton.setEnabled(false);
        imageSmall.setVisible(false);
        imageLarge.setVisible(false);

        messageLabel.setText("");
        questionLabel.setText("");

        popupImage.setAnimationEnabled(true);
        popupImage.setWidget(imageLarge);

        if (popupParags == null){
            popupParags = new PopupPanel(false);
            popupParags.setAnimationEnabled(true);
            popupParags.setWidth("700");
            popupParags.setGlassEnabled(true);
            popupParags.setHeight("400");
            //popupParags.setTitle("Title");

            scrollPaneParag = new ScrollPanelUpDown(this);
            scrollPaneParag.setAlwaysShowScrollBars(true);
            scrollPaneParag.setWidth("680");
            popupParags.add(scrollPaneParag);
        }
        popupParags.hide();

        messageLabel.setVisible(false);
        String style = messageLabel.getStyleName();
        if (containsStyle(messageLabel, "gwt-Lable-ErrorMessage"))
            messageLabel.addStyleName("gwt-Lable-ErrorMessage");

        if (tCaseTypeListBox.getItemCount() == 0){
            tCaseTypeListBox.clear();
            tCaseTypeListBox.addItem("loading....");
            tCaseTypeListBox.setWidth("200");
            tCaseTypeListBox.setVisibleItemCount(1);
            RPC.App.getInstance().getTCaseTypes(new TcTypeAsyncCallback(this.main));
            tCaseTypeListBox.addChangeHandler(tCTListBoxChangeHandler);
        }
        panelTextVertical.setWidth("800");
    }

    public void setHandlers() {
        answerButton.addClickHandler(new AnswerButtonClickHandler(this));
        nextButton.addClickHandler(nextButtonClickHandler);
        imageLarge.addClickHandler(imgLClickHandler);
        imageSmall.addDomHandler(new ImgSClickHandler(this), ClickEvent.getType());

        scrollPaneParag.addHandler(main.paragPopUpClickHandler, ClickEvent.getType());
        scrollPaneParag.addScrollHandler(new VertScrollHandler(this));
        popupParags.addHandler(main.paragPopUpClickHandler, ClickEvent.getType());
    }

    public void initHandlers() {

        tCTListBoxChangeHandler = new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                Integer itmeIndx = getTCaseTypeIdx();
                logger.info("tCTListBoxChangeHandler.onChange("+ itmeIndx +")");
                RPC.App.getInstance().getTC(itmeIndx, new TcAsyncCallback(main));
            }
        };

        radioButtonHandler = new ValueChangeHandler() {
            public void onValueChange(ValueChangeEvent e) {
                logger.info("radioButtonHandler.onValueChange()");
                answerButton.setEnabled(true);
            }
        };

        imgLClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info("imgLClickHandler.onClick()");
                if (!imageLarge.getUrl().isEmpty())
                    popupImage.hide();
            }
        };

        nextButtonClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                Integer itmeIdx = getTCaseTypeIdx();
                logger.info("nextButtonClickHandler.onClick(" + itmeIdx + ")");
                RPC.App.getInstance().getTC(itmeIdx, new TcAsyncCallback(main));
            }
        };

        paragrLableClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info("paragrLableClickHandler.ClickHandler()");
                ParagLabel paragLabel = (ParagLabel) event.getSource();
                Widget source = (Widget) event.getSource();
                popupParags.setPopupPosition(100, 20);
                RPC.App.getInstance().getParagraph(paragLabel.getParagraphId(), new ParagraphAsyncCallback(main));
            }
        };

        paragPopUpClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info("paragPopUpClickHandler.ClickHandler()");
                popupParags.hide();
            }
        };
    }

    public HorizontalPanel getParagrPanel(){
        logger.info("getParagrPanel()");
        Iterator<Widget> iterator = panelAnswersVertical.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof  HorizontalPanel){
                HorizontalPanel horizontalPanel = (HorizontalPanel) obj;
                Iterator<Widget> iterator2 = horizontalPanel.iterator();
                while (iterator2.hasNext()) {
                    Object obj2 = iterator2.next();
                    if (obj2 instanceof HorizontalPanel)
                        return (HorizontalPanel) obj2;
                }
            }
       }
       return null;
    }

    private List<AnswerRadioButton> getAnswerRadioButtons(){
        List<AnswerRadioButton> radioButtons = new ArrayList<AnswerRadioButton>();

        Iterator<Widget> iterator = panelAnswersVertical.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof  HorizontalPanel){
                HorizontalPanel horizontalPanel = (HorizontalPanel) obj;
                Iterator<Widget> iterator2 = horizontalPanel.iterator();
                while (iterator2.hasNext()) {
                    Object obj2 = iterator2.next();
                    if (obj2 instanceof AnswerRadioButton){
                        AnswerRadioButton radioButton = (AnswerRadioButton) obj2;
                        radioButtons.add(radioButton);
                    }
                }
            }
       }
       return radioButtons;
    }

    public Boolean isAnswerSelected() {
        for(RadioButton radioButton : getAnswerRadioButtons()){
            if (radioButton.getValue())
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean isSelectedCorrectAnswer() {
        for(AnswerRadioButton radioButton : getAnswerRadioButtons()){
            if (radioButton.getValue())
                if (radioButton.isCorrect())
                    return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public RadioButton getCorrectAnswerRadioButton() {
        for(AnswerRadioButton radioButton : getAnswerRadioButtons()){
            if (radioButton.isCorrect())
                return radioButton;
        }
        return null;
    }

    public RadioButton getCurrentAnswerRadioButton() {
        for(RadioButton radioButton : getAnswerRadioButtons()){
            if (radioButton.getValue())
                return radioButton;
        }
        return null;
    }

    public void removeOldStyle() {
        for(RadioButton radioButton : getAnswerRadioButtons()){
            String styleName = radioButton.getStyleName();
            if ((styleName != null) && (styleName.length() != 0)) {
                for (String style : styleName.split(" "))
                    if (style.contains(CUSTOM_STYLE_NAME)) {
                        radioButton.removeStyleName(style);
                        logger.info("remove style : " + style);
                    }
            }
        }
    }

    public void disableAllRadioButtons() {
        for(RadioButton radioButton : getAnswerRadioButtons()){
            radioButton.setEnabled(false);
        }
    }

    public Boolean containsStyle(Widget widget, String chkStyleName) {
        String styleName = widget.getStyleName();
        if (((styleName == null) || (styleName.isEmpty())) || (!styleName.contains(chkStyleName)))
            return true;
        else
            return false;
    }

    public void clearParagPopUp(){
        Iterator iterator = main.popupParags.iterator();
        while (iterator.hasNext()){
            Object obj =  iterator.next();
            if ((obj instanceof HTML) || (obj instanceof Label) || (obj instanceof ScrollPanel))
               iterator.remove();
        }

    }

        public Integer getTCaseTypeIdx(){
            String selectedTCaseType = tCaseTypeListBox.getItemText(tCaseTypeListBox.getSelectedIndex());
            Integer itmeIdx =  -1;
            for (int idx = 0; idx < tCaseTypeModels.size(); idx++ ){
                TCaseTypeModel tCaseTypeModel = tCaseTypeModels.get(idx);
                if (tCaseTypeModel.getTypeName().contentEquals(selectedTCaseType))
                    itmeIdx = tCaseTypeModel.getTypeIdx();
            }
            return itmeIdx;
        }



}
