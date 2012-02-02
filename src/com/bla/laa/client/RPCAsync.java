package com.bla.laa.client;

import com.bla.laa.shared.Model.TCaseModel;
import com.bla.laa.shared.Model.TCaseTypeModel;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.SortedMap;


public interface RPCAsync {
    void getTC(Integer tCaseTypeIdx ,AsyncCallback<TCaseModel> async);

    void getTCaseTypes(AsyncCallback<List<TCaseTypeModel>> async);

    void getParagraph(Integer paragId, AsyncCallback<SortedMap<Integer, SafeHtml>> async);

    void getParagraphMore(Integer paragId, AsyncCallback<SortedMap<Integer, SafeHtml>> async);
}
