package com.bla.laa.client;


import com.bla.laa.client.comp.ParagHtml;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.logging.Logger;

public class ParagraphAsyncCallback implements AsyncCallback<SortedMap<Integer, SafeHtml>> {
    private static final Logger logger = Logger.getLogger(ParagraphAsyncCallback.class.getName());
    Main main = null;

    public ParagraphAsyncCallback(Main main) {
        this.main = main;
    }

    public void onFailure(Throwable caught) {
        logger.severe("ParagraphAsyncCallback.onFailure("+ caught.getMessage() + ");");
        main.clearParagPopUp();
        main.paragPopup.hide();
        main.messageLabel.setText("Paragraph Not found !!!");
        main.messageLabel.setVisible(true);
    }

    public void onSuccess(SortedMap<Integer, SafeHtml> htmlMap) {
        Integer key = (Integer) htmlMap.keySet().toArray()[0];
        logger.info("ParagraphAsyncCallback.onSuccess("+ key +")");

        ParagHtml html = new ParagHtml();
        html.setHTML(htmlMap.get(key));
        html.addMap(htmlMap);
        html.setAutoHorizontalAlignment(HTML.ALIGN_JUSTIFY);
        html.addClickHandler(main.paragPopUpClickHandler);

        if (this.main.paragScrollPane.getWidget() != null){
            Iterator iterator = this.main.paragScrollPane.iterator();
            while (iterator.hasNext()){
                iterator.next();
                iterator.remove();
            }
        }

        //leave reference on main
        this.main.paragHtml = html;
        this.main.paragScrollPane.add(html);
        this.main.paragPopup.show();

    }
}
