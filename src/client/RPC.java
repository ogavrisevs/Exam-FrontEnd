package client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import shared.Model.TCaseModel;
import shared.Model.TCaseTypeModel;

import java.util.List;


@RemoteServiceRelativePath("RPC")
public interface RPC extends RemoteService {

    SafeHtml getParagraph(Integer paragId) throws RpcCustException;

    TCaseModel getTC(Integer str) throws RpcCustException;

    List<TCaseTypeModel> getTCaseTypes() throws RpcCustException;

    public static class App {
        private static final RPCAsync ourInstance = (RPCAsync) GWT.create(RPC.class);

        public static RPCAsync getInstance() {
            return ourInstance;
        }
    }
}
