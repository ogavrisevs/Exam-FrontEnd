package com.bla.laa.client;

import com.bla.laa.client.comp.ScrollPanelUpDown;
import com.bla.laa.shared.Model.TCaseModel;
import com.bla.laa.shared.Model.TCaseTypeModel;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.Map;


public interface RPCAsync {
    void getTC(Integer tCaseTypeIdx ,AsyncCallback<TCaseModel> async);

    void getTCaseTypes(AsyncCallback<List<TCaseTypeModel>> async);

    void getParagraph(Integer paragId, AsyncCallback<Map<Integer, SafeHtml>> async);

    void getParagraphMore(Integer paragId, ScrollPanelUpDown.Direction direction, AsyncCallback<Map<Integer, SafeHtml>> async);
}
