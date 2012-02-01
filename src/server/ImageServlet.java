package server;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import shared.DAO.ImageDAO;
import shared.PMF;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ImageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ImageServlet.class.getName());
    PersistenceManager pm = PMF.get().getPersistenceManager();

    enum UrlParam {cid, has, key}

    /**
     * url :
     * /img?cid=xxxx&imgl
     * /img?cid=xxxx&imgs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("ImageServlet : doGet()");

        String idVal = request.getParameter(UrlParam.cid.name());
        String keyVal = request.getParameter(UrlParam.key.name());
        String hashVal = request.getParameter(UrlParam.has.name());

        if ((idVal == null) && (keyVal == null) && (hashVal == null)) {
            response.sendError(999, missingParams().toString());
            return;
        }
        if (keyVal != null) {
            try {
                Integer.valueOf(keyVal);
            } catch (NumberFormatException nfe) {
                response.sendError(999, nfe.getLocalizedMessage());
                return;
            }
        }

        Query query = pm.newQuery(ImageDAO.class);
        if (idVal != null) {
            query.setFilter("imageCsddId == " + UrlParam.cid.name());
            query.declareParameters("Integer " + UrlParam.cid.name());
        } else if (hashVal != null) {
            query.setFilter("hash == " + UrlParam.has.name());
            query.declareParameters("String " + UrlParam.has.name());
        }

        //query.setOrdering("imageCsddId ascending");
        ImageDAO imageDAO = null;
        try {

            if (idVal != null) {
                List<ImageDAO> imageDAOs = (List<ImageDAO>) query.execute(Integer.valueOf(idVal));
                imageDAO = imageDAOs.get(0);
            } else if (hashVal != null) {
                List<ImageDAO> imageDAOs = (List<ImageDAO>) query.execute(hashVal);
                imageDAO = imageDAOs.get(0);
            } else if (keyVal != null) {
                Key key = KeyFactory.createKey(ImageDAO.class.getSimpleName(), Integer.valueOf(keyVal));
                Object imageDtoObj = PMF.getByKey(ImageDAO.class, key);
                if (imageDtoObj == null)
                    imageDAO = null;
                else
                    imageDAO = (ImageDAO) imageDtoObj;
            }

            if (imageDAO == null) {
                response.sendError(999, "img not found !");
                return;
            }

            response.setContentType("image/jpeg");
            response.setContentLength(imageDAO.getImage().length);
            response.getOutputStream().write(imageDAO.getImage());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(999, e.getMessage());
        } finally {
            query.closeAll();
        }
    }

    public StringBuffer missingParams() {
        StringBuffer sb = new StringBuffer();
        sb.append("legal params are : ");
        for (UrlParam up : UrlParam.values())
            sb.append(up.toString() + "  ");

        return sb;
    }

}
