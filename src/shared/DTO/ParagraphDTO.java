/*
 * @(#)ParagraphDTO.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package shared.DTO;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.client.rpc.IsSerializable;

import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ParagraphDTO implements IsSerializable  {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key key;

    @Persistent
    String paragName;

    @Persistent
    Text paragText = null;

    @Persistent
    Integer nr = null;

    static Integer counter = 0;

    public ParagraphDTO() {
    }

    public ParagraphDTO(String paragName, String paragText) {
        this.paragName = paragName;
        this.paragText = new Text(paragText);
        nr = counter++;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getParagName() {
        return paragName;
    }

    public void setParagName(String paragName) {
        this.paragName = paragName;
    }

    public String getParagText() {
        return paragText.getValue();
    }

    public void setParagText(String paragText) {
        this.paragText = new Text(paragText);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParagraphDTO that = (ParagraphDTO) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (nr != null ? !nr.equals(that.nr) : that.nr != null) return false;
        if (paragName != null ? !paragName.equals(that.paragName) : that.paragName != null) return false;
        if (paragText != null ? !paragText.equals(that.paragText) : that.paragText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (paragName != null ? paragName.hashCode() : 0);
        result = 31 * result + (paragText != null ? paragText.hashCode() : 0);
        result = 31 * result + (nr != null ? nr.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParagraphDTO{" +
                "key=" + key +
                ", paragName='" + paragName + '\'' +
                ", paragText=" + paragText +
                ", nr=" + nr +
                '}';
    }
}

