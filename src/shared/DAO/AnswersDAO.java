package shared.DAO;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.List;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class AnswersDAO {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key key;

    @Persistent
    List<AnswerDAO> answers;

    public AnswersDAO() {
    }

    public AnswersDAO(List<AnswerDAO> answers) {
        this.answers = answers;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public List<AnswerDAO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDAO> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("AnswersDAO");
        sb.append("{key=").append(key);
        sb.append(", answers=").append(answers);
        sb.append('}');
        return sb.toString();
    }
}

