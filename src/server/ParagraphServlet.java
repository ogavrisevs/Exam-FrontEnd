package server;

import shared.DAO.ParagraphDAO;
import shared.PMF;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;


public class ParagraphServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ParagraphServlet.class.getName());
    PersistenceManager pm = PMF.get().getPersistenceManager();
    HttpServletRequest request = null;
    HttpServletResponse response = null;

    public ParagraphServlet() {

    }

    enum urlParams {print, del, add};

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        Enumeration enumeration = request.getParameterNames();

        while (enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            if (obj instanceof String) {
                String paramName = (String) obj;
                if (paramName.contentEquals(urlParams.print.name()))
                    printAllParagraph();
                if (paramName.contentEquals(urlParams.add.name()))
                    addAllParagraph();
                if (paramName.contentEquals(urlParams.del.name()))
                    delAllParagraph();
            }

        }
    }

    private void delAllParagraph() throws IOException {
        logger.info("delete all from ParagraphDAO");
        StringBuffer sb = new StringBuffer("Delete all from ParagraphDAO . . . \n   ");
        try {
            Query query = pm.newQuery(ParagraphDAO.class);
            List<ParagraphDAO> paragraphDAOs = (List<ParagraphDAO>) query.execute();
            for (ParagraphDAO paragraphDAO : paragraphDAOs) {
                sb.append(paragraphDAO.getParagName() + " , ");
                pm.deletePersistent(paragraphDAO);

            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            response.sendError(999, e.getMessage());
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(sb.toString());
    }

    private void addAllParagraph() throws IOException {
        logger.info("add all ParagraphDAO");
        StringBuffer sb = new StringBuffer("Add all ParagraphDAO \n ");
        List<ParagraphDAO> paragraphDAOs = prepare();
        for (ParagraphDAO paragraphDAO : paragraphDAOs) {
            pm.makePersistent(paragraphDAO);
            sb.append(paragraphDAO.getParagName() + "\n");
            logger.info(paragraphDAO.toString());
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(sb.toString());
    }

    private void printAllParagraph() throws IOException {
        Query query = pm.newQuery(ParagraphDAO.class);
        query.setOrdering("id asc");
        List<ParagraphDAO> paragraphDAOs = (List<ParagraphDAO>) query.execute();

        StringBuffer sb = new StringBuffer("Start printing .... \n");
        addHeader(sb);
        for (ParagraphDAO paragraphDAO : paragraphDAOs) {
            sb.append(paragraphDAO.getParagText());
            sb.append("\n");
        }
        addFooter(sb);

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(sb);
    }

    void addHeader(StringBuffer sb) {
        sb.append("<html>");
        sb.append("\n");
        sb.append("<head>");
        sb.append("\n");
        sb.append("<title>ListAll</title>");
        sb.append("\n");
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"/template.css\" />");
        sb.append("\n");
        sb.append("</head>");
        sb.append("\n");
        sb.append("<body>");
        sb.append("\n");
    }

    void addFooter(StringBuffer sb) {
        sb.append("</body>");
        sb.append("\n");
        sb.append("</html>");
        sb.append("\n");
    }

    private List<ParagraphDAO> prepare() {
        List<ParagraphDAO> paragraphDAOs = new ArrayList<ParagraphDAO>();

        paragraphDAOs.add(new ParagraphDAO("1.", " <p id='toc_marker-2' class='Virsraksts'><strong>1. Vispārīgie jautājumi</strong></p><p class='txt'>1. Noteikumos lietotie termini:</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.1.", " <p class='txt'>1.1. <strong>apbraukšana</strong> – braukšana garām vienam vai vairākiem pilnīgi vai daļēji uz brauktuves apturētiem transportlīdzekļiem, kā arī citiem šķēršļiem (kavēkļiem), kas saistīta ar braukšanas virziena maiņu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.2.", " <p class='txt'>1.2. <strong>apdzīšana</strong> – braukšana garām vienam vai vairākiem braucošiem transportlīdzekļiem, iebraucot pretējā braukšanas joslā (brauktuves pusē) un atgriežoties iepriekšējā braukšanas joslā (brauktuves pusē), (skat. 1. att.);</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/1.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>1. att.</p> " +
                " </div> " +
                " </div> "));

        paragraphDAOs.add(new ParagraphDAO("1.3.", " <p class='txt'>1.3. <strong>apdzīvota vieta</strong> – apbūvēta teritorija, kurā iebraukšana apzīmēta ar 518. vai 520.ceļa zīmi, bet izbraukšana - ar 519. vai 521.ceļa zīmi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.4.", " <p class='txt'>1.4. <strong>apstāšanās</strong> – transportlīdzekļa apturēšana uz laiku, kas nepārsniedz piecas minūtes, ja tā ir nepieciešama pasažieru iekāpšanai transportlīdzeklī vai izkāpšanai no tā, kravas iekraušanai transportlīdzeklī vai izkraušanai no tā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.5.", " <p class='txt'>1.5. <strong>apsteigšana</strong> – braukšana garām vienam vai vairākiem braucošiem transportlīdzekļiem, neiebraucot pretējā braukšanas joslā (brauktuves pusē), (skat. 2. att.);</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.6.", " <p class='txt'>1.6. <strong>bīstama krava</strong> – krava, kas pārvadāšanas vai uzglabāšanas procesā savu īpašību dēļ var izraisīt sprādzienu, ugunsgrēku vai citus postījumus, kā arī apdraudēt cilvēku dzīvību vai veselību;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.7.", " <p class='txt'>1.7. <strong>braukšanas josla</strong> – jebkura brauktuves garenvirziena josla (var būt apzīmēta ar ceļa apzīmējumiem), kura ir pietiekami plata, lai pa to vienā rindā varētu braukt automobiļi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.8.", " <p class='txt'>1.8. <strong>brauktuve</strong> – ceļa daļa, kas paredzēta transportlīdzekļu braukšanai. Ceļam var būt vairākas brauktuves, kuras norobežotas ar sadalošajām joslām vai barjerām;</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/2.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>2. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("1.9.", " <p class='txt'>1.9. <strong>ceļš</strong><span> – jebkura satiksmei izbūvēta teritorija (autoceļš, iela, prospekts, šķērsiela un tamlīdzīgas teritorijas visā platumā, ieskaitot brauktuvi, ietves, nomales, sadalošās joslas un saliņas). Ceļa kompleksā ietilpst ceļš, mākslīgās būves (tilti, satiksmes pārvadi, tuneļi, caurtekas, ūdens novadīšanas ietaises, atbalsta sienas un citas būves), ceļu inženierbūves (pasažieru sabiedrisko transportlīdzekļu pieturvietas un paviljoni, bremzēšanas un paātrinājuma joslas, transportlīdzekļu stāvvietas, atpūtas laukumi, veloceliņi un ietves, ceļu sakaru un apgaismojuma līnijas un citas inženierbūves), satiksmes organizācijas tehniskie līdzekļi (ceļa zīmes, luksofori, signālstabiņi, aizsargbarjeras, gājēju barjeras, vertikālie un horizontālie ceļa apzīmējumi un citi tehniskie līdzekļi);</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("1.10.", " <p class='txt'>1.10. <strong>ceļu satiksme</strong> – attiecības, kas rodas, pārvietojoties pa ceļiem ar transportlīdzekļiem vai bez tiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.11.", " <p class='txt'>1.11. <strong>ceļu satiksmes dalībnieks</strong> – jebkura persona, kas atrodas uz ceļa vai tieši piedalās ceļu satiksmē;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.12.", " <p class='txt'>1.12. <strong>ceļu satiksmes negadījums</strong> – ceļu satiksmē noticis nelaimes gadījums, kurā iesaistīts vismaz viens transportlīdzeklis un kura rezultātā cilvēks gājis bojā, viņam nodarīti miesas bojājumi vai arī nodarīti zaudējumi fiziskās vai juridiskās personas mantai vai videi, kā arī citā vietā, kur iespējama transportlīdzekļa braukšana, noticis negadījums, kurā iesaistīts transportlīdzeklis;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.13.", " <p class='txt'>1.13. <strong>diennakts tumšais laiks</strong> – laiks no vakara krēslas iestāšanās līdz rīta krēslas izzušanai;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.14.", " <p class='txt'>1.14. <strong>dot ceļu</strong> – prasība, kas nosaka, ka ceļu satiksmes dalībnieks nedrīkst sākt braukt (iet), turpināt braukt (iet) vai izdarīt jebkādu manevru, ja tādējādi citiem ceļu satiksmes dalībniekiem tiek radīti traucējumi braukšanai (iešanai) vai viņi ir spiesti mainīt braukšanas (iešanas) virzienu vai ātrumu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.15.", " <p class='txt'>1.15. <strong>dzelzceļa pārbrauktuve</strong> – ceļa krustošanās ar dzelzceļa sliežu ceļa klātni vienā līmenī. Dzelzceļa pārbrauktuves robeža ir iedomāta līnija, kas perpendikulāri ceļa asij šķērso brauktuvi, sākot no dzelzceļa pārbrauktuves barjeras, bet, ja šādas barjeras nav, - no 134. vai 135.ceļa zīmes;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.16.", " <p class='txt'>1.16. <strong>dzīvojamā zona</strong><span> – apbūvēta teritorija (dzīvojamais masīvs, atpūtas vieta) vai tās daļa, kurā iebraukšana apzīmēta ar 528.ceļa zīmi, bet izbraukšana - ar 529.ceļa zīmi;</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("1.17.", " <p class='txt'>1.17. <strong>faktiskā masa</strong> – transportlīdzekļa masa kopā ar transportlīdzekļa vadītāju, pasažieriem un kravu. Vilcējam ar piekabi (puspiekabi) faktiskā masa ir vilcēja un piekabes (puspiekabes) faktisko masu summa;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.18.", " <p class='txt'>1.18. <strong>galvenais ceļš</strong> – ceļš, kas apzīmēts ar 201., 203., 204. vai 205.ceļa zīmi, vai ceļš, uz kura nav uzstādītas priekšrocības ceļa zīmes attiecībā pret ceļu, uz kura uzstādīta 206. vai 207.ceļa zīme, vai ceļš ar asfalta, asfaltbetona, bruģa u.tml. segumu attiecībā pret ceļu ar grants vai šķembu segumu, vai ceļš ar jebkādu segumu attiecībā pret ceļu bez seguma, vai jebkurš ceļš attiecībā pret vietu, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.). Šī termina izpratnē mazāk svarīga ceļa posma segums tieši pirms krustojuma nenozīmē, ka attiecīgais ceļš ir vienādas nozīmes ar krustojamo ceļu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.19.", " <p class='txt'>1.19. <strong>gājējs</strong><span> – persona, kas pārvietojas pa ceļu kājām vai invalīdu ratiņos. Šis termins neattiecas uz personām, kas veic darbus uz brauktuves, un uz satiksmes regulētājiem;</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("1.20.", " <p class='txt'>1.20. <strong>gājēju ceļš</strong> – ceļš, kas paredzēts gājējiem (var būt apzīmēts ar 414.ceļa zīmi);</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.21.", " <p class='txt'>1.21. <strong>gājēju pāreja</strong> – brauktuves daļa, kas apzīmēta ar 530. un 531.ceļa zīmi un (vai) 931.ceļa apzīmējumu un paredzēta gājēju pāriešanai pāri brauktuvei;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.22.", " <p class='txt'>1.22. <strong>gājēju un velosipēdu ceļš</strong> – ceļš, kas apzīmēts ar 415., 416. vai 417.ceļa zīmi un paredzēts gājējiem un braukšanai ar velosipēdu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.23.", " <p class='txt'>1.23. <strong>ietve</strong> – ceļa daļa, kas paredzēta gājējiem. Ietve piekļaujas brauktuvei vai ir atdalīta no tās;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.24.", " <p class='txt'>1.24. <strong>krustojums</strong> – vieta, kur krustojas, piekļaujas vai atzarojas ceļi vienā līmenī, kā arī vieta, kur ceļu satiksme organizēta ar 409. ceļa zīmi. Krustojums ir norobežots ar iedomātām līnijām, kas savieno brauktuves pretējo malu noapaļojuma sākumu (skat. 3. att.). Šis termins neattiecas uz vietu, kur uz brauktuves izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.);</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/3.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/4.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>3. att.</p> " +
                " </div> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("1.25.", " <p class='txt'>1.25. <strong>mehāniskais transportlīdzeklis</strong> –pašgājējs transportlīdzeklis, kas pārvietojas pa ceļu ar savu enerģiju (tai skaitā traktortehnika un trolejbuss), izņemot sliežu transportlīdzekļus;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.26.", " <p class='txt'>1.26. <strong>mopēds</strong> – divriteņu vai trīsriteņu mehāniskais transportlīdzeklis, kura motora darba tilpums nepārsniedz 50 kubikcentimetrus (dzirksteļaizdedzes tipa motoriem) vai kura maksimālā jauda nepārsniedz 4 kW (elektromotoriem vai cita veida iekšdedzes tipa motoriem) un konstrukcijā paredzētais maksimālais ātrums pārsniedz 25 kilometrus stundā, bet nepārsniedz 45 kilometrus stundā, kā arī četrriteņu mehāniskais transportlīdzeklis, kura pašmasa nepārsniedz 350 kilogramus (neskaitot akumulatoru masu elektrotransportlīdzekļiem), motora darba tilpums nepārsniedz 50 kubikcentimetrus (dzirksteļaizdedzes tipa motoriem) vai kura maksimālā jauda nepārsniedz 4 kW (elektromotoriem vai cita veida iekšdedzes tipa motoriem) un konstrukcijā paredzētais maksimālais ātrums nepārsniedz 45 kilometrus stundā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.27.", " <p class='txt'>1.27.<span class='Italic'> </span><strong>motocikls</strong> – divriteņu mehāniskais transportlīdzeklis (ar blakusvāģi vai bez tā), kura motora darba tilpums pārsniedz 50 kubikcentimetrus un konstrukcijā paredzētais maksimālais ātrums pārsniedz 45 kilometrus stundā. Šis termins attiecas arī uz sniega motocikliem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.28.", " <p class='txt'>1.28. <strong>nepietiekama redzamība</strong> – tādi laikapstākļi (migla, lietus, sniegs un tamlīdzīgi apstākļi), kuru dēļ redzamība uz ceļa ir tuvāka par 300 metriem, arī mijkrēslis;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.29.", " <p class='txt'>1.29. <strong>nomale </strong>– ceļa daļa, kas piekļaujas brauktuvei un nav paredzēta mehānisko transportlīdzekļu braukšanai;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.30.", " <p class='txt'>1.30. <strong>operatīvais transportlīdzeklis</strong> – mehāniskais transportlīdzeklis, kuram noteiktā kārtībā piešķirts operatīvā transportlīdzekļa statuss un kurš piedalās ceļu satiksmē ar iedegtu mirgojošu zilu vai zilu un sarkanu bākuguni un ieslēgtu speciālu skaņas signālu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.31.", " <p class='txt'>1.31. <strong>pasažieris</strong> – persona, kas atrodas transportlīdzeklī vai uz tā (izņemot transportlīdzekļa vadītāju), kā arī persona, kas iekāpj transportlīdzeklī vai uzkāpj uz tā, izkāpj no transportlīdzekļa vai nokāpj no tā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.32.", " <p class='txt'>1.32. <strong>pasažieru sabiedriskais transportlīdzeklis</strong> – autobuss, trolejbuss vai tramvajs, kas brauc pa noteiktu maršrutu. Šis termins attiecas arī uz maršruta taksometriem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.33.", " <p class='txt'>1.33. <strong>pašmasa</strong> – atbilstoši valsts standartam nokomplektēta transportlīdzekļa masa kopā ar vadītāju, bet bez pasažieriem un kravas;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.34.", " <p class='txt'>1.34. <strong>piekabe (puspiekabe)</strong> – transportlīdzeklis, kas paredzēts braukšanai savienojumā ar mehānisko transportlīdzekli. Šis termins neattiecas uz motociklu blakusvāģiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.35.", " <p class='txt'>1.35. <strong>piespiedu apstāšanās</strong> – transportlīdzekļa apturēšana tehniska bojājuma dēļ vai bīstamības dēļ, ko rada transportlīdzekļa vadītāja vai pasažiera veselības stāvoklis, vedamā krava vai ceļu satiksmei bīstams šķērslis uz ceļa;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.36.", " <p class='txt'>1.36. <strong>pilna masa</strong> – izgatavotāja noteikta transportlīdzekļa maksimālā pieļaujamā masa kopā ar vadītāju, pasažieriem un kravu. Vilcējam ar piekabi pilna masa ir vilcēja un piekabes pilnu masu summa, bet vilcējam ar puspiekabi – vilcēja pašmasas, pasažieru masas un puspiekabes pilnas masas summa;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.37.", " <p class='txt'>1.37. <strong>priekšroka</strong> – tiesības braukt (iet) iecerētajā virzienā pirmajam attiecībā pret citiem ceļu satiksmes dalībniekiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.38.", " <p class='txt'>1.38. <strong>sadalošā josla</strong> – ceļa daļa, kas norobežo blakus brauktuves vienu no otras un nav paredzēta transportlīdzekļu braukšanai vai apturēšanai;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.39.", " <p class='txt'>1.39. <strong>satiksmes regulētājs</strong> – persona, kas regulē ceļu satiksmi un ir pilnvarota to darīt; </p> "));
        paragraphDAOs.add(new ParagraphDAO("1.40.", " <p class='txt'>1.40. <strong>stāvēšana</strong> – transportlīdzekļa apturēšana, ja tā nav saistīta ar pasažieru iekāpšanu transportlīdzeklī vai izkāpšanu no tā, kravas iekraušanu transportlīdzeklī vai izkraušanu no tā, kā arī transportlīdzekļa apturēšana uz laiku, kas pārsniedz piecas minūtes, ja tā ir saistīta ar pasažieru iekāpšanu transportlīdzeklī vai izkāpšanu no tā, kravas iekraušanu transportlīdzeklī vai izkraušanu no tā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.41.", " <p class='txt'>1.41. <strong>traktortehnika</strong> – pašgājējs transportlīdzeklis uz riteņiem vai kāpurķēdēm, kurš pārvietojas ar savu enerģiju, kuram ir vismaz divas asis, kura galvenā funkcija ir vilce, kurš ir īpaši izstrādāts, lai vilktu, stumtu, pārvietotu vai darbinātu konkrētas ierīces, mašīnas vai piekabes, un kura izmantošana cilvēku vai kravu pārvadāšanai pa ceļiem vai cilvēku vai kravu pārvadāšanai izmantojamu transportlīdzekļu vilkšanai pa ceļiem ir tikai sekundāra funkcija;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.42.", " <p class='txt'>1.42. <strong>tramvajs</strong> – transportlīdzeklis, kas paredzēts braukšanai pa sliedēm savienojumā ar ārēju elektroenerģijas padeves avotu. Šis termins neattiecas uz elektrovilcieniem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.43.", " <p class='txt'>1.43. <strong>transportlīdzeklis</strong> – ierīce, kas pēc savas konstrukcijas paredzēta braukšanai pa ceļiem ar motora palīdzību vai bez motora;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.44.", " <p class='txt'>1.44. <strong>transportlīdzekļa īpašnieks</strong> – fiziskā vai juridiskā persona, kurai pieder transportlīdzeklis. Par valsts vai pašvaldības transportlīdzekļa īpašnieku šā likuma izpratnē uzskatāma tiešā vai pastarpinātā valsts pārvaldes iestāde, uz kuras vārda transportlīdzeklis reģistrēts;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.45.", " <p class='txt'>1.45. <strong>transportlīdzekļa turētājs</strong> – fiziskā vai juridiskā persona, kura uz tiesiska pamata (mantas īres, nomas, patapinājuma līgums u.c.) lieto transportlīdzekli. Par transportlīdzekļa turētāju nav uzskatāma persona, kas transportlīdzekli lieto uz dienesta vai darba tiesisko attiecību pamata;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.46.", " <p class='txt'>1.46. <strong>transportlīdzekļa vadītājs</strong> – fiziskā persona, kas vada transportlīdzekli, tai skaitā vadīšanas iemaņu pārbaudes laikā, kura notiek normatīvajos aktos noteiktajā kārtībā, vai apmāca vadīt transportlīdzekli personu, kurai nav atbilstošas kategorijas transportlīdzekļa vadīšanas tiesību. Šis jēdziens uz personu, kura ir vadījusi transportlīdzekli, attiecas līdz tam brīdim, kad šo transportlīdzekli sāk vadīt cita persona;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.47.", " <p class='txt'>1.47. <strong>transportlīdzekļu sastāvs</strong> – mehāniskais transportlīdzeklis savienots ar piekabi (puspiekabi), kā arī savienots velkošais un velkamais transportlīdzeklis;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.48.", " <p class='txt'>1.48. <strong>tricikls un kvadricikls</strong> – attiecīgi trīsriteņu vai četrriteņu mehāniskais transportlīdzeklis ar simetriski izvietotiem riteņiem, kura konstrukcijā paredzētais maksimālais ātrums pārsniedz 45 kilometrus stundā vai kuram ir iekšdedzes motors ar 50 kubikcentimetru vai lielāku darba tilpumu [kvadricikla pašmasa nepārsniedz 400 kilogramus (550 kilogramus, ja tas paredzēts kravu pārvadāšanai) un jauda - 15 kilovatus];</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.49.", " <p class='txt'>1.49. <strong>trolejbuss</strong> – mehāniskais transportlīdzeklis, kas paredzēts braukšanai pa ceļu savienojumā ar ārēju elektroenerģijas padeves avotu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.50.", " <p class='txt'>1.50. <strong>velosipēds</strong> – transportlīdzeklis, kas paredzēts braukšanai, izmantojot uz tā esošā cilvēka muskuļu spēku (izņemot invalīdu ratiņus). Velosipēds var būt aprīkots ar elektromotoru, kura jauda ir ne lielāka par 0,25 kW; </p> "));
        paragraphDAOs.add(new ParagraphDAO("1.51.", " <p class='txt'>1.51. <strong>velosipēdu ceļš</strong> – ceļš, kas paredzēts braukšanai ar velosipēdiem un mopēdiem (var būt apzīmēts ar 413.ceļa zīmi vai 932. vai 941.ceļa apzīmējumu);</p> "));
        paragraphDAOs.add(new ParagraphDAO("1.52.", " <p class='txt'>1.52. <strong>vilcējs</strong> – mehāniskais transportlīdzeklis, kas brauc savienojumā ar piekabi (puspiekabi).</p> "));
        paragraphDAOs.add(new ParagraphDAO("2.", " <p class='txt'>2. Noteikumi nosaka ceļu satiksmes kārtību Latvijā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("3.", " <p class='txt'>3. Transportlīdzekļu braukšana Latvijā ir noteikta pa ceļa labo pusi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("4.", " <p class='txt'>4. Ceļu satiksmes dalībniekiem jāizpilda šo noteikumu un citu normatīvo aktu prasības, kas nosaka ceļu satiksmes dalībnieku pienākumus, jāizpilda policijas darbinieku un to personu norādījumi, kuras pilnvarotas regulēt satiksmi, kā arī jāievēro luksoforu signālu, ceļa zīmju un ceļa apzīmējumu prasības.</p> "));
        paragraphDAOs.add(new ParagraphDAO("5.", " <p class='txt'>5. Katra ceļu satiksmes dalībnieka pienākums ir darīt visu iespējamo, lai sniegtu pirmo palīdzību ceļu satiksmes negadījumā cietušajiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("6.", " <p class='txt'>6. Katram ceļu satiksmes dalībniekam ir tiesības uzskatīt, ka arī citas personas ceļu satiksmes norisē izpildīs noteiktās prasības.</p> "));
        //" <p class='Uzmaniba'><span class='Italic'>[Atceries! Esi pieklājīgs! Cieni sevi un citus!]</span></p> "+
        paragraphDAOs.add(new ParagraphDAO("7.", " <p class='txt'>7. Ceļu satiksmi regulē un uzrauga policijas darbinieki un citas personas, kuras saskaņā ar likumiem un citiem normatīvajiem aktiem ir tam pilnvarotas.</p> "));
        paragraphDAOs.add(new ParagraphDAO("8.", " <p class='txt'>8. Satiksmes regulētājiem un personām, kuras uzrauga ceļu satiksmi, uz ceļa jebkurā diennakts laikā jābūt labi saredzamā attiecīgā formas tērpā ar gaismu atstarojoša materiāla elementiem un ar atšķirības zīmi, jābūt klāt dienesta apliecībai.</p> "));
        paragraphDAOs.add(new ParagraphDAO("9.", " <p class='txt'>9. Transportlīdzekļa vadītājam ir tiesības uzzināt viņa vadītā transportlīdzekļa apturēšanas iemeslu, kā arī tās amatpersonas vārdu, uzvārdu un amatu, kura to apstādinājusi. </p> "));
        paragraphDAOs.add(new ParagraphDAO("10.", " <p class='txt'>10. Ceļu satiksmes dalībniekiem un citām personām jārīkojas tā, lai neradītu satiksmei bīstamas vai satiksmi traucējošas situācijas un nenodarītu zaudējumus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("11.", " <p class='txt'>11. Šajos noteikumos neparedzētus ceļu satiksmes ierobežojumus, kad tiek noteiktas un ir pieļaujamas atkāpes no šo noteikumu prasībām (ceļu darbi,<span class='Italic'> </span>sporta sacensības, publiski pasākumi u.tml.), drīkst izdarīt tikai normatīvajos aktos noteiktajā kārtībā, izmantojot ceļa zīmes, ceļa apzīmējumus, luksoforus, kā arī informējošās, norobežojošās, brīdinājuma u.tml. ierīces.</p> "));
        paragraphDAOs.add(new ParagraphDAO("12.", " <p class='txt'>12. Nacionālo bruņoto spēku speciālās militārās tehnikas pārvietošanās pa ceļu atļauta tikai tad, ja attiecīgie ceļa posmi tiek slēgti normatīvajos aktos par satiksmes aizliegumiem vai ierobežojumiem noteiktajā kārtībā vai ja attiecīgie transportlīdzekļi tiek pavadīti aizsardzības ministra noteiktajā kārtībā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("12.", " <p class='txt'>12.<span class='superscript'>1</span> Īslaicīgi braukt pa ceļu ārpus apdzīvotām vietām, ja tas nepieciešams ceļa vai tilta šķērsošanai, atļauts mopēdiem, tricikliem, kvadricikliem un motocikliem, kuru izmantošanu ceļu satiksmē izgatavotājs nav paredzējis, kā arī sniega motocikliem, kas reģistrēti Ceļu satiksmes drošības direkcijā saskaņā ar normatīvajiem aktiem par transportlīdzekļu reģistrāciju, bet kurus to ražotājs nav paredzējis izmantošanai ceļu satiksmē. Braucot pa ceļu, kā arī ārpus tā vadītājam jādara viss iespējamais, lai tiktu nodrošināta satiksmes drošība.</p> "));
        paragraphDAOs.add(new ParagraphDAO("13.", " <p class='txt'>13. Šie noteikumi un citi normatīvie akti, kas regulē attiecības ceļu satiksmes drošības jomā, attiecas arī uz ārvalstīs reģistrētajiem transportlīdzekļiem, kā arī to īpašniekiem, transportlīdzekļu vadītājiem un citiem ceļu satiksmes dalībniekiem, tiem atrodoties Latvijas teritorijā, ja starptautiskajos līgumos, kuru dalībvalsts ir Latvija, nav noteikts citādi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("14.", " <p class='txt'>14. Ceļu satiksmes dalībniekiem aizliegts:</p> "));
        paragraphDAOs.add(new ParagraphDAO("14.1.", " <p class='txt'>14.1. bojāt, patvaļīgi noņemt vai uzstādīt ceļa zīmes, luksoforus vai citus ceļu satiksmes organizācijas tehniskos līdzekļus;</p> "));
        paragraphDAOs.add(new ParagraphDAO("14.2.", " <p class='txt'>14.2. bojāt, piesārņot vai piegružot ceļu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("15.", " <p class='txt'>15. Ja ceļu satiksmes dalībnieks piesārņojis vai piegružojis brauktuvi (izkritusi krava, izlijusi eļļa u.tml.), viņam tā nekavējoties jānotīra, bet, ja tas nav iespējams, par to jābrīdina citi ceļu satiksmes dalībnieki un jāpaziņo policijai.</p> "));
        paragraphDAOs.add(new ParagraphDAO("16.", " <p id='toc_marker-3' class='Virsraksts'><strong>2. Gājēju un pasažieru pienākumi</strong></p><p class='txt'>16. Gājējiem jāpārvietojas pa ietvi, gājēju ceļu vai gājēju un velosipēdu ceļu, bet, ja to nav, - pa nomali. Ja ietves, gājēju ceļa, gājēju un velosipēdu ceļa, nomales nav vai pa to pārvietoties nav iespējams, gājējiem ir atļauts pārvietoties pa brauktuves malu vienā rindā (pa ceļiem, kur ir sadalošā josla, - pa ārmalu). </p> "));
        paragraphDAOs.add(new ParagraphDAO("17.", " <p class='txt'>17. Pārvietoties pa ietvi, gājēju ceļu, gājēju un velosipēdu ceļu vai nomali, izmantojot skrituļslidas, skrituļdēļus u.tml. sporta vai atpūtas inventāru, atļauts, ja tas netraucē pārējos gājējus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("18.", " <p class='txt'>18. Ārpus apdzīvotām vietām gājējiem, kuri iet pa brauktuves malu vai nomali, jāpārvietojas pretim transportlīdzekļu braukšanas virzienam, bet personām, kuras brauc invalīdu ratiņos vai stumj motociklu, mopēdu, velosipēdu u.tml. pa brauktuves malu vai nomali, jāpārvietojas transportlīdzekļu braukšanas virzienā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("19.", " <p class='txt'>19. Diennakts tumšajā laikā ārpus apdzīvotām vietām, kā arī apdzīvotās vietās, ja ceļš nav pietiekami un vienmērīgi apgaismots, gājējiem, kas pārvietojas pa brauktuvi vai nomali, jālieto gaismas atstarotājs vai cits gaismu atstarojoša materiāla elements vai jātur rokā iedegts lukturītis.</p> "));
        paragraphDAOs.add(new ParagraphDAO("20.", " <p class='Uzmaniba'><img style='max-height: 616px;' class='rightFloat' src='/public/imgS/61570.jpg'><span class='Italic'>[Atceries! Gaismu atstarojoši elementi tavā apģērbā un aksesuāros ļauj transportlīdzekla vadītājam tevi laikus pamanīt (skat 4. att.).]</span></p><p class='txt'>20. Organizētas cilvēku grupas pa ceļu drīkst virzīties tikai pa brauktuves labo pusi transportlīdzekļu braukšanas virzienā kolonnā ne vairāk par četriem cilvēkiem rindā. Kolonnas priekšā un aizmugurē tās kreisajā pusē jāatrodas personām, kas to pavada, ar sarkaniem karodziņiem, bet diennakts tumšajā laikā un nepietiekamas redzamības apstākļos - ar iedegtiem lukturiem: priekšā ar baltas gaismas lukturi, bet aizmugurē ar sarkanas gaismas lukturi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("21.", " <p class='txt'>21. Bērnu grupas atļauts vest tikai diennakts gaišajā laikā pa ietvēm, gājēju ceļiem vai gājēju un velosipēdu ceļiem, bet, ja to nav, - pa nomali transportlīdzekļu braukšanas virzienā kolonnā ne vairāk par diviem bērniem rindā. Kolonnas priekšā un aizmugurē tās kreisajā pusē jāatrodas pieaugušajiem, kas to pavada, ar sarkaniem karodziņiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("22.", " <p class='txt'>22. Gājējiem brauktuve jāšķērso pa gājēju pārejām (pazemes vai virszemes), bet, ja to nav, - krustojumos pa ietvju vai nomaļu iedomāto turpinājumu. Ja redzamības zonā gājēju pārejas vai krustojuma nav, brauktuvi atļauts šķērsot taisnā leņķī attiecībā pret brauktuves malu vietās, kur ceļš labi pārredzams uz abām pusēm.</p> "));
        paragraphDAOs.add(new ParagraphDAO("23.", " <p class='txt'>23. Vietās, kur ceļu satiksme tiek regulēta, gājējiem jāievēro satiksmes regulētāja vai gājēju luksofora signāli, bet, ja tādu nav, - transportlīdzekļu satiksmes regulēšanas luksofora (turpmāk - luksofors) signāli.</p> "));
        paragraphDAOs.add(new ParagraphDAO("24.", " <p class='txt'>24. Pirms brauktuves šķērsošanas pa gājēju pāreju, kur satiksme netiek regulēta, gājējiem jānovērtē attālums līdz transportlīdzekļiem, kas tuvojas, kā arī jānovērtē to braukšanas ātrums un jāpārliecinās par drošību.</p> "));
        paragraphDAOs.add(new ParagraphDAO("25.", " <p class='txt'>25. Ārpus gājēju pārejas, kur satiksme netiek regulēta, gājēji drīkst iziet uz brauktuves tikai pēc tam, kad ir novērtējuši attālumu līdz transportlīdzekļiem, kas tuvojas, kā arī novērtējuši to braukšanas ātrumu un pārliecinājušies, ka brauktuves šķērsošana nav bīstama un ka netiks traucēta transportlīdzekļu satiksme.</p> "));
        paragraphDAOs.add(new ParagraphDAO("26.", " <p class='txt'>26. Gājēji uz brauktuves nedrīkst kavēties vai bez vajadzības apstāties. Gājējiem, kuri nav paguvuši šķērsot brauktuvi, jāapstājas uz „drošības saliņas“. Gājēji drīkst turpināt šķērsot brauktuvi tikai pēc tam, kad ir pārliecinājušies, ka tas nav bīstami.</p> "));
        paragraphDAOs.add(new ParagraphDAO("27.", " <p class='txt'>27. Gājējiem aizliegts:</p> "));
        paragraphDAOs.add(new ParagraphDAO("27.1.", " <p class='txt'>27.1. šķērsot brauktuvi ārpus gājēju pārejas, ja ceļam ir sadalošā josla, vai vietās, kur uzstādīti nožogojumi gājējiem vai ceļu nožogojumi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("27.2.", " <p class='txt'>27.2. šķērsot brauktuvi ārpus gājēju pārejas, ja ceļam, kur ceļu satiksme notiek divos virzienos, ir četras vai vairākas braukšanas joslas;</p> "));
        paragraphDAOs.add(new ParagraphDAO("27.3.", " <p class='txt'>27.3. pārvietoties pa ceļu, kas apzīmēts ar 548.ceļa zīmi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("27.4.", " <p class='txt'>27.4. iziet uz brauktuves, nepārliecinoties par to, vai netuvojas transportlīdzekļi, aiz stāvoša transportlīdzekļa vai cita šķēršļa, kas ierobežo redzamību.</p> "));
        paragraphDAOs.add(new ParagraphDAO("28.", " <p class='txt'>28. Ja tuvojas operatīvais transportlīdzeklis, gājējiem aizliegts iziet uz brauktuves, bet gājējiem, kuri atrodas uz brauktuves, jādod ceļš minētajam transportlīdzeklim.</p> "));
        paragraphDAOs.add(new ParagraphDAO("29.", " <p class='txt'>29. Gaidīt pasažieru sabiedrisko transportlīdzekli atļauts tikai uz iekāpšanas laukumiem, bet, ja to nav, - uz ietves vai nomales.</p> "));
        paragraphDAOs.add(new ParagraphDAO("30.", " <p class='txt'>30. Tramvaja pieturās, kur nav iekāpšanas laukumu, uziet uz brauktuves, lai iekāptu tramvajā, drīkst tikai tad, kad tramvajs pilnīgi apstājies. Pēc izkāpšanas no tramvaja gājējiem brauktuve jāatbrīvo.</p> "));
        paragraphDAOs.add(new ParagraphDAO("31.", " <p class='txt'>31. Pasažieriem atļauts iekāpt transportlīdzeklī un izkāpt no tā tikai pēc tam, kad transportlīdzeklis pilnīgi apstājies. Tas jādara no ietves, iekāpšanas laukuma vai nomales puses. Ja no ietves, iekāpšanas laukuma vai nomales puses iekāpt bezsliežu transportlīdzeklī vai izkāpt no tā nav iespējams, to drīkst izdarīt no brauktuves puses, ja tas nav bīstami un nerada traucējumus citu transportlīdzekļu braukšanai.</p> "));
        paragraphDAOs.add(new ParagraphDAO("32.", " <p class='txt'>32. Pasažieriem, kuru sēdvietas aprīkotas ar drošības jostām, braukšanas laikā jābūt piesprādzētiem. Motociklu, triciklu, kvadriciklu un mopēdu pasažieriem braukšanas laikā galvā jābūt aizsprādzētai aizsargķiverei.</p> "));
        paragraphDAOs.add(new ParagraphDAO("33.", " <p class='txt'>33. Pasažieriem aizliegts:</p> "));
        paragraphDAOs.add(new ParagraphDAO("33.1.", " <p class='txt'>33.1. braukšanas laikā traucēt transportlīdzekļa vadītāju vai novērst viņa uzmanību;</p> "));
        paragraphDAOs.add(new ParagraphDAO("33.2.", " <p class='txt'>33.2. atvērt braucoša transportlīdzekļa durvis;</p> "));
        paragraphDAOs.add(new ParagraphDAO("33.3.", " <p class='txt'>33.3. atvērt apturēta transportlīdzekļa durvis, ja tas apdraud ceļu satiksmes drošību vai traucē citus ceļu satiksmes dalībniekus;</p> "));
        paragraphDAOs.add(new ParagraphDAO("33.4.", " <p class='txt'>33.4. atstāt sēdvietu bez uzaicinājuma, ja transportlīdzeklis apturēts pēc policijas darbinieka vai robežsarga (pierobežā) pieprasījuma.</p> "));
        paragraphDAOs.add(new ParagraphDAO("34.", " <p id='toc_marker-4' class='Virsraksts'><strong>3. Transportlīdzekļu vadītāju vispārīgie pienākumi</strong></p> <p class='txt'>34. Transportlīdzekļa vadītājam, braucot pa ceļiem, kā arī pa citām vietām, kurās iespējama transportlīdzekļu braukšana, jāievēro šo noteikumu un citu ceļu satiksmi reglamentējošu normatīvo aktu prasības.</p> "));
        paragraphDAOs.add(new ParagraphDAO("35.", " <p class='txt'>35. Mehāniskā transportlīdzekļa vadītājam jābūt klāt šādiem dokumentiem, kas jāiedod pārbaudei pēc policijas darbinieku, robežsargu (uz valsts robežas vai pierobežā) vai muitas darbinieku (muitas zonā) pieprasījuma: </p> "));
        paragraphDAOs.add(new ParagraphDAO("35.1.", " <p class='txt'>35.1. vadītāja apliecība;</p> "));
        paragraphDAOs.add(new ParagraphDAO("35.2.", " <p class='txt'>35.2. transportlīdzekļa reģistrācijas dokumenti;</p> "));
        paragraphDAOs.add(new ParagraphDAO("35.3.", " <p class='txt'>35.3. <span class='Italic'>(svītrots, skat. MK not. 1. 929)</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("35.4.", " <p class='txt'>35.4. dokumenti, kuri saskaņā ar šiem noteikumiem un citiem ceļu satiksmi reglamentējošiem normatīvajiem aktiem transportlīdzekļa vadītājam nepieciešami, lai vadītu attiecīgu transportlīdzekli (operatīvo transportlīdzekli vai tamlīdzīgu transportlīdzekli), pārvadātu attiecīgu kravu (bīstamu, lielgabarīta, smagsvara un tamlīdzīgu kravu) vai pasažierus;</p> "));
        paragraphDAOs.add(new ParagraphDAO("35.5.", " <p class='txt'>35.5. dokuments par veikto valsts tehnisko apskati vai tehnisko kontroli uz ceļiem, ja uz transportlīdzekļa nav attiecīgās vizuālās informācijas (uzlīmes), vai Ceļu satiksmes drošības direkcijas izsniegta vienas dienas atļauja piedalīties ceļu satiksmē, lai veiktu valsts tehnisko apskati.</p> "));
        paragraphDAOs.add(new ParagraphDAO("36.", " <p class='txt'>36. Mopēda vadītājam jābūt klāt šo noteikumu 35.1. un 35.2.apakšpunktā minētajiem dokumentiem un jāiedod tie pārbaudei. Tramvaja un trolejbusa vadītājam jābūt klāt šo noteikumu 35.1. un 35.3.apakšpunktā minētajiem dokumentiem un pēc policijas darbinieku pieprasījuma jāiedod tie pārbaudei.</p> "));
        paragraphDAOs.add(new ParagraphDAO("37.", " <p class='txt'>37. Transportlīdzekļa vadītājam, kas ierodas Latvijā no ārvalstīm, atļauts vadīt transportlīdzekli, ja viņam ir klāt Eiropas Savienības dalībvalsts vai Eiropas Brīvās tirdzniecības asociācijas dalībvalsts izsniegta transportlīdzekļa vadītāja apliecība vai transportlīdzekļa vadītāja apliecība, kurā norādītas 1968.gada Konvencijā par ceļu satiksmi noteiktās transportlīdzekļu kategorijas.</p> "));
        paragraphDAOs.add(new ParagraphDAO("38.", " <p class='txt'>38. Transportlīdzekļa vadītājam ir šādi pienākumi:</p> "));
        paragraphDAOs.add(new ParagraphDAO("38.1.", " <p class='txt'>38.1. pirms izbraukšanas pārbaudīt, vai transportlīdzeklis ir tehniskā kārtībā un vai tas ir aprīkots saskaņā ar šo noteikumu 242., 243. un 244.punktā minētajām prasībām. Pēc policijas darbinieka pieprasījuma jāļauj veikt šī aprīkojuma apskati;</p> "));
        paragraphDAOs.add(new ParagraphDAO("38.2.", " <p class='txt'>38.2. ceļā uzraudzīt transportlīdzekļa tehnisko stāvokli;</p> "));
        paragraphDAOs.add(new ParagraphDAO("38.3.", " <p class='txt'>38.3. pakļauties policijas darbinieka prasībai nogādāt transportlīdzekli tehniskā stāvokļa un tā aprīkojuma kontrolei vai gabarītu, faktiskās masas vai ass slodzes kontrolei, kā arī nodot transportlīdzekli minēto kontroļu veikšanai; </p> "));
        paragraphDAOs.add(new ParagraphDAO("38.4.", " <p class='txt'>38.4. darīt visu iespējamo, lai netiktu apdraudēti citi ceļu satiksmes dalībnieki, it sevišķi mazāk aizsargātie (gājēji un velosipēdu vadītāji);</p> "));
        paragraphDAOs.add(new ParagraphDAO("38.5.", " <p class='txt'>38.5. braucot ar mehānisko transportlīdzekli, kura konstrukcijā paredzētas drošības jostas, būt piesprādzētam un nevest pasažierus, kuri nav piesprādzējušies (tas attiecas uz visiem pasažieriem, kuru sēdvietas aprīkotas ar drošības jostām);</p> "));
        paragraphDAOs.add(new ParagraphDAO("38.6.", " <p class='txt'>38.6. braucot ar motociklu, triciklu, kvadriciklu vai mopēdu, lietot aizsprādzētu aizsargķiveri, kā arī nevest pasažierus, kuriem galvā nav aizsprādzētas aizsargķiveres;</p> "));
        paragraphDAOs.add(new ParagraphDAO("38.7.", " <p class='txt'>38.7. pakļauties muitas amatpersonas prasībai veikt muitas kontroli.</p> "));
        paragraphDAOs.add(new ParagraphDAO("38.8.", " <p class='txt'>38.8. piespiedu apstāšanās gadījumā nepietiekamas redzamības apstākļos un diennakts tumšajā laikā, izejot uz brauktuves vai atrodoties uz nomales, jābūt tērptam apģērbā ar iestrādātiem gaismu atstarojošiem elementiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.", " <p class='txt'>39. Transportlīdzekļa<span class='Italic'> </span>vadītājam aizliegts:</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.1.", " <p class='txt'>39.1. vadīt transportlīdzekli, ja alkohola koncentrācija asinīs pārsniedz:</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.1.1.", " <p class='txt'>39.1.1. 0,2 promiles – transportlīdzekļu vadītājiem, kuriem mehānisko transportlīdzekļu vadīšanas stāžs nepārsniedz divus gadus;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.1.2.", " <p class='txt'>39.1.2. 0,5 promiles – transportlīdzekļu vadītājiem, kuriem mehānisko transportlīdzekļu vadīšanas stāžs ir lielāks par diviem gadiem, kā arī mopēdu un velosipēdu vadītājiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.2.", " <p class='txt'>39.2. vadīt transportlīdzekli narkotisko, psihotropo, toksisko, citu apreibinošu vielu vai arī reakcijas ātrumu un uzmanību samazinošu medikamentu ietekmē;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.3.", " <p class='txt'>39.3. vadīt transportlīdzekli, esot slimam vai arī tādā mērā nogurušam, ka tas var ietekmēt transportlīdzekļa vadītāja darbspējas un ceļu satiksmes drošību;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.4.", " <p class='txt'>39.4. vadīt transportlīdzekli, ja nav atbilstošas kategorijas (1.pielikums) transportlīdzekļu vadīšanas tiesību;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.5.", " <p class='txt'>39.5. vadīt transportlīdzekli, ja ir stājies spēkā transportlīdzekļu vadīšanas tiesību izmantošanas aizliegums;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.6.", " <p class='txt'>39.6. lietot alkoholiskus dzērienus, narkotiskas vai psihotropas vielas pēc ceļu satiksmes negadījuma, kā arī pēc tam, kad transportlīdzeklis tiek apturēts pēc policijas darbinieka vai robežsarga (uz valsts robežas vai pierobežā) pieprasījuma, līdz reibuma stāvokļa konstatēšanai nepieciešamās pārbaudes izdarīšanai vai atbrīvošanai noteiktā kārtībā no šādas pārbaudes, kā arī atteikties no šādas pārbaudes;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.7.", " <p class='txt'>39.7. nodot transportlīdzekli vadīt personai, kurai nav klāt atbilstošas kategorijas transportlīdzekļa vadītāja apliecības, izņemot mācību braucienus saskaņā ar šo noteikumu 215.punktu un vadīšanas iemaņu pārbaudei normatīvajos aktos par transportlīdzekļu vadītāja kvalifikācijas iegūšanu noteiktajā kārtībā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.8.", " <p class='txt'>39.8. bez uzaicinājuma atstāt transportlīdzekļa vadītāja vietu, ja transportlīdzeklis apturēts pēc policijas darbinieka vai robežsarga (pierobežā) pieprasījuma;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.9.", " <p class='txt'>39.9. lietot tālruni, ja transportlīdzeklis atrodas kustībā (izņemot gadījumus, kad transportlīdzekļa vadītājam, lai to lietotu, nav jāņem rokā tālruņa klausule).</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.10.", " <p class='txt'>39.10. atvērt apturēta transportlīdzekļa durvis, ja tas apdraud ceļu satiksmes drošību vai traucē citus ceļu satiksmes dalībniekus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.", " <p class='txt'>39.<span class='superscript'>1</span> Aizliegts mācīties vadīt transportlīdzekli:</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.", " <p class='txt'>39.<span class='superscript'>1</span> 1. ja alkohola koncentrācija asinīs pārsniedz 0,2 promiles;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.", " <p class='txt'>39.<span class='superscript'>1</span> 2. atrodoties narkotisko, psihotropo, toksisko, citu apreibinošu vielu vai arī reakcijas ātrumu un uzmanību samazinošu medikamentu ietekmē;</p> "));
        paragraphDAOs.add(new ParagraphDAO("39.", " <p class='txt'>39.<span class='superscript'>1</span> 3. esot slimam vai arī tādā mērā nogurušam, ka tas var ietekmēt darbspējas un ceļu satiksmes drošību.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Neapdraudi citus ceļu satiksmes dalībniekus un vadi transportlīdzekli, turot rokas uz stūres rata! Bez vajadzības neizdari pēkšņus manevrus!]</span></p>"));
        paragraphDAOs.add(new ParagraphDAO("40.", " <p id='toc_marker-5' class='Virsraksts'><strong>4. Transportlīdzekļa vadītāja pienākumi īpašos gadījumos</strong></p> <p class='txt'>40. Ja tuvojas operatīvais transportlīdzeklis ar iedegtu mirgojošu zilu bākuguni un ieslēgtu speciālu skaņas signālu, citiem ceļu satiksmes dalībniekiem jādod tam ceļš.</p> "));
        paragraphDAOs.add(new ParagraphDAO("41.", " <p class='txt'>41. Ja tuvojas operatīvais transportlīdzeklis ar iedegtu mirgojošu zilu un sarkanu bākuguni un ieslēgtu speciālu skaņas signālu, citiem tajā pašā virzienā braucošo transportlīdzekļu vadītājiem un pretim braucošo transportlīdzekļu vadītājiem (ja nav konstruktīvi izveidota ceļa elementa, kas atdala brauktuvi, piemēram, barjeras, sadalošās joslas) jādod tam ceļš, apturot transportlīdzekli pie ietves vai uz nomales, vai, ja tā aizņemta, - iespējami tuvāk brauktuves labajai malai. Transportlīdzekļu vadītājiem, kas tuvojas krustojumam (ceļam) no cita virziena ceļiem, jāaptur transportlīdzeklis pirms šķērsojamās brauktuves. Uzsākt (turpināt) braukt drīkst tikai pēc tam, kad ir pabraucis garām transportlīdzeklis ar iedegtu mirgojošu zilu bākuguni, kurš brauc aiz pavadāmā transportlīdzekļa (kolonnas).</p> "));
        paragraphDAOs.add(new ParagraphDAO("42.", " <p class='txt'>42. Operatīvie transportlīdzekļi drīkst piedalīties ceļu satiksmē, tikai pildot neatliekamus dienesta uzdevumus, lai:</p> "));
        paragraphDAOs.add(new ParagraphDAO("42.1.", " <p class='txt'>42.1. glābtu cilvēka dzīvību, materiālās vērtības vai novērstu personai smagus veselības traucējumus un kaitējumu videi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("42.2.", " <p class='txt'>42.2. novērstu draudus sabiedriskajai drošībai un kārtībai (arī braucot uz noziedzīga nodarījuma (notikuma) vietu);</p> "));
        paragraphDAOs.add(new ParagraphDAO("42.3.", " <p class='txt'>42.3. sekotu no operatīvā transportlīdzekļa bēgošai personai;</p> "));
        paragraphDAOs.add(new ParagraphDAO("42.4.", " <p class='txt'>42.4. pārvadātu materiālās vērtības sevišķi lielos apmēros;</p> "));
        paragraphDAOs.add(new ParagraphDAO("42.5.", " <p class='txt'>42.5. pavadītu citus transportlīdzekļus (kolonnas);</p> "));
        paragraphDAOs.add(new ParagraphDAO("42.6.", " <p class='txt'>42.6. nodrošinātu aizsargājamās amatpersonas drošu un netraucētu pārvietošanos.</p> "));
        paragraphDAOs.add(new ParagraphDAO("43.", " <p class='txt'>43. Noteikumu 42.punktā minētajos gadījumos operatīvo transportlīdzekļu vadītāji drīkst neievērot šo noteikumu prasības, izņemot šo noteikumu 35.punktā, 39.1., 39.2., 39.3., 39.4., 39.5., 39.6. un 39.7.apakšpunktā un 50.punktā minētās prasības un prasības, kas tiek noteiktas ar satiksmes regulētāja signāliem. Operatīvā transportlīdzekļa vadītājam jādara viss iespējamais, lai tiktu garantēta ceļu satiksmes drošība. Minētās tiesības un pienākumi ir arī tiem transportlīdzekļu vadītājiem, kuru transportlīdzekļus pavada operatīvie transportlīdzekļi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("44.", " <p class='txt'>44. Pirms pavadāmā transportlīdzekļa (kolonnas) braucošajam operatīvajam transportlīdzeklim jābrauc ar iedegtu mirgojošu zilu un sarkanu bākuguni un ieslēgtu speciālu skaņas signālu. Aiz pavadāmā transportlīdzekļa (kolonnas) braucošajam transportlīdzeklim jābrauc ar iedegtu mirgojošu zilu bākuguni.</p> "));
        paragraphDAOs.add(new ParagraphDAO("44.", " <p class='txt'>44.<span class='superscript'>1 </span>Lai nodrošinātu aizsargājamās amatpersonas drošu un netraucētu pārvietošanos, operatīvajam transportlīdzeklim, kas veic aizsargājamo amatpersonu aizsardzību (apsardzi), ir tiesības, izbraucot no kolonnas, aizšķērsot ceļu transportlīdzeklim, kurš, pārkāpjot ceļu satiksmi reglamentējošo normatīvo aktu prasības, rada draudus aizsargājamās amatpersonas dzīvībai vai veselībai.</p> "));
        paragraphDAOs.add(new ParagraphDAO("45.", " <p class='txt'>45. Pavadot transportlīdzekli (kolonnu), braukšanas ātrums apdzīvotās vietās nedrīkst pārsniegt maksimālo atļauto braukšanas ātrumu vairāk par 10 km/h, bet ārpus apdzīvotām vietām - vairāk par 20 km/h.</p> "));
        paragraphDAOs.add(new ParagraphDAO("46.", " <p class='txt'>46. Iedegtai mirgojošai zilai bākugunij bez ieslēgta speciāla skaņas signāla ir tikai brīdinoša nozīme. To var izmantot, brīdinot par nelaimes gadījumu (piemēram, ceļu satiksmes negadījumu), kā arī pavadot transportlīdzekli (kolonnu). Transportlīdzekļu vadītāji, kuru transportlīdzekļiem ir iedegta mirgojoša zila bākuguns bez ieslēgta speciāla skaņas signāla, nedrīkst pārkāpt šo noteikumu prasības, izņemot apstāšanās un stāvēšanas noteikumus, kā arī šo noteikumu 111.punktā minēto aizliegumu mehāniskajiem transportlīdzekļiem braukt pa ietvēm, gājēju ceļiem, velosipēdu ceļiem, kā arī gājēju un velosipēdu ceļiem. Transportlīdzekļu vadītāji, kuru transportlīdzekļiem ir speciāls krāsojums un piešķirts operatīvo transportlīdzekļu statuss, bez iedegtas mirgojošas zilas bākuguns un bez ieslēgta speciāla skaņas signāla nedrīkst pārkāpt šo noteikumu prasības, izņemot šo noteikumu 135.4., 135.6., 135.7., 135.9., 135.10., 135.11., 135.12., 136.2., 136.3. un 136.4.apakšpunktā minētos aizliegumus, un tiem ir atļauts braukt pa pasažieru sabiedriskajiem transportlīdzekļiem paredzētajām joslām.</p> "));
        paragraphDAOs.add(new ParagraphDAO("47.", " <p class='txt'>47. Iedegta mirgojoša oranža (dzeltena) bākuguns nedod priekšroku. To lieto, lai pievērstu citu ceļu satiksmes dalībnieku uzmanību un brīdinātu tos par iespējamām briesmām. Mirgojoša oranža (dzeltena) bākuguns obligāti jāiededz transportlīdzekļu vadītājiem, kas:</p> "));
        paragraphDAOs.add(new ParagraphDAO("47.1.", " <p class='txt'>47.1. pavada transportlīdzekļus, kuri pārvadā sevišķi smagas vai lielgabarīta kravas;</p> "));
        paragraphDAOs.add(new ParagraphDAO("47.2.", " <p class='txt'>47.2. pavada tādu transportlīdzekļu kolonnas, kuri pārvadā sprāgstvielas, radioaktīvas vielas vai spēcīgas iedarbības indīgas vielas;</p> "));
        paragraphDAOs.add(new ParagraphDAO("47.3.", " <p class='txt'>47.3. uz ceļa veic ceļa uzturēšanas, kā arī citus remonta vai avārijas seku likvidēšanas darbus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("47.4.", " <p class='txt'>47.4. vada traktortehniku ar divām piekabēm.</p> "));
        paragraphDAOs.add(new ParagraphDAO("48.", " <p class='txt'>48. Ja, veicot ceļa uzturēšanas, remonta, avārijas vai ceļu satiksmes negadījuma seku likvidēšanas darbus, transportlīdzekļu vadītāji iedeguši mirgojošu oranžu (dzeltenu) bākuguni, viņi drīkst neņemt vērā ar ceļa zīmēm un ceļa apzīmējumiem noteiktās prasības un atkāpties no šo noteikumu 98., 99., 100., 101., 105., 106., 108. un 110.punktā minētajām prasībām, bet, veicot ceļa uzturēšanas darbus, arī atrasties tajā ceļa pusē, kas paredzēta braukšanai pretējā virzienā, ja tiek nodrošināta ceļu satiksmes drošība. Citi transportlīdzekļu vadītāji nedrīkst traucēt minēto transportlīdzekļu vadītāju darbu, un viņiem jābrauc ar tādu ātrumu, lai, ja nepieciešams, varētu nekavējoties apturēt transportlīdzekli.</p> "));
        paragraphDAOs.add(new ParagraphDAO("49.", " <p class='txt'>49. Mirgojošu oranžu (dzeltenu) bākuguni ceļu satiksmē atļauts iedegt arī tiem transportlīdzekļu vadītājiem, kuri vada transportlīdzekļus, kas pēc savas konstrukcijas ir paredzēti specifiskiem uzdevumiem (lielgabarīta automobiļi, traktortehnika u.tml.), transportlīdzekļus, kas tiek izmantoti specifiskiem uzdevumiem (tehniskās palīdzības<span class='Italic'>, </span>ceļu uzraudzības automobiļi u.tml.), kā arī komunālo dienestu (elektroapgādes, siltumapgādes u.tml.) transportlīdzekļus, ja šādu transportlīdzekļu atrašanās uz ceļa var radīt traucējumus vai bīstamību citiem ceļu satiksmes dalībniekiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("50.", " <p id='toc_marker-6' class='Virsraksts'><strong>5. Transportlīdzekļa vadītāja rīcība pēc ceļu satiksmes negadījuma</strong></p><p class='txt'>50. Ja ceļu satiksmes negadījumā ir cietuši cilvēki vai nodarīti bojājumi trešās personas mantai (par trešās personas mantu nav uzskatāms ceļu satiksmes negadījumā iesaistītais transportlīdzeklis, kura īpašnieks nav transportlīdzekļa vadītājs), kā arī tad, ja transportlīdzekļiem radušies bojājumi, kuru dēļ tie nevar vai tiem aizliegts braukt, transportlīdzekļa vadītājs:</p> "));
        paragraphDAOs.add(new ParagraphDAO("50.1.", " <p class='txt'>50.1. nekavējoties apstājas un paliek negadījuma vietā, iededz avārijas gaismas signalizāciju un uzstāda avārijas zīmi, bet, ja tas nav iespējams, citādā veidā brīdina pārējos ceļu satiksmes dalībniekus par ceļu satiksmes negadījumu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("50.2.", " <p class='txt'>50.2. dara visu iespējamo, lai cietušajam sniegtu pirmo palīdzību, izsauc neatliekamo medicīnisko palīdzību vai glābšanas dienestu, bet, ja tas nav iespējams, ar savu vai citu garām braucošu transportlīdzekli nogādā cietušo tuvākajā ārstniecības iestādē un pats atgriežas negadījuma vietā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("50.3.", " <p class='txt'>50.3. dara visu iespējamo, lai negadījuma vietā saglabātu notikuma pēdas, pieraksta aculiecinieku vārdu, uzvārdu un adresi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("50.4.", " <p class='txt'>50.4. par ceļu satiksmes negadījumu paziņo policijai un, ja nepieciešams, glābšanas dienestam un tālāk rīkojas pēc to norādījumiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("51.", " <p class='txt'>51. Ja ceļu satiksmes negadījumā nav cietuši cilvēki, nav nodarīti bojājumi trešās personas mantai, kā arī transportlīdzekļiem nav radušies bojājumi, kuru dēļ tie nevar vai tiem aizliegts braukt, transportlīdzekļa vadītājs:</p> "));
        paragraphDAOs.add(new ParagraphDAO("51.1.", " <p class='txt'>51.1. nekavējoties apstājas un paliek negadījuma vietā, iededz avārijas gaismas signalizāciju un uzstāda avārijas zīmi, bet, ja tas nav iespējams, citādā veidā brīdina pārējos ceļu satiksmes dalībniekus par ceļu satiksmes negadījumu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("51.2.", " <p class='txt'>51.2. vienojas ar otra ceļu satiksmes negadījumā iesaistītā transportlīdzekļa vadītāju par visiem būtiskajiem negadījuma apstākļiem, aizpilda saskaņoto paziņojumu un, neziņojot policijai par negadījumu, atstāj negadījuma vietu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("52.", " <p class='txt'>52. Ja šo noteikumu 51.punktā minētajā ceļu satiksmes negadījumā ir iesaistīti vairāk nekā divi transportlīdzekļi vai ja transportlīdzekļu vadītāji nespēj vienoties par visiem būtiskajiem negadījuma apstākļiem un aizpildīt saskaņoto paziņojumu, transportlīdzekļu vadītāji: </p> "));
        paragraphDAOs.add(new ParagraphDAO("52.1.", " <p class='txt'>52.1. nekavējoties apstājas un paliek negadījuma vietā, iededz avārijas gaismas signalizāciju un uzstāda avārijas zīmi, bet, ja tas nav iespējams, citādā veidā brīdina pārējos ceļu satiksmes dalībniekus par ceļu satiksmes negadījumu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("52.2.", " <p class='txt'>52.2. par ceļu satiksmes negadījumu paziņo policijai un, ja nepieciešams, glābšanas dienestam un tālāk rīkojas pēc to norādījumiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("53.", " <p class='txt'>53. Ja šo noteikumu<span class='Italic'> </span>50., 51. vai 52.punktā minētajā ceļu satiksmes negadījumā iesaistītie transportlīdzekļi neļauj vai traucē citiem transportlīdzekļiem pabraukt garām ceļu satiksmes negadījuma vietai un tas nav pretrunā ar policijas norādījumiem, transportlīdzekļu vadītāji atbrīvo brauktuvi, pirms tam fiksējot ceļu satiksmes negadījumā iesaistīto transportlīdzekļu un citu priekšmetu stāvokli.</p> "));
        paragraphDAOs.add(new ParagraphDAO("54.", " <p class='txt'>54. Ja ceļu satiksmes negadījumā ir iesaistīts tikai viens transportlīdzeklis, nav cietuši cilvēki un nav nodarīti bojājumi trešās personas mantai, transportlīdzekļa vadītājs drīkst atstāt negadījuma vietu, neziņojot policijai par negadījumu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("55.", " <p id='toc_marker-7' class='Virsraksts'><strong>6. Ceļu satiksmes regulēšana</strong></p> <p class='txt'>55. Satiksmi regulē:</p> "));
        paragraphDAOs.add(new ParagraphDAO("55.1.", " <p class='txt'>55.1. ar luksoforiem (2.pielikums), kuriem ir zaļas, dzeltenas, sarkanas un baltas gaismas signāli. Luksoforus izgatavo un uzstāda atbilstoši valsts standartu LVS EN 12368:2000 “Ceļu satiksmes regulēšanas tehniskie līdzekļi. Luksofori” un LVS 370:2004 “Ceļu satiksmes regulēšanas luksofori” prasībām;</p> "));
        paragraphDAOs.add(new ParagraphDAO("55.2.", " <p class='txt'>55.2. satiksmes regulētāji.</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.", " <p class='txt'>56. Luksoforu signāliem ir šāda nozīme:</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.1.", " <p class='txt'>56.1. zaļš signāls atļauj kustību. Lai ceļu satiksmes dalībniekus informētu par to, ka zaļā signāla darbības laiks beidzas un drīz iedegsies aizlieguma signāls, var lietot zaļu mirgojošu signālu. Lai ceļu satiksmes dalībniekus informētu par laiku (sekundēs), kas atlicis līdz zaļā signāla darbības laika beigām, var lietot informācijas tablo;</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.2.", " <p class='txt'>56.2. dzeltens signāls aizliedz kustību, izņemot šo noteikumu 65.punktā minētos gadījumus, un brīdina par gaidāmo signālu maiņu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.3.", " <p class='txt'>56.3. dzeltens mirgojošs signāls vai divi pārmaiņus mirgojoši dzelteni signāli atļauj kustību un informē par neregulējamu krustojumu, gājēju pāreju, ceļu darbu vietu vai citu bīstamu ceļa posmu. Lai informētu par neregulējamu gājēju pāreju vai vietu, kur satiksme netiek regulēta un brauktuvi šķērso velosipēdu ceļš vai gājēju un velosipēdu ceļš, var lietot dzeltenu mirgojošu signālu vai divus pārmaiņus mirgojošus dzeltenus signālus ar attiecīgi cilvēka siluetu, velosipēda simbolu vai cilvēka siluetu un velosipēda simbolu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.4.", " <p class='txt'>56.4. sarkans signāls (arī mirgojošs) vai divi pārmaiņus mirgojoši sarkani signāli aizliedz kustību. Lai informētu par sarkanā signāla degšanas ilgumu, zem luksofora vai tā sekcijas var uzstādīt paskaidrojoša satura plāksnīti;</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.5.", " <p class='txt'>56.5. sarkans signāls vienlaikus ar dzeltenu signālu aizliedz kustību un informē, ka iedegsies zaļais signāls;</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.6.", " <p class='txt'>56.6. zaļas bultas (bultu) signāls uz melna fona atļauj braukt norādītajā virzienā (virzienos). Tāda pati nozīme ir zaļai bultai luksofora papildsekcijā, turklāt bulta, kas atļauj nogriezties pa kreisi, atļauj arī apgriezties braukšanai pretējā virzienā. Ja zaļās bultas signāls luksofora papildsekcijā ir izslēgts, transportlīdzekļu braukšana attiecīgajā virzienā ir aizliegta;</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.7.", " <p class='txt'>56.7. melna bulta (bultas) uz sarkana un dzeltena signāla fona nemaina šo signālu nozīmi. Tās informē par atļautajiem braukšanas virzieniem, kad iedegsies zaļas bultas (bultu) signāls uz melna fona;</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.8.", " <p class='txt'>56.8. melna bulta (bultas) uz zaļa signāla fona norāda, ka luksoforam ir papildsekcija, kā arī atļauj braukt bultas (bultu) norādītajos virzienos</p> "));
        paragraphDAOs.add(new ParagraphDAO("56.9.", " <p class='txt'>56.9. sarkana krusta signāls uz melna fona informē par to, ka pretim braucošo transportlīdzekļu vadītājiem luksoforā deg aizliedzošais signāls..</p> "));
        paragraphDAOs.add(new ParagraphDAO("57.", " <p class='txt'>57. Luksofora signāls ar cilvēka siluetu, velosipēda simbolu vai cilvēka siluetu un velosipēda simbolu attiecas tikai uz šiem ceļu satiksmes dalībniekiem. Zaļais signāls atļauj kustību, bet sarkanais - aizliedz.</p> "));
        paragraphDAOs.add(new ParagraphDAO("58.", " <p class='txt'>58. Lai informētu neredzīgos gājējus par luksoforu gaismas maiņas signālu pogas atrašanās vietu, kā arī lai informētu gājējus un velosipēdu vadītājus par luksofora gaismas signālu, kas atļauj šķērsot brauktuvi, gājēju vai gājēju un velosipēdu luksofora gaismas signālus var papildināt ar skaņas signāliem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("59.", " <p class='txt'>59. Transportlīdzekļu satiksmes regulēšanai joslā var lietot luksoforus ar sarkanu „X“ veida signālu, zaļu lejup vērstas bultas signālu un dzeltenu lejup slīpi vērstas bultas signālu (luksoforus ar ilgstošu signāla degšanas periodu satiksmes regulēšanai joslā). Šiem signāliem ir šāda nozīme:</p> "));
        paragraphDAOs.add(new ParagraphDAO("59.1.", " <p class='txt'>59.1. sarkans „X“ veida signāls aizliedz braukt pa joslu, virs kuras tas novietots;</p> "));
        paragraphDAOs.add(new ParagraphDAO("59.2.", " <p class='txt'>59.2. zaļš lejup vērstas bultas signāls atļauj braukt pa joslu, virs kuras tas novietots; </p> "));
        paragraphDAOs.add(new ParagraphDAO("59.3.", " <p class='txt'>59.3. dzeltens lejup slīpi vērstas bultas signāls norāda, ka nekavējoties jāpārkārtojas uz bultas norādīto braukšanas joslu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("60.", " <p class='txt'>60. Ja šo noteikumu 59.punktā minētie luksofora signāli ir izslēgti, joslā, virs kuras luksofors novietots, iebraukt aizliegts.</p> "));
        paragraphDAOs.add(new ParagraphDAO("61.", " <p class='txt'>61. Tramvaju satiksmes regulēšanai var lietot luksoforus ar četriem baltiem gaismas signāliem, kas izvietoti T burta veidā. Braukt atļauts tikai tad, ja vienlaikus ar apakšējo signālu iedegts arī viens vai vairāki augšējie signāli, no kuriem kreisais signāls atļauj braukt pa kreisi, vidējais - taisni, labais - pa labi. Ja iedegti tikai 3 augšējie signāli, braukt aizliegts.</p> "));
        paragraphDAOs.add(new ParagraphDAO("62.", " <p class='txt'>62. Satiksmes regulētāju signāliem ir šāda nozīme:</p> "));
        paragraphDAOs.add(new ParagraphDAO("62.1.", " <p class='txt'>62.1. rokas paceltas sānis vai nolaistas lejā (skat. 5. att.):</p> "));
        paragraphDAOs.add(new ParagraphDAO("62.1.1.", " <p class='txt'>62.1.1. no kreisā un labā sāna puses tramvajiem atļauts braukt taisni, bezsliežu transportlīdzekļiem - taisni un pa labi, gājējiem atļauts šķērsot brauktuvi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("62.1.2.", " <p class='txt'>62.1.2. no priekšpuses un no mugurpuses visiem transportlīdzekļiem braukt aizliegts, gājējiem brauktuvi šķērsot aizliegts;</p> "));
        paragraphDAOs.add(new ParagraphDAO("62.2.", " <p class='txt'>62.2. labā roka iztaisnota priekšā (skat. 6. att.):</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/6.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-3'>6. att.</p> " +
                " </div> " +
                " </div> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/5.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-3'>5. att.</p> " +
                " </div> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("62.2.1.", " <p class='txt'>62.2.1. no kreisā sāna puses tramvajam atļauts braukt pa kreisi, bezsliežu transportlīdzekļiem - visos virzienos;</p> "));
        paragraphDAOs.add(new ParagraphDAO("62.2.2.", " <p class='txt'>62.2.2. no priekšpuses visiem transportlīdzekļiem atļauts braukt tikai pa labi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("62.2.3.", " <p class='txt'>62.2.3. no labā sāna puses un no mugurpuses visiem transportlīdzekļiem braukt aizliegts;</p> "));
        paragraphDAOs.add(new ParagraphDAO("62.2.4.", " <p class='txt'>62.2.4. gājējiem atļauts šķērsot brauktuvi aiz satiksmes regulētāja muguras;</p> "));
        paragraphDAOs.add(new ParagraphDAO("62.3.", " <p class='txt'>62.3. roka pacelta augšā (skat. 7. att.) - transportlīdzekļiem braukt un gājējiem brauktuvi šķērsot aizliegts, izņemot šo noteikumu 65. punktā minētos gadījumus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("63.", " <p class='txt'>63. Satiksmes regulētājs var dot arī citus transportlīdzekļu vadītājiem un gājējiem saprotamus signālus (skat. 8. att.). Labākai redzamībai satiksmes regulētājs var lietot zizli (disku ar sarkanu gaismas atstarotāju). Lai pievērstu ceļu satiksmes dalībnieku uzmanību, satiksmes regulētājs var dot svilpes signālu.</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/7.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-3'>7. att.</p> " +
                " </div> " +
                " </div> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/8.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-3'>8. att.</p> " +
                " </div> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("64.", " <p class='txt'>64. Ja satiksmes regulētāja vai luksofora signāli, izņemot šo noteikumu 59.punktā minēto luksoforu, braukt aizliedz, transportlīdzekļa vadītājam jāaptur transportlīdzeklis tieši pirms stoplīnijas (929.ceļa apzīmējums) vai 541. ceļa zīmes. Ja nav stoplīnijas (929.ceļa apzīmējums) vai 541.ceļa zīmes, transportlīdzeklis jāaptur šādās vietās:</p> "));
        paragraphDAOs.add(new ParagraphDAO("64.1.", " <p class='txt'>64.1. pirms krustojumiem un citos ceļa posmos - pirms gājēju pārejas, bet vietās, kur tās nav, - pirms luksofora (ja braukt aizliedz satiksmes regulētāja signāls, - pirms brauktuvju krustošanās vietas) tā, lai netraucētu citus transportlīdzekļus un gājējus, kuru kustība ir atļauta;</p> "));
        paragraphDAOs.add(new ParagraphDAO("64.2.", " <p class='txt'>64.2. pirms dzelzceļa pārbrauktuvēm - saskaņā ar šo noteikumu 169.punktu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("65.", " <p class='txt'>65. Ja, iedegoties dzeltenajam signālam vai brīdī, kad satiksmes regulētājs paceļ roku augšā, transportlīdzekļa vadītājs transportlīdzekli šo noteikumu 64.punktā noteiktajās vietās var apturēt, tikai strauji bremzējot, šī transportlīdzekļa vadītājs drīkst ceļu turpināt, neapturot transportlīdzekli. Gājējiem, kuri šajā brīdī atrodas uz brauktuves, jāpabeidz to šķērsot vai jāapstājas uz „drošības saliņas“.</p> "));
        paragraphDAOs.add(new ParagraphDAO("66.", " <p class='txt'>66. Transportlīdzekļu vadītājiem un gājējiem jāpakļaujas satiksmes regulētāja signāliem un norādījumiem arī tad, ja tie ir pretrunā ar luksofora signālu, ceļa zīmju vai ceļa apzīmējumu prasībām.</p> "));
        paragraphDAOs.add(new ParagraphDAO("67.", " <p class='txt'>67. Tuvojoties stāvošam transportlīdzeklim, kuram ir iedegta mirgojoša zila bākuguns, transportlīdzekļa vadītājam jābrauc ar tādu ātrumu, lai, ja nepieciešams, varētu nekavējoties apturēt transportlīdzekli.</p> "));
        paragraphDAOs.add(new ParagraphDAO("68.", " <p class='txt'>68. Transportlīdzekļa vadītājs aptur transportlīdzekli, ja policijas darbinieks, robežsargs (uz valsts robežas vai pierobežā) vai muitas amatpersona (muitas zonā) dod norādījumu apturēt transportlīdzekli:</p> "));
        paragraphDAOs.add(new ParagraphDAO("68.1.", " <p class='txt'>68.1. ar skaļruņa palīdzību vai uz konkrēto transportlīdzekļa vadītāju vērstas rokas vai zižļa svārstveida kustību no policijas, Valsts robežsardzes (uz valsts robežas vai pierobežā) vai Valsts ieņēmumu dienesta automobiļa vai motocikla, kam ir valsts standartā LVS 63:2009 “Operatīvie transportlīdzekļi, krāsojums un aprīkojums” noteiktais operatīvā transportlīdzekļa krāsojums vai tā nav;</p> "));
        paragraphDAOs.add(new ParagraphDAO("68.2.", " <p class='txt'>68.2. ar horizontāli vērstu roku vai zizli, atrodoties uz (pie) brauktuves. Vienlaikus ar otru roku var dot norādījumus, kuram konkrēti transportlīdzekļa vadītājam un kur jāaptur transportlīdzeklis.</p> "));
        paragraphDAOs.add(new ParagraphDAO("69.", " <p class='txt'>69. Amatpersonai, kas dod norādījumu apstādināt transportlīdzekli, jābūt tērptai formas tērpā ar gaismu atstarojoša materiāla elementiem un atšķirības zīmi. Ja apstādināšana notiek no šo noteikumu 68.1.apakšpunktā minētajiem transportlīdzekļiem, gaismu atstarojoša materiāla elementi nav obligāti.</p> "));
        paragraphDAOs.add(new ParagraphDAO("70.", " <p id='toc_marker-8' class='Virsraksts'><strong>7. Braukšanas sākšana un braukšanas virziena maiņa</strong></p><p class='txt'>70. Pirms jebkura manevra - braukšanas sākšanas, pārkārtošanās, nogriešanās, apgriešanās braukšanai pretējā virzienā, apdzīšanas, transportlīdzekļa apturēšanas u.tml. - transportlīdzekļa vadītājam jāpārliecinās par ceļu satiksmes drošību un par to, vai netiks traucēti citi ceļu satiksmes dalībnieki, jādod brīdinājuma signāls ar gaismas virzienrādītājiem. Ja gaismas virzienrādītāju nav vai tie nedarbojas, brīdinājuma signāls jādod ar roku atbilstoši manevram.</p> "));
        paragraphDAOs.add(new ParagraphDAO("71.", " <p class='txt'>71. Kreisā pagrieziena signālam atbilst plecu līmenī sānis pacelta iztaisnota kreisā roka vai arī sānis pacelta un elkonī taisnā leņķī augšup saliekta labā roka (skat. 9. att.).</p> "));
        paragraphDAOs.add(new ParagraphDAO("72.", " <p class='txt'>72. Labā pagrieziena signālam atbilst plecu līmenī sānis pacelta iztaisnota labā roka vai arī sānis pacelta un elkonī taisnā leņķī augšup saliekta kreisā roka (skat. 9. att.).</p> "));
        paragraphDAOs.add(new ParagraphDAO("73.", " <p class='txt'>73. Bremzēšanas signālam atbilst augšup pacelta kreisā vai labā roka (skat. 10. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='txt para-style-override-2'>9. att.</p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/9.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/10.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>10. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("74.", " <p class='txt'>74. Signāls ar roku jārāda nepārprotami, lai citi ceļu satiksmes dalībnieki to varētu skaidri redzēt.</p> "));
        paragraphDAOs.add(new ParagraphDAO("75.", " <p class='txt'>75. Signāls ar gaismas virzienrādītāju vai ar roku jārāda laikus pirms manevra sākuma un jāpārtrauc tūlīt pēc tā pabeigšanas (signāla rādīšanu ar roku var pārtraukt tieši pirms manevra). Signāls nav jārāda, ja tas var maldināt citus ceļu satiksmes dalībniekus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("76.", " <p class='txt'>76. Pirms nogriešanās pa kreisi vai apgriešanās braukšanai pretējā virzienā brīdinājuma signāls jārāda tikai pēc tam, kad transportlīdzekļa vadītājs pārliecinājies, ka viņš netraucēs nevienu aiz viņa braucošu transportlīdzekļa vadītāju, kas uzsācis apdzīšanu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("77.", " <p class='txt'>77. Signāla lietošana nedod transportlīdzekļa vadītājam priekšroku un neatbrīvo viņu no pienākuma ievērot piesardzību.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Spogulis – signāls – skatiens – manevrs.]</span></p>"));
        paragraphDAOs.add(new ParagraphDAO("78.", " <p class='txt'>78. Izbraucot uz ceļa no blakusteritorijām (pagalmiem, stāvvietām, degvielas uzpildes stacijām, uzņēmumiem u.tml.), transportlīdzekļa vadītājam jādod ceļš gājējiem un citiem ceļu satiksmes dalībniekiem, kas virzās pa ceļu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("79.", " <p class='txt'>79. Nobraucot no ceļa uz blakusteritoriju, transportlīdzekļa vadītājam jādod ceļš gājējiem un citiem ceļu satiksmes dalībniekiem, kuru ceļu viņš šķērso.</p> "));
        paragraphDAOs.add(new ParagraphDAO("80.", " <p class='txt'>80. Apdzīvotās vietās transportlīdzekļa vadītājam jādod ceļš trolejbusiem un autobusiem, kuri sāk braukt no apzīmētas pieturas. Trolejbusu un autobusu vadītāji drīkst sākt braukt no pieturas tikai tad, kad ir pārliecinājušies, ka viņiem tiek dots ceļš (skat. 11. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/11.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>11. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("81.", " <p class='txt'>81. Pārkārtojoties transportlīdzekļa vadītājam jādod ceļš transportlīdzekļiem, kas, nemainot braukšanas virzienu, brauc blakus (skat. 12. att.). Vienā virzienā braucošiem transportlīdzekļiem pārkārtojoties vienlaikus, transportlīdzekļa vadītājam jādod ceļš transportlīdzeklim, kas atrodas pa labi (skat. 13. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/12.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>12. att.</p> " +
                " </div> " +
                " </div> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/13.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>13. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("82.", " <p class='txt'>82. Ārpus krustojumiem, kur tramvaja sliežu ceļa klātne šķērso bezsliežu transportlīdzekļu brauktuvi, tramvajam ir priekšroka.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Ja pa ceļiem ar vairākām viena virziena braukšanas joslām nepārtraukta braukšana kādā no joslām nav iespējama (šķērslis, ceļu satiksmes negadījums u. tml.), ievēro principu – transportlīdzekļi brauc pa vienam no katras joslas.]</span></p>"));
        paragraphDAOs.add(new ParagraphDAO("83.", " <p class='txt'>83. Pirms nogriešanās pa labi, pa kreisi vai apgriešanās braukšanai pretējā virzienā transportlīdzekļa vadītājam uz bezsliežu transportlīdzekļiem paredzētās brauktuves laikus jāieņem attiecīgs malējais stāvoklis, kas paredzēts braukšanai attiecīgajā virzienā, izņemot gadījumus, kad jānogriežas, lai iebrauktu krustojumā, kurā jābrauc pa loku (409. ceļa zīme).</p> "));
        paragraphDAOs.add(new ParagraphDAO("84.", " <p class='txt'>84. Ja brauktuves vidū vienā līmenī atrodas tramvaja sliežu ceļa klātne, jānogriežas pa kreisi vai jāapgriežas braukšanai pretējā virzienā no kreisā malējā stāvokļa, kas ieņemts uz tā paša virziena tramvaja sliežu ceļa klātnes (skat. 14. un 15. att.)</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/14.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>14. att.</p> " +
                " </div> " +
                " </div> " +
                " <p class='txt'><span></span></p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/15.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>15. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("85.", " <p class='txt'>85. Ja krustojumā joslu skaitu un braukšanas virzienu tajās nosaka 512., 515. vai 517.ceļa zīme, nogriezties pa kreisi vai apgriezties braukšanai pretējā virzienā no tramvaja sliežu ceļa klātnes aizliegts (skat. 16. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/16.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>16. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("86.", " <p class='txt'>86. Jānogriežas tā, lai, izbraucot no brauktuvju krustošanās vietas, transportlīdzeklis neatrastos pretējā braukšanas virziena pusē.</p> "));
        paragraphDAOs.add(new ParagraphDAO("87.", " <p class='txt'>87. Ja tramvaja sliežu ceļa klātne ir atdalīta no pārējās brauktuves vai neatrodas vienā līmenī ar to, šķērsot tramvaja sliežu ceļa klātni atļauts tikai šim nolūkam paredzētās vietās.</p> "));
        paragraphDAOs.add(new ParagraphDAO("88.", " <p class='txt'>88. Ja transportlīdzekļa gabarītu vai citu iemeslu dēļ nav iespējams nogriezties no attiecīgā malējā stāvokļa, ir atļauta atkāpe no šīs prasības, ja tas netraucē citu transportlīdzekļu kustību.</p> "));
        paragraphDAOs.add(new ParagraphDAO("89.", " <p class='txt'>89. Nogriežoties pa kreisi vai apgriežoties braukšanai pretējā virzienā ārpus krustojuma, bezsliežu transportlīdzekļa vadītājam jādod ceļš pretim braucošajiem transportlīdzekļiem un tiem transportlīdzekļiem, kas uzsākuši apdzīšanu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("90.", " <p class='txt'>90. Ja brauktuve nav pietiekami plata, lai apgrieztos braukšanai pretējā virzienā no kreisā malējā stāvokļa, to atļauts darīt no brauktuves labās malas vai labās nomales, netraucējot citus ceļu satiksmes dalībniekus. Šādā gadījumā transportlīdzekļa vadītājam, kas apgriežas braukšanai pretējā virzienā, jādod ceļš tajā pašā virzienā braucošajiem un pretim braucošajiem transportlīdzekļiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("91.", " <p class='txt'>91. Ja transportlīdzekļu braukšanas trajektorijas krustojas, bet braukšanas secība šajos noteikumos nav reglamentēta, ceļu dod transportlīdzekļa vadītājs, kuram transportlīdzeklis tuvojas no labās puses.</p> "));
        paragraphDAOs.add(new ParagraphDAO("92.", " <p class='txt'>92. Ja ir iekārtota bremzēšanas josla, transportlīdzekļa vadītājam, kurš paredzējis nogriezties, laikus jāpārkārtojas braukšanai minētajā joslā, un transportlīdzekļa vadītājs drīkst samazināt braukšanas ātrumu, tikai atrodoties uz tās.</p> "));
        paragraphDAOs.add(new ParagraphDAO("93.", " <p class='txt'>93. Ja izbraukšanai uz ceļa iekārtota ieskrējiena josla, transportlīdzekļa vadītājam jābrauc pa to un jāiekļaujas transportlīdzekļu plūsmā, dodot ceļu transportlīdzekļiem, kas brauc pa šo ceļu.</p> " +
                " <p class='Uzmaniba'><span class='Italic'>[Atceries! Tuvojoties vietai, kur ceļam piekļaujas ieskrējiena josla, pārkārtojies tā, lai vadītāji, kas atrodas uz ieskrējiena joslas, varētu netraucēti pārkārtoties (skat. 17. att.).]</span></p> " +
                " <div class='group'> <div class='image'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/17.jpg'> </p> " +
                " </div> " +
                " <div class='Basic-Text-Frame'> <p class='txt para-style-override-2'>17. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("94.", " <p class='txt'>94. Apgriezties braukšanai pretējā virzienā aizliegts šādās vietās:</p> "));
        paragraphDAOs.add(new ParagraphDAO("94.1.", " <p class='txt'>94.1. uz gājēju pārejām;</p> "));
        paragraphDAOs.add(new ParagraphDAO("94.2.", " <p class='txt'>94.2. tuneļos;</p> "));
        paragraphDAOs.add(new ParagraphDAO("94.3.", " <p class='txt'>94.3. uz tiltiem, estakādēm, ceļa pārvadiem un zem tiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("94.4.", " <p class='txt'>94.4. uz dzelzceļa pārbrauktuvēm;</p> "));
        paragraphDAOs.add(new ParagraphDAO("94.5.", " <p class='txt'>94.5. vietās, kur ceļa redzamība kaut vienā virzienā ir mazāka par 100 m.</p> "));
        paragraphDAOs.add(new ParagraphDAO("95.", " <p class='txt'>95. Braucot atpakaļgaitā, transportlīdzekļa vadītājs nedrīkst traucēt citus ceļu satiksmes dalībniekus. Ja tas nepieciešams ceļu satiksmes drošībai, transportlīdzekļa vadītājam minētā manevra veikšanā jālūdz citu personu palīdzība. Aizliegts braukt atpakaļgaitā pa ceļu, kas apzīmēts ar 548. ceļa zīmi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("96.", " <p id='toc_marker-9' class='Virsraksts'><strong>8. Transportlīdzekļu izkārtojums uz brauktuves</strong></p> <p class='txt'>96. Bezsliežu transportlīdzekļu braukšanai paredzēto joslu skaitu norāda attiecīgie ceļa apzīmējumi vai ceļa zīmes, bet, ja to nav, braukšanas joslu skaitu nosaka paši transportlīdzekļu vadītāji, ņemot vērā brauktuves platumu, automobiļu gabarītus un nepieciešamos intervālus starp transportlīdzekļiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("97.", " <p class='txt'>97. Uz ceļiem, kur ceļu satiksme notiek divos virzienos un ir četras vai vairākas braukšanas joslas, kā arī uz ceļiem ar vienu braukšanas joslu katrā virzienā, ja brauktuves vidū ir tramvaja sliežu ceļa klātne, tajā ceļa pusē, kas paredzēta braukšanai pretējā virzienā, atļauts iebraukt, tikai nogriežoties pa kreisi vai apgriežoties braukšanai pretējā virzienā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("98.", " <p class='txt'>98. Ārpus apdzīvotām vietām jābrauc iespējami tuvāk brauktuves labajai malai. Ja braukšanai vienā virzienā ir divas vai vairākas joslas, kreisajā malējā joslā atļauts iebraukt tikai tad, ja citas braukšanas joslas ir aizņemtas, kā arī lai nogrieztos pa kreisi vai apgrieztos braukšanai pretējā virzienā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("99.", " <p class='txt'>99. Apdzīvotās vietās, ja bezsliežu transportlīdzekļu braukšanai vienā virzienā ir trīs vai vairākas joslas, kreisajā malējā joslā atļauts iebraukt tikai šādos gadījumos:</p> "));
        paragraphDAOs.add(new ParagraphDAO("99.1.", " <p class='txt'>99.1. ja citas braukšanas joslas ir aizņemtas;</p> "));
        paragraphDAOs.add(new ParagraphDAO("99.2.", " <p class='txt'>99.2. lai nogrieztos pa kreisi vai apgrieztos braukšanai pretējā virzienā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("99.3.", " <p class='txt'>99.3. lai apturētu transportlīdzekli uz vienvirziena ceļa.</p> "));
        paragraphDAOs.add(new ParagraphDAO("100.", " <p class='txt'>100. Kravas automobiļiem un to sastāviem, kuru pilna masa pārsniedz 3,5 t, apdzīvotās vietās, kur braukšanai vienā virzienā ir trīs vai vairākas joslas, kreisajā malējā joslā atļauts iebraukt, tikai lai nogrieztos pa kreisi, apgrieztos braukšanai pretējā virzienā vai apturētu transportlīdzekli uz vienvirziena ceļa kravas iekraušanai vai izkraušanai.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Vadi transportlīdzekli iespējami tuvāk brauktuves labajai malai.]</span></p>"));
        paragraphDAOs.add(new ParagraphDAO("101.", " <p class='txt'>101. Transportlīdzekļiem, kuriem aizliegts pārsniegt vai kuri tehnisku iemeslu dēļ nespēj pārsniegt braukšanas ātrumu 40 km/h, un traktortehnikai jābrauc tikai pa labo malējo joslu. Minētajiem transportlīdzekļiem un traktortehnikai citā joslā atļauts iebraukt, tikai lai apdzītu, apsteigtu, apbrauktu vai pārkārtotos pirms nogriešanās pa kreisi vai apgrieztos braukšanai pretējā virzienā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("102.", " <p class='txt'>102. Ja braukšanas joslās ceļu satiksme ir intensīva, apbraucot vai apsteidzot atļauts braukt pa tramvaja sliežu ceļa klātni tajā pašā virzienā viet- ās, kur sliežu ceļa klātne atrodas pa kreisi vienā līmenī ar brauktuvi (Skat. 18. att.). Šādā gadījumā nedrīkst traucēt tramvaja braukšanu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("103.", " <p class='txt'>103. Ja joslu skaitu un braukšanas virzienus krustojumā nosaka 512., 513., 514., 515., 516. vai 517.ceļa zīme, braukt pa tramvaja sliedēm krustojumā aizliegts (skat. 19. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/18.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>18. att.</p> " +
                " </div> " +
                " </div> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/19.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>19. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("104.", " <p class='txt'>104. Braukt pa tramvaja pretējā virziena sliežu ceļa klātni aizliegts.</p> "));
        paragraphDAOs.add(new ParagraphDAO("105.", " <p class='txt'>105. Ja ceļš apzīmēts ar 505., 506., 508. vai 509.ceļa zīmi, pa joslu, kas paredzēta pasažieru sabiedriskajiem transportlīdzekļiem, pārējo transportlīdzekļu vadītājiem, izņemot taksometrus, aizliegts braukt, kā arī aizliegts apturēt transportlīdzekli šajā joslā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("106.", " <p class='txt'>106. Ja josla pasažieru sabiedriskajiem transportlīdzekļiem apzīmēta ar 505.ceļa zīmi un 940.ceļa apzīmējumu un iekārtota brauktuves labajā pusē, un nav atdalīta no pārējās brauktuves ar nepārtrauktu ceļa apzīmējuma līniju, transportlīdzekļa vadītājam jānogriežas pa labi no šīs joslas. Šajā joslā atļauts iebraukt arī, lai pasažieri iekāptu transportlīdzeklī vai izkāptu no tā, ja tas netraucē pasažieru sabiedrisko transportlīdzekļu vai taksometru satiksmi, kā arī lai izbrauktu pa labi uz ceļa ar šādu joslu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("107.", " <p class='txt'>107. Ja uz brauktuves ierīkots ar 413.ceļa zīmi un 941.ceļa apzīmējumu apzīmēts velosipēdu ceļš, kas no pārējās brauktuves atdalīts ar platu 920.ceļa apzīmējumu, pārējo transportlīdzekļu vadītājiem velosipēdu ceļu šķērsot atļauts tikai nepārtrauktā ceļa apzīmējuma pārtraukuma vietās.</p> "));
        paragraphDAOs.add(new ParagraphDAO("108.", " <p class='txt'>108. Ja brauktuve sadalīta joslās ar ceļa apzīmējuma līnijām, transportlīdzekļa vadītājam jābrauc tikai pa joslām. Uzbraukt uz pārtrauktajām ceļa apzīmējuma līnijām atļauts tikai pārkārtojoties.</p> "));
        paragraphDAOs.add(new ParagraphDAO("109.", " <p class='txt'>109. Transportlīdzekļa vadītājam, nogriežoties uz ceļa, kas apzīmēts ar 745. ceļa zīmi, aizliegts iebraukt joslā, kas no abām pusēm apzīmēta ar 927.ceļa apzīmējumu. Iebraukt šajā joslā atļauts tikai pēc pabraukšanas garām pirmajam pēc krustojuma uzstādītajam šo noteikumu 59.punktā minētajam luksoforam.</p> "));
        paragraphDAOs.add(new ParagraphDAO("110.", " <p class='txt'>110. Intensīvas ceļu satiksmes apstākļos, kad visas braukšanas joslas aizņemtas, mainīt joslu atļauts, tikai lai nogrieztos, apgrieztos braukšanai pretējā virzienā, apbrauktu vai apturētu transportlīdzekli.</p> "));
        paragraphDAOs.add(new ParagraphDAO("111.", " <p class='txt'>111. Pa ietvēm, gājēju ceļiem, velosipēdu ceļiem, kā arī gājēju un velosipēdu ceļiem mehāniskajiem transportlīdzekļiem braukt aizliegts, izņemot ceļu uzturēšanas un komunālo dienestu (piemēram, elektroapgādes, siltumapgādes) transportlīdzekļus, ja tie veic ceļu uzturēšanas, remonta vai avārijas seku likvidēšanas darbus. Pašvaldības ar attiecīgu lēmumu ir tiesīgas noteikt laikposmu, kad pa šiem ceļiem atļauts braukt un stāvēt (tai skaitā stāvēšanas ilgumu) transportlīdzekļiem, kas apkalpo tirdzniecības vai citus uzņēmumus, veic ar īpašumu apsaimniekošanu saistītus darbus (piemēram, pieved preces, būvmateriālus) objektos, kas atrodas tieši pie šiem ceļiem, ja nav citu piebraukšanas iespēju, netiek traucēta gājēju pārvietošanās un attiecīgo transportlīdzekļu pilna masa nepārsniedz 5 t. Novietojot transportlīdzekli stāvēšanai, tā salonā pie priekšējā stikla novieto informāciju par laiku, kad transportlīdzeklis novietots.</p> "));

        paragraphDAOs.add(new ParagraphDAO("112.", " <p id='toc_marker-10' class='Virsraksts'><strong>9. Braukšanas ātrums, distance un intervāls</strong></p> <p class='txt'>112. Transportlīdzekļa vadītājam jābrauc ar ātrumu, kas nepārsniedz noteiktos ierobežojumus, ņemot vērā ceļu satiksmes intensitāti, transportlīdzekļa un kravas īpatnības un stāvokli, kā arī ceļa un meteoroloģiskos apstākļus (īpaši redzamību braukšanas virzienā).</p> "));
        paragraphDAOs.add(new ParagraphDAO("113.", " <p class='txt'>113. Ja izveidojas ceļu satiksmei bīstami šķēršļi vai citas briesmas, kuras transportlīdzekļa vadītājs spēj pamanīt, viņam jāsamazina braukšanas ātrums vai pilnīgi jāaptur transportlīdzeklis, vai, neradot bīstamību citiem ceļu satiksmes dalībniekiem, šķērslis jāapbrauc.</p> "));

        paragraphDAOs.add(new ParagraphDAO("114.", " <p class='Uzmaniba'><span class='Italic'>[Atceries! Redzamības attālumam braukšanas virzienā vienmēr jābūt lielākam nekā apstāšanās ceļa garumam.]</span></p><p class='txt'>114. Apdzīvotās vietās visu transportlīdzekļu braukšanas ātrums nedrīkst pārsniegt 50 km/h, bet dzīvojamās zonās, daudzdzīvokļu namu pagalmos, degvielas uzpildes staciju un stāvvietu teritorijās - 20 km/h.</p> "));
        paragraphDAOs.add(new ParagraphDAO("115.", " <p class='txt'>115. Ārpus apdzīvotām vietām transportlīdzekļu braukšanas ātrums nedrīkst pārsniegt:</p> "));
        paragraphDAOs.add(new ParagraphDAO("115.1.", " <p class='txt'>115.1. motocikliem, tricikliem, kvadricikliem, vieglajiem automobiļiem, kravas automobiļiem, kuru pilna masa nepārsniedz 7,5 t, un autobusiem - 90 km/h;</p> "));
        paragraphDAOs.add(new ParagraphDAO("115.2.", " <p class='txt'>115.2. automobiļiem un autobusiem, velkot piekabi, un kravas automobiļiem, kuru pilna masa pārsniedz 7,5 t, - 80 km/h.</p> "));
        paragraphDAOs.add(new ParagraphDAO("116.", " <p class='txt'>116. Ceļa posmos, kur uzstādītas attiecīgas ceļa zīmes, transportlīdzekļi drīkst braukt ar ātrumu, kas nepārsniedz ceļa zīmē norādīto.</p> "));
        paragraphDAOs.add(new ParagraphDAO("117.", " <p class='txt'>117. Citos gadījumos, kas nav minēti šo noteikumu 115. un 116.punktā, transportlīdzekļu braukšanas ātrums nedrīkst pārsniegt:</p> "));
        paragraphDAOs.add(new ParagraphDAO("117.1.", " <p class='txt'>117.1. pārvadājot pasažierus ārpus kabīnes speciāliem darbiem paredzētos kravas automobiļos, kuru furgona tipa kravas telpā ir ierīkotas pasažieru sēdvietas, - 60 km/h;</p> "));
        paragraphDAOs.add(new ParagraphDAO("117.2.", " <p class='txt'>117.2. velkot mehāniskos transportlīdzekļus, - 50 km/h;</p> "));
        paragraphDAOs.add(new ParagraphDAO("117.3.", " <p class='txt'>117.3. velkot šo noteikumu 249.punktā minētos tehnoloģiskos agregātus - piekabes, - 40 km/h;</p> "));
        paragraphDAOs.add(new ParagraphDAO("117.4.", " <p class='txt'>117.4. braucot pa ceļu, kas apzīmēts ar 548.ceļa zīmi, motocikliem, tricikliem, kvadricikliem, vieglajiem automobiļiem, kravas automobiļiem, kuru pilna masa nepārsniedz 7,5 t, un autobusiem – 110 km/h, automobiļiem un autobusiem, velkot piekabi, un kravas automobiļiem, kuru pilna masa pārsniedz 7,5 t, – 90 km/h, bet laikposmā no 1.decembra līdz 1.martam attiecīgi – 90 km/h un 80 km/h;</p> "));
        paragraphDAOs.add(new ParagraphDAO("117.5.", " <p class='txt'>117.5. mopēdiem – 45 km/h.</p> "));
        paragraphDAOs.add(new ParagraphDAO("118.", " <p class='txt'>118. Transportlīdzekļa vadītājam aizliegts:</p> "));
        paragraphDAOs.add(new ParagraphDAO("118.1.", " <p class='txt'>118.1. pārsniegt transportlīdzekļa izgatavotāja noteikto maksimālo braukšanas ātrumu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("118.2.", " <p class='txt'>118.2. pārsniegt braukšanas ātrumu, kāds saskaņā ar šo noteikumu 263.punktā minētajām prasībām norādīts pazīšanas zīmē uz transportlīdzekļa;</p> "));
        paragraphDAOs.add(new ParagraphDAO("118.3.", " <p class='txt'>118.3. traucēt ceļu satiksmi, bez vajadzības braucot ar pārāk mazu ātrumu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("118.4.", " <p class='txt'>118.4. strauji bremzēt, ja tas nav nepieciešams ceļu satiksmes drošībai.</p> "));
        paragraphDAOs.add(new ParagraphDAO("119.", " <p class='txt'>119. Transportlīdzekļa vadītājam atkarībā no braukšanas ātruma jāizvēlas tāda distance, lai, priekšā braucošajam transportlīdzeklim bremzējot, būtu iespējams izvairīties no sadursmes, kā arī jāizvēlas tāds intervāls, kas nodrošina ceļu satiksmes drošību (skat. 20. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/20.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>20. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("120.", " <p class='txt'>120. Ārpus apdzīvotām vietām šo noteikumu 266. un 268.punktā minēto transportlīdzekļu (transportlīdzekļu sastāvu) vadītājiem starp savu un priekšā braucošo transportlīdzekli jāietur tāda distance, lai apdzenošie transportlīdzekļi varētu netraucēti pārkārtoties ceļa labajā pusē. Šī prasība nav spēkā, ja transportlīdzekļa vadītājs gatavojas apdzīt, kā arī intensīvas ceļu satiksmes apstākļos.</p> "));

        paragraphDAOs.add(new ParagraphDAO("121.", " <p id='toc_marker-11' class='Virsraksts'><strong>10. Apdzīšana, samainīšanās ar pretim braucošu transportlīdzekli un apsteigšana</strong></p> <p class='txt'>121. Pirms apdzīšanas sākuma transportlīdzekļa vadītājam jāpārliecinās par to, vai:</p> "));
        paragraphDAOs.add(new ParagraphDAO("121.1.", " <p class='txt'>121.1. neviens aiz viņa braucošo transportlīdzekļu vadītājs, kuram viņš varētu traucēt, nav sācis apdzīšanu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("121.2.", " <p class='txt'>121.2. pa to pašu joslu priekšā braucošā transportlīdzekļa vadītājs nerāda kreisā pagrieziena signālu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("121.3.", " <p class='txt'>121.3. apdzīšanas laikā netiks traucēti pretim braucošie transportlīdzekļi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("121.4.", " <p class='txt'>121.4. pēc apdzīšanas viņš varēs atgriezties iepriekšējā braukšanas joslā (brauktuves pusē), netraucējot apdzenamo transportlīdzekli.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Spogulis – signāls – manevrs.Iegaumē! Ja šaubies – neapdzen.]</span></p>"));

        paragraphDAOs.add(new ParagraphDAO("122.", " <p class='txt'>122. Apdzenamā transportlīdzekļa vadītājam aizliegts traucēt apdzīšanu (palielināt braukšanas ātrumu u.tml.).</p> "));
        paragraphDAOs.add(new ParagraphDAO("123.", " <p class='txt'>123. Aizliegts apdzīt šādās vietās:</p> "));
        paragraphDAOs.add(new ParagraphDAO("123.1.", " <p class='txt'>123.1. regulējamos krustojumos;</p> "));
        paragraphDAOs.add(new ParagraphDAO("123.2.", " <p class='txt'>123.2. neregulējamos krustojumos, izņemot gadījumus, kad apdzen uz galvenā ceļa attiecībā pret šķērsojamo ceļu (skat. 21., 22., 23. att.);</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/21.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>21. att.</p> " +
                " </div> " +
                " </div> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/22.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>22. att.</p> " +
                " </div> " +
                " </div> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/23.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>23. att.</p> " +
                " </div> " +
                " </div> " +
                " </div> "));

        paragraphDAOs.add(new ParagraphDAO("123.3.", " <p class='txt'>123.3. uz dzelzceļa pārbrauktuvēm un tuvāk par 100 m pirms tām;</p> "));
        paragraphDAOs.add(new ParagraphDAO("123.4.", " <p class='txt'>123.4. augšupceļu beigās, bīstamos pagriezienos un citos ceļa posmos, kur redzamību ierobežo apturēti transportlīdzekļi, ēkas, apstādījumi u.tml.;</p> "));
        paragraphDAOs.add(new ParagraphDAO("123.5.", " <p class='txt'>123.5. uz gājēju pārejām un tuneļos.</p> "));
        paragraphDAOs.add(new ParagraphDAO("124.", " <p class='txt'>124. Netraucējot citus ceļu satiksmes dalībniekus, lai palaistu garām ar lielāku ātrumu braucošu transportlīdzekli, drīkst pārkārtoties un īslaicīgi braukt pa ceļa nomali, ja tā ir ar asfalta vai asfaltbetona segumu. Ja ārpus apdzīvotām vietām ceļa platums vai brauktuves stāvoklis, ņemot vērā pretim braucošo transportlīdzekļu satiksmes intensitāti, neļauj apdzīt lēni braucošu, lielgabarīta vai ar noteiktu ierobežotu ātrumu braucošu transportlīdzekli, tā vadītājam, netraucējot citus ceļu satiksmes dalībniekus, jābrauc iespējami tuvāk ceļa (nomales) labajai malai, kā arī, ja nepieciešams, jāaptur transportlīdzeklis un jāpalaiž garām transportlīdzekļi, kas aiz tā izveidojuši sastrēgumu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("125.", " <p class='txt'>125. Ja samainīšanās ar pretim braucošo transportlīdzekli ir apgrūtināta, transportlīdzekļa vadītājam, kura pusē atrodas šķērslis (kavēklis), jādod ceļš pretim braucošajam transportlīdzeklim. Ja stāvos augšupceļos vai lejupceļos, kas apzīmēti ar 110. un 111. ceļa zīmi, ir šķēršļi, ceļu dod lejup braucošā transportlīdzekļa vadītājs.</p> "));
        paragraphDAOs.add(new ParagraphDAO("126.", " <p class='txt'>126. Lai pievērstu apdzenamā transportlīdzekļa vadītāja uzmanību, atļauts signalizēt ar lukturu gaismu pārslēgšanu saskaņā ar šo noteikumu 185.punktā noteiktajām prasībām.</p> "));
        paragraphDAOs.add(new ParagraphDAO("127.", " <p class='txt'>127. Ārpus apdzīvotām vietām uz ceļiem ar divām vai vairākām braukšanas joslām vienā virzienā aizliegts apsteigt tajā pašā joslā priekšā braucošo transportlīdzekli pa labo pusi, izņemot gadījumu, kad tas rāda kreisā pagrieziena signālu un ir uzsācis manevru.</p> "));

        paragraphDAOs.add(new ParagraphDAO("128.", " <p id='toc_marker-12' class='Virsraksts'><strong>11. Apstāšanās un stāvēšana</strong></p><p class='txt'>128. Transportlīdzekļu apstāšanās un stāvēšana atļauta ceļa labajā pusē iespējami vairāk pa labi uz nomales, bet, ja tās nav, - brauktuves malā, vietās, kur ir iespējams piebraukt, nepārkāpjot šo noteikumu prasības. Ja brauktuves malā izveidoti šim nolūkam paredzēti paplašinājumi, transportlīdzekļu apstāšanās un stāvēšana atļauta šajos paplašinājumos.</p> "));
        paragraphDAOs.add(new ParagraphDAO("129.", " <p class='txt'>129. Apdzīvotās vietās atļauts apstāties un stāvēt arī ceļa kreisajā pusē iespējami vairāk pa kreisi uz nomales, bet, ja tās nav, - brauktuves malā vai tās paplašinājumos uz šādiem ceļiem:</p> "));
        paragraphDAOs.add(new ParagraphDAO("129.1.", " <p class='txt'>129.1. uz ceļiem ar vienu braukšanas joslu katrā virzienā, ja brauktuves vidū nav tramvaja sliežu ceļa klātnes (skat. 24. att.);</p> "));
        paragraphDAOs.add(new ParagraphDAO("129.2.", " <p class='txt'>129.2. uz vienvirziena ceļiem (kravas automobiļiem un to sastāviem, kuru pilna masa pārsniedz 3,5 t, - tikai kravas iekraušanai vai izkraušanai).</p> "));
        paragraphDAOs.add(new ParagraphDAO("130.", " <p class='txt'>130. Transportlīdzekļus atļauts apstādināt un novietot stāvēšanai vienā rindā paralēli brauktuves malai. Motociklus bez blakusvāģa, mopēdus un velosipēdus atļauts novietot stāvēšanai divās rindās (skat. 25. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/24.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>24. att.</p> " +
                " </div> " +
                " </div> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/25.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>25. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("131.", " <p class='txt'>131. Stāvvietās pie ietves vai, uzbraucot uz ietves, ja stāvvieta apzīmēta ar 532. vai 533.ceļa zīmi un 827., 828., 829., 830., 831. vai 832.papildzīmi, atļauts stāvēt motocikliem, tricikliem, kvadricikliem un vieglajiem automobiļiem bez piekabēm, bet, ja stāvvieta apzīmēta ar 825. vai 826. papildzīmi, arī vieglajiem automobiļiem ar piekabēm, kā norādīts ar attiecīgo papildzīmi un ceļa apzīmējumiem. Stāvvietās uz ietves transportlīdzekļi jānovieto vienā rindā iespējami tuvu brauktuvei (skat. 26. att.). Uz ietves atļauts stāvēt velosipēdiem, mopēdiem un motocikliem bez blakusvāģa, ja tas netraucē gājēju pārvietošanos.</p> "));
        paragraphDAOs.add(new ParagraphDAO("132.", " <p class='txt'>132. Ilgstoša stāvēšana (atpūta, nakšņošana u.tml.) ārpus apdzīvotām vietām atļauta tikai stāvlaukumos vai ārpus ceļa robežām. Ilgstoši atstāt transportlīdzekli uz ceļa ir aizliegts.</p> "));
        paragraphDAOs.add(new ParagraphDAO("133.", " <p class='txt'>133. Transportlīdzekļa vadītājs drīkst atstāt transportlīdzekļa vadītāja vietu tikai tad, ja ir veikts viss nepieciešamais, lai novērstu transportlīdzekļa izkustēšanos no vietas, bet atstāt transportlīdzekli, ja novērsta arī iespēja to lietot bez transportlīdzekļa vadītāja ziņas.</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/26.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>26. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("134.", " <p class='txt'>134. Aizliegts atvērt apturēta transportlīdzekļa durvis, ja tas apdraud ceļu satiksmes drošību vai traucē citus ceļu satiksmes dalībniekus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.", " <p class='txt'>135. Aizliegts apstāties un stāvēt:</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.1.", " <p class='txt'>135.1. uz tramvaja sliežu ceļa klātnes vai tās tiešā tuvumā, ja tas traucē tramvaja braukšanai;</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.2.", " <p class='txt'>135.2. uz dzelzceļa pārbrauktuvēm un tuneļos;</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.3.", " <p class='txt'>135.3. uz tiltiem, estakādēm, ceļa pārvadiem un zem tiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.4.", " <p class='txt'>135.4. vietās, kur attālums starp brauktuves nepārtraukto ceļa apzīmējuma līniju, sadalošo joslu vai pretējo brauktuves malu un apturētu transportlīdzekli ir mazāks par 3m;</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.5.", " <p class='txt'>135.5. uz gājēju pārejām<span class='Italic'>,</span> vietās, kur brauktuvi šķērso velosipēdu ceļš un tuvāk par 5 m no tām (skat. 27. att.);</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/27.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>27. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("135.6.", " <p class='txt'>135.6. brauktuvju krustošanās vietā un tuvāk par 5<span class='Italic'> </span>m no šķērsojamās vai šķērsotās brauktuves malas, izņemot apstāšanos un stāvēšanu pretim sānceļam trīsvirzienu krustojumā, kur ir nepārtraukta 920. vai 921. ceļa apzīmējuma līnija vai sadalošā josla. Šajā apakšpunktā minētās prasības neattiecas uz vietām, kur uz brauktuves izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.);</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.7.", " <p class='txt'>135.7. tuvāk par 25 m pirms un 10 m aiz 534. vai 535. ceļa zīmes. Atļauts apstāties, ja tas<span class='Italic'> </span>nepieciešams pasažieru iekāpšanai transportlīdzeklī vai izkāpšanai no tā un ja tas netraucē pasažieru sabiedrisko transportlīdzekļu vai taksometru satiksmi (skat. 27. att.);</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.8.", " <p class='txt'>135.8. vietās, kur transportlīdzeklis citiem transportlīdzekļu vadītājiem aizsedz luksofora signālus vai ceļa zīmes;</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.9.", " <p class='txt'>135.9. uz ietvēm, izņemot šo noteikumu 131. punktā minētos gadījumus;</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.10.", " <p class='txt'>135.10. uz ieskrējiena un bremzēšanas joslām;</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.11.", " <p class='txt'>135.11. 326.ceļa zīmes darbības zonā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.12.", " <p class='txt'>135.12. vietās, kur brauktuves mala apzīmēta ar 920. ceļa apzīmējumu, kā arī vietās, kur brauktuves mala vai apmale apzīmēta ar 943. vai 947.ceļa apzīmējumu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("135.13.", " <p class='txt'>135.13. ārpus apdzīvotām vietām bīstamu pagriezienu un ceļa garenprofila lūzuma vietas tuvumā, kur redzamība kaut vienā virzienā ir mazāka par 100 m.</p> "));
        paragraphDAOs.add(new ParagraphDAO("136.", " <p class='txt'>136. Aizliegts stāvēt:</p> "));
        paragraphDAOs.add(new ParagraphDAO("136.1.", " <p class='txt'>136.1. ārpus apdzīvotām vietām tuvāk par 100 m no dzelzceļa pārbrauktuves, bet apdzīvotās vietās - tuvāk par 50 m no tās;</p> "));
        paragraphDAOs.add(new ParagraphDAO("136.2.", " <p class='txt'>136.2. citās vietās, kur stāvošs transportlīdzeklis var neļaut braukt (iebraukt vai izbraukt) citiem transportlīdzekļiem vai traucē gājēju pārvietošanos;</p> "));
        paragraphDAOs.add(new ParagraphDAO("136.3.", " <p class='txt'>136.3. 327.ceļa zīmes darbības zonā un – attiecīgi nepāra vai pāra datumos – 328. vai 329.ceļa zīmes darbības zonā, kā arī uz visiem ceļiem teritorijā, kurā iebraukšana apzīmēta ar 522.ceļa zīmi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("136.4.", " <p class='txt'>136.4. vietās, kur brauktuves mala vai apmale apzīmēta ar 944. ceļa apzīmējumu, kā arī vietās, kas apzīmētas ar 945. ceļa apzīmējumu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("137.", " <p class='txt'>137. Piespiedu apstāšanās gadījumā vietās, kur apstāšanās vai stāvēšana aizliegta, transportlīdzekļa vadītājam jādara viss iespējamais, lai atbrīvotu brauktuvi un nogādātu transportlīdzekli vietā, kur apstāšanās un stāvēšana nav aizliegta.</p> "));
        paragraphDAOs.add(new ParagraphDAO("138.", " <p id='toc_marker-13' class='Virsraksts'><strong>12. Braukšana krustojumos</strong></p> <p class='txt'>138. Nogriežoties pa labi vai pa kreisi, transportlīdzekļa vadītājam jādod ceļš gājējiem, kas šķērso to brauktuvi, kurā viņš nogriežas, un velosipēdu vadītājiem, kuru ceļu viņš šķērso.</p> "));
        paragraphDAOs.add(new ParagraphDAO("139.", " <p class='txt'>139. Aizliegts iebraukt krustojumā vai brauktuvju krustošanās vietā, ja izveidojies sastrēgums, kas transportlīdzekļa vadītāju var piespiest apturēt transportlīdzekli krustojumā vai brauktuvju krustošanās vietā un traucēt braukt citiem transportlīdzekļiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("140.", " <p id='toc_marker-14' class='Virsraksts'><strong>12.1. Regulējami krustojumi</strong></p> <p class='txt'>140. Nogriežoties pa kreisi vai apgriežoties braukšanai pretējā virzienā, kad to atļauj luksofora signāls, transportlīdzekļa vadītājam jādod ceļš to transportlīdzekļu vadītājiem, kas brauc no pretējā virziena taisni vai nogriežas pa labi (skat. 28. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/28.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>28. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("141.", " <p class='txt'>141. Ja satiksmes regulētāja vai luksofora signāli atļauj braukt vienlaikus tramvajam un bezsliežu transportlīdzekļiem, priekšroka ir tramvajam neatkarīgi no tā braukšanas virziena (skat. 29. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/29.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>29. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("142.", " <p class='txt'>142. Braucot bultas norādītajā virzienā, kas iedegta papildsekcijā kopā ar luksofora sarkano vai dzelteno signālu, transportlīdzekļa vadītājam (arī tramvaja vadītājam) jādod ceļš no citiem virzieniem braucošo transportlīdzekļu vadītājiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("143.", " <p class='txt'>143. Transportlīdzekļa vadītājam, kas iebraucis krustojumā, kad to atļāva luksofora signāls, no krustojuma iecerētajā virzienā jāizbrauc neatkarīgi no tā luksofora signāla, kas uzstādīts pirms izbraukšanas no krustojuma. Ja krustojumā pirms luksoforiem, kas atrodas transportlīdzekļa vadītāja braukšanas ceļā, ir 541. ceļa zīme, transportlīdzekļa vadītājam jāņem vērā katra luksofora signāli.</p> "));
        paragraphDAOs.add(new ParagraphDAO("144.", " <p class='txt'>144. Kad luksoforā iedegas atļaujošais signāls, transportlīdzekļa vadītājam jādod ceļš to transportlīdzekļu vadītājiem, kas iebraukuši krustojumā, kad viņiem to atļāva luksofora signāls, kā arī gājējiem, kas nav paguvuši šķērsot brauktuvi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("145.", " <p class='txt'>145. Ja luksofors izslēgts vai darbojas mirgojošas dzeltenās gaismas režīmā, transportlīdzekļa vadītājam jāievēro neregulējamu krustojumu šķērsošanas noteikumi un pirms krustojuma uzstādīto priekšrocības ceļa zīmju prasības.</p> "));

        paragraphDAOs.add(new ParagraphDAO("146.", " <p id='toc_marker-15' class='Virsraksts'><strong>12.2. Neregulējami krustojumi</strong></p> <p class='txt'>146. Nevienādas nozīmes ceļu krustojumā transportlīdzekļa vadītājam, kurš brauc pa mazāk svarīgu ceļu, jādod ceļš to transportlīdzekļu vadītājiem, kas krustojumam tuvojas pa galveno ceļu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("147.", " <p class='txt'>147. Vienādas nozīmes ceļu krustojumā transportlīdzekļa vadītājam jādod ceļš tā transportlīdzekļa vadītājam, kas tuvojas no labās puses (skat. 30. att.).</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/30.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>30. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("148.", " <p class='txt'>148. Vienādas nozīmes ceļu krustojumā kā arī nevienādas nozīmes ceļu krustojumā, ja transportlīdzekļi tam tuvojas no viena virziena tramvaja vadītājam neatkarīgi no tā braukšanas virziena ir priekšroka attiecībā pret bezsliežu transportlīdzekļu vadītājiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("149.", " <p class='txt'>149. Ja galvenais ceļš krustojumā maina virzienu, transportlīdzekļu vadītājiem, kas brauc pa galveno ceļu, savstarpēji jāievēro vienādas nozīmes ceļu krustojuma pārbraukšanas noteikumi. Šie noteikumi savstarpēji jāievēro arī transportlīdzekļu vadītājiem, kas brauc pa mazāk svarīgu ceļu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("150.", " <p class='txt'>150. Nogriežoties pa kreisi vai apgriežoties braukšanai pretējā virzienā, bezsliežu transportlīdzekļu vadītājiem jādod ceļš to transportlīdzekļu vadītājiem, kas brauc pa vienādas nozīmes ceļu pretējā virzienā taisni vai nogriežas pa labi. Šie noteikumi savstarpēji jāievēro arī tramvaju vadītājiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("151.", " <p class='txt'>151. Transportlīdzekļa vadītājam, kas uz galvenā ceļa nogriežas pa kreisi vai apgriežas braukšanai pretējā virzienā, jādod ceļš transportlīdzekļu vadītājiem, kas uzsākuši apdzīšanu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("152.", " <p class='txt'>152. Ja pirms krustojuma nav uzstādītas priekšrocības ceļa zīmes, kā arī ja uz šķērsojamā ceļa pirms krustojuma nav uzstādīta 206. vai 207.ceļa zīme un vadītājs nevar noteikt, vai ceļam ir segums (diennakts tumšajā laikā vai ja ceļu klāj dubļi, sniegs u.tml.), transportlīdzekļa vadītājam jāuzskata, ka viņš atrodas uz mazāk svarīga ceļa.</p> "));

        paragraphDAOs.add(new ParagraphDAO("153.", " <p id='toc_marker-16' class='Virsraksts'><strong>13. Ceļu satiksme dzīvojamās zonās</strong></p> <p class='txt'>153. Dzīvojamā zonā gājējiem un velosipēdu vadītājiem atļauts pārvietoties pa ietvēm un pa brauktuvi visā tās platumā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("154.", " <p class='txt'>154. Dzīvojamā zonā, degvielas uzpildes staciju un stāvvietu teritorijā gājējiem un velosipēdu vadītājiem ir priekšroka, taču viņiem aizliegts nepamatoti traucēt citu transportlīdzekļu braukšanu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("155.", " <p class='txt'>155. Transportlīdzekļu stāvēšana dzīvojamās zonās atļauta tikai speciāli paredzētās stāvvietās. Ja tādu stāvvietu nav vai ja tās ir aizņemtas, - transportlīdzekļu stāvēšana atļauta tikai vietās, kur tas netraucē gājēju pārvietošanos vai citu transportlīdzekļu braukšanu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("156.", " <p class='txt'>156. Dzīvojamā zonā aizliegts:</p> "));
        paragraphDAOs.add(new ParagraphDAO("156.1.", " <p class='txt'>156.1. mācīt vadīt transportlīdzekli;</p> "));
        paragraphDAOs.add(new ParagraphDAO("156.2.", " <p class='txt'>156.2. stāvēt transportlīdzekļiem ar iedarbinātu motoru ilgāk par piecām minūtēm;</p> "));
        paragraphDAOs.add(new ParagraphDAO("156.3.", " <p class='txt'>156.3. iebraukt kravas automobiļiem, kuru pilna masa pārsniedz 5 t, kā arī traktortehnikai, izņemot gadījumus, kad tas nepieciešams kravas iekraušanai, izkraušanai vai darbu veikšanai, autobusiem, kuru garums pārsniedz 6 m, izņemot gadījumus, kad tas nepieciešams pasažieru iekāpšanai vai izkāpšanai no tā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("157.", " <p class='txt'>157. Izbraucot no dzīvojamās zonas, transportlīdzekļa vadītājam jādod ceļš citiem ceļu satiksmes dalībniekiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("158.", " <p class='txt'>158. Šajā nodaļā minētās prasības attiecas arī uz daudzdzīvokļu namu pagalmiem.</p> "));

        paragraphDAOs.add(new ParagraphDAO("159.", " <p id='toc_marker-17' class='Virsraksts'><strong>14. Gājēju pārejas un pasažieru sabiedrisko transportlīdzekļu pieturas</strong></p> <p class='txt'>159. Transportlīdzekļa vadītājam, tuvojoties neregulējamai gājēju pārejai, jāsamazina braukšanas ātrums vai jāaptur transportlīdzeklis pirms gājēju pārejas, lai dotu ceļu gājējiem, kas šķērso brauktuvi pa gājēju pāreju vai iziet uz tās, ja tie varētu tikt traucēti vai apdraudēti.</p> "));
        paragraphDAOs.add(new ParagraphDAO("160.", " <p class='txt'>160. Ja pirms neregulējamas gājēju pārejas apturēts transportlīdzeklis vai citi transportlīdzekļi, tuvojoties neregulējamai gājēju pārejai, samazina braukšanas ātrumu, transportlīdzekļa vadītājs var turpināt braukt tikai tad, kad ir pārliecinājies, ka transportlīdzekļu priekšā neatrodas gājēji.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Uz neregulējamas gājēju pārejas gājējiem ir priekšroka.]</span></p>"));

        paragraphDAOs.add(new ParagraphDAO("161.", " <p class='txt'>161. Jebkurā gadījumā (arī ārpus gājēju pārejām) transportlīdzekļa vadītājam jāpalaiž garām neredzīgi gājēji, kas rāda signālu ar baltu spieķi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("162.", " <p class='txt'>162. Uz regulējamas gājēju pārejas un krustojumos, kad ir iedegts atļaujošais luksofora signāls vai tiek parādīts satiksmes regulētāja atļaujošais signāls, transportlīdzekļa vadītājam jādod iespēja gājējiem pabeigt brauktuves šķērsošanu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("163.", " <p class='txt'>163. Aizliegts uzbraukt uz gājēju pārejas, ja aiz tās izveidojies sastrēgums, kas var piespiest transportlīdzekļa vadītāju uz tās apturēt transportlīdzekli un traucēt gājēju pārvietošanos.</p> "));
        paragraphDAOs.add(new ParagraphDAO("164.", " <p class='txt'>164. Transportlīdzekļa vadītājam jādod ceļš gājējiem, kuri iet uz tajā pašā braukšanas virzienā pieturā (ceļa vidū) apturētu tramvaju vai nāk no tā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("165.", " <p class='txt'>165. Tuvojoties apturētam transportlīdzeklim, kuram uzstādīta bērnu grupas pārvadāšanas pazīšanas zīme, transportlīdzekļa vadītājam jābrauc ar tādu ātrumu, lai, ja nepieciešams, varētu apturēt transportlīdzekli un palaist garām bērnus, kas šķērso brauktuvi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("166.", " <p id='toc_marker-18' class='Virsraksts'><strong>15. Dzelzceļa pārbrauktuvju šķērsošana</strong></p> <p class='txt'>166. Šķērsojot dzelzceļa pārbrauktuvi, transportlīdzekļa vadītājam jādod ceļš vilcienam (lokomotīvei, drezīnai), kas tuvojas.</p> "));
        paragraphDAOs.add(new ParagraphDAO("167.", " <p class='txt'>167. Pirms pārbrauktuves transportlīdzekļa vadītājam jāpārliecinās, vai netuvojas vilciens, un jāņem vērā barjeras stāvoklis, gaismas signalizācija, ceļa zīmes un ceļa apzīmējumi, kā arī pārbrauktuves dežuranta norādījumi un signāli. Aizliegts braukt, ja pārbrauktuves dežurants, nostājoties ar krūtīm vai muguru pret vadītāju, signalizē ar virs galvas paceltu zizli vai sānis paceltām rokām.</p> "));
        paragraphDAOs.add(new ParagraphDAO("168.", " <p class='txt'>168. Aizliegts uzbraukt uz pārbrauktuves, ja barjera ir aizvērta vai sāk aizvērties vai ja luksoforā deg aizlieguma signāls (neatkarīgi no barjeras stāvokļa, kā arī ja tās nav). Ja luksofors nedarbojas vai deg tikai balts mirgojošs signāls, bet barjera atvērta vai tās nav, transportlīdzekļa vadītājs drīkst braukt pāri pārbrauktuvei tikai pēc tam, kad ir pārliecinājies, vai netuvojas vilciens.</p> "));
        paragraphDAOs.add(new ParagraphDAO("169.", " <p class='txt'>169. Lai palaistu garām tuvojošos vilcienu, kā arī citos gadījumos, kad pārbrauktuvi šķērsot aizliegts, transportlīdzekļa vadītājam jāaptur transportlīdzeklis tieši pirms stoplīnijas (929.ceļa apzīmējums), bet, ja tās nav, - pirms 541. vai 207.ceļa zīmes; ja to nav, - ne tuvāk par 5 m pirms barjeras; ja tās nav, - ne tuvāk par 10 m no dzelzceļa tuvākās sliedes; ja dzelzceļa pārbrauktuve aprīkota tikai ar luksoforu, - pirms luksofora. Sākot braukt pēc transportlīdzekļa apturēšanas, transportlīdzekļa vadītājam vēlreiz jāpārliecinās, vai netuvojas vilciens.</p> "));
        paragraphDAOs.add(new ParagraphDAO("170.", " <p class='txt'>170. Aizliegts uzbraukt uz pārbrauktuves, ja:</p> "));
        paragraphDAOs.add(new ParagraphDAO("170.1.", " <p class='txt'>170.1. aiz tās izveidojies sastrēgums, kas var piespiest transportlīdzekļa vadītāju apturēt transportlīdzekli uz pārbrauktuves;</p> "));
        paragraphDAOs.add(new ParagraphDAO("170.2.", " <p class='txt'>170.2. uz tās atrodas transportlīdzeklis. Ja uz pārbrauktuves atrodas avarējis transportlīdzeklis, pārējiem vadītājiem jādara viss iespējamais, lai atbrīvotu pārbrauktuvi no tā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("171.", " <p class='txt'>171. Piespiedu apstāšanās gadījumā uz pārbrauktuves transportlīdzekļa vadītājam jāizsēdina pasažieri un jādara viss iespējamais, lai atbrīvotu pārbrauktuvi. Ja transportlīdzekli no pārbrauktuves nobraukt neizdodas, vadītājam, ja iespējams, jāinformē glābšanas dienests un jārīkojas šādi:</p> "));
        paragraphDAOs.add(new ParagraphDAO("171.1.", " <p class='txt'>171.1. ja iespējams, jānosūta cilvēki virzienā gar sliežu ceļu uz abām pusēm (katrs - 1000 m attālumā no pārbrauktuves), ja ir tikai viens cilvēks, - tajā virzienā, kur sliežu ceļš sliktāk pārredzams, un jāpaskaidro, kā jārāda apstāšanās signāls vilciena mašīnistam, lai apturētu tuvojošos vilcienu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("171.2.", " <p class='txt'>171.2. jāpaliek transportlīdzekļa tuvumā un jādod vispārējas trauksmes signāls;</p> "));
        paragraphDAOs.add(new ParagraphDAO("171.3.", " <p class='txt'>171.3. kad parādās vilciens, jāskrien tam pretī, rādot apstāšanās signālu. Apstāšanās signālu rāda ar rokas apļošanu, dienā - turot rokā spilgtas krāsas drānu vai citu labi saskatāmu priekšmetu, naktī – iedegtu lāpu vai lukturi. Vispārējās trauksmes signāls ir skaņu signālu sērija, kas sastāv no viena gara un trijiem īsiem signāliem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("172.", " <p class='txt'>172. Transportlīdzekļa vadītājam aizliegts:</p> "));
        paragraphDAOs.add(new ParagraphDAO("172.1.", " <p class='txt'>172.1. vest pāri pārbrauktuvei transportēšanai nesagatavotas iekārtas un mašīnas (lauksaimniecības mašīnas, ceļu būves mašīnas, celtniecības mašīnas u.tml.);</p> "));
        paragraphDAOs.add(new ParagraphDAO("172.2.", " <p class='txt'>172.2. šķērsot dzelzceļa sliežu ceļus šim nolūkam neparedzētās vietās;</p> "));
        paragraphDAOs.add(new ParagraphDAO("172.3.", " <p class='txt'>172.3. apbraukt transportlīdzekļus, kuri apturēti pirms pārbrauktuves, ja kustība pāri dzelzceļa pārbrauktuvei aizliegta un apbraukšana saistīta ar iebraukšanu tajā ceļa pusē, kas paredzēta braukšanai pretējā virzienā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("172.4.", " <p class='txt'>172.4. patvaļīgi atvērt barjeru vai apbraukt to.</p> "));
        paragraphDAOs.add(new ParagraphDAO("173.", " <p class='txt'>173. Ar dzelzceļa ceļa distances priekšnieka atļauju pārbrauktuvi drīkst šķērsot šādi transportlīdzekļi:</p> "));
        paragraphDAOs.add(new ParagraphDAO("173.1.", " <p class='txt'>173.1. transportlīdzekļi, kuru gabarīti ar kravu vai bez tās platumā pārsniedz 5 m vai augstumā no brauktuves virsmas - 4,5 m;</p> "));
        paragraphDAOs.add(new ParagraphDAO("173.2.", " <p class='txt'>173.2. transportlīdzekļi vai to sastāvi, kuru faktiskā masa pārsniedz 52 t.</p> "));
        paragraphDAOs.add(new ParagraphDAO("174.", " <p id='toc_marker-19' class='Virsraksts'><strong>16. Ārējās apgaismes ierīču lietošana</strong></p> <p class='txt'>174. Diennakts gaišajā laikā mehāniskajiem transportlīdzekļiem, kā arī tramvajiem un trolejbusiem jābrauc ar iedegtiem dienas gaitas, tuvās gaismas<span class='Italic'> </span>vai priekšējiem miglas lukturiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("175.", " <p class='txt'>175. Braucot diennakts tumšajā laikā, transportlīdzekļiem jābūt iedegtām šādām ārējās apgaismes ierīcēm:</p> "));
        paragraphDAOs.add(new ParagraphDAO("175.1.", " <p class='txt'>175.1. mehāniskajiem transportlīdzekļiem - tuvās vai tālās gaismas lukturiem, kā arī gabarītlukturiem un numura zīmes apgaismojumam;</p> "));
        paragraphDAOs.add(new ParagraphDAO("175.2.", " <p class='txt'>175.2. piekabēm - gabarītlukturiem un numura zīmes apgaismojumam.</p> "));
        paragraphDAOs.add(new ParagraphDAO("176.", " <p class='txt'>176. Braucot nepietiekamas redzamības apstākļos, transportlīdzekļiem jābūt iedegtām šādām ārējās apgaismes ierīcēm:</p> "));
        paragraphDAOs.add(new ParagraphDAO("176.1.", " <p class='txt'>176.1. mehāniskajiem transportlīdzekļiem - tuvās gaismas, tālās gaismas vai priekšējiem miglas lukturiem, kā arī gabarītlukturiem un numura zīmes apgaismojumam;</p> "));
        paragraphDAOs.add(new ParagraphDAO("176.2.", " <p class='txt'>176.2. piekabēm - gabarītlukturiem un numura zīmes apgaismojumam.</p> "));
        paragraphDAOs.add(new ParagraphDAO("177.", " <p class='txt'>177. Transportlīdzekļa vadītājam tālās gaismas lukturi jāpārslēdz uz tuvās gaismas lukturiem:</p> "));
        paragraphDAOs.add(new ParagraphDAO("177.1.", " <p class='txt'>177.1. apdzīvotās vietās, ja ceļš ir pietiekami un vienmērīgi apgaismots;</p> "));
        paragraphDAOs.add(new ParagraphDAO("177.2.", " <p class='txt'>177.2. vismaz 150 m attālumā no pretim braucošā transportlīdzekļa vai arī lielākā attālumā, ja pretim braucošā transportlīdzekļa vadītājs par lukturu pārslēgšanas nepieciešamību signalizē, periodiski pārslēdzot lukturu gaismu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("177.3.", " <p class='txt'>177.3. ja var apžilbināt citus vadītājus (arī tos, kuri brauc tajā pašā virzienā). Ja transportlīdzekļa vadītāju apžilbina, viņam jāieslēdz avārijas gaismas signalizācija un, nemainot braukšanas joslu, jāsamazina braukšanas ātrums vai jāaptur transportlīdzeklis.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Pirms pārslēdz tālās gaismas lukturus uz tuvās gaismas lukturiem, pārliecinies, vai tavs braukšanas ātrums atbilst redzamībai, ko nodrošina tuvās gaismas lukturi.]</span></p>"));
        paragraphDAOs.add(new ParagraphDAO("178.", " <p class='txt'>178. Apstājoties un stāvot uz ceļa ārpus apdzīvotām vietām diennakts tumšajā laikā vai nepietiekamas redzamības apstākļos, mehāniskajiem transportlīdzekļiem un to piekabēm jābūt iedegtiem gabarītlukturiem. Ja ir bieza migla, intensīvi līst vai snieg u.tml., atļauts iedegt arī tuvās gaismas lukturus vai priekšējos un pakaļējos miglas lukturus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("179.", " <p class='txt'>179. Apstājoties un stāvot apdzīvotās vietās neapgaismotos ceļa posmos diennakts tumšajā laikā vai nepietiekamas redzamības apstākļos, mehāniskajiem transportlīdzekļiem un to piekabēm jābūt iedegtiem gabarītlukturiem, bet mehāniskajiem transportlīdzekļiem bez piekabēm gabarītlukturu vietā atļauts iedegt stāvgaismas lukturus brauktuves pusē. Ja ir bieza migla, intensīvi līst vai snieg u.tml., atļauts iedegt arī tuvās gaismas lukturus vai priekšējos un pakaļējos miglas lukturus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("180.", " <p class='txt'>180. Apstājoties un stāvot apdzīvotās vietās diennakts tumšajā laikā vai nepietiekamas redzamības apstākļos, gabarītlukturus vai stāvgaismas lukturus atļauts neiedegt ceļa posmos, kur apturētais transportlīdzeklis citiem vadītājiem labi saredzams pietiekamā attālumā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("181.", " <p class='txt'>181. Braucot priekšējos miglas lukturus atļauts iedegt:</p> "));
        paragraphDAOs.add(new ParagraphDAO("181.1.", " <p class='txt'>181.1. nepietiekamas redzamības apstākļos - atsevišķi vai kopā ar tuvās gaismas lukturiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("181.2.", " <p class='txt'>181.2. diennakts tumšajā laikā neapgaismotos ceļa posmos - kopā ar tuvās gaismas lukturiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("182.", " <p class='txt'>182. Pakaļējos miglas lukturus atļauts iedegt tikai tad, ja ir bieza migla, intensīvi līst vai snieg u.tml.</p> "));
        paragraphDAOs.add(new ParagraphDAO("183.", " <p class='txt'>183. Priekšējiem un pakaļējiem miglas lukturiem jāieslēdzas kopā ar gabarītlukturiem un numura zīmes apgaismojumu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("184.", " <p class='txt'>184. Prožektorus un grozāmlukturus atļauts iedegt tikai tiem transportlīdzekļiem, kuriem piešķirts operatīvo transportlīdzekļu statuss, ja netiek apžilbināti citi transportlīdzekļu vadītāji.</p> "));
        paragraphDAOs.add(new ParagraphDAO("185.", " <p class='txt'>185. Lai pievērstu citu ceļu satiksmes dalībnieku uzmanību, ar lukturu gaismām drīkst signalizēt šādi:</p> "));
        paragraphDAOs.add(new ParagraphDAO("185.1.", " <p class='txt'>185.1. diennakts gaišajā laikā - ar īslaicīgu tālās gaismas lukturu ieslēgšanu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("185.2.", " <p class='txt'>185.2. diennakts tumšajā laikā - ar vairākkārtēju īslaicīgu (tuvā gaisma - tālā gaisma) lukturu pārslēgšanu, ja netiek apžilbināti citi transport-līdzekļu vadītāji.</p> "));
        paragraphDAOs.add(new ParagraphDAO("186.", " <p class='txt'>186. Velkamajam mehāniskajam transportlīdzeklim jebkurā diennakts laikā jābūt iedegtai avārijas gaismas signalizācijai. Ja avārijas gaismas signalizācijas nav vai tā nedarbojas, transportlīdzekļa aizmugurē jāpiestiprina avārijas zīme.</p> "));
        paragraphDAOs.add(new ParagraphDAO("187.", " <p id='toc_marker-20' class='Virsraksts'><strong>17. Skaņas signāla, avārijas gaismas signalizācijas un avārijas zīmes lietošana</strong></p> <p class='txt'>187. Skaņas signālu atļauts lietot, tikai lai novērstu ceļu satiksmei bīstamas situācijas, bet ārpus apdzīvotām vietām - arī lai pievērstu citu ceļu satiksmes dalībnieku uzmanību.</p> "));
        paragraphDAOs.add(new ParagraphDAO("188.", " <p class='txt'>188. Avārijas gaismas signalizācija jāiededz (izņemot gadījumus, ja mehāniskais transportlīdzeklis ar tādu nav aprīkots vai tā nedarbojas) un avārijas zīme uz ceļa jāizliek šādos gadījumos:</p> "));
        paragraphDAOs.add(new ParagraphDAO("188.1.", " <p class='txt'>188.1. piespiedu apstāšanās gadījumā vietās, kur apstāties vai stāvēt aizliegts, vai vietās, kur redzamības, meteoroloģisko apstākļu, citu transportlīdzekļu braukšanas ātruma, ceļa seguma stāvokļa u.tml. iemeslu dēļ apturētais transportlīdzeklis var radīt draudus satiksmes drošībai; </p> "));
        paragraphDAOs.add(new ParagraphDAO("188.2.", " <p class='txt'>188.2. ja noticis ceļu satiksmes negadījums vai uz brauktuves izkritusi vai izlijusi krava;</p> "));
        paragraphDAOs.add(new ParagraphDAO("188.3.", " <p class='txt'>188.3. apstājoties un stāvot uz ceļa ārpus apdzīvotām vietām diennakts tumšajā laikā vai nepietiekamas redzamības apstākļos, ja nedeg kāds no priekšējiem vai pakaļējiem gabarītlukturiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("189.", " <p class='txt'>189. Braucot avārijas gaismas signalizācija jāiededz arī šādos gadījumos:</p> "));
        paragraphDAOs.add(new ParagraphDAO("189.1.", " <p class='txt'>189.1. ja vadītājs tiek apžilbināts;</p> "));
        paragraphDAOs.add(new ParagraphDAO("189.2.", " <p class='txt'>189.2. velkamajam mehāniskajam transportlīdzeklim.</p> "));
        paragraphDAOs.add(new ParagraphDAO("189.3.", " <p class='txt'>189.3. ja transportlīdzeklim ir kāds no šo noteikumu 239. punktā minētajiem bojājumiem.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Iededz avārijas gaismas signalizāciju, lai brīdinātu citus ceļu satiksmes dalībniekus par iespējamām briesmām (sastrēgumiem, transportlīdzekļu bojājumiem u.tml.).]</span></p>"));
        paragraphDAOs.add(new ParagraphDAO("190.", " <p class='txt'>190. Avārijas zīme uz ceļa jāizliek tādā attālumā no transportlīdzekļa, lai laikus brīdinātu citu transportlīdzekļu vadītājus par briesmām. Jebkurā gadījumā attālumam no avārijas zīmes līdz transportlīdzeklim jābūt šādam:</p> "));
        paragraphDAOs.add(new ParagraphDAO("190.1.", " <p class='txt'>190.1. apdzīvotās vietās - ne mazākam par 15 m;</p> "));
        paragraphDAOs.add(new ParagraphDAO("190.2.", " <p class='txt'>190.2. ārpus apdzīvotām vietām - ne mazākam par 100 m. </p> "));
        paragraphDAOs.add(new ParagraphDAO("191.", " <p class='txt'>191. Transportlīdzekļa vadītājs, kurš pārvadā bīstamas kravas, piespiedu apstāšanās gadījumā uz ceļa papildus šo noteikumu 190. punktā noteiktajai avārijas zīmei izliek 2 mirgojošus oranžus (dzeltenus) lukturus.</p> <p id='toc_marker-21' class='Virsraksts'><strong>18. Transportlīdzekļu vilkšana</strong></p>"));
        paragraphDAOs.add(new ParagraphDAO("192.", " <p class='txt'>192. Mehāniskos transportlīdzekļus atļauts vilkt:</p> "));
        paragraphDAOs.add(new ParagraphDAO("192.1.", " <p class='txt'>192.1. izmantojot lokano vai cieto sakabi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("192.2.", " <p class='txt'>192.2. daļēji novietojot velkamo mehānisko transportlīdzekli uz velkošā transportlīdzekļa (kravas kastē vai uz speciālas atbalstierīces).</p> "));
        paragraphDAOs.add(new ParagraphDAO("193.", " <p class='txt'>193. Velkot ar cieto vai lokano sakabi, pie velkamā mehāniskā transportlīdzekļa stūres jābūt transportlīdzekļa vadītājam, izņemot gadījumu, kad cietās sakabes konstrukcija nodrošina velkamā mehāniskā transportlīdzekļa vadāmību.</p> "));
        paragraphDAOs.add(new ParagraphDAO("194.", " <p class='txt'>194. Cietajai sakabei starp velkošo un velkamo mehānisko transportlīdzekli jānodrošina attālums, kas nepārsniedz 4 m, bet lokanajai sakabei šim attālumam jābūt no 4 līdz 6 m. Lokanajai sakabei jābūt apzīmētai tā, lai citi ceļu satiksmes dalībnieki to varētu laikus pamanīt.</p> "));
        paragraphDAOs.add(new ParagraphDAO("194.", " <p class='txt'>194.<span class='superscript'>1 </span>Traktortehnikai ar pilnu masu virs 4500 kg atļauts vilkt divas ar darba bremzēm aprīkotas divasu vai trīsasu piekabes laikaposmā no 1. maija līdz 1. oktobrim.</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.", " <p class='txt'>195. Aizliegts vilkt:</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.1.", " <p class='txt'>195.1. vairāk par vienu mehānisko transportlīdzekli;</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.2.", " <p class='txt'>195.2. mehānisko transportlīdzekli, kuram ir bojāta stūres iekārta (atļauts vilkt, to daļēji novietojot uz velkošā transportlīdzekļa);</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.3.", " <p class='txt'>195.3. ja mehāniskajam transportlīdzeklim, kuram ir bojātas darba bremzes, faktiskā masa ir lielāka par pusi no velkošā mehāniskā transportlīdzekļa faktiskās masas;</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.4.", " <p class='txt'>195.4. ar lokano sakabi, ja ir bojāta vai nedarbojas velkamā mehāniskā transportlīdzekļa darba bremžu sistēma;</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.5.", " <p class='txt'>195.5. ar lokano sakabi atkalas laikā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.6.", " <p class='txt'>195.6. ar motociklu bez blakusvāģa, izņemot gadījumu, kad to paredzējis motocikla izgatavotājs un kad motocikls bez blakusvāģa velk speciāli šim nolūkam paredzētu piekabi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.7.", " <p class='txt'>195.7. velosipēdu vai mopēdu, kā arī motociklu bez blakusvāģa; </p> "));
        paragraphDAOs.add(new ParagraphDAO("195.8.", " <p class='txt'>195.8.<span class='Italic'> (svītrots);</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("195.9.", " <p class='txt'>195.9. vairāk par vienu piekabi, izņemot šo noteikumu 194.</span><span class='superscript'>1 </span><span>punktā minēto gadījumu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.10.", " <p class='txt'>195.10. piekabi, kuras masa pārsniedz vilcēja izgatavotāja noteikto vilkšanai paredzētās piekabes masu; </p> "));
        paragraphDAOs.add(new ParagraphDAO("195.11.", " <p class='txt'>195.11. diennakts tumšajā laikā vai nepietiekamas redzamības apstākļos - šo noteikumu 249. punktā minēto tehnoloģisko agregātu - piekabi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("195.12.", " <p class='txt'>195.12. traktortehnikas piekabi ar automobili.</p> "));
        paragraphDAOs.add(new ParagraphDAO("196.", " <p class='txt'>196. Velkot ar lokano vai cieto sakabi, aizliegts vest pasažierus ārpus kabīnes velkamajā speciāliem darbiem paredzētajā kravas automobilī, kura furgona tipa kravas telpā ir ierīkotas pasažieru sēdvietas, kā arī velkamajā autobusā vai trolejbusā. Ja velkamais automobilis ir daļēji novietots uz velkošā mehāniskā transportlīdzekļa, pasažieriem atļauts atrasties tikai velkošā mehāniskā transportlīdzekļa kabīnē.</p> "));
        paragraphDAOs.add(new ParagraphDAO("197.", " <p id='toc_marker-22' class='Virsraksts'><strong>19. Pasažieru pārvadāšana</strong></p> <p class='txt'>197. Transportlīdzeklī nedrīkst pārvadāt lielāku pasažieru skaitu, kā norādīts transportlīdzekļa reģistrācijas dokumentā vai norādījis (paredzējis) transportlīdzekļa izgatavotājs. Pasažieri jāpārvadā tā, lai tie netraucētu transportlīdzekļa vadītāju un neierobežotu viņa redzamību. Pasažierus vieglajos automobiļos un kravas automobiļos atļauts pārvadāt tikai šim nolūkam paredzētajās sēdvietās, bet autobusos – arī šim nolūkam paredzētajās stāvvietās.</p> "));
        paragraphDAOs.add(new ParagraphDAO("198.", " <p class='txt'>198. <span class='Italic'>(Svītrots)</span>.</p> "));
        paragraphDAOs.add(new ParagraphDAO("199.", " <p class='txt'>199. Pārvadājot bērnu grupas, kas dodas ekskursijās, uz sporta pasākumiem u.tml., autobusā jāatrodas vismaz vienam pieaugušajam pavadonim. Šādam transportlīdzeklim priekšpusē un aizmugurē jābūt piestiprinātai pazīšanas zīmei atbilstoši šo noteikumu 257. punktā minētajām prasībām.</p> "));
        paragraphDAOs.add(new ParagraphDAO("200.", " <p class='txt'>200. Ja transportlīdzekļos, kuru sēdvietas aprīkotas ar drošības jostām, ved bērnus, kuru augums nepārsniedz 150 cm, jālieto viens no šādiem drošības līdzekļiem: </p> "));
        paragraphDAOs.add(new ParagraphDAO("200.1.", " <p class='txt'>200.1. bērnam jāatrodas viņa vecumam un svaram atbilstošā bērnu sēdeklītī un jābūt piesprādzētam ar drošības jostu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("200.2.", " <p class='txt'>200.2. bērnam jāsēž uz viņa vecumam un svaram atbilstoša paliktņa un jābūt piesprādzētam ar drošības jostu.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Pat nelielas sadursmes gadījumā nepiesprādzēts bērns vienmēr tiek izsviests no sēdekļa. Ievēro transportlīdzekļa izgatavotāja instrukcijas: nesēdini bērnu sēdvietā, kas aprīkota ar gaisa spilvenu.]</span></p>"));
        paragraphDAOs.add(new ParagraphDAO("200.", " <p class='txt'>200.<span class='superscript'>1</span> Pārvadājot personu invalīdu ratiņkrēslā, invalīdam jābūt ar drošības jostu piesprādzētam pie transportlīdzeklī nostiprināta ratiņkrēsla.</p> "));
        paragraphDAOs.add(new ParagraphDAO("201.", " <p class='txt'>201. Aizliegts pārvadāt pasažierus kravas automobilī ārpus tā kabī-nes, izņemot:</p> "));
        paragraphDAOs.add(new ParagraphDAO("201.1.", " <p class='txt'>201.1. Iekšlietu ministrijas un Nacionālo bruņoto spēku struktūrvienību transportlīdzekļus (pasažieru pārvadāšanas kārtību šādos gadījumos nosaka attiecīgās institūcijas iekšējie normatīvie akti);</p> "));
        paragraphDAOs.add(new ParagraphDAO("201.2.", " <p class='txt'>201.2. speciāliem darbiem paredzētus kravas automobiļus, kuru furgona tipa kravas telpās ir ierīkotas pasažieru sēdvietas un kuri ir noteiktā kārtībā reģistrēti.</p> "));
        paragraphDAOs.add(new ParagraphDAO("202.", " <p class='txt'>202. Noteikumu 201.punktā minētajos gadījumos pārvadāt pasažierus atļauts transportlīdzekļu vadītājiem, kuriem ir tiesības vadīt C kategorijai atbilstošus transportlīdzekļus, bet, ja pasažieru skaits (neskaitot transportlīdzekļa vadītāju) pārsniedz astoņus cilvēkus, - transportlīdzekļu vadītājiem, kuriem ir tiesības vadīt C un attiecīgi D1 vai D kategorijai atbilstošus transportlīdzekļus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("203.", " <p class='txt'>203. Aizliegts pārvadāt pasažierus:</p> "));
        paragraphDAOs.add(new ParagraphDAO("203.1.", " <p class='txt'>203.1. ārpus traktortehnikas kabīnes, piekabē (puspiekabē) un motocikla kravas nodalījumā;</p> "));
        paragraphDAOs.add(new ParagraphDAO("203.2.", " <p class='txt'>203.2. kas jaunāki par 14 gadiem, vieglā automobiļa priekšējā sēdeklī, ja tā konstrukcijā nav paredzētas drošības jostas, kā arī uz motocikla sēdekļa.</p> "));
        paragraphDAOs.add(new ParagraphDAO("204.", " <p id='toc_marker-23' class='Virsraksts'><strong>20. Kravas pārvadāšana</strong></p> <p class='txt'>204. Pārvadājamās kravas masa un slodzes sadalījums uz asīm nedrīkst pārsniegt attiecīgā transportlīdzekļa izgatavotāja noteiktos lielumus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("205.", " <p class='txt'>205. Kravu transportlīdzeklī novieto un, ja nepieciešams, nostiprina atbilstoši normatīvajos aktos par kravu nostiprināšanu noteiktajām prasībām tā, lai krava:</p> "));
        paragraphDAOs.add(new ParagraphDAO("205.1.", " <p class='txt'>205.1. neapdraudētu ceļu satiksmes dalībniekus, nenokristu un nevilktos pa ceļu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("205.2.", " <p class='txt'>205.2. transportlīdzekļa vadītājam neierobežotu pārredzamību;</p> "));
        paragraphDAOs.add(new ParagraphDAO("205.3.", " <p class='txt'>205.3. neietekmētu transportlīdzekļa stabilitāti un neapgrūtinātu tā vadīšanu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("205.4.", " <p class='txt'>205.4. neaizsegtu ārējās apgaismošanas ierīces, gaismas atstarotājus, numura zīmes un pazīšanas zīmes, kā arī netraucētu uztvert signālus, ko rāda ar roku;</p> "));
        paragraphDAOs.add(new ParagraphDAO("205.5.", " <p class='txt'>205.5. neradītu troksni, nesaceltu putekļus un nepiesārņotu vai nepiegružotu apkārtējo vidi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("206.", " <p class='txt'>206. Pārvadāt kravu ar vieglo automobili tā aizmugurē, izmantojot speciāli šim nolūkam paredzētu statīvu, kas kopā ar kravu vai bez tās daļēji vai pilnīgi aizsedz ārējās apgaismošanas ierīces, gaismas atstarotājus, valsts numura vai pazīšanas zīmes, atļauts, ja statīvs aprīkots ar dublējošām ārējās apgaismošanas ierīcēm, gaismas atstarotājiem un valsts numura stiprināšanas vietu ar atbilstošu numura zīmes apgaismojumu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("207.", " <p class='txt'>207. Krava jāapzīmē atbilstoši šo noteikumu 264. punktā minētajām prasībām, ja:</p> "));
        paragraphDAOs.add(new ParagraphDAO("207.1.", " <p class='txt'>207.1. priekšpusē vai aizmugurē tā izvirzīta ārpus transportlīdzekļa gabarītiem tālāk par 1 m;</p> "));
        paragraphDAOs.add(new ParagraphDAO("207.2.", " <p class='txt'>207.2. platumā tā izvirzīta tālāk par 0,4 m no priekšējo vai pakaļējo gabarītlukturu ārējās malas.</p> "));
        paragraphDAOs.add(new ParagraphDAO("208.", " <p class='txt'>208. Ja kravas automobiļu, kuru pilna masa pārsniedz 3,5 t, autobusu un to piekabju, kā arī transportlīdzekļu sastāvu gabarīti ar kravu vai bez tās, ar noņemamo aprīkojumu (piemēram, iekrāvēju) vai bez tā, faktiskā masa vai ass slodze pārsniedz šo noteikumu 3.pielikumā noteiktos lielumus, atļauja braukšanai pa ceļiem jāsaņem normatīvajos aktos noteiktajā kārtībā. Atļaujas netiek izsniegtas dalāmu kravu pārvadājumiem ar transportlīdzekļiem, kuru gabarīti pārsniedz šo noteikumu 3.pielikumā noteiktos lielumus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("209.", " <p class='txt'>209. Policijas darbinieki vai robežsargi (uz valsts robežas un pierobežā) var pieprasīt kravas automobiļu, to piekabju (puspiekabju) un autobusu gabarītu, faktiskās masas un ass slodžu pārbaudi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("210.", " <p id='toc_marker-24' class='Virsraksts'><strong>21. Mācību braukšana</strong></p> <p class='txt'>210. Mācību iestāžu braukšanas mācību instruktoram jābūt atbilstošās kategorijas transportlīdzekļa vadītāja un instruktora apliecībai. Individuāli mācīt vadīt atļauts transportlīdzekļa vadītājam, kura atbilstošās kategorijas transportlīdzekļa vadītāja stāžs ir vismaz 3 gadi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("211.", " <p class='txt'>211. Personai, kura mācās braukt ar motociklu, triciklu, kvadriciklu, automobili vai autobusu, jābūt klāt braukšanas mācību atļaujai vai dokumentam par tiesībām vadīt mehāniskos transportlīdzekļus. Pēc policijas darbinieku pieprasījuma viens no šiem dokumentiem jāiedod pārbaudei.</p> "));
        paragraphDAOs.add(new ParagraphDAO("212.", " <p class='txt'>212. <span class='Italic'>(svītrots).</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("213.", " <p class='txt'>213.<span class='Italic'> (svītrots).</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("214.", " <p class='txt'>214. <span class='Italic'>(svītrots).</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("215.", " <p class='txt'>215. Mācību braucieni pa ceļiem drīkst notikt tikai apmācītāja (transportlīdzekļa vadītāja) klātbūtnē un tikai tad, kad mācāmā persona ir pietiekami labi apguvusi vadīšanas iemaņas. Mācāmajai personai jāzina un jāievēro šo noteikumu prasības. Pašvaldības savā administratīvajā teritorijā ir tiesīgas noteikt laikposmus un ceļus, pa kuriem laikā no plkst.7.00 līdz 10.00 un no plkst.16.00 līdz 20.00 aizliegti mācību braucieni.</p> "));
        paragraphDAOs.add(new ParagraphDAO("216.", " <p class='txt'>216. Mehāniskajiem transportlīdzekļiem, kurus lieto mācību braukšanai, jābūt pazīšanas zīmei atbilstoši šo noteikumu 260., 261. un 262.punktā minētajām prasībām.</p> "));
        paragraphDAOs.add(new ParagraphDAO("217.", " <p id='toc_marker-25' class='Virsraksts'><strong>22. Papildu prasības velosipēdu un mopēdu vadītājiem</strong></p> <p class='txt'>217. Braukt ar velosipēdu pa brauktuvi vai nomali atļauts personām, kas nav jaunākas par 12 gadiem, ar mopēdu - personām, kas nav jaunākas par 14 gadiem. Šādā gadījumā velosipēda vadītājam jābūt klāt velosipēda, mopēda vai jebkura mehāniskā transportlīdzekļa vadītāja apliecībai (mopēda vadītājam – mopēda vai jebkura mehāniskā transportlīdzekļa vadītāja apliecībai), kas pēc šo noteikumu 35.punktā minētās amatpersonas pieprasījuma jāiedod pārbaudei.</p> "));
        paragraphDAOs.add(new ParagraphDAO("218.", " <p class='txt'>218. Braukt ar velosipēdu pa brauktuvi vai nomali pilngadīgas personas pavadībā atļauts bērniem, kas nav jaunāki par septiņiem gadiem. Bērniem šajā gadījumā jālieto aizsprādzēta aizsargķivere. Dzīvojamās zonās un daudzdzīvokļu namu pagalmos bērniem neatkarīgi no to vecuma braukt ar velosipēdu atļauts patstāvīgi.</p> "));
        paragraphDAOs.add(new ParagraphDAO("219.", " <p class='txt'>219. Velosipēdam un mopēdam jābūt tehniskā kārtībā. Velosipēdam jābūt aprīkotam ar bremzēm.</p> "));
        paragraphDAOs.add(new ParagraphDAO("220.", " <p class='txt'>220. Mopēdam jābūt uzstādītām izgatavotāja paredzētajām iekārtām, mehānismiem un ierīcēm, tām jādarbojas visos paredzētajos režīmos. Mopēdam jābūt aprīkotam ar divām savstarpēji nesaistītām bremzēm, priekšā ar baltas gaismas lukturi, bet aizmugurē – sarkanas gaismas lukturi un sarkanu gaismas atstarotāju, skaņas signālu, stāvbalstu un trokšņu slāpētāju. Mopēdam, kas izgatavots pēc 2001. gada 1. janvāra, papildus jābūt aprīkotam ar pakaļējo bremžu signāllukturi, gaismas virzienrādītājiem, numura apgaismojuma lukturi, atpakaļskata spoguļiem un ātruma mērīšanas ierīci (spidometru).</p> "));
        paragraphDAOs.add(new ParagraphDAO("221.", " <p class='txt'>221. Velosipēdu un mopēdu piekabēm aizmugurē jābūt sarkanam gaismas atstarotājam.</p> "));
        paragraphDAOs.add(new ParagraphDAO("222.", " <p class='txt'>222. Braucot ar mopēdu, jebkurā diennakts laikā priekšpusē jābūt iedegtam baltas gaismas lukturim, bet aizmugurē - sarkanas gaismas lukturim.</p> "));
        paragraphDAOs.add(new ParagraphDAO("223.", " <p class='txt'>223. Braucot diennakts tumšajā laikā vai nepietiekamas redzamības apstākļos, velosipēdam abos sānos (riteņos) jābūt aprīkotam ar diviem oranžiem (dzelteniem) gaismas atstarotājiem, kā arī priekšpusē jādeg baltas gaismas lukturim, bet aizmugurē - sarkanas gaismas lukturim. Ja lukturu nav vai tie nedarbojas, velosipēdam priekšpusē jābūt aprīkotam ar baltu atstarotāju, bet aizmugurē ar sarkanu atstarotāju.</p> "));
        paragraphDAOs.add(new ParagraphDAO("224.", " <p class='txt'>224. Velosipēdu un mopēdu vadītājiem pa ceļu atļauts braukt vienā rindā iespējami tuvāk brauktuves (braukšanas joslas) labajai malai. Tālāk uz brauktuves atļauts izbraukt, tikai lai apbrauktu, apsteigtu vai apdzītu vai ja braukšanai attiecīgajā virzienā ir iekārtota atsevišķa braukšanas josla (skat. 31. att.), kā arī šo noteikumu 229. punktā minētajos gadījumos, lai nogrieztos pa kreisi vai apgrieztos braukšanai pretējā virzienā. Netraucējot gājējus, atļauts braukt pa nomali, bet velosipēdu vadītājiem – arī pa ietvi.</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/31.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>31. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("225.", " <p class='txt'>225. Velosipēdu vadītāji treniņnodarbību laikā vai organizētu sacensību laikā, ja tos noteiktā kārtībā pavada speciāli aprīkoti automobiļi, drīkst neievērot šo noteikumu 224. punktā, kā arī 219., 221. un 223. punktā minētās prasības, ja tiek nodrošināta ceļu satiksmes drošība.</p> "));
        paragraphDAOs.add(new ParagraphDAO("226.", " <p class='txt'>226. Vietās, kur ārpus krustojumiem velosipēdu ceļš šķērso brauktuvi un ceļu satiksme netiek regulēta, velosipēdu un mopēdu vadītājiem jādod ceļš transportlīdzekļiem, kas brauc pa šķērsojamo ceļu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("227.", " <p class='txt'>227. Vietās, kur velosipēdu vadītāju braukšanas trajektorijas krustojas ar pārējo transportlīdzekļu braukšanas trajektorijām un velosipēdu vadītājiem ir priekšroka, velosipēdu vadītāji drīkst turpināt braukt tad, kad tie novērtējuši attālumu līdz transportlīdzekļiem, kas tuvojas, kā arī novērtējuši to braukšanas ātrumu un pārliecinājušies par drošību.</p> "));
        paragraphDAOs.add(new ParagraphDAO("228.", " <p class='txt'>228. Nogriezties pa kreisi vai apgriezties braukšanai pretējā virzienā ārpus apdzīvotām vietām velosipēdu un mopēdu vadītājiem atļauts tikai no brauktuves labās malas (labās nomales), dodot ceļu tajā pašā virzienā un pretim braucošajiem transportlīdzekļiem (skat. 32. att.). </p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/32.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>32. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("229.", " <p class='txt'>229. Nogriežoties pa kreisi vai apgriežoties braukšanai pretējā virzienā krustojumos apdzīvotās vietās, kur nav iekārtots velosipēdu ceļš, velosipēdu vadītājiem brauktuvju krustošanās vieta jāšķērso pa brauktuvju labo malu, iepriekš pārliecinoties par ceļu satiksmes drošību un dodot ceļu tajā pašā virzienā braucošajiem transportlīdzekļiem un tiem ceļu satiksmes dalībniekiem, kam saskaņā ar braukšanas noteikumiem krustojumos ir priekšroka. Ja braukšanai pa kreisi iekārtota atsevišķa braukšanas josla, nogriezties atļauts no šīs joslas labās malas (skat. 33. att.). Nogriezties pa kreisi vai apgriezties braukšanai pretējā virzienā ārpus krustojumiem atļauts no brauktuves labās malas.</p> " +
                " <div class='Object-Style-4'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/33.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>33. att.</p> " +
                " </div> " +
                " </div> "));
        paragraphDAOs.add(new ParagraphDAO("230.", " <p class='txt'>230. Velosipēdu vai mopēdu vadītājiem aizliegts:</p> "));
        paragraphDAOs.add(new ParagraphDAO("230.1.", " <p class='txt'>230.1. braukt, neturot stūri;</p> "));
        paragraphDAOs.add(new ParagraphDAO("230.2.", " <p class='txt'>230.2. braukt, turoties pie cita braucoša transportlīdzekļa;</p> "));
        paragraphDAOs.add(new ParagraphDAO("230.3.", " <p class='txt'>230.3. pārvadāt kravu, kas traucē vadīšanu vai apdraud citus ceļu satiksmes dalībniekus;</p> "));
        paragraphDAOs.add(new ParagraphDAO("230.4.", " <p class='txt'>230.4. vest ar velosipēdu pasažierus, izņemot bērnus vecumā līdz septiņiem gadiem, ja ir iekārtoti papildu sēdekļi un droši kāpšļi kāju atbalstam.</p> "));
        paragraphDAOs.add(new ParagraphDAO("230.5.", " <p class='txt'>230.5. vest ar mopēdu pasažierus, ja tas nav paredzēts pasažieru pārvadāšanai;</p> "));
        paragraphDAOs.add(new ParagraphDAO("230.6.", " <p class='txt'>230.6. braukt pa ceļu, ja tam blakus atrodas velosipēdu ceļš;</p> "));
        paragraphDAOs.add(new ParagraphDAO("230.7.", " <p class='txt'>230.7. vilkt velosipēdus vai mopēdus, kā arī citus transportlīdzekļus, izņemot piekabes, kas izgatavotas speciāli šim nolūkam;</p> "));
        paragraphDAOs.add(new ParagraphDAO("230.8.", " <p class='txt'>230.8. braukt ar mopēdu bez aizsargķiveres vai ar neaizsprādzētu galvā uzliktās aizsargķiveres siksnu, kā arī vest ar mopēdu pasažierus, kas nav uzlikuši galvā aizsargķiveri vai nav aizsprādzējuši galvā uzliktās aizsargķiveres siksnu. Bērnus vecumā līdz septiņiem gadiem atļauts pārvadāt tikai pilngadīgām personām;</p> "));
        paragraphDAOs.add(new ParagraphDAO("230.9.", " <p class='txt'>230.9. braukt pa ceļu, kas apzīmēts ar 548. ceļa zīmi.</p> " +
                " <div class='Object-Style-4 rightFloat'> <div class='Object-Style-4'> <p class='para-style-override-1'> <img style='max-height: 616px;' src='/public/imgS/34.jpg'> </p> " +
                " </div> " +
                " <div class='Object-Style-4'> <p class='txt para-style-override-2'>34. att.</p> " +
                " </div> " +
                " </div> " +
                " <p class='Uzmaniba'>[Atceries! Braucot ar velosipēdu, lieto speciālu velosipēdu vadītājiem paredzētu aizsargķiveri. Atceries! Braucot ar velosipēdu vai mopēdu, uzvelc spilgtas krāsas apģērbu ar gaismas atstarotājiem, kas ļauj citu transportlīdzekļu vadītājiem tevi laikus pamanīt (skat. 34. att.).]</p>"));
        paragraphDAOs.add(new ParagraphDAO("231.", " <p id='toc_marker-26' class='Virsraksts'><strong>23. Papildu prasības pajūgu vadītājiem, jātniekiem un dzīvnieku dzinējiem</strong></p> <p class='txt'>231. Vadīt pajūgu, jāt vai dzīt dzīvniekus pa ceļiem atļauts personām, kas nav jaunākas par 12 gadiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("232.", " <p class='txt'>232. Vadīt pajūgu, jāt un dzīt dzīvniekus atļauts pa nomali, bet, ja nomales nav vai pa to pārvietoties nav iespējams, - pa brauktuvi iespējami tuvāk brauktuves labajai malai. Dzīvniekus pa ceļiem atļauts dzīt tikai diennakts gaišajā laikā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("233.", " <p class='txt'>233. Braucot pa ceļiem diennakts tumšajā laikā vai nepietiekamas redzamības apstākļos, pajūga priekšpusē jābūt baltiem, bet aizmugurē - sarkaniem gaismas atstarotājiem, kas novietoti labi saredzamās vietās un apzīmē pajūga (ar kravu vai bez tās) gabarītus platumā. Aizliegts aizsegt gaismas atstarotājus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("234.", " <p class='txt'>234. Pajūgu vadītājiem, izbraucot no mazāk svarīga ceļa uz galvenā ceļa, vietās, kur ceļš nav pietiekami labi pārredzams vai redzamība ir ierobežota (spēji ceļa pagriezieni, koki, ēkas u.tml.), dzīvnieki jāved pavadā.</p> "));
        paragraphDAOs.add(new ParagraphDAO("235.", " <p class='txt'>235. Pajūgu vadītājiem un personām, kas dzen dzīvniekus vai to ganāmpulkus, aizliegts:</p> "));
        paragraphDAOs.add(new ParagraphDAO("235.1.", " <p class='txt'>235.1. dzīt dzīvniekus pāri dzelzceļa sliežu ceļa klātnei un pāri ceļam ārpus speciāli norādītajām vietām;</p> "));
        paragraphDAOs.add(new ParagraphDAO("235.2.", " <p class='txt'>235.2. atstāt dzīvniekus uz ceļa bez uzraudzības;</p> "));
        paragraphDAOs.add(new ParagraphDAO("235.3.", " <p class='txt'>235.3. dzīt dzīvniekus pa asfaltbetona un cementbetona seguma ceļiem, izņemot gadījumus, kad nepieciešams tos šķērsot;</p> "));
        paragraphDAOs.add(new ParagraphDAO("235.4.", " <p class='txt'>235.4. vadīt pajūgu un dzīt ganāmpulku, atrodoties tādā alkoholisko dzērienu, narkotisko, psihotropo, citu apreibinošo vielu vai medikamentu ietekmē, esot slimam vai tādā mērā nogurušam, ka tas ietekmē spējas droši vadīt pajūgu vai dzīt ganāmpulku, veikt noteiktos ceļu satiksmes dalībnieka pienākumus un var apdraudēt citus ceļu satiksmes dalībniekus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("236.", " <p id='toc_marker-27' class='Virsraksts'><strong>24. Transportlīdzekļu tehniskais stāvoklis un aprīkojums</strong></p> <p class='txt'>236. <span class='Italic'>(svītrots)</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("237.", " <p class='txt'>237. <span class='Italic'>(svītrots)</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("238.", " <p class='txt'>238. Aizliegts braukt ar transportlīdzekli, ja tam ir kāds no šādiem bojājumiem:</p> "));
        paragraphDAOs.add(new ParagraphDAO("238.1.", " <p class='txt'>238.1. diennakts tumšajā laikā nedeg abi tuvās gaismas lukturi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("238.2.", " <p class='txt'>238.2. nepietiekamas redzamības apstākļos nedeg abi tuvās gaismas lukturi un vienlaikus abi priekšējie miglas lukturi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("238.3.", " <p class='txt'>238.3. lietus vai snigšanas laikā transportlīdzekļa vadītāja pusē nedarbojas priekšējā stikla tīrītājs;</p> "));
        paragraphDAOs.add(new ParagraphDAO("238.4.", " <p class='txt'>238.4. stūres iekārtas bojājumi neļauj transportlīdzekļa vadītājam izdarīt manevru;</p> "));
        paragraphDAOs.add(new ParagraphDAO("238.5.", " <p class='txt'>238.5. darba bremžu sistēma transportlīdzekļa vadītājam nedod iespēju apturēt transportlīdzekli;</p> "));
        paragraphDAOs.add(new ParagraphDAO("238.6.", " <p class='txt'>238.6. vilcēja un piekabes (puspiekabes) sakabes ierīcei ir bojājumi, kas braukšanas laikā var izraisīt sakabes pārrāvumu;</p> "));
        paragraphDAOs.add(new ParagraphDAO("238.7.", " <p class='txt'>238.7. transportlīdzeklim valsts tehniskajā apskatē vai tehniskā stāvokļa pārbaudē uz ceļa ir konstatēti bīstami bojājumi, kas apdraud satiksmes drošību.</p> "));
        paragraphDAOs.add(new ParagraphDAO("239.", " <p class='txt'>239. Ja transportlīdzeklim ir kāds no šādiem ārējās apgaismes ierīču bojājumiem, ar to atļauts braukt uz stāvvietu vai remontvietu ar iedegtu avārijas gaismas signalizāciju vai ar transportlīdzekļa aizmugurē piestiprinātu avārijas zīmi (tikai pakaļējo ārējās apgaismes ierīču bojājuma gadījumā):</p> "));
        paragraphDAOs.add(new ParagraphDAO("239.1.", " <p class='txt'>239.1. nedeg neviens no bremzēšanas signāllukturiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("239.2.", " <p class='txt'>239.2. diennakts tumšajā laikā vai nepietiekamas redzamības apstākļos nedeg neviens no pakaļējiem gabarītlukturiem vai neviens no numura zīmes apgaismojuma lukturiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("239.3.", " <p class='txt'>239.3. diennakts tumšajā laikā nedeg kāds no tuvās gaismas lukturiem;</p> "));
        paragraphDAOs.add(new ParagraphDAO("239.4.", " <p class='txt'>239.4. nepietiekamas redzamības apstākļos nedeg kāds no tuvās gaismas lukturiem un vienlaikus kāds no priekšējiem miglas lukturiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("240.", " <p class='txt'>240. <span class='Italic'>(svītrots).</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("241.", " <p class='txt'>241. Ja uz ceļa ārpus apdzīvotām vietām apturētam mehāniskajam transportlīdzeklim vai tā piekabei diennakts tumšajā laikā vai nepietiekamas redzamības apstākļos nedeg kāds no priekšējiem vai pakaļējiem gabarītlukturiem, transportlīdzekļa vadītājam jādara viss iespējamais, lai transportlīdzekli nobrauktu no ceļa, bet, ja to izdarīt nav iespējams, tas jāapzīmē atbilstoši šo noteikumu 190. punktā minētajām prasībām.</p> <p class='Uzmaniba'><span class='Italic'>[Atceries! Rūpējies par to, lai automobilī vienmēr atrastos rezerves spuldzes un drošinātāji.]</span></p>"));
        paragraphDAOs.add(new ParagraphDAO("242.", " <p class='txt'>242. Autobusiem, vieglajiem un kravas automobiļiem jābūt aprīkotiem ar:</p> "));
        paragraphDAOs.add(new ParagraphDAO("242.1.", " <p class='txt'>242.1. avārijas zīmi;</p> "));
        paragraphDAOs.add(new ParagraphDAO("242.2.", " <p class='txt'>242.2. medicīnisko aptieciņu (autobusiem, kuros ir vairāk nekā 25 sēdvietas, - ar divām aptieciņām);</p> "));
        paragraphDAOs.add(new ParagraphDAO("242.3.", " <p class='txt'>242.3. ugunsdzēšamo aparātu (autobusiem, kuros ir vairāk nekā 9 sēdvietas, - ar diviem aparātiem).</p> "));
        paragraphDAOs.add(new ParagraphDAO("243.", " <p class='txt'>243. Motocikliem ar blakusvāģi jābūt aprīkotiem ar avārijas zīmi un medicīnisko aptieciņu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("244.", " <p class='txt'>244. Laikposmā no 1.decembra līdz 1.martam visiem automobiļiem un autobusiem, kuru pilna masa nepārsniedz 3,5 t,<span class='Italic'> </span>kā arī to piekabēm jābūt aprīkotām ar riepām, kas paredzētas braukšanai ziemas apstākļos. Laikposmā no 1.maija līdz 1.oktobrim aizliegts braukt ar transportlīdzekļiem, kuriem ir riepas ar radzēm.</p> "));
        paragraphDAOs.add(new ParagraphDAO("245.", " <p class='txt'>245. Aizliegts braukt ar transportlīdzekli, kas aprīkots ar:</p> "));
        paragraphDAOs.add(new ParagraphDAO("245.1.", " <p class='txt'>245.1. priekšmetiem, stiklu pārklājumiem, aizkariem, žalūzijām u.tml., kas būtiski ierobežo redzamību transportlīdzekļa vadītājam tā tiešās redzamības zonā. Attiecībā uz stiklu pārklājumiem par būtisku redzamības ierobežojumu transportlīdzekļa vadītāja redzamības zonā tiek uzskatīti stiklu pārklājumi, kas padara priekšējā stikla (vējstikla) gaismas caurlaidību mazāku par 75 % vai priekšējo sānstiklu (priekšējo durvju stiklu) gaismas caurlaidību mazāku par 70 %;</p> "));
        paragraphDAOs.add(new ParagraphDAO("245.2.", " <p class='txt'>245.2. ierīcēm (antiradaru u.tml.), kas var uztvert braukšanas ātruma kontroles mērierīču raidītos signālus vai radīt traucējumus šo mērierīču darbībai;</p> "));
        paragraphDAOs.add(new ParagraphDAO("245.3.", " <p class='txt'>245.3. ierīcēm (speciālo zilo vai sarkano bākuguni, skaņas signālu, plafonu, gaismas kontrolsignālu u.tml.) un speciālo krāsojumu, kas saskaņā ar šo noteikumu un Latvijā obligāto standartu un normatīvu prasībām ir paredzēti noteiktiem transportlīdzekļiem (operatīvajiem transportlīdzekļiem, taksometriem u.tml.) un var maldināt pārējos ceļu satiksmes dalībniekus;</p> "));
        paragraphDAOs.add(new ParagraphDAO("245.4.", " <p class='txt'>245.4. <span class='Italic'>(svītrots)</span>;</p> "));
        paragraphDAOs.add(new ParagraphDAO("245.5.", " <p class='txt'>245.5. normatīvo aktu prasībām neatbilstošām apgaismes ierīcēm.</p> "));
        paragraphDAOs.add(new ParagraphDAO("246.", " <p id='toc_marker-28' class='Virsraksts'><strong>25. Transportlīdzekļu reģistrācija un numura zīmes</strong></p> <p class='txt'>246.<span class='Italic'>(svītrots);</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("247.", " <p class='txt'>247. <span class='Italic'>(svītrots);</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("248.", " <p class='txt'> 248. <span class='Italic'>(svītrots);</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("249.", " <p class='txt'>249. Tehnoloģiskā agregāta - piekabes (piemēram, kompresori, ģeneratori, celtnieku dzīvojamie vagoniņi) īslaicīga pārvietošana pa ceļiem nozīmē tā pārvietošanu no viena objekta uz citu (neatkarīgi no attāluma), kur tas tiks izmantots paredzētajiem darbiem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("250.", " <p class='txt'>250. <span class='Italic'>(svītrots);</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("251.", " <p class='txt'>251. Aizliegts braukt, ja kaut viena no numura zīmēm nav piestiprināta paredzētajā vietā. Aizliegts numura zīmes aizsegt vai pārklāt ar aizsargmateriāliem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("252.", " <p class='txt'>252. <span class='Italic'>(svītrots);</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("253.", " <p id='toc_marker-29' class='Virsraksts'><strong>26. Transportlīdzekļu pazīšanas zīmes, brīdināšanas ierīces un apzīmējumi </strong></p> <p class='txt'>253. Transportlīdzekļu pazīšanas zīmes (turpmāk - pazīšanas zīme), brīdināšanas ierīces un apzīmējumi izgatavojami saskaņā ar starptautiskajiem nolīgumiem, kuru dalībvalsts ir Latvija, vai šo noteikumu 4.pielikumu.</p> "));
        paragraphDAOs.add(new ParagraphDAO("254.", " <p class='txt'>254. Pazīšanas zīmēm jābūt tīrām, lai citi ceļu satiksmes dalībnieki tās varētu laikus pamanīt.</p> "));
        paragraphDAOs.add(new ParagraphDAO("255.", " <p class='txt'>255. Transportlīdzekļiem aizliegts piestiprināt jebkādu citu informāciju vai zīmes, kurās izmantotas ar valsts standartu noteiktās ceļa zīmju formas, krāsu kombinācijas un simbolika un kuras var maldināt citus ceļu satiksmes dalībniekus.</p> "));
        paragraphDAOs.add(new ParagraphDAO("256.", " <p class='txt'>256. Latvijā pastāvīgi reģistrētajiem transportlīdzekļiem, kas šķērso valsts robežu, aizmugurē jābūt piestiprinātai valsts atšķirības zīmei - 175 mm platai un 115 mm augstai baltai elipsei ar 4 mm platu melnu apmali 2 mm attālumā no ārējās malas. Uz zīmes ir melni burti „LV“, kuru augstums ir 60 mm (4.pielikuma 1.punkts). Valsts atšķirības zīmi atļauts neuzstādīt, izbraucot uz Eiropas Savienības valstīm, ja transportlīdzekļa valsts reģistrācijas numura zīmes atbilst valsts standarta LVS 20:2003 „Transportlīdzekļu valsts reģistrācijas numura zīmes“ prasībām.</p> "));
        paragraphDAOs.add(new ParagraphDAO("257.", " <p class='txt'>257. Pārvadājot bērnu grupas, autobusa priekšpusē un aizmugurē jābūt piestiprinātai dzeltenai kvadrātveida pazīšanas zīmei (malas garums no 250 līdz 300 mm) ar sarkanu apmali (apmales platums - 1/10 no malas garuma) un 121.ceļa zīmes simbolu melnā krāsā (4. pielikuma 2 .punkts).</p> "));
        paragraphDAOs.add(new ParagraphDAO("258.", " <p class='txt'>258. Automobiļiem, kurus vada kurlmēmi vai nedzirdīgi vadītāji, priekšpusē un aizmugurē jābūt piestiprinātai dzeltenai apļveida pazīšanas zīmei (diametrs 160mm), uz kuras attēloti trīs melni apļi (diametrs 40 mm), kas veido vienādmalu trijstūri ar lejup vērstu virsotni (4.pielikuma 3.punkts).</p> "));
        paragraphDAOs.add(new ParagraphDAO("259.", " <p class='txt'>259. <span class='Italic'>(Svītrots).</span></p> "));
        paragraphDAOs.add(new ParagraphDAO("260.", " <p class='txt'>260. Mehāniskajiem transportlīdzekļiem (arī to piekabēm), kurus lieto mācību braukšanai, jābūt piestiprinātai pazīšanas zīmei - baltam vienādmalu trijstūrim (malas garums no 200 līdz 300 mm - atkarībā no transportlīdzekļa veida) ar sarkanu apmali (apmales platums - 1/10 no malas garuma), uz kura ir melns „M“ burts (4. pielikuma 5. punkts).</p> "));
        paragraphDAOs.add(new ParagraphDAO("261.", " <p class='txt'>261. Izgaismota 260. punktā minētā pazīšanas zīme jānovieto uz kabīnes vai salona jumta (autobusiem apgaismotu pazīšanas zīmi atļauts novietot aiz priekšējā stikla vai maršruta plāksnes vietā). Autobusiem, kravas automobiļiem un to piekabēm aizmugurē jābūt piestiprinātai papildu pazīšanas zīmei, kas izgatavota no gaismu atstarojoša materiāla.</p> "));
        paragraphDAOs.add(new ParagraphDAO("262.", " <p class='txt'>262. Automobiļiem, kurus izmanto individuālai mācību braukšanai, šo noteikumu 260. punktā minētās pazīšanas zīmes atļauts piestiprināt tikai iekšpusē pie priekšējā un aizmugures stikla. Motocikliem priekšā un aizmugurē jābūt piestiprinātai pazīšanas zīmei, kas izgatavota no gaismu atstarojoša materiāla.</p> "));
        paragraphDAOs.add(new ParagraphDAO("263.", " <p class='txt'>263. Transportlīdzekļiem, ar kuriem pārvadā sevišķi smagas vai lielgabarīta kravas, kā arī transportlīdzekļiem, kuru izgatavotāja noteiktais maksimālais braukšanas ātrums ir mazāks, nekā norādīts šo noteikumu 114. un 115. punktā (izņemot šo noteikumu 269. punktā minētos transportlīdzekļus), transportlīdzekļa aizmugurē kreisajā pusē jābūt piestiprinātai (uzkrāsotai) braukšanas ātruma ierobežojuma pazīšanas zīmei - 323. ceļa zīmes krāsainam attēlam ar norādītu atļauto braukšanas ātrumu (diametrs no 160 līdz 250 mm - atkarībā no transportlīdzekļa veida, apmales platums - 1/10 no diametra garuma) (4. pielikuma 6. punkts).</p> "));
        paragraphDAOs.add(new ParagraphDAO("264.", " <p class='txt'>264. Ārpus transportlīdzekļa gabarītiem izvirzīta krava vai transportlīdzekļa daļas šo noteikumu 207. punktā minētajos gadījumos diennakts gaišajā laikā jāapzīmē ar gaismu atstarojošām signālplāksnītēm vai karodziņiem (malas garums 400 mm), kuriem abās pusēs pa diagonāli novilktas baltas un sarkanas 50 mm platas svītras (4. pielikuma 7. punkts), bet diennakts tumšajā laikā un nepietiekamas redzamības apstākļos - ar iepriekšminētajām gaismu atstarojošām signālplāksnītēm vai iedegtiem lukturiem: priekšpusē - baltiem, aizmugurē - sarkaniem, sānos - oranžiem (dzelteniem).</p> "));
        paragraphDAOs.add(new ParagraphDAO("265.", " <p class='txt'>265. Transportlīdzeklim, ar kuru pārvadā bīstamas kravas, priekšpusē un aizmugurē jābūt piestiprinātai pazīšanas zīmei - oranžam taisnstūrim, kas atbilst Eiropas valstu nolīguma par bīstamo kravu starptautiskajiem pārvadājumiem ar autotransportu (ADR) prasībām (4. pielikuma 8. punkts).</p> "));
        paragraphDAOs.add(new ParagraphDAO("266.", " <p class='txt'>266. Kravas automobiļiem, kuru pilna masa pārsniedz 12 t (izņemot kravas automobiļus - seglu vilcējus), un<span class='Italic'> </span>posmainajiem tūristu autobusiem aizmugurē jābūt piestiprinātai vienai (horizontālai) vai divām (horizontālām vai vertikālām), vai četrām (divām vertikālām un divām horizontālām) pazīšanas zīmēm - taisnstūriem ar slīpām sarkanām fluorescējošām paralēlām svītrām (45° leņķī ar kāpumu transportlīdzekļa ass virzienā) (4. pielikuma 9. punkts).</p> "));
        paragraphDAOs.add(new ParagraphDAO("267.", " <p class='txt'>267. Noteikumu 266. punktā minētās pazīšanas zīmes jāpiestiprina tā, lai to apakšējā mala atrastos ne zemāk par 250 mm, bet augšējā mala - ne augstāk par 2100 mm no ceļa virsmas. Pazīšanas zīmju kopējam garumam jābūt no 1130 līdz 2300 mm, platumam - no 130 līdz 150 mm, starpsvītru platums nedrīkst būt lielāks par 100 mm, un starpsvītru materiālam jābūt izgatavotam no dzeltena gaismu atstarojoša materiāla.</p> "));
        paragraphDAOs.add(new ParagraphDAO("268.", " <p class='txt'>268. Piekabēm (puspiekabēm), kuru garums, ieskaitot jūgierīci, pārsniedz 8 m, kā arī piekabēm (puspiekabēm), kuru pilna masa pārsniedz 10 t, aizmugurē jābūt piestiprinātām pazīšanas zīmēm - dzelteniem gaismu atstarojošiem taisnstūriem ar sarkanu fluorescējošu apmali (taisnstūra malas platums no 195 līdz 230 mm, apmales platums 40 mm), kuru skaits, novietojums un kopējais garums atbilst šo noteikumu 267. punktā minētajām prasī-bām (4. pielikuma 10. punkts).</p> "));
        paragraphDAOs.add(new ParagraphDAO("269.", " <p class='txt'>269. Mehāniskajiem transportlīdzekļiem, kuru izgatavotāja noteiktais maksimālais braukšanas ātrums nepārsniedz 30 km/h, kā arī to piekabēm aizmugurē jābūt piestiprinātai pazīšanas zīmei - sarkanam fluorescējošam vienādmalu trijstūrim ar gaismu atstarojošu dzeltenu vai sarkanu apmali (trijstūra malas garums no 350 līdz 365 mm, apmales platums no 45 līdz 48 mm) (4. pielikuma 11. punkts).</p> "));
        paragraphDAOs.add(new ParagraphDAO("270.", " <p class='txt'>270. Avārijas zīme (4. pielikuma 12. punkts) – sarkans gaismu atstarojoša materiāla vienādmalu trijstūris ar malas garumu no 450 līdz 550 mm un apmales platumu vismaz 50 mm, kas atbilst Apvienoto Nāciju Organizācijas 1958. gada Ženēvas nolīgumam pievienoto noteikumu Nr.27 prasībām.</p> "));
        paragraphDAOs.add(new ParagraphDAO("270.", " <p class='txt'>270.<span class='superscript'>1</span> Automobiļiem, kurus vada invalīdi ar pārvietošanās traucējumiem (invalīdi, kuriem ir tiesības uz valsts sociālo pabalstu - pabalstu transporta izdevumu kompensēšanai invalīdiem, kuriem ir apgrūtināta pārvietošanās), un automobiļiem, kuru pasažieri ir invalīdi ar pārvietošanās traucējumiem vai 1.grupas redzes invalīdi, automobiļa salonā pie priekšējā stikla atļauts novietot invalīdu stāvvietu izmantošanas karti, kuru izsniedz Ceļu satiksmes drošības direkcija, pamatojoties uz Veselības un darbspēju ekspertīzes ārstu valsts komisijas sniegto informāciju. Maksu par invalīdu stāvvietu izmantošanas kartes izsniegšanu sedz pakalpojuma saņēmējs. Invalīdu stāvvietu izmantošanas karte (4. pielikuma 13. punkts) ir tumši zils taisnstūris (garums 148 mm, augstums 106 mm). Kartes aversa kreisajā pusē norādīts kartes derīguma termiņš, izdevējs, kartes sērijas numurs, zem tā attēlota invalīda pazīšanas zīme baltā krāsā, labajā pusē – Latvijas atšķirības zīme „LV“, zem tās uzraksts latviešu valodā „Eiropas Kopienas modelis“, zem tā ar atstarpi uzraksts latviešu valodā „ Invalīdu stāvvietu izmantošanas karte „, zem tā ar atstarpi analogs uzraksts citās Eiropas Savienības valodās. Kartes reversa kreisajā pusē – kartes īpašnieka fotogrāfija, zem tās norādīts personas vārds, uzvārds un paraksts, labajā pusē – informācija latviešu valodā „Šī karte pilnvaro tās īpašnieku izmantot speciāli apzīmētu stāvvietu pakalpojumus Eiropas Savienības dalībvalstīs.</p> "));

        paragraphDAOs.add(new ParagraphDAO("271.", " <p id='toc_marker-34' class='Virsraksts'><strong>27. Ceļa zīmes un to raksturojums</strong></p> <p class='txt'>271. Ceļa zīmēm (turpmāk - zīmes) (5.pielikums) jāatbilst valsts standarta LVS 77 prasībām.</p> "));
        paragraphDAOs.add(new ParagraphDAO("272.", " <p class='txt'>272. Ja pagaidu zīmēm, kas novietotas, piemēram, uz pārvietojama statīva, transportlīdzekļa, kas veic ceļa darbus, vai citā veidā, kas norāda šo zīmju uzstādīšanas pagaidu raksturu, ir cita nozīme nekā stacionārām zīmēm, transport­līdzekļa vadītājiem jāievēro pagaidu zīmju prasības.</p> "));
        paragraphDAOs.add(new ParagraphDAO("273.", " <p id='toc_marker-35' class='Virsraksts'><strong>27.1. Brīdinājuma zīmes</strong></p><p class='txt'>273. Brīdinājuma zīmes brīdina vadītājus par tuvošanos bīstamam ceļa posmam. Braucot pa šo posmu, transportlīdzekļa vadītājam jārīkojas atbilstoši apstākļiem. Brīdinājuma zīmes ir šādas:</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.1. ", " <p class='zim-bolded'><strong>101.&nbsp;zīme “Vienādas nozīmes ceļu krustojums”.</strong></p> " +
                "<p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/63242.jpg'> 273.1. Ārpus apdzīvotām vietām zīmi uzstāda 150 –200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi.</p>"));

        paragraphDAOs.add(new ParagraphDAO("273.2. ", " <p class='zim-bolded'><strong> 102.&nbsp;zīme “Krustojums, kur brauc pa loku”.</strong></p> " +
                "<p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/62946.jpg'>273.2. Ārpus apdzīvotām vietām zīmi uzstāda 150–200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.3. ", "<p class='zim-bolded'><strong>103., 104.&nbsp;zīme “Bīstams pagrieziens”.</strong></p> " +
                "<p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/63289.jpg'>273.3. Ceļa pagrieziens (103.&nbsp;zīme - pa labi; 104.&nbsp;zīme - pa kreisi) ar mazu rādiusu vai pagrieziens, kura redzamību ierobežo ēkas, apstādījumi u.tml.;</p> " +
                "<p class='zim-text'>273.3. Ārpus apdzīvotām vietām zīmi uzstāda 150 –200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi.</p>"));

        paragraphDAOs.add(new ParagraphDAO("273.4. ", "<p class='zim-bolded'><strong> 105., 106.&nbsp;zīme “Bīstami pagriezieni”.</strong></p> " +
                "<p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/63438.jpg'>273.4. Ceļa posms ar bīstamiem pagriezieniem (105.&nbsp;zīme - pirmais pagrieziens pa labi; 106.&nbsp;zīme – pirmais pagrieziens pa kreisi);</p> " +
                "<p class='zim-text'>273.4. Ārpus apdzīvotām vietām zīmi uzstāda 150 –200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi.</p>"));

        paragraphDAOs.add(new ParagraphDAO("273.5. ", " <p class='zim-bolded'><strong>107., 108., 109.&nbsp;zīme “Ceļa sašaurinājums”.</strong></p> " +
                "<p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/63139.jpg'></p> " +
                "<p class='txt'>(107.&nbsp;zīme - no abām pusēm; 108.&nbsp;zīme - no labās puses; 109.&nbsp;zīme - no kreisās puses)</p> " +
                "<p class='txt'>273.5. Ārpus apdzīvotām vietām zīmi uzstāda 150 –200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.6. ", " <p class='zim-bolded'><strong>110.&nbsp;zīme “Stāvs lejupceļš”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/63904.jpg'>273.6. Ārpus apdzīvotām vietām zīmi uzstāda 150 –200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi</p> " +
                " <p class='zim-text'> Ja stāvs lejupceļš vai augšupceļš seko viens aiz otra, tad zīme var tikt uzstādīta pirms otrā vai tam sekojošā lejupceļa attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.7. ", " <p class='zim-bolded'><strong>111.&nbsp;zīme “Stāvs augšupceļš”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/63938.jpg'>273.7. Ārpus apdzīvotām vietām zīmi uzstāda 150 –200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi</p> " +
                " <p class='zim-text'> Ja stāvs lejupceļš vai augšupceļš seko viens aiz otra, tad zīme var tikt uzstādīta pirms otrā vai tam sekojošā augšupceļa sākuma attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.8. ", " <p class='zim-bolded'><strong>112.&nbsp;zīme “Nelīdzens ceļš”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/63976.jpg'>273.8. Ceļa posms, kur uz brauktuves ir nelīdzenumi (viļņi, bedres, nelīdzenas ceļa un tilta salaiduma vietas u.tml.)</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 –200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.9. ", " <p class='zim-bolded'><strong>113.&nbsp;zīme “Nelīdzens ceļš (slieksnis)”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64022.jpg'>273.9. Ceļa posms, kur uz brauktuves izveidoti mākslīgi paaugstinājumi braukšanas ātruma samazināšanai. </p> " +
                " <p class='zim-text'>Ārpus apdzīvotām vietām zīmi uzstāda 150 –200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801. “Attālums līdz objektam” zīmi.</p> " +
                " <p class='zim-text'>&nbsp;zīme var nebūt uzstādīta dzīvojamās zonās, kā arī vietās, kur maksimālais atļautais braukšanas ātrums nepārsniedz 30 km/h.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.10. ", " <p class='zim-bolded'><strong>114.&nbsp;zīme “Ceļa seguma maiņa”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64046.jpg'> 273.10. Ceļa posms, kur asfaltbetona vai cementbetona segums mainās uz grants segumu un otrādi;</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.11. ", " <p class='zim-bolded'><strong>115.&nbsp;zīme “Slidens ceļš”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64067.jpg'>273.11  Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.12. ", " <p class='zim-bolded'><strong>116.&nbsp;zīme “Uzbērta grants vai šķembas”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64138.jpg'>273.12. Ceļa posms, kurā no riteņu apakšas var tikt izsviesta grants, šķembas u.tml. materiāli;</p> " +
                " <p class='zim-text'>274. Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.13. ", " <p class='zim-bolded'><strong>117.&nbsp;zīme “Akmeņu nogruvumi”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64159.jpg'>273.13. Ceļa posms, kur iespējami nogruvumi, noslīdeņi krītoši akmeņi </p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.14. ", " <p class='zim-bolded'><strong>118.&nbsp;zīme “Uz ceļa strādā”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64200.jpg'>273.14. Ceļa posms, kur uz ceļa, blakus tam vai virs tā tiek veikti jebkādi darbi, kas aizņem ceļu vai ietekmē satiksmes apstākļus;</p> " +
                " <p class='zim-text'>274. Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> " +
                " <p class='zim-text'>275. Ārpus apdzīvotām vietām zīmi uzstāda atkārtoti. Otru zīmi uzstāda vismaz 50 m pirms bīstamā posma sākuma.</p> " +
                " <p class='zim-text'>276. Ierobežotos apstākļos un apdzīvotā vietā otru zīmi var uzstādīt tieši ceļa darbu vietas sākumā. Ja ceļa darbi ir īslaicīgi, zīmi (bez 801.&nbsp;zīmes) var uzstādīt 10-15 m pirms ceļa darbu vietas.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.15. ", " <p class='zim-bolded'><strong>119.&nbsp;zīme “Ceļš ar bīstamām nomalēm”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64229.jpg'>273.15. Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.16. ", " <p class='zim-bolded'><strong>120.&nbsp;zīme “Gājēju pāreja”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64250.jpg'>273.16. Tuvošanās ar 530. un 531.&nbsp;zīmi apzīmētai gājēju pārejai, kur jādod ceļš gājējiem</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.17. ", " <p class='zim-bolded'><strong>121.&nbsp;zīme “Bērni”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64271.jpg'>273.17. Ceļa posms bērnu iestādes tuvumā, kur uz brauktuves pēkšņi var uziet bērni;</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801. &nbsp;zīmi.</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda atkārtoti. Otru zīmi uzstāda vismaz 50 m pirms bīstamā posma sākuma.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.18. ", " <p class='zim-bolded'><strong>122.&nbsp;zīme “Divvirzienu satiksme”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64303.jpg'>273.18. Ceļa (brauktuves) posma sākums, kur ceļu satiksme notiek abos virzienos;</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.19. ", " <p class='zim-bolded'><strong>123.&nbsp;zīme “Satiksmi regulē luksofors”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64333.jpg'>273.19. Krustojums, gājēju pāreja vai ceļa posms, kur ceļu satiksmi regulē luksofors;</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.20. ", " <p class='zim-bolded'><strong>124.&nbsp;zīme “Mājdzīvnieki”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64364.jpg'>273.20. Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.21. ", " <p class='zim-bolded'><strong>125.&nbsp;zīme “Savvaļas dzīvnieki”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64498.jpg'>273.21. Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.22. ", " <p class='zim-bolded'><strong>126.&nbsp;zīme “Paceļams tilts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64520.jpg'>273.22. Paceļams tilts vai prāmja pārceltuve </p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda atkārtoti. Otru zīmi uzstāda vismaz 50 m pirms bīstamā posma sākuma.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.23. ", " <p class='zim-bolded'><strong>127.&nbsp;zīme “Krastmala”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64540.jpg'>273.23. Vieta, kur ceļš iziet uz krastmalu vai kādas ūdenstilpes krastu;</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda atkārtoti. Otru zīmi uzstāda vismaz 50 m pirms bīstamā posma sākuma.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.24. ", " <p class='zim-bolded'><strong>128.&nbsp;zīme “Sānvējš”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64551.jpg'>273.24. Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.25. ", " <p class='zim-bolded'><strong>129.&nbsp;zīme “Zemu lidojošas lidmašīnas”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64562.jpg'>273.25 Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.26. ", " <p class='zim-bolded'><strong>130.&nbsp;zīme “Krustojums ar velosipēdu ceļu“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64573.jpg'>273.26. Vieta, kur ceļu šķērso velosipēdu ceļš, velosipēdiem paredzēta josla vai uz ceļa bieži izbrauc velosipēdi</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.27. ", " <p class='zim-bolded'><strong>131.&nbsp;zīme “Tramvaja sliežu šķērsošana“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64592.jpg'>273.27. Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.28. ", " <p class='zim-bolded'><strong>132.&nbsp;zīme “Dzelzceļa pārbrauktuve ar barjeru“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64603.jpg'>273.28. Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda atkārtoti. Otru zīmi uzstāda vismaz 50 m pirms bīstamā posma sākuma.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.29. ", " <p class='zim-bolded'><strong>133.&nbsp;zīme “Dzelzceļa pārbrauktuve bez barjeras“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64614.jpg'>273.29. Ārpus apdzīvotām vietām zīmi uzstāda 150 – 200 m pirms bīstamā ceļa posma, bet apdzīvotās vietās 50–60 m pirms tā. Ja nepieciešams, zīmes uzstāda citā attālumā, ko norāda ar 801.&nbsp;zīmi.</p> " +
                " <p class='zim-text'> Ārpus apdzīvotām vietām zīmi uzstāda atkārtoti. Otru zīmi uzstāda vismaz 50 m pirms bīstamā posma sākuma.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.30. ", " <p class='zim-bolded'><strong>134.&nbsp;zīme “Viensliežu dzelzceļa pārbrauktuve“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64641.jpg'>273.30. Dzelzceļa pārbrauktuve ar vienu sliežu ceļu, kur nav ierīkota barjera;</p> " +
                " <p class='zim-text'>&nbsp;zīmi uzstāda tieši pirms dzelzceļa pārbrauktuves.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.31. ", " <p class='zim-bolded'><strong>135.&nbsp;zīme “Daudzsliežu dzelzceļa pārbrauktuve“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64651.jpg'>273.31. Dzelzceļa pārbrauktuve ar diviem vai vairākiem sliežu ceļiem, kur nav ierīkota barjera;</p> " +
                " <p class='zim-text'> &nbsp;zīmi uzstāda tieši pirms dzelzceļa pārbrauktuves.</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.32. ", " <p class='zim-bolded'><strong>136., 137., 138., 139., 140. un 141.&nbsp;zīme “Tuvojas dzelzceļa pārbrauktuve“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/64934.jpg'></p> " +
                " <p class='txt'>273.32. Papildu brīdinājums par tuvošanos dzelzceļa pārbrauktuvei ārpus apdzīvotām vietām;</p> "));

        paragraphDAOs.add(new ParagraphDAO("273.33.", " <p class='txt'>273.33. 136., 138. un 140.&nbsp;zīmi uzstāda ceļa labajā pusē, bet 137., 139. un 141.&nbsp;zīmi - kreisajā pusē. 136. un 137.&nbsp;zīmi uzstāda kopā ar braukšanas virzienā pirmo 132. vai 133.&nbsp;zīmi; 140. un 141.&nbsp;zīmi - ar otro 132. vai 133.&nbsp;zīmi. 138. Un 139.&nbsp;zīmi uzstāda atsevišķi vienādā attālumā starp pirmo un otro 132.&nbsp;zīmi vai pirmo un otro 133.&nbsp;zīmi.</p> " +
                " <p class='zim-bolded'><strong>142. Bīstami</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65019.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("281.", " <p id='toc_marker-36' class='Virsraksts'><strong>27.2. Priekšrocības zīmes</strong></p> <p class='txt'>281. Priekšrocības zīmes norāda braukšanas secību neregulējamos krusto­jumos, brauktuvju krustošanās vietās vai šauros ceļa posmos. Priekšrocības zīmes ir šādas:</p> "));

        paragraphDAOs.add(new ParagraphDAO("281.1.", " <p class='zim-bolded'><strong>201.&nbsp;zīme “Galvenais ceļš”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65173.jpg'>281.1. Ceļš, pa kuru braucot ir priekšroka šķērsot neregulējamus krustojumus;</p> "));

        paragraphDAOs.add(new ParagraphDAO("281.2.", " <p class='zim-bolded'><strong>202.&nbsp;zīme “Galvenā ceļa beigas”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65205.jpg'></p> " +
                " <p class='txt'>281.2. </p>"));

        paragraphDAOs.add(new ParagraphDAO("281.3.", " <p class='zim-bolded'><strong>203., 204. un 205.&nbsp;zīme “Krustojums ar mazāk svarīgu ceļu”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65242.jpg'></p> " +
                " <p class='txt'>(203.&nbsp;zīme- no abām pusēm; 204.&nbsp;zīme - no labās puses; 205.&nbsp;zīme - no kreisās puses).281.3. Uz vienas zīmes var būt attēloti divi savstarpēji tuvu esoši krustojumi, un attālumu starp tiem nenorāda. Šīs zīmes uzstāda ārpus apdzīvotām vietām 150-200 m attālumā no krustojuma;</p> "));

        paragraphDAOs.add(new ParagraphDAO("281.4.", " <p class='zim-bolded'><strong>206.&nbsp;zīme “Dodiet ceļu”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65286.jpg'>281.4. Transportlīdzekļa vadītājam jādod ceļš transportlīdzekļiem, kuri brauc pa šķērsojamo ceļu, bet, ja papildus uzstādīta 840.&nbsp;zīme, - jādod ceļš transportlīdzekļiem, kuri brauc pa galveno ceļu, kā arī transportlīdzekļiem, kas tuvojas pa vienādas nozīmes ceļu no labās puses, izņemot tramvaju vadītājus, kuriem saskaņā ar šo noteikumu 148. punktu šajā gadījumā ir priekšroka;</p> "));

        paragraphDAOs.add(new ParagraphDAO("281.5.", " <p class='zim-bolded'><strong>207.&nbsp;zīme “Neapstājoties tālāk braukt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65295.jpg'>281.5. Aizliegts braukt tālāk, neapturot transportlīdzekli tieši pirms stoplīnijas (929. apzīmējums), bet, ja tās nav, - tieši pirms šķērsojamās brauktuves malas. Transportlīdzekļa vadītājam jādod ceļš transportlīdzekļiem, kuri brauc pa šķērsojamo ceļu, bet, ja papildus uzstādīta 840.&nbsp;zīme, - jādod ceļš transportlīdzekļiem, kuri brauc pa galveno ceļu, kā arī transportlīdzekļiem, kas tuvojas pa vienādas nozīmes ceļu no labās puses, izņemot tramvaja vadītājus, kuriem saskaņā ar šo noteikumu 148. punktu šajā gadījumā ir priekšroka.&nbsp;Zīmi var uzstādīt arī pirms neapsargājamas dzelzceļa pārbrauktuves, kā arī speciāla karantīnas posteņa, robežas šķērsošanas vietas un citām vietām, kur ceļu satiksmi apstādina ar barjeru vai citādi. Šajā gadījumā transportlīdzekļa vadītājam jāaptur transportlīdzeklis tieši pirms stoplīnijas (929. apzīmējums), bet, ja tās nav, - tieši pirms zīmes;</p> "));

        paragraphDAOs.add(new ParagraphDAO("281.6.", " <p class='zim-bolded'><strong>208.&nbsp;zīme “Priekšroka pretim braucošajiem”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65307.jpg'>281.6. Aizliegts iebraukt ceļa sašaurinātajā posmā, ja tas var apgrūtināt pretim braukšanu. Transportlīdzekļa vadītājam jādod ceļš pretim braucošajiem transportlīdzekļiem, kuri atrodas ceļa sašaurinātajā posmā vai šā posma pretējā galā;</p> "));

        paragraphDAOs.add(new ParagraphDAO("281.7.", " <p class='zim-bolded'><strong>209.&nbsp;zīme “Priekšroka attiecībā pret pretim braucošajiem”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65327.jpg'>281.7. Šaurs ceļa posms, pa kuru braucot transportlīdzekļa vadītājam ir priekšroka attiecībā pret pretim braucošajiem transportlīdzekļiem.</p> "));


        paragraphDAOs.add(new ParagraphDAO("282.", "<p id='toc_marker-37' class='Virsraksts'><strong>27.3. Aizlieguma zīmes</strong></p> <p class='txt'>282. Aizlieguma zīmes nosaka vai atceļ noteiktus ceļu satiksmes ierobe­žojumus. Aizlieguma zīmes ir šādas:</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.1.", " <p class='zim-bolded'><strong>301.&nbsp;zīme “Iebraukt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65585.jpg'> Aizliegts iebraukt jebkādiem transportlīdzekļiem</p> " +
                " <p class='zim-text'>282.1. &nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.2.", " <p class='zim-bolded'><strong>302.&nbsp;zīme “Braukt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65626.jpg'> Aizliegts braukt jebkādiem transportlīdzekļiem;</p> " +
                " <p class='zim-text'>282.2. &nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.</p> " +
                " <p class='zim-text'>&nbsp;zīmes darbība neattiecas uz transportlīdzekļiem, kuri nogādā vai brauc saņemt kravu, un speciālajiem transportlīdzekļiem (piemēram, autoceltņiem), kas brauc veikt darbus zonā, kas apzīmēta ar kādu no šīm ceļa zīmēm.&nbsp;zīmes darbība neattiecas arī uz vieglajiem automobiļiem un kravas automobiļiem, kuru pilna masa nepārsniedz 3,5 t un kuru īpašnieks (turētājs) dzīvo vai strādā šajā zonā, un uz taksometriem, kuri brauc pēc pasažiera vai ar pasažieri.</p> " +
                " <p class='zim-text'>&nbsp;zīmes darbība neattiecas uz automobiļiem, kas apzīmēti ar invalīdu stāvvietu izmantošanas karti.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.3.", " <p class='zim-bolded'><strong>303.&nbsp;zīme “Mehāniskajiem transportlīdzekļiem braukt aizliegts”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65640.jpg'>&nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.</p> " +
                " <p class='zim-text'>282.3. &nbsp;zīmes darbība neattiecas uz transportlīdzekļiem, kuri nogādā vai brauc saņemt kravu, un speciālajiem transportlīdzekļiem (piemēram, autoceltņiem), kas brauc veikt darbus zonā, kas apzīmēta ar kādu no šīm ceļa zīmēm.&nbsp;zīmes darbība neattiecas arī uz vieglajiem automobiļiem un kravas automobiļiem, kuru pilna masa nepārsniedz 3,5 t un kuru īpašnieks (turētājs) dzīvo vai strādā šajā zonā, un uz taksometriem, kuri brauc pēc pasažiera vai ar pasažieri.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.4.", " <p class='zim-bolded'><strong>304.&nbsp;zīme “Motocikliem braukt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65654.jpg'>282.3. Aizliegts braukt motocikliem, tricikliem un kvadricikliem;</p> " +
                " <p class='zim-text'>282.3. &nbsp;zīmes darbība neattiecas uz transportlīdzekļiem, kuri nogādā vai brauc saņemt kravu, un speciālajiem transportlīdzekļiem (piemēram, autoceltņiem), kas brauc veikt darbus zonā, kas apzīmēta ar kādu no šīm ceļa zīmēm.&nbsp;zīmes darbība neattiecas arī uz to transportlīdzekļu īpašniekiem (turētājiem), kuri šajā zonā dzīvo vai strādā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.5.", " <p class='zim-bolded'><strong>305.&nbsp;zīme “Velosipēdiem braukt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65665.jpg'>Aizliegts braukt velosipēdiem un mopēdiem;</p> " +
                " <p class='zim-text'>282.5. &nbsp;zīmes darbība neattiecas uz transportlīdzekļiem, kuri nogādā vai brauc saņemt kravu, un speciālajiem transportlīdzekļiem (piemēram, autoceltņiem), kas brauc veikt darbus zonā, kas apzīmēta ar kādu no šīm ceļa zīmēm.&nbsp;zīmes darbība neattiecas arī uz to transportlīdzekļu īpašniekiem (turētājiem), kuri šajā zonā dzīvo vai strādā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.6.", " <p class='zim-bolded'><strong>306.&nbsp;zīme “Kravas automobiļiem braukt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65682.jpg'> Aizliegts brauktkravas automobiļiem un to sastāviem, kuru pilna masa ir lielāka par 3,5 t (ja zīmē pilna masa nav norādīta) vai kuru pilna masa ir lielāka par zīmē norādīto, kā arī traktortehnikai;</p> " +
                " <p class='zim-text'>282.6. &nbsp;zīmes darbība neattiecas uz transportlīdzekļiem, kuri nogādā vai brauc saņemt kravu, un speciālajiem transportlīdzekļiem (piemēram, autoceltņiem), kas brauc veikt darbus zonā, kas apzīmēta ar kādu no šīm ceļa zīmēm.&nbsp;zīmes darbība neattiecas arī uz transportlīdzekļiem, ar kuriem normatīvajos aktos noteiktajā kārtībā notiek vadīšanas iemaņu pārbaude.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.7.", " <p class='zim-bolded'><strong>307.&nbsp;zīme “Ar piekabi braukt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65712.jpg'>Aizliegts braukt kravas automobiļiem un traktortehnikai ar jebkura tipa piekabi, kā arī vilkt mehānisko transportlīdzekli;</p> " +
                " <p class='zim-text'>282.7. &nbsp;zīmes darbība neattiecas uz transportlīdzekļiem, kuri nogādā vai brauc saņemt kravu, un speciālajiem transportlīdzekļiem (piemēram, autoceltņiem), kas brauc veikt darbus zonā, kas apzīmēta ar kādu no šīm ceļa zīmēm.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.8.", " <p class='zim-bolded'><strong>308.&nbsp;zīme “Traktoriem braukt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65723.jpg'>Aizliegts braukt traktortehnikai;</p> " +
                " <p class='zim-text'>282.8. &nbsp;zīmes darbība neattiecas uz transportlīdzekļiem, kuri nogādā vai brauc saņemt kravu, un speciālajiem transportlīdzekļiem (piemēram, autoceltņiem), kas brauc veikt darbus zonā, kas apzīmēta ar kādu no šīm ceļa zīmēm.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.9.", " <p class='zim-bolded'><strong>309.&nbsp;zīme “Gājējiem iet aizliegts”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65741.jpg'>282.9. &nbsp;zīmes darbība attiecas tikai uz to brauktuves pusi, kurā tā uzstādīta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.10.", " <p class='zim-bolded'><strong>310.&nbsp;zīme “Platuma ierobežojums”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65753.jpg'>282.10. Aizliegts braukt transportlīdzekļiem, kuru gabarīts (ar kravu vai bez tās) platumā pārsniedz zīmē norādīto;</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.11.", " <p class='zim-bolded'><strong>311.&nbsp;zīme “Augstuma ierobežojums”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65779.jpg'>282.11. Aizliegts braukt transportlīdzekļiem, kuru gabarīts (ar kravu vai bez tās) augstumā no brauktuves virsmas pārsniedz zīmē norādīto;</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.12.", " <p class='zim-bolded'><strong>312.&nbsp;zīme “Masas ierobežojums”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65801.jpg'>Aizliegts braukt transportlīdzekļiem (transportlīdzekļu sastāviem), kuru kopējā faktiskā masa pārsniedz zīmē norādīto;</p> " +
                " <p class='zim-text'>282.12. Zīmes darbība neattiecas uz transportlīdzekļiem, kuriem, izvērtējot sabiedrisko drošību un bīstamību, lai veiktu speciālus pārvadājumus (piemēram, pārvadātu dzīvnieku izcelsmes blakusproduktus un atvasinātus produktus, kas nav paredzēti cilvēka patēriņam), izsniegta valsts akciju sabiedrības “Latvijas Valsts ceļi” atļauja.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.13.", " <p class='zim-bolded'><strong>313.&nbsp;zīme “Ass slodzes ierobežojums”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65814.jpg'>Aizliegts braukt transportlīdzekļiem, kuriem faktiskā slodze uz jebkuru asi pārsniedz zīmē norādīto;</p> " +
                " <p class='zim-text'>282.13. Zīmes darbība neattiecas uz transportlīdzekļiem, kuriem, izvērtējot sabiedrisko drošību un bīstamību, lai veiktu speciālus pārvadājumus (piemēram, pārvadātu dzīvnieku izcelsmes blakusproduktus un atvasinātus produktus, kas nav paredzēti cilvēka patēriņam), izsniegta valsts akciju sabiedrības “Latvijas Valsts ceļi” atļauja.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.14.", " <p class='zim-bolded'><strong>314.&nbsp;zīme “Garuma ierobežojums”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65855.jpg'>282.14. Aizliegts braukt transportlīdzekļiem (transportlīdzekļu sastāviem), kuru gabarīts (ar kravu vai bez tās) garumā pārsniedz zīmē norādīto;</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.15.", " <p class='zim-text'>315.&nbsp;zīme “Nogriezties pa labi aizliegts”</p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65877.jpg'>&nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.</p> " +
                " <p class='zim-text'>282.15. &nbsp;zīmes darbība attiecas uz to brauktuvju krustošanās vietu, pirms kuras zīmes uzstādītas.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.16.", " <p class='zim-bolded'><strong>316.&nbsp;zīme “Nogriezties pa kreisi aizliegts”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65890.jpg'>&nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.</p> " +
                " <p class='zim-text'>282.16. &nbsp;zīmes darbība attiecas uz to brauktuvju krustošanās vietu, pirms kuras zīmes uzstādītas.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.17.", " <p class='zim-bolded'><strong>317.&nbsp;zīme “Apgriezties braukšanai pretējā virzienā aizliegts”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65918.jpg'>282.17. &nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.18.", " <p class='zim-bolded'><strong>318.&nbsp;zīme “Minimālās distances ierobežojums”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/65933.jpg'>Distance starp braucošiem transportlīdzekļiem nedrīkst būt mazāka par zīmē norādīto;</p> " +
                " <p class='zim-text'>282.18. &nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. </p> " +
                " <p class='txt'>Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>&nbsp;zīmju darbības zonu var samazināt:</p> " +
                " <p class='txt'> uzstādot 330. vai 803.&nbsp;zīmi;</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.19.", " <p class='zim-bolded'><strong>319.&nbsp;zīme “Apdzīt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66353.jpg'>Aizliegts apdzīt jebkurus transportlīdzekļus, izņemot atsevišķi braucošos transportlīdzekļus un to sastāvus, kuri brauc ar ātrumu, mazāku par 30 km/h.</p> " +
                " <p class='zim-text'>282.19. &nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. </p> " +
                " <p class='txt'>Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>&nbsp;zīmes darbības zonu var samazināt:</p> " +
                " <p class='txt'>uzstādot 330. vai 803.&nbsp;zīmi;</p> " +
                " <p cl ass='txt'>uzstādot 320 zīmi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.20.", " <p class='zim-bolded'><strong>320.&nbsp;zīme “Apdzīšanas aizliegums beidzas”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66379.jpg'>292.2. Samazina 319.&nbsp;zīmes darbības zonu.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.21.", " <p class='zim-bolded'><strong>321.&nbsp;zīme “Kravas automobiļiem apdzīt aizliegts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66402.jpg'>Kravas automobiļiem un to sastāviem, kuru pilna masa pārsniedz 3,5 t, aizliegts apdzīt jebkurus transportlīdzekļus, izņemot atsevišķi braucošos transportlīdzekļus un to sastāvus, kuri brauc ar ātrumu, kas mazāks par 30 km/h. Traktortehnikai aizliegts apdzīt jebkurus transportlīdzekļus, izņemot pajūgus un velosipēdus;</p> " +
                " <p class='zim-text'>291.&nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. </p> " +
                " <p class='txt'>Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>292.&nbsp;zīmes darbības zonu var samazināt:</p> " +
                " <p class='txt'>292.1. uzstādot 330. vai 803.&nbsp;zīmi;</p> " +
                " <p class='txt'>292.2. uzstādot 322.&nbsp;zīmi;</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.22.", " <p class='zim-bolded'><strong>322.&nbsp;zīme “Kravas automobiļiem apdzīšanas aizliegums beidzas“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66424.jpg'>292.2. Samazina 321.&nbsp;zīmes darbības zonu.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.23.", " <p class='zim-bolded'><strong>323.&nbsp;zīme “Maksimālā ātruma ierobežojums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66450.jpg'>Aizliegts braukt ar ātrumu (km/h), kas pārsniedz zīmē norādīto;</p> " +
                " <p class='zim-text'>291.&nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. </p> " +
                " <p class='zim-text'>Ja 323.&nbsp;zīme uzstādīta pirms apdzīvotās vietas, minētās zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz 518.&nbsp;zīmei. </p> " +
                " <p class='txt'>Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>292.&nbsp;zīmju darbības zonu var samazināt:</p> " +
                " <p class='txt'>292.1. uzstādot 330. vai 803.&nbsp;zīmi;</p> " +
                " <p class='txt'>292.2. uzstādot 324.&nbsp;zīmi;</p> " +
                " <p class='txt'>292.3. uzstādot 323.&nbsp;zīmi ar citu maksimālā braukšanas ātruma ierobežojumu;</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.24.", " <p class='zim-bolded'><strong>324.&nbsp;zīme “Maksimālā ātruma ierobežojums beidzas“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66463.jpg'>292.2. Samazina 323.&nbsp;zīmes darbības zonu.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.25.", " <p class='zim-bolded'><strong>325.&nbsp;zīme “Skaņas signālu lietot aizliegts“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66494.jpg'>291.&nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>292.&nbsp;zīmes darbības zonu var samazināt:</p> " +
                " <p class='txt'>292.1. uzstādot 330. vai 803.&nbsp;zīmi;</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.26", " <p class='zim-bolded'><strong>326.&nbsp;zīme “Apstāties aizliegts“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66507.jpg'>Transportlīdzekļiem aizliegts apstāties un stāvēt;</p> " +
                " <p class='zim-text'>288.&nbsp;zīmes darbības zonā atļauta: </p> " +
                " <p class='zim-text'>288.1.Pasažieru iekāpšana taksometros - vieglajos automobiļos - vai izkāpšana no tiem </p> " +
                " <p class='zim-text'>288.2. taksometru – vieglo automobiļu – ar ieslēgtu skaitītāju apstāšanās, taksometra vadītājam paliekot transportlīdzeklī;</p> " +
                " <p class='txt'>288.3. invalīdu iekāpšana transportlīdzekļos, kas apzīmēti ar invalīdu stāvvietu izmantošanas karti, vai izkāpšana no tiem;</p> " +
                " <p class='txt'>288.4. pasažieru iekāpšana un izkāpšana pasažieru sabiedriskajos transportlīdzekļos to pieturās;</p> " +
                " <p class='txt'>288.5. taksometru – vieglo automobiļu, kravas taksometru vai maršruta taksometru -stāvēšana attiecīgi ar 536., 537. vai 538.&nbsp;zīmi apzīmētā stāvvietā;</p> " +
                " <p class='txt'>288.6. pasažieru iekāpšana un izkāpšana maršruta taksometros laikā no plkst. 20.00 līdz 7.00.</p> " +
                " <p class='txt'>288.7. dzīvnieku izcelsmes blakusproduktu un atvasinātu produktu, kas nav paredzēti cilvēka patēriņam, iekraušana transportlīdzekļos.</p> " +
                " <p class='txt'>289. Pašvaldības ar attiecīgu lēmumu ir tiesīgas noteikt laikposmu, kad transportlīdzekļos drīkst iekraut vai izkraut kravu zīmes darbības zonā.</p> " +
                " <p class='txt'>290.&nbsp;zīmes darbība attiecas tikai uz to brauktuves pusi, kurā tā uzstādīta.</p> " +
                " <p class='txt'>291.&nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>292.&nbsp;zīmes darbības zonu var samazināt:</p> " +
                " <p class='txt'>292.1. uzstādot 330. vai 803.&nbsp;zīmi</p> " +
                " <p class='txt'>292.4.&nbsp;zīmes darbības zonā atkārtoti uzstādot zīmi - kopā ar 808.&nbsp;zīmi.&nbsp;zīmi var lietot kopā ar 943.ceļa apzīmējumu, Šādos gadījumos zīmes darbības zonu nosaka ceļa apzīmējuma līnijas garums.&nbsp;zīmes darbības zona beidzas vietā, kur sākas uz brauktuves un ietves ierīkota un ar 532. vai 533.&nbsp;zīmi apzīmēta stāvvieta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.27.", " <p class='zim-bolded'><strong>327.&nbsp;zīme “Stāvēt aizliegts“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66559.jpg'>Transportlīdzekļiem aizliegts stāvēt</p> " +
                " <p class='zim-text'>286.&nbsp;zīmes darbība neattiecas uz automobiļiem, kas apzīmēti ar invalīdu stāvvietu izmantošanas karti.</p> " +
                " <p class='zim-text'>289. Pašvaldības ar attiecīgu lēmumu ir tiesīgas noteikt laikposmu, kad transportlīdzekļos drīkst iekraut vai izkraut kravu zīmes darbības zonā.</p> " +
                " <p class='txt'>290.&nbsp;zīmes darbība attiecas tikai uz to brauktuves pusi, kurā tā uzstādīta.</p> " +
                " <p class='txt'>291.&nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>292.&nbsp;zīmes darbības zonu var samazināt:</p> " +
                " <p class='txt'>292.1. uzstādot 330. vai 803.&nbsp;zīmi</p> " +
                " <p class='txt'>292.4.&nbsp;zīmes darbības zonā atkārtoti uzstādot zīmi - kopā ar 808.&nbsp;zīmi.&nbsp;zīmi var lietot kopā ar 944. ceļa apzīmējumu. Šādos gadījumos zīmes darbības zonu nosaka ceļa apzīmējuma līnijas garums.&nbsp;zīmes darbības zona beidzas vietā, kur sākas uz brauktuves un ietves ierīkota un ar 532. vai 533.&nbsp;zīmi apzīmēta stāvvieta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.28.", " <p class='zim-bolded'><strong>328.&nbsp;zīme “Nepāra datumos stāvēt aizliegts“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66584.jpg'>283. Ja 328. un 329.&nbsp;zīmes attiecīgi uzstādītas vienā un otrā ceļa pusē, tad laikposmā no plkst.21.00 līdz 24.00 transportlīdzekļi, kam nākamajā dienā būs aizliegts stāvēt attiecīgajā ceļa pusē, jāpārvieto uz ceļa otru pusi.</p> " +
                " <p class='zim-text'>291.&nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei.&nbsp;</p> " +
                " <p class='txt'>Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>289. Pašvaldības ar attiecīgu lēmumu ir tiesīgas noteikt laikposmu, kad transportlīdzekļos drīkst iekraut vai izkraut kravu zīmes darbības zonā.</p> " +
                " <p class='txt'>290.&nbsp;zīmes darbība attiecas tikai uz to brauktuves pusi, kurā tā uzstādīta.</p> " +
                " <p class='txt'>291.&nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>292.&nbsp;zīmes darbības zonu var samazināt:</p> " +
                " <p class='txt'>292.1. uzstādot 330. vai 803.&nbsp;zīmi</p> " +
                " <p class='txt'>292.4.&nbsp;zīmes darbības zonā atkārtoti uzstādot zīmi - kopā ar 808.&nbsp;zīmi.&nbsp;zīmes darbības zona beidzas vietā, kur sākas uz brauktuves un ietves ierīkota un ar 532. vai 533.&nbsp;zīmi apzīmēta stāvvieta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.29.", " <p class='zim-bolded'><strong>329.&nbsp;zīme “Pāra datumos stāvēt aizliegts“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66602.jpg'>283. Ja 328. un 329.&nbsp;zīmes attiecīgi uzstādītas vienā un otrā ceļa pusē, tad laikposmā no plkst.21.00 līdz 24.00 transportlīdzekļi, kam nākamajā dienā būs aizliegts stāvēt attiecīgajā ceļa pusē, jāpārvieto uz ceļa otru pusi.</p> " +
                " <p class='zim-text'>291.&nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. </p> " +
                " <p class='txt'>Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>289. Pašvaldības ar attiecīgu lēmumu ir tiesīgas noteikt laikposmu, kad transportlīdzekļos drīkst iekraut vai izkraut kravu zīmes darbības zonā.</p> " +
                " <p class='txt'>290.&nbsp;zīmes darbība attiecas tikai uz to brauktuves pusi, kurā tā uzstādīta.</p> " +
                " <p class='txt'>291.&nbsp;zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, – līdz 519.&nbsp;zīmei. Zīmju darbības zona nebeidzas vietās, kur uz ceļa izbrauc no blakusteritorijas (pagalma, stāvvietas, degvielas uzpildes stacijas, uzņēmuma u.tml.), un vietās, kur ceļu šķērso vai no tā atzarojas lauku ceļš, meža ceļš vai cits tamlīdzīgs ceļš, pirms kura nav uzstādītas attiecīgas priekšrocības zīmes, kā arī krustojumos, kuriem nav tieša pieslēguma attiecīgajai brauktuvei.</p> " +
                " <p class='txt'>292.&nbsp;zīmes darbības zonu var samazināt:</p> " +
                " <p class='txt'>292.1. uzstādot 330. vai 803.&nbsp;zīmi</p> " +
                " <p class='txt'>292.4.&nbsp;zīmes darbības zonā atkārtoti uzstādot zīmi - kopā ar 808.&nbsp;zīmi.&nbsp;zīmes darbības zona beidzas vietā, kur sākas uz brauktuves un ietves ierīkota un ar 532. vai 533.&nbsp;zīmi apzīmēta stāvvieta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.30.", " <p class='zim-bolded'><strong>330.&nbsp;zīme “Visi ierobežojumi beidzas“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66632.jpg'>Vienlaikus apzīmē 318., 319., 321., 323., 325., 326., 327., 328. un 329.&nbsp;zīmes darbības zonas beigas;</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.31.", " <p class='zim-bolded'><strong>331.&nbsp;zīme “Muita“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66647.jpg'>Neapturot transportlīdzekli tieši pirms zīmes, tālāk braukt aizliegts. Atļauju braukt tālāk dod muitas (kontroles punkta) amatpersona</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.32.", " <p class='zim-text'>332.&nbsp;zīme “Policija“</p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66692.jpg'>Neapturot transportlīdzekli tieši pirms zīmes, tālāk braukt aizliegts. Atļauju braukt tālāk dod policijas darbinieks vai robežsargs (uz valsts robežas);</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.33.", " <p class='zim-text'>333.&nbsp;zīme “Tālāk braukt bīstami“</p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66734.jpg'>Aizliegts tālāk braukt visiem (bez izņēmuma) transportlīdzekļiem ceļu satiksmes negadījuma, avārijas u.tml. iemeslu dēļ.</p> "));

        paragraphDAOs.add(new ParagraphDAO("282.34.", " <p class='zim-bolded'><strong>334.&nbsp;zīme “Transportlīdzekļiem ar bīstamu kravu braukt aizliegts“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/66784.jpg'></p> " +
                " <p id='toc_marker-38' class='Virsraksts'><strong>27.4. Rīkojuma zīmes</strong></p> "));

        paragraphDAOs.add(new ParagraphDAO("293.", " <p class='txt'>293. Rīkojuma zīmes ir šādas:</p> "));


        paragraphDAOs.add(new ParagraphDAO("293.1", " <p class='zim-bolded'><strong>401.&nbsp;zīme “Braukt taisni“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67144.jpg'>294.&nbsp;zīme norāda, ka atļauts braukt tikai zīmē norādītajā virzienā. </p> " +
                " <p class='zim-text'>295.&nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.&nbsp;zīmes darbība attiecas uz brauktuvju krustošanās vietu, pirms kuras zīme uzstādīta.</p> " +
                " <p class='zim-text'> 296. Ja zīme uzstādīta ceļa posma sākumā, tās darbības zona ir spēkā līdz tuvākajam krustojumam aiz zīmes. Zīme neaizliedz nogriezties pa labi, lai iebrauktu pagalmos un citās blakus teritorijās.</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.2.", " <p class='zim-bolded'><strong>402.&nbsp;zīme “Braukt pa labi“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67155.jpg'>294.&nbsp;zīme norāda, ka atļauts braukt tikai zīmē norādītajā virzienā. </p> " +
                " <p class='zim-text'>295.&nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.&nbsp;zīmes darbība attiecas uz brauktuvju krustošanās vietu, pirms kuras zīme uzstādīta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.3.", " <p class='zim-bolded'><strong>403.&nbsp;zīme “Braukt pa kreisi“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67173.jpg'>294.&nbsp;zīme norāda, ka atļauts braukt tikai zīmē norādītajā virzienā.&nbsp;zīme, kas atļauj nogriezties pa kreisi, atļauj arī apgriezties braukšanai pretējā virzienā. </p> " +
                " <p class='zim-text'>295.&nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.&nbsp;zīmes darbība attiecas uz brauktuvju krustošanās vietu, pirms kuras zīme uzstādīta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.4.", " <p class='zim-bolded'><strong>404.&nbsp;zīme “Braukt taisni vai pa labi“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67185.jpg'>294.&nbsp;zīme norāda, ka atļauts braukt tikai zīmē norādītajos virzienos. </p> " +
                " <p class='zim-text'>295.&nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.&nbsp;zīmes darbība attiecas uz brauktuvju krustošanās vietu, pirms kuras zīme uzstādīta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.5", " <p class='zim-bolded'><strong>405.&nbsp;zīme “Braukt taisni vai pa kreisi“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67226.jpg'>294.&nbsp;zīme norāda, ka atļauts braukt tikai zīmē norādītajos virzienos.&nbsp;zīme, kas atļauj nogriezties pa kreisi, atļauj arī apgriezties braukšanai pretējā virzienā.</p> " +
                " <p class='zim-text'>295.&nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.&nbsp;zīmes darbība attiecas uz brauktuvju krustošanās vietu, pirms kuras zīme uzstādīta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.6.", " <p class='zim-bolded'><strong>406.&nbsp;zīme “Braukt pa labi vai pa kreisi“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67238.jpg'>294.&nbsp;zīme norāda, ka atļauts braukt tikai zīmē norādītajos virzienos.&nbsp;zīme, kas atļauj nogriezties pa kreisi, atļauj arī apgriezties braukšanai pretējā virzienā. </p> " +
                " <p class='zim-text'>295.&nbsp;zīmes darbība neattiecas uz pasažieru sabiedriskajiem transportlīdzekļiem.&nbsp;zīmes darbība attiecas uz brauktuvju krustošanās vietu, pirms kuras zīme uzstādīta.</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.7.", " <p class='zim-bolded'><strong>407.&nbsp;zīme “Braukt pa labi“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67250.jpg'>294.&nbsp;zīme norāda, ka atļauts braukt tikai zīmē norādītajā virzienā. </p> " +
                " <p class='zim-text'>297.&nbsp;zīme norāda, ka jānogriežas pirms šīs zīmes.</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.8.", " <p class='zim-text'>408.&nbsp;zīme “Braukt pa kreisi“;</p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67264.jpg'>294.&nbsp;zīme norāda, ka atļauts braukt tikai zīmē norādītajā virzienā. </p> " +
                " <p class='zim-text'>297.&nbsp;zīme norāda, ka jānogriežas pirms šīs zīmes.</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.9.", " <p class='zim-text'>409.&nbsp;zīme “Braukt pa loku“;</p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67275.jpg'>294.&nbsp;zīme norāda, ka atļauts braukt tikai zīmē norādītajā virzienā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.10.", " <p class='zim-bolded'><strong>410.&nbsp;zīme “Šķērsli apbraukt pa labo pusi“;</strong></p> " +
                " <p class='zim-bolded'><strong><img style='max-height: 616px;' class='zimes' src='/public/imgS/67306.jpg'></strong></p> "));

        paragraphDAOs.add(new ParagraphDAO("293.11.", " <p class='zim-bolded'><strong>411.&nbsp;zīme “Šķērsli apbraukt pa kreiso pusi“;</strong></p> " +
                " <p class='zim-bolded'><strong><img style='max-height: 616px;' class='zimes' src='/public/imgS/67323.jpg'></strong></p> "));

        paragraphDAOs.add(new ParagraphDAO("293.12.", " <p class='zim-bolded'><strong>412.&nbsp;zīme “Šķērsli apbraukt pa labo vai pa kreiso pusi“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67337.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("293.13.", " <p class='zim-bolded'><strong>413.&nbsp;zīme “Velosipēdu ceļš”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67357.jpg'>Atļauts braukt tikai ar velosipēdiem, kā arī pārvietoties gājējiem, netraucējot velosipēdu braukšanai;</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.14.", " <p class='zim-bolded'><strong>413. A zīme “Velosipēdu ceļa beigas“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67374.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("293.15.", " <p class='zim-bolded'><strong>414.&nbsp;zīme “Gājēju ceļš“. </strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67387.jpg'>Atļauts pārvietoties tikai gājējiem;</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.16.", " <p class='zim-bolded'><strong>414. A zīme “Gājēju ceļa beigas“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67408.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("293.17.", " <p class='zim-bolded'><strong>415.&nbsp;zīme “Gājēju un velosipēdu ceļš“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67427.jpg'>Atļauts braukt tikai ar velosipēdiem, kā arī pārvietoties gājējiem;</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.18.", " <p class='zim-bolded'><strong>293.18. 415. A zīme “Gājēju un velosipēdu ceļa beigas“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67459.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("293.19.", " <p class='zim-bolded'><strong>416. un 417.&nbsp;zīme “Gājēju un velosipēdu ceļš“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67483.jpg'>Pa ceļa vienu pusi atļauts braukt tikai ar velosipēdiem, bet pa otru pusi - pārvietoties gājējiem (atbilstoši zīmē norādītajam);</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.20.", " <p class='zim-bolded'><strong>418.&nbsp;zīme “Minimālā ātruma ierobežojums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67535.jpg'>Atļauts braukt ar ātrumu (km/h), kas nav mazāks par zīmē norādīto;</p> "));

        paragraphDAOs.add(new ParagraphDAO("293.21.", " <p class='zim-bolded'><strong>419.&nbsp;zīme “Minimālā ātruma ierobežojums beidzas“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67582.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("293.22.", " <p class='zim-bolded'><strong>420.&nbsp;zīme “Transportlīdzekļiem ar bīstamu kravu braukt taisni“;421.&nbsp;zīme “Transportlīdzekļiem ar bīstamu kravu braukt pa labi”;422.&nbsp;zīme “Transportlīdzekļiem ar bīstamu kravu braukt pa kreisi”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67717.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298", " <p id='toc_marker-39' class='Virsraksts'><strong>27.5. Norādījuma zīmes</strong></p> " +
                " <p class='txt'>298. Norādījuma zīmes ievieš vai atceļ noteiktu ceļu satiksmes režīmu. Norādījuma zīmes ir šādas:</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.1.", " <p class='zim-bolded'><strong>501.&nbsp;zīme “Vienvirziena ceļš“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67889.jpg'>Ceļš vai brauktuve, pa kuru visā platumā atļauts braukt transportlīdzekļiem vienā virzienā;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.2.", " <p class='zim-bolded'><strong>502.&nbsp;zīme “Vienvirziena ceļa beigas“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67900.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.3.", " <p class='zim-bolded'><strong>503. un 504.&nbsp;zīme “Izbraukšana uz vienvirziena ceļa“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67924.jpg'></p> " +
                " <p class='txt'>Izbraukšana uz ceļa vai brauktuves, pa kuru atļauts braukt vienā - zīmē norādītajā - virzienā;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.4.", " <p class='zim-bolded'><strong>505.&nbsp;zīme “Josla pasažieru sabiedriskajiem transportlīdzekļiem”. </strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67949.jpg'>Josla, kas paredzēta tikai pasažieru sabiedriskajiem transportlīdzekļiem un taksometriem, kuri brauc vienā virzienā ar pārējo transportlīdzekļu plūsmu. </p> " +
                " <p class='zim-text'>Zīmes darbība attiecas uz joslu, virs kuras zīme uzstādīta. </p> " +
                " <p class='zim-text'>Ja zīme uzstādīta ceļa labajā pusē, tās darbība attiecas uz labo braukšanas joslu.</p> " +
                " <p class='txt'>Zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, - līdz 505.A zīmei;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.5.", " <p class='zim-bolded'><strong>505. A zīme “Joslas pasažieru sabiedriskajiem transportlīdzekļiem beigas”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67968.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.6.", " <p class='zim-bolded'><strong>506.&nbsp;zīme “Ceļš ar joslu pasažieru sabiedriskajiem transportlīdzekļiem“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/67988.jpg'>Ceļš, pa kuru pasažieru sabiedrisko transportlīdzekļu un taksometru satiksme notiek pa atsevišķu joslu pretim pārējo transportlīdzekļu plūsmai;</p> " +
                " <p class='zim-text'>507.&nbsp;zīme “Ceļa ar joslu pasažieru sabiedriskajiem transportlīdzekļiem beigas“;</p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/68144.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.7.", " <p class='zim-bolded'><strong>508. un 509.&nbsp;zīme “Izbraukšana uz ceļa ar joslu pasažieru sabiedriskajiem transportlīdzekļiem“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/68171.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.8.", " <p class='zim-bolded'><strong>510.&nbsp;zīme “Minimālā braukšanas ātruma ierobežojums joslās“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/68208.jpg'>Joslu skaits un minimālā braukšanas ātruma ierobežojums katrā joslā;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.9.", " <p class='zim-bolded'><strong>511.&nbsp;zīme “Maksimālā braukšanas ātruma ierobežojums joslās“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/68251.jpg'>Joslu skaits un maksimālā braukšanas ātruma ierobežojums katrā joslā;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.10.", " <p class='zim-bolded'><strong>512.&nbsp;zīme “Braukšanas virzieni joslās“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/68375.jpg'>Joslu skaits un no tām noteiktie braukšanas virzieni krustojumā.</p> " +
                " <p class='txt'>Var būt norādīta informācija par ceļu satiksmes Ierobežojumiem krustojumā.</p> " +
                " <p class='txt'>Zīme, kas atļauj nogriezties pa kreisi no kreisās malējās braukšanas joslas, atļauj no šīs joslas apgriezties arī braukšanai pretējā virzienā </p> " +
                " <p class='txt'>(var tikt lietota zīme ar bultu konfigurāciju, kas atbilst noteiktajiem braukšanas virzieniem krustojumā).</p> " +
                " <p class='txt'>Zīmes darbība attiecas uz visu krustojumu, pirms kura zīme uzstādīta, ja cita zīme, kas uzstādīta krustojumā, nedod citus norādījumus</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.11.", " <p class='zim-bolded'><strong>513., 514., 515., 516. un 517.&nbsp;zīme “Braukšanas virzieni joslā“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/68521.jpg'></p> " +
                " <p class='txt'>No joslas noteiktie braukšanas virzieni krustojumā. </p> " +
                " <p class='txt'>Uz zīmēm var būt norādīta informācija par ceļu satiksmes Ierobežojumiem krustojumā.&nbsp;zīmes, kas atļauj nogriezties pa kreisi no kreisās malējās braukšanas joslas, atļauj no šīs joslas apgriezties arī braukšanai pretējā virzienā (var tikt lietota zīme ar bultu konfigurāciju, kas atbilst noteiktajiem braukšanas virzieniem krustojumā). </p> " +
                " <p class='txt'>Zīmes darbība attiecas uz visu krustojumu, pirms kura zīme uzstādīta, ja cita zīme, kas uzstādīta krustojumā, nedod citus norādījumus.</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.12.", " <p class='zim-bolded'><strong>518.&nbsp;zīme “Apdzīvotas vietas sākums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/68959.jpg'>Nosaukums un sākums apdzīvotai vietai, kurā ir spēkā šo noteikumu prasības, kas nosaka ceļu satiksmes kārtību apdzīvotās vietās;</p> "));


        paragraphDAOs.add(new ParagraphDAO("298.13.", " <p class='zim-bolded'><strong>519.&nbsp;zīme “Apdzīvotas vietas beigas“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/68988.jpg'>Ar zīmi “Apdzīvotas vietas sākums (uz balta fona)“ apzīmētas apdzīvotas vietas beigas;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.14.", " <p class='zim-bolded'><strong>520.&nbsp;zīme “Apdzīvotas vietas sākums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69027.jpg'>Nosaukums un sākums apdzīvotai vietai, kurā nav spēkā šo noteikumu prasības, kas nosaka ceļu satiksmes kārtību apdzīvotās vietās;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.15.", " <p class='zim-bolded'><strong>521.&nbsp;zīme “Apdzīvotas vietas beigas“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69039.jpg'>Ar zīmi “Apdzīvotas vietas sākums (uz zila fona)“ apzīmētas apdzīvotas vietas beigas.</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.16.", " <p class='zim-bolded'><strong>522.&nbsp;zīme “Stāvēšanas aizlieguma zona“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69065.jpg'>Stāvēt aizliegts uz visiem ceļiem teritorijā, kurā iebraukšana apzīmēta ar zīmi;</p> " +
                " <p class='zim-text'>301. Pašvaldības ar attiecīgu lēmumu ir tiesīgas noteikt laikposmu, kad transportlīdzekļos drīkst iekraut vai izkraut kravu ar zīmi apzīmētajā teritorijā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.17.", " <p class='zim-bolded'><strong>523.&nbsp;zīme “Stāvēšanas aizlieguma zonas beigas“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69125.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.18.", " <p class='zim-bolded'><strong>524.&nbsp;zīme “Maksimālā ātruma ierobežojuma zona“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69500.jpg'>Maksimālā ātruma ierobežojums uz visiem ceļiem teritorijā, kurā iebraukšana apzīmēta ar zīmi;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.19.", " <p class='zim-bolded'><strong>525.&nbsp;zīme “Maksimālā ātruma ierobežojuma zonas beigas“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69531.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.20.", " <p class='zim-bolded'>526.&nbsp;zīme “Muitas zona“.</p> " +
                        " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69575.jpg'>Vieta, no kuras sākot ir spēkā noteikumi, kas nosaka kārtību muitas zonās;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.21.", " <p class='zim-bolded'><strong>527.&nbsp;zīme “Muitas zonas beigas“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69654.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.22.", " <p class='zim-bolded'><strong>528.&nbsp;zīme “Dzīvojamā zona“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69733.jpg'>Vieta, no kuras sākot ir spēkā šo noteikumu prasības, kas nosaka ceļu satiksmes kārtību dzīvojamās zonās;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.23.", " <p class='zim-bolded'><strong>529.&nbsp;zīme “Dzīvojamās zonas beigas“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/69794.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.24.", " <p class='zim-bolded'><strong>530.&nbsp;zīme “Gājēju pāreja“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70003.jpg'>Zīmi uzstāda ceļa labajā pusē uz pārejas tuvākās robežas;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.25.", " <p class='zim-bolded'><strong>531.&nbsp;zīme “Gājēju pāreja“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70167.jpg'>Zīmi uzstāda ceļa kreisajā pusē uz pārejas tālākās robežas;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.26.", " <p class='zim-bolded'><strong>532.&nbsp;zīme “Stāvvieta”; 532. A zīme “Stāvvietas beigas“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/91250.jpg'>Zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, - līdz 532.A, 519., 326., 327., 328. vai 329.&nbsp;zīmei. </p> " +
                " <p class='zim-text'>Zīmes apakšdaļā var tikt norādīts virziens, kādā jābrauc uz stāvvietu, kas atrodas ceļa tiešā tuvumā. </p> " +
                " <p class='txt'>Ēkas jumta simbols apzīmē pazemes vai virszemes daudzstāvu stāvvietu. </p> " +
                " <p class='txt'>Stāvvietā nav spēkā šo noteikumu 135.6. un 136.1.apakšpunktā noteiktie aizliegumi;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.28.", " <p class='zim-bolded'><strong>533.&nbsp;zīme “Maksas stāvvieta”; 533. A zīme “Maksas stāvvietas beigas“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/91383.jpg'>Zīmes darbība ir spēkā no tās uzstādīšanas vietas līdz tuvākajam krustojumam aiz zīmes, bet, ja krustojuma nav, - līdz 533.A, 519., 326., 327., 328. vai 329.&nbsp;zīmei. </p> " +
                " <p class='zim-text'>Uz zīmes var būt norādīts stāvēšanas maksimālais ilgums, par kuru tūlīt var samaksāt. </p> " +
                " <p class='txt'>Zīmes apakšdaļā var tikt norādīts virziens, kādā jābrauc uz stāvvietu, kas atrodas ceļa tiešā tuvumā. Stāvvietā nav spēkā šo noteikumu 135.6. un 136.1.apakšpunktā noteiktie aizliegumi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.30.", " <p class='zim-bolded'><strong>534.&nbsp;zīme “Autobusa un trolejbusa pietura“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70303.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.31.", " <p class='zim-bolded'><strong>535.&nbsp;zīme “Tramvaja pietura“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70318.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.32.", " <p class='zim-bolded'><strong>536.&nbsp;zīme “Vieglo taksometru stāvvieta“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70344.jpg'>299.&nbsp;zīmes apakšdaļā ar ciparu var tikt norādīts taksometru skaits, kādam vienlaikus atļauts atrasties stāvvietā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.33.", " <p class='zim-bolded'><strong>537.&nbsp;zīme “Kravas taksometru stāvvieta“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70374.jpg'>299.&nbsp;zīmes apakšdaļā ar ciparu var tikt norādīts taksometru skaits, kādam vienlaikus atļauts atrasties stāvvietā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.34.", " <p class='zim-bolded'><strong>538.&nbsp;zīme “Maršruta taksometru stāvvieta“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70406.jpg'>Apzīmē maršruta taksometru stāvvietu to galapunktos;</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.35.", " <p class='zim-bolded'><strong>539.&nbsp;zīme “Tunelis”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70437.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.36.", " <p class='zim-bolded'><strong>540.&nbsp;zīme “Tuneļa beigas” </strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70457.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.37.", " <p class='zim-bolded'><strong>541.&nbsp;zīme “Apstāšanās vieta”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70472.jpg'>Vieta, kur jāaptur transportlīdzeklis, ja braukšanu aizliedz luksofora vai satiksmes regulētāja signāls; </p> "));

        paragraphDAOs.add(new ParagraphDAO("298.38.", " <p class='zim-bolded'><strong>542.&nbsp;zīme “Pierobežas sākums”, 543.&nbsp;zīme “Pierobežas beigas”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/87199.jpg'></p> " +
                " <p class='txt'>300.&nbsp;zīme norāda vietu, no kuras sākot stājas spēkā noteikumi, kas nosaka pierobežas un pierobežas joslas režīmu.</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.40.", " <p class='zim-bolded'><strong>544.&nbsp;zīme “Pierobežas joslas sākums“, 545.&nbsp;zīme “Pierobežas joslas beigas“</strong></p> " +
                " <p class='txt'><img style='max-height: 616px;' class='zimes' src='/public/imgS/87140.jpg'></p> " +
                " <p class='txt'>300.&nbsp;zīme norāda vietu, no kuras sākot stājas spēkā noteikumi, kas nosaka pierobežas un pierobežas joslas režīmu.</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.42.", " <p class='zim-bolded'><strong>546.&nbsp;zīme “Robežkontroles punkts“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70661.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.43.", " <p class='zim-bolded'><strong>547.&nbsp;zīme “Robežpārejas punkts“;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70679.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("298.44.", " <p class='zim-bolded'><strong>548.&nbsp;zīme “Ātrgaitas ceļš“, 549.&nbsp;zīme “Ātrgaitas ceļa beigas“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/87254.jpg'>Izbraukšana uz ceļa, pa kuru atļauts braukt tikai ar motocikliem, tricikliem, kvadricikliem, automobiļiem un autobusiem.</p> "));

        paragraphDAOs.add(new ParagraphDAO("298.45.", " <p class='zim-bolded'><strong>550.&nbsp;zīme “Piespiedu apstāšanās vieta“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70763.jpg'>Norāda vietu tunelī, kas paredzēta transportlīdzekļa novietošanai piespiedu apstāšanās gadījumā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("302.", " <p id='toc_marker-40' class='Virsraksts'><strong>27.6. Servisa zīmes</strong></p> " +
                " <p class='txt'>304. Servisa zīmju apakšdaļā var būt norādīti attālumi un virzieni no zīmes uzstādīšanas vietas līdz attiecīgajam objektam, kā arī attiecīgo objektu nosaukumi.</p> "));


        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>601.&nbsp;zīme “Medicīniskās palīdzības punkts”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70968.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>602.&nbsp;zīme “Slimnīca”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/70991.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>603.un 604.&nbsp;zīme “Degvielas uzpildes stacija”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71034.jpg'>604.&nbsp;zīme norāda, ka stacijā var iegādāties arī benzīnu (neetilēto), kurš nesatur indīgus savienojumus;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>605.&nbsp;zīme “Tehniskās apkopes punkts”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71074.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>606.&nbsp;zīme “Automobiļu mazgātava”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71095.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>607.&nbsp;zīme “Telefons”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71124.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>608.&nbsp;zīme “Restorāns”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71139.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>609.&nbsp;zīme “Kafejnīca”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71156.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>610.&nbsp;zīme “Viesnīca vai motelis”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71169.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>611.&nbsp;zīme “Jauniešu tūristu mītne”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71183.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>612.&nbsp;zīme “Kempings”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71196.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>613.&nbsp;zīme “Kempingpiekabju stāvvieta”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71210.jpg'>Ar zīmi apzīmētajās vietās var novietot arī automobiļus vasarnīcas.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>614.&nbsp;zīme “Kempings un kempingpiekabju stāvvieta”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71227.jpg'>Ar zīmi apzīmētajās vietās var novietot arī automobiļus vasarnīcas.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>615.&nbsp;zīme “Atpūtas vieta”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71628.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>616.&nbsp;zīme “Gājēju maršruta sākums”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71643.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>617.&nbsp;zīme “Tualete“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71659.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>618.&nbsp;zīme “Peldvieta vai peldbaseins“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71672.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>619.&nbsp;zīme “Informācijas punkts”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71684.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>620.&nbsp;zīme “Policija”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71702.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>621.&nbsp;zīme “Ceļu policija“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71738.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>622.&nbsp;zīme “Pasts“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71775.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>623.&nbsp;zīme “Radiokanāls ceļu satiksmes informācijas sniegšanai“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71796.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>624.&nbsp;zīme “Lidosta“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71846.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>625.&nbsp;zīme “Autoosta“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71883.jpg'></p> " +
                " <p class='zim-text'><span class='char-style-override-2'>626.&nbsp;zīme “Dzelzceļa stacija“</span></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/87327.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>627.&nbsp;zīme “Jūras pasažieru stacija“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71938.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>628.&nbsp;zīme “Prāmis“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71954.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>629.&nbsp;zīme “Kravas osta”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/71987.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>630.&nbsp;zīme “Informācijas bloks”</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/72000.jpg'> Zīme norāda, ka vairāki zīmē attēlotie objekti atrodas vienuviet.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>631.&nbsp;zīme “Ievērojama vieta”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/72036.jpg'>Norāda vietu, kur pie ceļa atrodas tūrisma objekts;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>632.&nbsp;zīme “Ugunsdzēšamais aparāts”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/72065.jpg'>Norāda vietu tunelī, kur atrodas ugunsdzēšamais aparāts;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>633.&nbsp;zīme “Avārijas tālrunis”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/72081.jpg'>Norāda vietu tunelī, kur atrodas tālrunis, pa kuru var izsaukt glābšanas dienestu.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p id='toc_marker-41' class='Virsraksts'><strong>27.7. Virzienu rādītāji un informācijas zīmes</strong></p> "));

        paragraphDAOs.add(new ParagraphDAO("305.", " <p class='txt'>305. Virzienu rādītāji un informācijas zīmes ir šādas:</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>701. un 702.&nbsp;zīme “Iepriekšējs virzienu rādītājs“</strong></p> " +
                " <p class='txt'><img style='max-height: 616px;' class='zimes' src='/public/imgS/72340.jpg'></p> " +
                " <p class='txt'>Uz zīmes norādīti braukšanas virzieni uz apdzīvotām vietām un citiem objektiem. </p> " +
                " <p class='txt'>Uz zīmes var būt attēloti simboli, piktogrammas un citas zīmes, kas informē par ceļu satiksmes īpatnībām. Zīmes apakšdaļā norādīts attālums no zīmes uzstādīšanas vietas līdz krustojumam vai bremzēšanas joslas sākumam.</p> "));

        paragraphDAOs.add(new ParagraphDAO("306.", " <p class='txt'>306.&nbsp;zīmes (ja tā uzstādīta apdzīvotā vietā) zilais fons nozīmē, ka norādītais objekts atrodas ārpus attiecīgās apdzīvotās vietas, bet baltais fons -, ka norādītais objekts atrodas attiecīgajā apdzīvotajā vietā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>703.&nbsp;zīme “Virzienu rādītājs“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/72671.jpg'>Braukšanas virzieni un attālumi (km) līdz apdzīvotām vietām un citiem objektiem;</p> " +
                " <p class='zim-text'>306.&nbsp;zīmes (ja tā uzstādīta apdzīvotā vietā) zilais fons nozīmē, ka norādītais objekts atrodas ārpus attiecīgās apdzīvotās vietas, bet baltais fons -, ka norādītais objekts atrodas attiecīgajā apdzīvotajā vietā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>704. un 705.&nbsp;zīme “Virziena rādītājs“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/72864.jpg'>Braukšanas virziens un attālums (km) līdz apdzīvotai vietai vai citam objektam.</p> " +
                " <p class='zim-text'>306.&nbsp;zīmes (ja tā uzstādīta apdzīvotā vietā) zilais fons nozīmē, ka norādītais objekts atrodas ārpus attiecīgās apdzīvotās vietas, bet baltais fons -, ka norādītais objekts atrodas attiecīgajā apdzīvotajā vietā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>706.&nbsp;zīme “Virziena rādītājs“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/73597.jpg'></p> " +
                " <p class='txt'>Braukšanas virziens un attālums (km) līdz lidostai (zīme ar zilu fonu), tūrisma objektam (zīme ar brūnu fonu) vai braukšanas virziens uz viensētu (zīme ar pelēku fonu);</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>707.&nbsp;zīme “Attālumu rādītājs“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/73926.jpg'>Attālumi (km) līdz maršrutā esošo apdzīvoto vietu centriem;</p> " +
                " <p class='zim-text'>708.&nbsp;zīme “Ūdensšķēršļa nosaukums“</p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74112.jpg'>Tās upes, kanāla, ezera u.tml. ūdensšķēršļa nosaukums, kuru šķērso ceļš.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>709.&nbsp;zīme “Braukšanas shēma“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74135.jpg'>Braukšanas maršruts, ja krustojumā aizliegti atsevišķi manevri vai atļautie braukšanas virzieni sarežģītā krustojumā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>710.&nbsp;zīme “Strupceļš“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74164.jpg'>Ceļš, pa kuru nav iespējams caurbraukt. </p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>711. un 712.&nbsp;zīme “Iepriekšējs virziena rādītājs strupceļam“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74179.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>713. un 714.&nbsp;zīme “Joslas sākums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74224.jpg'>Bremzēšanas, augšupceļa u.tml. joslas sākums</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>715. un 716.&nbsp;zīme “Joslas beigas“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74254.jpg'>Ieskrējiena, augšupceļa u.tml. joslas beigas.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>717., 718. un 719.&nbsp;zīme “Braukšanas virzieni joslās“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74291.jpg'></p> " +
                " <p class='txt'>Joslu skaits katrā virzienā ceļa posmā un braukšanas virzieni katrā joslā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>720. un 721.&nbsp;zīme “Joslas piekļaušanās pamatjoslām“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74361.jpg'>Vienas vai vairāku joslu piekļaušanās pamatjoslām ceļa pārvadu tuvumā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>722.&nbsp;zīme “Apgriešanās vieta“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74656.jpg'>Ceļa posmā starp krustojumiem norāda vietu vai pārtraukumu sadalošajā joslā, kur atļauta apgriešanās braukšanai pretējā virzienā. Nogriezties pa kreisi aizliegts.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>723.&nbsp;zīme “Satiksmes ierobežojumi Latvijā“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74534.jpg'>Informē Latvijā iebraucošos par braukšanas ātruma vispārējiem ierobežojumiem un dienas gaitas vai tuvās gaismas lukturu lietošanu;</p> " +
                " <p class='zim-text'> </p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>724.&nbsp;zīme “Ieteicamais ātrums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/74848.jpg'>Ātrums, ar kādu ieteicams braukt turpmākajā ceļa posmā.&nbsp;zīme ir spēkā līdz tuvākajam krustojumam aiz zīmes, bet, ja 724.&nbsp;zīme lietota kopā ar brīdinājuma zīmi, tās darbības zonu nosaka bīstamā posma garums.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>725., 726. un 727.&nbsp;zīme “Kravas automobiļu braukšanas virziens“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/87419.jpg'></p> " +
                " <p class='txt'>Ieteicamais braukšanas virziens kravas automobiļiem, to sastāviem un traktortehnikai, ja krustojumā tiem aizliegts braukt vienā no iespējamiem virzieniem.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>728.&nbsp;zīme “Gājēju apakšzemes vai virszemes pāreja“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75084.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>729.&nbsp;zīme “Apbraukšanas ceļa shēma“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75123.jpg'>Uz laiku ceļu satiksmei slēgta ceļa posma apbraukšanas maršruts;</p> " +
                " <p class='zim-text'> </p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>730., 731. un 732.&nbsp;zīme “Apbraukšanas ceļa virziens“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75236.jpg'></p> " +
                " <p class='txt'>Apbraukšanas virziens ceļa posmam, kurā ceļu satiksme uz laiku slēgta vai ierobežota;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>733.&nbsp;zīme “Apbraukšanas ceļa beigas“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75278.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>734., 734.A un 735.&nbsp;zīme “Iepriekšējs norādītājs pārkārtoties“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75343.jpg'></p> " +
                " <p class='txt'>Uz laiku ceļu satiksmei slēgta brauktuves posma vai braukšanas joslas apbraukšanas virziens;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>736.&nbsp;zīme “Kilometru rādītājs“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75819.jpg'>Attālums (km) no ceļa sākuma;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>737.&nbsp;zīme “Ceļa numurs“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75841.jpg'>Zīme ar zaļu fonu un numurs, kas piešķirts attiecīgajam Eiropas autoceļu (maģistrāļu) sistēmas ceļam (maršrutam);</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>738.&nbsp;zīme “Ceļa numurs“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75857.jpg'>Zīme ar sarkanu fonu un numurs, kas piešķirts attiecīgajam Latvijas galvenajam valsts autoceļam;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>739.&nbsp;zīme “Ceļa numurs“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75881.jpg'>Zīme ar zilu fonu un numurs, kas piešķirts attiecīgajam Latvijas 1.šķiras valsts autoceļam.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>740., 741. un 742.&nbsp;zīme “Ceļa numurs un virziens“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75911.jpg'></p> " +
                " <p class='txt'>Zīmes fons norāda ceļa piederību pie attiecīgas šķiras autoceļa.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>743.&nbsp;zīme “Maiņvirziena satiksme“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/75974.jpg'>Tāda ceļa posma sākums, uz kura vienā vai vairākās joslās ceļu satiksmes virziens var mainīties uz pretējo.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>744.&nbsp;zīme “Maiņvirziena satiksmes beigas“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76000.jpg'>Tāda ceļa posma beigas, uz kura vienā vai vairākās joslās ceļu satiksmes virziens var mainīties uz pretējo;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>745.&nbsp;zīme “Izbraukšana uz ceļa, kur organizēta maiņvirziena satiksme“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/87489.jpg'>Izbraukšana uz ceļa, uz kura vienā vai vairākās joslās ceļu satiksmes virziens var mainīties uz pretējo;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>746.&nbsp;zīme “Valsts nosaukums“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76042.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>747.&nbsp;zīme “Administratīvi teritoriālās vienības nosaukums”;</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76090.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>748.&nbsp;zīme “Tūrisma objektu teritorija”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76165.jpg'>Iebraukšana teritorijā, kur atrodas daudzi savstarpēji tuvu esoši tūrisma objekti;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>749.&nbsp;zīme “Avārijas izeja”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76225.jpg'>Norāda vietu tunelī, kur atrodas avārijas izeja</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>750.&nbsp;zīme “Avārijas izejas virziens”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76242.jpg'>Norāda virzienu tunelī, kādā jādodas uz avārijas izeju, kā arī attālumu līdz tai.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <div id='CSN_A5_CSN_zimes_LV_epub-8.html' xml:lang='lv-LV'> <div class='Basic-Text-Frame'> <p id='toc_marker-42' class='Virsraksts'><strong>27.8. Papildzīmes</strong></p> "));

        paragraphDAOs.add(new ParagraphDAO("307.", " <p class='txt'>307. Papildzīmes tiek lietotas kopā ar citām zīmēm un precizē vai ierobežo attiecīgo zīmju darbību. Papildzīmes ir šādas:</p> "));
        paragraphDAOs.add(new ParagraphDAO("310.", " <p class='txt'>310. Papildzīmes piestiprina tieši zem zīmes, uz kuru tās attiecas, izņemot 841. vai 846. papildzīmi, ja tā uzstādīta bez pamatzīmes. Ja zīmes novietotas virs brauk­tuves, nomales vai ietves, 807., 808. un 840. papildzīme var būt piestiprināta blakus zīmei.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>801.papildzīme “Attālums līdz objektam“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76647.jpg'>Norāda attālumu no zīmes līdz ceļa bīstamā posma sākumam vai attiecīgā ceļu satiksmes ierobežojuma ieviešanas vietai.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>802. papildzīme “Attālums līdz objektam“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76830.jpg'>Norāda attālumu no 206.&nbsp;zīmes līdz krustojumam, ja tieši pirms krustojuma uzstādīta 207.&nbsp;zīme;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>803. papildzīme “Darbības zona“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76856.jpg'>Norāda garumu ceļa bīstamajam posmam, kas apzīmēts ar brīdinājuma zīmēm, vai aizlieguma zīmes darbības zonu, kā arī 532., 533. un 724.&nbsp;zīmes darbības zonu;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>804., 805. un 806.papildzīme “Darbības zona”.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76891.jpg'></p> " +
                " <p class='txt'>Norāda 326., 327., 328. un 329.&nbsp;zīmes darbības virzienu un zonu, ja apstāšanās vai stāvēšana gar laukuma vienu pusi, ēkas fasādi u.tml. ir aizliegta.</p> " +
                " <p class='txt'>Ar 804. un 806. papildzīmi norāda arī attālumu līdz objektam, kas ir apzīmēts ar 532. vai 533.&nbsp;zīmi, bet ar 805.papildzīmi - 532. un 533.&nbsp;zīmes darbības zonu, ja tās ir uzstādītas paralēli stāvvietas malai.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>807. papildzīme “Darbības zona“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76962.jpg'>Informē vadītāju, ka viņš atrodas 326., 327., 328. vai 329.&nbsp;zīmes darbības zonā;</p> " +
                " <p class='zim-text'>310. Papildzīme var būt piestiprināta blakus zīmei.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>808. papildzīme “Darbības zona“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/76990.jpg'>Norāda 326., 327., 328. un 329.&nbsp;zīmes darbības zonas beigas;</p> " +
                " <p class='zim-text'>310. Papildzīme var būt piestiprināta blakus zīmei.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>809. papildzīme “Darbības virziens“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/77016.jpg'>308. Papildzīme norāda pirms krustojuma uzstādīto zīmju darbības virzienus vai braukšanas virzienu uz stāvvietu, kas apzīmēta ar 532. vai 533.&nbsp;zīmi un atrodas ceļa tiešā tuvumā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>810. papildzīme “Darbības virzieni“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/77032.jpg'>308. Papildzīme norāda pirms krustojuma uzstādīto zīmju darbības virzienus vai braukšanas virzienu uz stāvvietu, kas apzīmēta ar 532. vai 533.&nbsp;zīmi un atrodas ceļa tiešā tuvumā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>811. papildzīme “Darbības virziens“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/77044.jpg'>308. Papildzīme norāda pirms krustojuma uzstādīto zīmju darbības virzienus vai braukšanas virzienu uz stāvvietu, kas apzīmēta ar 532. vai 533.&nbsp;zīmi un atrodas ceļa tiešā tuvumā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>812. papildzīme “Braukšanas josla“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/77440.jpg'>Norāda braukšanas joslu vai tramvaja sliežu ceļa klātni, uz kuru attiecas zīmes darbība. </p> " +
                " <p class='zim-text'>Ja papildzīme uzstādīta kopā ar aizlieguma zīmi, tad noteiktais aizliegums ir spēkā līdz tuvākajam krustojumam aiz zīmes;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>813., 814., 815., 816., 817., 818. un 819. papildzīme “Transportlīdzekļa veids“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/77510.jpg'></p> " +
                " <p class='txt'>Norāda transportlīdzekļa veidu, uz kuru attiecas zīmes darbība. </p> "));

        paragraphDAOs.add(new ParagraphDAO("813.", " <p class='txt'>813. papildzīme attiecas uz kravas automobiļiem (arī uz kravas automobiļiem ar piekabēm (puspiekabēm), kuru pilna masa pārsniedz 3,5 t. </p> "));
        paragraphDAOs.add(new ParagraphDAO("815.", " <p class='txt'>815.papildzīme attiecas uz vieglajiem automobiļiem (arī uz vieglajiem automobiļiem ar piekabēm), kā arī uz kravas automobiļiem, kuru pilna masa nepārsniedz 3,5 t. </p> "));
        paragraphDAOs.add(new ParagraphDAO("818.", " <p class='txt'>818. papildzīme attiecas uz motocikliem, tricikliem un kvadricikliem.</p> "));
        paragraphDAOs.add(new ParagraphDAO("819.", " <p class='txt'>819. papildzīme attiecas uz velosipēdiem un mopēdiem;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>820. papildzīme “Darbdienās“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/77854.jpg'>309. papildzīme norāda dienas, kurās zīme ir spēkā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>821. papildzīme “Sestdienās, svētdienās un svētku dienās“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/77868.jpg'>309. papildzīme norāda dienas, kurās zīme ir spēkā.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>822. papildzīme “Darbības laiks“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/77886.jpg'>Norāda diennakts laiku, kurā zīme darbojas.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>823. un 824. papildzīme “Darbības laiks“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/77937.jpg'>Norāda dienas un diennakts laiku, kurā zīme darbojas</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>825., 826., 827., 828., 829., 830., 831. un 832. papildzīme “Transportlīdzekļa novietojuma veids stāvvietā“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78200.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("827.", " <p class='txt'>827., 828., 829., 830., 831. un 832. papildzīme norāda vieglo automobiļu bez piekabēm, motociklu, triciklu un kvadriciklu novietojuma veidu stāvvietā pie ietves vai uzbraucot uz tās, bet 825. un 826. papildzīme - arī vieglo automobiļu ar piekabēm novietojuma veidu stāvvietā pie ietves vai uzbraucot uz tās.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>833. papildzīme “Stāvēšanas ilgums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78287.jpg'>Šo papildzīmi lieto kopā ar 532.&nbsp;zīmi. Vadītājam, kurš novietojis automobili ar šīm zīmēm apzīmētajā stāvvietā, automobiļa salonā pie priekšējā stikla jānovieto informācija par laiku, kad transportlīdzeklis novietots stāvvietā;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>834. papildzīme “Automobiļu apskates vieta“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78321.jpg'>Norāda, ka ir ierīkota estakāde vai remontbedre automobiļu apskatei;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>835. papildzīme “Pilnas masas ierobežojums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78341.jpg'>Norāda, ka zīme attiecas tikai uz transportlīdzekļiem, kuru pilna masa ir lielāka par papildzīmē norādīto;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>836. papildzīme “Neredzīgi gājēji“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78356.jpg'>Norāda, ka gājēju pāreju izmanto arī neredzīgi gājēji;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>837. papildzīme “Invalīdiem“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78379.jpg'>Norāda, ka 532.&nbsp;zīme attiecas tikai uz automobiļiem, kas apzīmēti ar invalīdu stāvvietu izmantošanas karti;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>838. papildzīme “Mitrs segums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78407.jpg'>Norāda, ka zīme attiecas uz laikposmu, kad brauktuves segums ir mitrs.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>839. papildzīme “Slidens segums“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78436.jpg'>Norāda, ka priekšā ir slidens ceļa posms, kurš klāts ar sniegu vai ledu;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>840. papildzīme “Galvenā ceļa virziens“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78447.jpg'>Norāda galvenā ceļa virzienu krustojumā;</p> " +
                " <p class='zim-text'>310. Papildzīme var būt piestiprināta blakus zīmei.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>841. papildzīme “Strādā autoevakuators“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78468.jpg'>Papildzīmi uzstāda kopā ar 326., 327., 328. un 329.&nbsp;zīmi, kā arī bez tās vietās, kur stāvošie automobiļi traucē iebraukt vai izbraukt no pagalmiem u.tml. vietām;</p> " +
                " <p class='zim-text'>310. Papildzīme var tikt uzstādīta bez pamatzīmes</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>842. papildzīme “Pārējā papildinformācija“</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78503.jpg'>Norāda informāciju, ko nesatur pārējās papildzīmes (atceļ apstāšanās un stāvēšanas aizliegumu 326., 327., 328. un 329.&nbsp;zīmes darbības zonā transportlīdzekļiem ar speciālām atļaujām (vadītājam automobiļa salonā pie priekšējā stikla jānovieto atļauja), norāda laikposmu, kurā atļauts iekraut un izkraut kravu), norāda informāciju par velosipēdu satiksmes organizācijas īpatnībām (vienvirziena, divvirzienu satiksme, satiksme uz vienvirziena ceļa pretēji kopējai transportlīdzekļu plūsmai u.c.) vai velosipēdu braukšanas maršrutus (uz zaļa vai zila fona) u.tml.)</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>843. papildzīme “Nelīdzens ceļš (slieksnis)“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78757.jpg'>Norāda, ka gājēju pāreja ierīkota uz mākslīga paaugstinājuma braukšanas ātruma samazināšanai;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>844. papildzīme “Maksas stāvvietas darbības laiks“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78779.jpg'>Norāda, ka ar 532.&nbsp;zīmi apzīmēta stāvvieta noteiktu laiku tiek lietota kā maksas stāvvieta, bet pārējā laikā tā ir bezmaksas;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>845. papildzīme “Izslēgt motoru“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78819.jpg'>Norāda, ka stāvvietā aizliegts stāvēt ar ieslēgtu motoru.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>846. papildzīme “Fotoradars“.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/78837.jpg'>Norāda, ka uz ceļa var būt uzstādīts fotoradars vai videoradars.</p> " +
                " <p class='zim-text'>310. Papildzīme var tikt uzstādīta bez pamatzīmes</p> " +
                " <p id='toc_marker-43' class='Virsraksts'><strong>28. Ceļa apzīmējumi un to raksturojums</strong></p> "));

        paragraphDAOs.add(new ParagraphDAO("311.", " <p class='txt'>311. Ceļa apzīmējumiem (6. pielikums) jāatbilst valsts standartu LVS 85 un LVS 93 prasībām. Gājēju pārejas tiek projektētas atbilstoši valsts standartam LVS 190-10:2007 “Gājēju pāreju projektēšanas noteikumi”.</p> " +
                " <div id='CSN_A5_CSN_zimes_LV_epub-10.html' xml:lang='lv-LV'> <div class='Basic-Text-Frame'> <p id='toc_marker-44' class='Virsraksts'><strong>28.1. Vertikālie apzīmējumi</strong></p> "));

        paragraphDAOs.add(new ParagraphDAO("312.", " <p class='txt'>312. Vertikālie apzīmējumi uz ceļa būvēm, aprīkojuma elementiem un ceļa darbu vietās norāda objekta gabarītus un kalpo arī par orientēšanās līdzekļiem. Vertikālie apzīmējumi ir šādi:</p> " +
                " <p class='zim-bolded'><strong>901., 902., 903. un 904. vertikālie apzīmējumi.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/79026.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("312.1.", " <p class='txt'>312.1. ceļa apzīmējums apzīmē braukšanas virzienu pagriezienos, kur redzamību ierobežo ēkas, apstādījumi u.tml., braukšanas virzienu trīsvirzienu krustojumos un ceļa darbu vietas apbraukšanas virzienu.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>905. vertikālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/79191.jpg'>312.2. Ceļa apzīmējums apzīmē pa labo vai pa kreiso pusi apbraucamus paaugstinājumus (sadalošās joslas, drošības saliņas u.tml.);</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>906. un 907. vertikālie apzīmējumi.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/79204.jpg'>312.3.ceļa apzīmējumi apzīmē apbraucamus paaugstinājumus, sadalošās joslas, drošības saliņas u.tml.), kā arī šķēršļus (barjeru un nožogojumu galus u.tml.), kas atrodas tiešā brauktuves tuvumā un var būt bīstami braucošiem transportlīdzekļiem;</p> " +
                " <p class='zim-text'>906. ceļa apzīmējums apbraucams pa kreiso pusi; </p> " +
                " <p class='zim-text'>907. ceļa apzīmējums apbraucams pa labo pusi; </p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>908., 909., 910., 911., 912., 913. vertikālie apzīmējumi.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/79254.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("312.4.", " <p class='txt'>312.4. ceļa apzīmējumi apzīmē ceļa darbu vietas </p> "));
        paragraphDAOs.add(new ParagraphDAO("908.", " <p class='txt'>908. un 910. ceļa apzīmējums - pa kreiso pusi apbraucamas ceļa darbu vietas; </p> "));
        paragraphDAOs.add(new ParagraphDAO("909.", " <p class='txt'>909. un 911. ceļa apzīmējums - pa labo pusi apbraucamas ceļa darbu vietas; </p> "));
        paragraphDAOs.add(new ParagraphDAO("912.", " <p class='txt'>912.ceļa apzīmējums - tālākā braukšana aizliegta; </p> "));
        paragraphDAOs.add(new ParagraphDAO("913.", " <p class='txt'>913. ceļa apzīmējums - īslaicīgu ceļa darbu vietas);</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>914. un 915. vertikālie apzīmējumi.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/79339.jpg'>312.5. ceļa apzīmējums apzīmē ceļa būvju elementus (tiltu, ceļa pārvadu balstus u.tml.), kas var būt bīstami braucošiem transportlīdzekļiem un kas ir apbraucami;</p> " +
                " <p class='zim-text'>914. ceļa apzīmējums apbraucams pa kreiso pusi; </p> " +
                " <p class='zim-text'>915. ceļa apzīmējums apbraucams pa labo pusi; </p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>916. vertikālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/79371.jpg'>312.6. ceļa apzīmējums apzīmē tuneļu, tiltu un ceļu pārvadu laiduma konstrukciju apakšējo malu;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>917. un 918. vertikālie apzīmējumi.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/79390.jpg'>312.7. ceļa apzīmējums apzīmē signālstabiņus </p> " +
                " <p class='zim-text'>917. Ceļa apzīmējums - ceļa labajā pusē; </p> " +
                " <p class='zim-text'>918. ceļa apzīmējums - ceļa kreisajā pusē;</p> " +
                " <p class='zim-text'>313. Lai pievērstu uzmanību bīstamiem ceļu posmiem (vietās, kur uz ceļa izbrauc no blakusteritorijas u.tml.), 917. un 918.ceļa apzīmējuma – signālstabiņa - apzīmējums var būt dzeltenā krāsā uz melna fona.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>919. vertikālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/79427.jpg'>312.8. ceļa apzīmējums apzīmē ceļa darbu vietu.</p> "));


        paragraphDAOs.add(new ParagraphDAO("", " <p id='toc_marker-45' class='Virsraksts'><strong>28.2. Horizontālie apzīmējumi</strong></p> "));

        paragraphDAOs.add(new ParagraphDAO("314.", " <p class='txt'>314. Horizontālie apzīmējumi (līnijas, bultas, simboli un citas norādes uz ceļa) nosaka noteiktus satiksmes režīmus un kārtību. Horizontālie apzīmējumi ir baltā krāsā, izņemot 943., 944., 945., 946., 947. un 948. ceļa apzīmējumu, kas ir dzeltenā krāsā. Horizontālie apzīmējumi ir šādi:</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>920. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80153.jpg'>314.1.ceļa apzīmējums - nepārtraukta līnija - sadala pretējos virzienos braucošu transportlīdzekļu plūsmas, apzīmē braukšanas joslu robežas bīstamās vietās, brauktuves posmus, kuros aizliegts iebraukt, transportlīdzekļu stāvvietu un stāvlaukumu robežas, brauktuves malu ceļu posmos, kuros aizliegts apstāties un stāvēt, kā arī atdala joslu, kas paredzēta noteiktu transportlīdzekļu braukšanai. Plata līnija apzīmē mehānisko transportlīdzekļu brauktuves malu vietās, kur uz ceļa vienā līmenī ar tā segumu ierīkots velosipēdu ceļš;</p> "));

        paragraphDAOs.add(new ParagraphDAO("317.", " <p class='txt'>317. ceļa apzīmējuma līniju šķērsot aizliegts, izņemot gadījumos, kad tā tiek lietota, lai apzīmētu stāvvietas robežas, vai sadala pretējos virzienos braucošu transportlīdzekļu plūsmas, lai atgrieztos savā braukšanas virziena pusē.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>921. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80174.jpg'>314.2. ceļa apzīmējums - dubulta nepārtraukta līnija - sadala pretējos virzienos braucošu transportlīdzekļu plūsmas uz ceļiem ar četrām vai vairāk braukšanas joslām; </p> " +
                " <p class='zim-text'>317. ceļa apzīmējuma līnijas šķērsot aizliegts; </p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>922. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80226.jpg'>314.3. ceļa apzīmējums - pārtraukta līnija, kurai svītru garums ir trīs reizes mazāks par atstarpēm starp tām, - sadala pretējos virzienos braucošu transportlīdzekļu plūsmas uz ceļiem ar divām vai trim braukšanas joslām, kā arī apzīmē braukšanas joslu robežas uz ceļiem ar divām vai vairāk braukšanas joslām vienā virzienā;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>923. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80247.jpg'>314.4. ceļa apzīmējums - pārtraukta līnija, kurai svītru garums trīs reizes pārsniedz atstarpes starp tām, - sadala pretējā vai tajā pašā virzienā braucošu transportlīdzekļu plūsmas un brīdina par tuvošanos 920., 921. vai 928. ceļa apzīmējuma līnijai;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>924. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80269.jpg'>314.5. ceļa apzīmējums - pārtraukta līnija, kurai svītru garums divas reizes pārsniedz atstarpes starp tām, - apzīmē brauktuves malu ceļa posmos, kuros atļauts apstāties un stāvēt;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>925. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80294.jpg'>314.6. ceļa apzīmējums - pārtraukta līnija, kurai svītru garums ir vienāds ar atstarpēm starp tām, - apzīmē braukšanas joslas krustojuma robežās;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>926. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80330.jpg'>314.7. ceļa apzīmējums - plata pārtraukta līnija - apzīmē robežu starp ieskrējiena joslu un pamatjoslu vai bremzēšanas joslu un pamatjoslu, vai pasažieru sabiedrisko transportlīdzekļu pieturu un pamatjoslu;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>927. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80365.jpg'>314.8. ceļa apzīmējums − dubulta pārtraukta līnija − apzīmē robežas braukšanas joslai, kurā braukšanas virziens var mainīties uz pretējo. Iebraukt šajā joslā atļauts tikai tad, ja virs tās uzstādītajam šo noteikumu 59. punktā minētajam luksoforam iedegts signāls ar zaļu lejup vērstu bultu. Izbraukt no šīs joslas atļauts, tikai pārkārtojoties pa labi;</p> " +
                " <p class='zim-text'>316. Ja, braucot pa joslu, kas apzīmēta ar šo ceļa apzīmējumu, vadītāja priekšā vairs nav šo noteikumu 59. punktā minētā luksofora vai tas ir izslēgts, vadītājs nekavējoties pārkārtojas pa labi.</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>928. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80395.jpg'>314.9. ceļa apzīmējums – nepārtraukta līnija kopā ar 923. ceļa apzīmējumu – sadala pretējā virzienā braucošu transportlīdzekļu plūsmas augšupceļos vai lejupceļos, sadala tajā pašā virzienā braucošu transportlīdzekļu plūsmas uz ceļiem ar vairākām braukšanas joslām vienā virzienā, kā arī apzīmē vietas, kas paredzētas, lai apgrieztos braukšanai pretējā virzienā, iebrauktu vai izbrauktu no stāvvietām u.tml. Šīs ceļa apzīmējuma līnijas atļauts šķērsot tikai no pārtrauktās līnijas puses;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>929. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80415.jpg'>314.10. ceļa apzīmējums - plata nepārtraukta šķērslīnija – stoplīnija - norāda vietu, pirms kuras vadītājam jāaptur transportlīdzeklis, ja uzstādīta 207.&nbsp;zīme vai braukšanu aizliedz luksofora vai satiksmes regulētāja signāls;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>930. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80465.jpg'>314.11. ceļa apzīmējums - plata pārtraukta šķērslīnija, kam svītru garums divreiz pārsniedz atstarpes starp tām, - norāda vietu, kur vadītājam, ja tas nepieciešams, jāaptur transportlīdzeklis, lai dotu ceļu transportlīdzekļiem, kas brauc pa šķērsojamo ceļu;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>931. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80502.jpg'>314.12. ceļa apzīmējums - vairākas viena no otras atdalītas platas paralēlas garenlīnijas - apzīmē gājēju pāreju;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>932. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80519.jpg'>314.13. ceļa apzīmējums - divas viena no otras atdalītas platas pārtrauktas šķērslīnijas - apzīmē vietu, kur brauktuvi šķērso velosipēdu ceļš;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>933. horizontālais apzīmējums.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80561.jpg'>314.14. ceļa apzīmējums - divas viena no otras atdalītas šahveida šķērslīnijas - apzīmē robežas uz brauktuves izveidotam mākslīgam paaugstinājumam - slieksnim, kas paredzēts braukšanas ātruma samazināšanai;</p> "));

        paragraphDAOs.add(new ParagraphDAO("", " <p class='zim-bolded'><strong>934., 935., 936. horizontālie apzīmējumi.</strong></p> " +
                " <p class='zim-text'><img style='max-height: 616px;' class='zimes' src='/public/imgS/80644.jpg'></p> "));

        paragraphDAOs.add(new ParagraphDAO("314.15.", " <p class='txt'>314.15. ceļa apzīmējums apzīmē virzienu saliņas transportlīdzekļu plūsmu sadalīšanās vai sakļaušanās vietās, kā arī sadalošo joslu un drošības saliņu sākumu un beigas. Šiem ceļa apzīmējumiem uzbraukt aizliegts;</p> "));

        return paragraphDAOs;
    }
}
