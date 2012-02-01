package shared.Model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class TCaseTypeModel implements IsSerializable, Comparable<TCaseTypeModel> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String key;

    @Persistent
    private Integer typeIdx;

    @Persistent
    private String typeName;

    public TCaseTypeModel() {
    }

    public TCaseTypeModel(Integer typeIdx, String typeName) {
        this.typeIdx = typeIdx;
        this.typeName = typeName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getTypeIdx() {
        return typeIdx;
    }

    public void setTypeIdx(Integer typeIdx) {
        this.typeIdx = typeIdx;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCaseTypeModel that = (TCaseTypeModel) o;

        if (typeIdx != null ? !typeIdx.equals(that.typeIdx) : that.typeIdx != null) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeIdx != null ? typeIdx.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TCaseTypeModel{" +
                "key=" + key +
                ", typeIdx=" + typeIdx +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    public int compareTo(TCaseTypeModel o) {
        Integer itmeIdx = o.getTypeIdx();
        return this.getTypeIdx().compareTo(itmeIdx);
    }
}
