package com.bla.laa.server;

import com.bla.laa.client.RPC;
import com.bla.laa.client.RpcCustException;
import com.bla.laa.shared.DAO.ParagraphDAO;
import com.bla.laa.shared.DAO.TCaseDAO;
import com.bla.laa.shared.Model.TCaseModel;
import com.bla.laa.shared.Model.TCaseTypeModel;
import com.bla.laa.shared.PMF;
import com.bla.laa.shared.RandomTCase;
import com.bla.laa.shared.Services.ParagraphService;
import com.bla.laa.shared.TCaseWraper;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class RPCImpl extends RemoteServiceServlet implements RPC {
    private static final Logger logger = Logger.getLogger(RPCImpl.class.getName());
    private static final PersistenceManager pm = PMF.get().getPersistenceManager();
    public static final Integer VALUE_NOT_SET = -1;

    ParagraphService paragraphService = null;
    TCaseWraper tCaseWraper = null;

    public RPCImpl() {
        paragraphService = new ParagraphService();
        tCaseWraper = new TCaseWraper();
    }

    public SafeHtml getParagraph(Integer paragId) throws RpcCustException {
        logger.info("RPCImpl.getParagraph(" + paragId + ")");

        if ( (paragId == null) || (paragId < 0 ))
            throw new RpcCustException(" Par. number not corect !");

        ParagraphDAO paragraphDAO = paragraphService.getParagById(paragId);
        if (paragraphDAO == null)
            throw new RpcCustException("Par. not found !");

        String html = paragraphDAO.getParagText();
        if ((html == null) || (html.isEmpty()))
            throw new RpcCustException("Par. text not found !");

        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(html);
        return safeHtml;
    }

     /*
    public TCaseModel getTC() throws RpcCustException {
        return getTCMock();
    }*/

    public TCaseModel getTC(Integer tCaseType) throws RpcCustException {
        logger.info("RPCImpl.getTC("+ tCaseType +")");

        // check type exists
        Integer tCaseTypeInt = VALUE_NOT_SET;
        try {
             tCaseTypeInt = Integer.valueOf(tCaseType);
        }catch (NumberFormatException nfe){
            logger.severe(nfe.getMessage());
        }

        if ( tCaseTypeInt != VALUE_NOT_SET){
            Query query = pm.newQuery(TCaseTypeModel.class);
            query.setFilter("typeIdx == param");
            query.declareParameters("Integer param");
            List<TCaseTypeModel> tCaseTypeModels = (List<TCaseTypeModel>) query.execute(tCaseTypeInt);
            if (tCaseTypeModels.isEmpty()){
                tCaseTypeInt = VALUE_NOT_SET;
                logger.warning("TCaseType not found !!! ");
            } else{
                logger.info("TCaseType found : "+ tCaseTypeModels.get(0).getTypeName());
            }
        }

        //get actual rand case
        TCaseDAO tc = RandomTCase.getNextCase(tCaseTypeInt);
        if (tc == null) {
            logger.severe("testCase not found !");
            if (tCaseTypeInt != VALUE_NOT_SET){
                logger.severe("search for all testCasesTypes");
                tc = RandomTCase.getNextCase(VALUE_NOT_SET);
            }
        } else {
            logger.info("got tc.key == " + tc.getKey());
        }
        if (tc == null)
            throw new RpcCustException("No Test case found !");

        return tCaseWraper.getTCaseMod(tc);
    }

    public List<TCaseTypeModel> getTCaseTypes() throws RpcCustException  {
        logger.info("RPCImpl.getTCaseTypes()");
        Extent exents = (Extent) pm.getExtent(TCaseTypeModel.class, false);
        List<TCaseTypeModel> caseTypeModelList = new ArrayList<TCaseTypeModel>();
        for (Object obj : exents)
            caseTypeModelList.add((TCaseTypeModel) obj);

        Collections.sort(caseTypeModelList);
        if (caseTypeModelList.isEmpty())
            throw new RpcCustException("TestCase type  not found !");
        return caseTypeModelList;
    }

    public TCaseModel getTCMock() {
        TCaseModel tCaseModel = new TCaseModel();
        tCaseModel.setCorectAnswer("Answer1");
        tCaseModel.setQuestionText("Jautajums ");
        tCaseModel.addAnswers("Answer1");
        tCaseModel.addAnswers("Answer2");
        tCaseModel.addAnswers("Answer3");
        //tCaseModel.addParagNames("1.");
        //tCaseModel.addParagNames("1.2.");

        return tCaseModel;
    }


}