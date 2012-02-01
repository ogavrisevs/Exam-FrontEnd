package shared.DTO;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.List;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class TCaseDTO {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key key;

    @Persistent
    private Key qusetionKey;

    @Persistent
    private Key answersKey;

    @Persistent
    private Key imageLKey;

    @Persistent
    private Key imageSKey;

    @Persistent
    private List<String> paragraphs;

    // index for TCaseTypeModel
    @Persistent
    private Integer tCaseTypeIdx;

    public TCaseDTO() {
    }

    public TCaseDTO(Key qusetion, Key answers) {
        this.qusetionKey = qusetion;
        this.answersKey = answers;
    }

    public TCaseDTO(Key qusetion, Key answers, Key imageL, Key imageS) {
        this.qusetionKey = qusetion;
        this.answersKey = answers;
        this.imageLKey = imageL;
        this.imageSKey = imageS;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getQusetionKey() {
        return qusetionKey;
    }

    public void setQusetionKey(Key qusetionKey) {
        this.qusetionKey = qusetionKey;
    }

    public Key getAnswersKey() {
        return answersKey;
    }

    public void setAnswersKey(Key answersKey) {
        this.answersKey = answersKey;
    }

    public Key getImageLKey() {
        return imageLKey;
    }

    public void setImageLKey(Key imageLKey) {
        this.imageLKey = imageLKey;
    }

    public Key getImageSKey() {
        return imageSKey;
    }

    public void setImageSKey(Key imageSKey) {
        this.imageSKey = imageSKey;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public Integer gettCaseTypeIdx() {
        return tCaseTypeIdx;
    }

    public void settCaseTypeIdx(Integer tCaseTypeIdx) {
        this.tCaseTypeIdx = tCaseTypeIdx;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("TCaseDTO");
        sb.append("{key=").append(key);
        sb.append(", qusetionKey=").append(qusetionKey);
        sb.append(", answersKey=").append(answersKey);
        sb.append(", imageLKey=").append(imageLKey);
        sb.append(", imageSKey=").append(imageSKey);
        sb.append(", paragraphs=").append(paragraphs);
        sb.append(", tCaseTypeIdx=").append(tCaseTypeIdx);
        sb.append('}');
        return sb.toString();
    }
}

