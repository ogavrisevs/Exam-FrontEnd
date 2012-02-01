package shared.DAO;

import com.bla.laa.Container.Answer;
import com.google.appengine.api.datastore.Key;
import shared.Common;

import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class AnswerDAO {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key key;

    @Persistent
    String AnswerTxt = "";

    @Persistent
    Integer answerCsddId = 0;

    @Persistent
    String hash = "";

    @Persistent
    Boolean isCorrect = Boolean.FALSE;

    public AnswerDAO(Answer answer) {
        this.AnswerTxt = answer.getAnswerText();
        this.answerCsddId = answer.getAnswerCsddId();
        this.hash = answer.getAnswerHash();
        this.isCorrect = answer.isCorrect();
    }

    public AnswerDAO(String answerTxt, Integer answerCsddId, String hash, Boolean correct) {
        AnswerTxt = answerTxt;
        this.answerCsddId = answerCsddId;
        this.hash = hash;
        isCorrect = correct;
    }

    public AnswerDAO() {
    }

    public AnswerDAO(String answerTxt) {
        AnswerTxt = answerTxt;
    }

    public String asObjStat(){
        StringBuffer sb = new StringBuffer();
        sb.append("answerDTOs.add(");
            sb.append("new AnswerDAO(");

            sb.append("\" ");
            sb.append(Common.remQuotes(this.getAnswerTxt()) );
            sb.append("\" , ");

            sb.append(this.getAnswerCsddId() );
            sb.append(" , ");

            sb.append("\" ");
            sb.append(this.getHash() );
            sb.append("\", ");

            sb.append(" ");
            sb.append(this.isCorrect );
            sb.append(" ");

            sb.append(")");
        sb.append(");");

        return sb.toString();
    }

    public String getAnswerTxt() {
        return AnswerTxt;
    }

    public Key getKey() {
        return key;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public void setAnswerTxt(String answerTxt) {
        AnswerTxt = answerTxt;
    }

    public Integer getAnswerCsddId() {
        return answerCsddId;
    }

    public void setAnswerCsddId(Integer answerCsddId) {
        this.answerCsddId = answerCsddId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("AnswerDAO");
        sb.append("{key=").append(key);
        sb.append(", AnswerTxt='").append(AnswerTxt).append('\'');
        sb.append(", isCorrect=").append(isCorrect);
        sb.append('}');
        return sb.toString();
    }
}

