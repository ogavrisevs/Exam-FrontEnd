package shared;

//import com.google.gwt.user.client.Random;

import com.google.appengine.api.datastore.Key;
import server.RPCImpl;
import shared.DAO.TCaseDAO;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class RandomTCase {
    private static final Logger logger = Logger.getLogger(RandomTCase.class.getName());
    private static final PersistenceManager pm = PMF.get().getPersistenceManager();

    public static TCaseDAO getNextCase(Integer tCaseTypeInt) {
        logger.info("TCaseDAO.getNextCase("+ tCaseTypeInt +")");

        Query query = pm.newQuery(TCaseDAO.class);
        if ( tCaseTypeInt != RPCImpl.VALUE_NOT_SET ){
            query.setFilter("tCaseTypeIdx == param");
            query.declareParameters("Integer param");
        }

        query.setResult("key");
        TCaseDAO t1 = null;
        try {
            List<Object> result;
            if ( tCaseTypeInt != RPCImpl.VALUE_NOT_SET )
                result = (List<Object>) query.execute(tCaseTypeInt);
            else
                result = (List<Object>) query.execute();

            int rowCount = result.size();
            if (rowCount == 0) {
                logger.info("row count : " + rowCount);
                return null;
            }
            Random generator = new Random();
            int randomRowNumber = generator.nextInt(rowCount);
            Key key = (Key) result.get(randomRowNumber);
            return pm.getObjectById(TCaseDAO.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            query.closeAll();
        }
        return null;
    }


}
