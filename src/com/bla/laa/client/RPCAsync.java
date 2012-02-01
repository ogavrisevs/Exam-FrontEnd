package com.bla.laa.client;

import com.bla.laa.shared.Model.TCaseModel;
import com.bla.laa.shared.Model.TCaseTypeModel;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;


public interface RPCAsync {
    void getParagraph(Integer id, AsyncCallback<SafeHtml> async);

    void getTC(Integer tCaseTypeIdx ,AsyncCallback<TCaseModel> async);

    void getTCaseTypes(AsyncCallback<List<TCaseTypeModel>> async);
}
