package shared.Services;

import shared.DAO.ParagraphDAO;
import shared.PMF;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;
import java.util.logging.Logger;

public class ParagraphService {
    private static final Logger logger = Logger.getLogger( ParagraphService.class.getName());
    private static final PersistenceManager pm = PMF.get().getPersistenceManager();

    public ParagraphDAO getParagByName(String paragName){
        Query query = pm.newQuery(ParagraphDAO.class);
        query.setFilter("paragName == param");
        query.declareParameters("String param");
        List<ParagraphDAO> paragraphDAOs = (List<ParagraphDAO>) query.execute(paragName);
        if (paragraphDAOs.isEmpty())
            return null;
        else
            return paragraphDAOs.get(0);
    }

    public ParagraphDAO getParagById(Integer paragId){
        Query query = pm.newQuery(ParagraphDAO.class);
        query.setFilter("id == param");
        query.declareParameters("String param");
        List<ParagraphDAO> paragraphDAOs = (List<ParagraphDAO>) query.execute(paragId);
        if (paragraphDAOs.isEmpty())
            return null;
        else
            return paragraphDAOs.get(0);
    }

}
