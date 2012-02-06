package com.bla.laa.shared.DAO;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.client.rpc.IsSerializable;

import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ParagraphDAO implements IsSerializable  {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key key;

    @Persistent
    String paragName;

    @Persistent
    Text paragText = null;

    @Persistent
    Integer id = null;

    static Integer counter = 0;

    public ParagraphDAO() {
    }

    public ParagraphDAO(String paragName, String paragText) {
        this.paragName = paragName;
        this.paragText = new Text(paragText);
        this.id = ++counter;
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

    public Integer getNr() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParagraphDAO that = (ParagraphDAO) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (paragName != null ? !paragName.equals(that.paragName) : that.paragName != null) return false;
        if (paragText != null ? !paragText.equals(that.paragText) : that.paragText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (paragName != null ? paragName.hashCode() : 0);
        result = 31 * result + (paragText != null ? paragText.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParagraphDTO{" +
                "key=" + key +
                ", nr=" + id +
                ", paragName='" + paragName + '\'' +
                ", paragText=" + paragText +
                '}';
    }
}


