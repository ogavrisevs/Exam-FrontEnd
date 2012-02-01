package shared.DTO;


import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ImageDTO {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key key;

    @Persistent
    private Blob image;

    private String imagePathName;

    @Persistent
    private Integer imageCsddId = -1;

    @Persistent
    private String hash = "";

    public ImageDTO() {
    }

    public ImageDTO(Blob image, Integer imageCsddId, String imageHash) {
        this.image = image;
        this.imageCsddId = imageCsddId;
        this.hash = imageHash;
    }
    public ImageDTO(String filePath, Integer imageCsddId, String imageHash) {
        this.imagePathName = filePath;
        this.imageCsddId = imageCsddId;
        this.hash = imageHash;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public byte[] getImage() {
        return image.getBytes();
    }

    public void setImage(byte[] image) {
        this.image = new Blob(image);
    }

    public Integer getImageCsddId() {
        return imageCsddId;
    }

    public void setImageCsddId(Integer imageCsddId) {
        this.imageCsddId = imageCsddId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String asObjStat(){
        StringBuffer sb = new StringBuffer();
        sb.append("new ImageDTO(");

            sb.append(" getImage(\"");
            sb.append(this.imagePathName);
            sb.append("\") , ");

            sb.append(this.imageCsddId );
            sb.append(" , ");

            sb.append("\" ");
            sb.append(this.getHash() );
            sb.append("\" ");

        sb.append(");");
        return sb.toString();
    }
}

