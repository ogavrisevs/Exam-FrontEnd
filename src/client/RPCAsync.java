package client;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;
import shared.Model.TCaseModel;
import shared.Model.TCaseTypeModel;

import java.util.List;


public interface RPCAsync {
    void getParagraph(Integer id, AsyncCallback<SafeHtml> async);

    void getTC(Integer tCaseTypeIdx ,AsyncCallback<TCaseModel> async);

    void getTCaseTypes(AsyncCallback<List<TCaseTypeModel>> async);
}
