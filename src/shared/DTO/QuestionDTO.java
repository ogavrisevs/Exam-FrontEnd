package shared.DTO;

import com.bla.laa.Container.Question;
import com.google.appengine.api.datastore.Key;
import shared.Common;

import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class QuestionDTO {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    String questionText = "";

    @Persistent
    String hash = "";

    @Persistent
    Integer questionCsddId = 0;

    public QuestionDTO(Question question) {
        this.questionText = question.getQuestionText();
        this.hash = question.getQuestionHash();
        this.questionCsddId = question.getQuestionId();
    }

    public QuestionDTO(String questionText, String hash, Integer questionCsddId) {
        this.questionText = questionText;
        this.hash = hash;
        this.questionCsddId = questionCsddId;
    }

    public Integer getQuestionCsddId() {
        return questionCsddId;
    }

    public void setQuestionCsddId(Integer questionCsddId) {
        this.questionCsddId = questionCsddId;
    }

    public QuestionDTO(Key key, String questionText, String hash) {
        this.key = key;
        this.questionText = questionText;
        this.hash = hash;
    }

    public String asObjStat(){
        StringBuffer sb = new StringBuffer();
        sb.append("new QuestionDTO(");
            sb.append("\" ");
            sb.append(Common.remQuotes(this.questionText));
            sb.append("\", ");

            sb.append("\" ");
            sb.append(this.hash);
            sb.append("\", ");

            sb.append(this.questionCsddId);
            sb.append(" ); ");

        return sb.toString();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public QuestionDTO() {
    }

    public QuestionDTO(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("QuestionDTO");
        sb.append("{key=").append(key);
        sb.append(", questionText='").append(questionText).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

