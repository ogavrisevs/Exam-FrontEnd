package shared.Model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Model for passing to client side by RPC
 */
public class TCaseModel implements IsSerializable {
    private static final Logger logger = Logger.getLogger(TCaseModel.class.getName());


    protected String questionText = "";
    protected List<String> answers = new ArrayList<String>();
    protected String corectAnswer = "";
    protected Map<Integer, String> paragraphMap = new HashMap<Integer, String> ();
    protected Long imgLurl = new Long(0);
    protected Long imgSurl = new Long(0);

    public TCaseModel() {
        logger.info("construct TCaseModel()");
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void addAnswers(String answer) {
        this.answers.add(answer);
    }

    public Long getImgLurl() {
        return imgLurl;
    }

    public void setImgLurl(Long imgLurl) {
        this.imgLurl = imgLurl;
    }

    public Long getImgSurl() {
        return imgSurl;
    }

    public void setImgSurl(Long imgSurl) {
        this.imgSurl = imgSurl;
    }

    public String getCorectAnswer() {
        return corectAnswer;
    }

    public void setCorectAnswer(String corectAnswer) {
        this.corectAnswer = corectAnswer;
    }

    public Map<Integer, String> getParagraphMap() {
        return paragraphMap;
    }

    public void addParagraphDTOs(Integer intKey, String strParagName) {
        paragraphMap.put(intKey, strParagName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCaseModel that = (TCaseModel) o;

        if (answers != null ? !answers.equals(that.answers) : that.answers != null) return false;
        if (corectAnswer != null ? !corectAnswer.equals(that.corectAnswer) : that.corectAnswer != null) return false;
        if (imgLurl != null ? !imgLurl.equals(that.imgLurl) : that.imgLurl != null) return false;
        if (imgSurl != null ? !imgSurl.equals(that.imgSurl) : that.imgSurl != null) return false;
        if (paragraphMap != null ? !paragraphMap.equals(that.paragraphMap) : that.paragraphMap != null) return false;
        if (questionText != null ? !questionText.equals(that.questionText) : that.questionText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionText != null ? questionText.hashCode() : 0;
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (corectAnswer != null ? corectAnswer.hashCode() : 0);
        result = 31 * result + (paragraphMap != null ? paragraphMap.hashCode() : 0);
        result = 31 * result + (imgLurl != null ? imgLurl.hashCode() : 0);
        result = 31 * result + (imgSurl != null ? imgSurl.hashCode() : 0);
        return result;
    }
}

