package com.bla.laa.client.comp;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;

import java.util.logging.Logger;

public class AboutDialogBox extends DialogBox{
    private static final Logger logger = Logger.getLogger(AboutDialogBox.class.getName());

    public static final String OK_BUTTON = "Aizvert";

    public AboutDialogBox() {
        logger.info(AboutDialogBox.class.getName() +".Constructor() ");

        DockPanel dock = new DockPanel();
        dock.setSpacing(4);

        setAnimationEnabled(true);
        SafeHtml safeHtml =  SafeHtmlUtils.fromTrustedString(
                "<p>Dotā aplikācija ir izveidota mācību nolūkos lai iepazītos ar tehnoloģijām un to pielietojumu." +
                "<br>" +
                "Ja jums ir kādi : jautājumi \\ iebildumi \\ priekšlikumi droši rakstiet uz :" +
                        "<b> codon.dev[att-/+]gmail.com </b>"+
                "<br><br>" +
                "Nekādas tiesības netiek paturētas ! </p>");

        HTML html = new HTML(safeHtml);

        //setHTML(safeHtml);
        Button okButton = new Button(OK_BUTTON);
        okButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                logger.info( AboutDialogBox.class.getName() +" okButton.addClickHandler ");
                AboutDialogBox.this.hide();
            }
        });

        dock.add(okButton, DockPanel.SOUTH);
        dock.setCellHorizontalAlignment(okButton, DockPanel.ALIGN_CENTER);
        dock.add(html, DockPanel.NORTH);
        dock.setWidth("100%");

        setWidget(dock);

    }

}

