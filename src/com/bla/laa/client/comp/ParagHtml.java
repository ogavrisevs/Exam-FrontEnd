/*
 * @(#)ParagHtml.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.bla.laa.client.comp;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParagHtml extends HTML{

    Map<Integer, SafeHtml> map = new HashMap<Integer, SafeHtml>();

    public ParagHtml() {
        super();
    }

    public Map<Integer, SafeHtml> getMap() {
        return map;
    }

    public void setMap(Map<Integer, SafeHtml> map) {
        this.map = map;
    }

    public void addMap(Integer key, SafeHtml html) {
        this.map.put(key, html);
    }

}

