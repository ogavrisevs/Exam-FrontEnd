package com.bla.laa.client;

import com.bla.laa.client.comp.ScrollPanelUpDown;
import com.bla.laa.shared.Model.TCaseModel;
import com.bla.laa.shared.Model.TCaseTypeModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;
import java.util.Map;


@RemoteServiceRelativePath("RPC")
public interface RPC extends RemoteService {

    Map<Integer, SafeHtml> getParagraph(Integer paragId) throws RpcCustException;

    Map<Integer, SafeHtml> getParagraphMore(Integer paragId, ScrollPanelUpDown.Direction direction) throws RpcCustException;

    TCaseModel getTC(Integer str) throws RpcCustException;

    List<TCaseTypeModel> getTCaseTypes() throws RpcCustException;

    public static class App {
        private static final RPCAsync ourInstance = (RPCAsync) GWT.create(RPC.class);

        public static RPCAsync getInstance() {
            return ourInstance;
        }
    }
}
