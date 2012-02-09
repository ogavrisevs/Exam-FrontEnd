package com.bla.laa.server;

import com.bla.laa.shared.Model.TCaseTypeModel;
import com.bla.laa.shared.PMF;
import org.datanucleus.jdo.exceptions.ClassNotPersistenceCapableException;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TCaseTypesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TCaseTypesServlet.class.getName());
    PersistenceManager pm = PMF.get().getPersistenceManager();
    HttpServletRequest request = null;
    HttpServletResponse response = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        if (request.getParameter("del") != null)
            delAllTCaseTypes();

        if (request.getParameter("add") != null)
            addAllTCaseTypes();

        if (request.getParameter("print") != null)
            printAllTCaseTypes();

    }

    private void printAllTCaseTypes() throws IOException {
        logger.info("print all from TCaseTypeModel");
        StringBuffer sb  = new StringBuffer("Print all from TCaseTypeModel \n  ");
        try {
            Extent exents = pm.getExtent(TCaseTypeModel.class, true);
            for (Object obj : exents){
                TCaseTypeModel tCaseTypeModel = (TCaseTypeModel)obj;
                //sb.append( tCaseTypeModel.getKey() +" , " + tCaseTypeModel.getTypeName() +" \n ") ;
                sb.append(tCaseTypeModel.toString() + "\n");
            }
        }catch (Exception e){
            logger.severe(e.getMessage());
            response.sendError(999, e.getMessage());
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(sb);
    }

    private void addAllTCaseTypes() throws IOException {
        logger.info("Add TCaseTypeModel items");
        StringBuffer sb = new StringBuffer("Add TCaseTypeModel items");
        List<TCaseTypeModel> caseTypeModelList = prepareTCaseTypesList();
        for (TCaseTypeModel tCaseTypeModel : caseTypeModelList) {
            pm.makePersistent(tCaseTypeModel);
            sb.append(tCaseTypeModel.toString());
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(sb);
    }

    private void delAllTCaseTypes() throws IOException {
        logger.info("delete all from TCaseTypeModel");
        StringBuffer sb  = new StringBuffer("Delete all from TCaseTypeModel \n  ");
        try {
            Extent exents = pm.getExtent(TCaseTypeModel.class, true);
            for (Object obj : exents){
                sb.append( ((TCaseTypeModel)obj).getTypeName() +" , ");
                pm.deletePersistent(obj);
            }
        } catch (ClassNotPersistenceCapableException cnpce) {
            // its ok, no rows
        } catch (Exception e) {
            logger.severe(e.getMessage());
            response.sendError(999, e.getMessage());
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(sb);
    }


    public List<TCaseTypeModel> prepareTCaseTypesList() {
        List<TCaseTypeModel> caseTypeModelList = new ArrayList<TCaseTypeModel>();
        caseTypeModelList.add(new TCaseTypeModel(10,  "Visi "));
        caseTypeModelList.add(new TCaseTypeModel(11,  "Termini"));
        caseTypeModelList.add(new TCaseTypeModel(12,  "Transportlīdzekļu vadītāju vispārīgie pienākumi"));

        /*
        caseTypeModelList.add(new TCaseTypeModel(13,  "Transportlīdzekļa vadītāja pienākumi īpašos gadījumos"));
        caseTypeModelList.add(new TCaseTypeModel(14,  "Braukšanas sākšana, braukšanas virziena maiņa"));
        caseTypeModelList.add(new TCaseTypeModel(16,  "Transportlīdzekļu izkārtojums uz brauktuves"));
        caseTypeModelList.add(new TCaseTypeModel(17,  "Braukšanas ātrums, distance un intervāls"));
        caseTypeModelList.add(new TCaseTypeModel(18,  "Apdzīšana, samainīšanās ar pretimbraucošu transportlīdzekli un apsteigšana"));
        caseTypeModelList.add(new TCaseTypeModel(19,  "Apstāšanās un stāvēšana"));
        caseTypeModelList.add(new TCaseTypeModel(110, "Braukšana krustojumos"));
        caseTypeModelList.add(new TCaseTypeModel(111, "Ceļu satiksme dzīvojamās zonās"));
        caseTypeModelList.add(new TCaseTypeModel(112, "Gājēju pārejas un pasažieru sabiedrisko transportlīdzekļu pieturas"));
        caseTypeModelList.add(new TCaseTypeModel(113, "Dzelzceļa pārbrauktuvju šķērsošana"));
        caseTypeModelList.add(new TCaseTypeModel(114, "Ārējās apgaismes ierīču lietošana"));
        caseTypeModelList.add(new TCaseTypeModel(115, "Skaņas signāla, avārijas gaismas signalizācijas un avārijas zīmes lietošana"));
        caseTypeModelList.add(new TCaseTypeModel(116, "Transportlīdzekļu vilkšana"));
        caseTypeModelList.add(new TCaseTypeModel(117, "Pasažieru un kravas pārvadāšana"));
        caseTypeModelList.add(new TCaseTypeModel(118, "Transportlīdzekļu tehniskais stāvoklis un aprīkojums"));
        caseTypeModelList.add(new TCaseTypeModel(119, "Transportlīdzekļu reģistrācija un numura zīmes"));
        caseTypeModelList.add(new TCaseTypeModel(120, "Transportlīdzekļu pazīšanās zīmes"));
        caseTypeModelList.add(new TCaseTypeModel(121, "Brīdinājuma zīmes"));
        caseTypeModelList.add(new TCaseTypeModel(122, "Priekšrocības zīmes"));
        caseTypeModelList.add(new TCaseTypeModel(123, "Aizlieguma zīmes"));
        caseTypeModelList.add(new TCaseTypeModel(124, "Rīkojuma zīmes"));
        caseTypeModelList.add(new TCaseTypeModel(125, "Norādījuma zīmes"));
        caseTypeModelList.add(new TCaseTypeModel(126, "Virzienu rādītāji un informācijas zīmesv"));
        caseTypeModelList.add(new TCaseTypeModel(127, "Papildzīmes"));
        caseTypeModelList.add(new TCaseTypeModel(128, "Ceļa apzīmējumi"));
        caseTypeModelList.add(new TCaseTypeModel(129, "Satiksmes drošība"));
        caseTypeModelList.add(new TCaseTypeModel(130, "Alkohola un medikamentu ietekme uz satiksmes drošību"));
        caseTypeModelList.add(new TCaseTypeModel(131, "Pirmā palīdzība"));
        */
        return caseTypeModelList;
    }

}
