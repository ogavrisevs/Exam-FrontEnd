package client.comp;

import com.google.gwt.user.client.ui.Label;

public class ParagLabel extends Label{

    private Integer paragraphId;
    private String paragName;

    public ParagLabel(Integer paragId, String paragName) {
        super(paragName);
        this.paragraphId = paragId;
        this.paragName = paragName;
    }

    public Integer getParagraphId() {
        return paragraphId;
    }

    public String getParagName() {
        return paragName;
    }
}
