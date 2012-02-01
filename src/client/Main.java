package client;

import client.comp.AnswerRadioButton;
import client.comp.ParagLabel;
import client.comp.ScrollPanelUpDown;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;
import shared.Model.TCaseTypeModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

//import com.google.gwt.user.client.ui.*;


public class Main implements EntryPoint {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private static final String CHK_ANSW_BUT = "Parbaudīt atbildi";
    private static final String NEXT_QUESTION_BUT = "Nākamais jautājums";
    private static final String QUESTION_TYPES = "Temati : ";
    private static final String CUSTOM_STYLE_NAME = "-Custom-";
    private static final String MAIL = "codon.dev@gmail.com";
    private static final String NEXT_PARAG_BUT = "Parādīt nākamo punktu";

    final HorizontalPanel menuHorizontalPanel = new HorizontalPanel();
    final VerticalPanel textVerticalPanel = new VerticalPanel();
    final VerticalPanel answersVerticalPanel = new VerticalPanel();
    final HorizontalPanel buttonHorizontalPanel = new HorizontalPanel();

    final Label messageLabel = new Label("");
    final Label questionLabel = new Label("");
    //final Label correctAnswer = new Label("");
    final Label questionTypeLable = new Label(QUESTION_TYPES);

    final Button answerButton = new Button(CHK_ANSW_BUT);
    final Button nextButton = new Button(NEXT_QUESTION_BUT);
    final Button nextParagButton = new Button(NEXT_PARAG_BUT);

    final Image imageLarge = new Image();
    final Image imageSmall = new Image();

    final ListBox tCaseTypeListBox = new ListBox(false);

    final PopupPanel imagePopup = new PopupPanel(true);
    PopupPanel paragPopup = null;

    ScrollPanelUpDown paragScrollPane = null;

    //--- handlers
    ValueChangeHandler radioButtonHandler = null;
    ClickHandler answerButtonClickHandler = null;
    ClickHandler nextButtonClickHandler = null;
    ClickHandler imgLClickHandler = null;
    ClickHandler imgSClickHandler = null;
    ChangeHandler tCTListBoxChangeHandler = null;
    ClickHandler paragrLableClickHandler = null;
    ClickHandler paragPopUpClickHandler = null;
    ScrollHandler scrollHandler = null;

    //data
    List<TCaseTypeModel> tCaseTypeModels = new ArrayList<TCaseTypeModel>();

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

        menuHorizontalPanel.add(questionTypeLable);
        menuHorizontalPanel.add(tCaseTypeListBox);
        RootPanel.get("menu").add(menuHorizontalPanel);

        textVerticalPanel.add(messageLabel);
        textVerticalPanel.add(questionLabel);
        textVerticalPanel.add(answersVerticalPanel);
        RootPanel.get("text").add(textVerticalPanel);

        RootPanel.get("pic").add(imageSmall);

        buttonHorizontalPanel.add(answerButton);
        buttonHorizontalPanel.add(nextButton);
        RootPanel.get("butt").add(buttonHorizontalPanel);

    }

    public void initComponents() {
        answerButton.setEnabled(false);
        imageSmall.setVisible(false);
        imageLarge.setVisible(false);

        messageLabel.setText("");
        questionLabel.setText("");

        imagePopup.setAnimationEnabled(true);
        imagePopup.setWidget(imageLarge);

        if (paragPopup == null){
            paragPopup = new PopupPanel(false);
            paragPopup.setAnimationEnabled(true);
            paragPopup.setWidth("700");
            paragPopup.setGlassEnabled(true);
            paragPopup.setHeight("400");
            paragPopup.setTitle("Title");

            paragScrollPane = new ScrollPanelUpDown();
            paragScrollPane.setAlwaysShowScrollBars(false);
            paragScrollPane.getMinimumHorizontalScrollPosition();
            paragScrollPane.setWidth("680");

            paragPopup.add(paragScrollPane);
        }
        paragPopup.hide();

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
        textVerticalPanel.setWidth("800");
    }

    public void setHandlers() {
        answerButton.addClickHandler(answerButtonClickHandler);
        nextButton.addClickHandler(nextButtonClickHandler);
        imageLarge.addClickHandler(imgLClickHandler);
        imageSmall.addDomHandler(imgSClickHandler, ClickEvent.getType());

        paragScrollPane.addHandler(main.paragPopUpClickHandler, ClickEvent.getType());
        paragScrollPane.addScrollHandler(scrollHandler);
        paragPopup.addHandler(main.paragPopUpClickHandler, ClickEvent.getType());
    }

    public void initHandlers() {

        tCTListBoxChangeHandler = new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                Integer itmeIndx = getTCaseTypeIdx();
                logger.info("tCTListBoxChangeHandler.onChange("+ itmeIndx +")");
                RPC.App.getInstance().getTC( itmeIndx , new TcAsyncCallback(main));
            }
        };

        radioButtonHandler = new ValueChangeHandler() {
            public void onValueChange(ValueChangeEvent e) {
                logger.info("radioButtonHandler.onValueChange()");
                answerButton.setEnabled(true);
            }
        };

        answerButtonClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info("answerButtonClickHandler.onClick()");
                removeOldStyle();
                if (isAnswerSelected()) {
                    if (isSelectedCorrectAnswer()) {
                        RadioButton selectedRB = getCorrectAnswerRadioButton();
                        selectedRB.addStyleName("gwt-RadioButton-Custom-Correct");
                    } else {
                        RadioButton correctRB = getCorrectAnswerRadioButton();
                        correctRB.addStyleName("gwt-RadioButton-Custom-Border-Green");

                        RadioButton currentRB = getCurrentAnswerRadioButton();
                        currentRB.addStyleName("gwt-RadioButton-Custom-InCorrect");
                    }
                    HorizontalPanel horizontalPanel = getParagrPanel();
                    horizontalPanel.setVisible(true);

                    disableAllRadioButtons();
                    answerButton.setEnabled(false);
                }
            }
        };

        imgLClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info("imgLClickHandler.onClick()");
                if (!imageLarge.getUrl().isEmpty())
                    imagePopup.hide();
            }
        };

        imgSClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info("imgSClickHandler.onClick()");
                if (!imageLarge.getUrl().isEmpty()) {
                    Widget source = (Widget) event.getSource();
                    int left = source.getAbsoluteLeft();
                    int top = source.getAbsoluteTop();
                    imagePopup.setPopupPosition(left - 100, top - 118);
                    imagePopup.show();
                }
            }
        };

        nextButtonClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                Integer itmeIdx = getTCaseTypeIdx();
                logger.info("nextButtonClickHandler.onClick("+ itmeIdx +")");
                RPC.App.getInstance().getTC(itmeIdx, new TcAsyncCallback(main));
            }
        };

        paragrLableClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info("paragrLableClickHandler.ClickHandler()");
                ParagLabel paragLabel = (ParagLabel) event.getSource();
                Widget source = (Widget) event.getSource();
                paragPopup.setPopupPosition( 100, 20);
                RPC.App.getInstance().getParagraph( paragLabel.getParagraphId(), new ParagraphAsyncCallback(main));
            }
        };

        paragPopUpClickHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info("paragPopUpClickHandler.ClickHandler()");
                paragPopup.hide();
            }
        };

        scrollHandler = new ScrollHandler() {
            public void onScroll(ScrollEvent event) {
                logger.info("ScrollHandler().onScroll();");
                Widget source = (Widget) event.getSource();
                if (source instanceof ScrollPanel){
                    ScrollPanelUpDown scrollPanel =  (ScrollPanelUpDown) source;

                    logger.info(String.valueOf(scrollPanel.getVerticalScrollPosition()));
                    logger.info(String.valueOf(scrollPanel.getScrollPosition()));

                    ScrollPanelUpDown.Direction direction = scrollPanel.getDirection();
                    logger.info(String.valueOf(direction.name()));
                }
            }
        };
    }

    private HorizontalPanel getParagrPanel(){
        logger.info("getParagrPanel()");
        Iterator<Widget> iterator = answersVerticalPanel.iterator();
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

        Iterator<Widget> iterator = answersVerticalPanel.iterator();
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
        Iterator iterator = main.paragPopup.iterator();
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
