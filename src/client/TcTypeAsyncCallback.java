package client;


import com.google.gwt.user.client.rpc.AsyncCallback;
import shared.Model.TCaseTypeModel;

import java.util.List;
import java.util.logging.Logger;


public class TcTypeAsyncCallback implements AsyncCallback<List<TCaseTypeModel>> {
    private static final Logger logger = Logger.getLogger(TcTypeAsyncCallback.class.getName());
    Main main = null;

    public TcTypeAsyncCallback(Main main) {
        this.main = main;
    }

    public void onFailure(Throwable caught) {
        logger.severe("TcTypeAsyncCallback.onFailure("+ caught.getMessage() +")");
        main.tCaseTypeListBox.clear();
        main.tCaseTypeListBox.addItem("Error loading data");
    }

    public void onSuccess(List<TCaseTypeModel> tCaseTypeModels) {
        logger.info("TcTypeAsyncCallback.onSuccess("+ tCaseTypeModels.size() +")");
        main.tCaseTypeListBox.clear();

        main.tCaseTypeModels = tCaseTypeModels;
        for (TCaseTypeModel tCaseTypeModel : tCaseTypeModels)
            main.tCaseTypeListBox.addItem(tCaseTypeModel.getTypeName(), String.valueOf(tCaseTypeModel.getTypeIdx()));
    }
}
