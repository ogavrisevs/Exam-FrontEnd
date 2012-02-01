package client.comp;


import com.google.gwt.user.client.ui.RadioButton;

public class AnswerRadioButton extends RadioButton{

    private String answer = "";
    private Boolean isCorrect = Boolean.FALSE;

    public AnswerRadioButton(String group, String labelName) {
        super(group, labelName);
        this.answer = labelName;
    }

    public AnswerRadioButton(String group, String labelName, Boolean isCorrect) {
        super(group, labelName);
        this.answer = labelName;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public Boolean isCorrect() {
        return isCorrect;
    }
}
