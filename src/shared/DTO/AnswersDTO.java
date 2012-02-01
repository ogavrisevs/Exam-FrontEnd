package shared.DTO;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.List;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class AnswersDTO {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key key;

    @Persistent
    List<AnswerDTO> answers;

    public AnswersDTO() {
    }

    public AnswersDTO(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("AnswersDTO");
        sb.append("{key=").append(key);
        sb.append(", answers=").append(answers);
        sb.append('}');
        return sb.toString();
    }
}

