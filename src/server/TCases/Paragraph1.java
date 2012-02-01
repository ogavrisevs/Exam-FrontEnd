/*
 * @(#)Paragraph1.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package server.TCases;

import server.TCasesServlet;
import shared.DAO.*;

import javax.jdo.PersistenceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Paragraph1 extends Paragraphs{

    public Paragraph1(TCasesServlet tCasesServlet) {
        super(tCasesServlet);
    }

    public String makeAllTc() throws Exception {
        PersistenceManager pm = tCasesServlet.pm;

        StringBuffer sb = new StringBuffer("Start of loading ...");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        List<AnswerDAO> answerDAOs = null;
        AnswersDAO answersDAO = null;
        QuestionDAO questionDAO = null;
        TCaseDAO tCaseDAO = null;
        ImageDAO imgLargeDAO = null;
        ImageDAO imgSmallDAO = null;
        Integer caseTypeIdx = 11;

        sb.append("//------------------------------------  1063" );
        questionDAO = new QuestionDAO(" Ko nozīmē termins \"Apsteigšana\"?", " 652a093e0b169f3d998842899a7f07ee", 68441313 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Braukšana garām vienam vai vairākiem braucošiem transportlīdzekļiem, neiebraucot pretējā virziena braukšanas joslā." , 57770 , " 5ec26ce9629378f1c345f1a686eebc4e",  true ));
        answerDAOs.add(new AnswerDAO(" Braukšana garām vienam vai vairākiem braucošiem transportlīdzekļiem, kas saistīta ar iebraukšanu pretējā virziena braukšanas joslā." , 57809 , " 83dc9e4589df0741bed11d33d8a06132",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.5.")));
        tCaseDAO.settCaseTypeIdx(1);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  833" );
        questionDAO = new QuestionDAO(" Vai ceļš, uz kura nav uzstādītas priekšrocības ceļa zīmes, attiecībā pret krustojamo ceļu, uz kura uzstādīta ceļa zīme \"Dodiet ceļu\", ir uzskatāms par galveno ceļu?", " c2c14992a81e5bef187f423b54023e44", 68048814 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Ir uzskatāms." , 57772 , " e037ec73f8bc8c943ac1f1145a2e3d87",  true ));
        answerDAOs.add(new AnswerDAO(" Nav uzskatāms." , 57773 , " eeac8714be308720a0b5a758a112f451",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.18.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  377" );
        questionDAO = new QuestionDAO(" Uz kuru no minētajiem gadījumiem attiecināms termins \"Stāvēšana\"?", " 681da2b09f457fa3b2e9560b89cc3ae7", 67779349 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Transportlīdzekļa apturēšana uz laiku, ilgāku par 5 minutēm." , 57735 , " dd1a75f8fc207e5b3a743cc09d4641d8",  false ));
        answerDAOs.add(new AnswerDAO(" Transportlīdzekļa apturēšana uz laiku, ilgāku par 5 minūtēm, kas saistīta ar kravas iekraušanu transportlīdzeklī vai izkraušanu no tā." , 57774 , " dc0684cbff1e870303117579c9fa745a",  false ));
        answerDAOs.add(new AnswerDAO(" Uz abiem iepriekšminētajiem gadījumiem." , 57811 , " affa5596f65958e510274405d2ec6446",  true ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.40")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  747" );
        questionDAO = new QuestionDAO(" Vai brauktuves daļa, kas apzīmēta tikai ar ceļa zīmēm \"Gājēju pāreja\", šī termina izpratnē ir uzskatāma par gājēju pāreju?", " e7388b318db8af87acf72418be5f9b8f", 67921490 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Ir uzskatāma." , 57761 , " c94a7dd1820a17a019def267df2b7c41",  true ));
        answerDAOs.add(new AnswerDAO(" Nav uzskatāma." , 57800 , " ade32eca6e3923e20218a5918aaa92f0",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.21.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  801" );
        questionDAO = new QuestionDAO(" Vai mehāniskais transportlīdzeklis, kas piedalās ceļu satiksmē ar iedegtu mirgojošu zilu bākuguni un speciālu skaņas signālu ir uzskatāms par operatīvo transportlīdzekli?", " 62e39dc36d1aaad76d2b83af4af14566", 67992743 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Ir uzskatāms." , 57737 , " e037ec73f8bc8c943ac1f1145a2e3d87",  true ));
        answerDAOs.add(new AnswerDAO(" Nav uzskatāms." , 57776 , " eeac8714be308720a0b5a758a112f451",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.15.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  507" );
        questionDAO = new QuestionDAO(" Uz kuru no minētajiem gadījumiem attiecināms termins \"Apstāšanās\"?", " bc8c80e66d03d23f311c9954b8cbcc8c", 67783205 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Transportlīdzekļa apturēšana uz laiku līdz 5 minūtēm, ja tā saistīta ar kravas iekraušanu transportlīdzeklī." , 57738 , " 4cc6eb43d7c2aa97ba433713013fa8a8",  true ));
        answerDAOs.add(new AnswerDAO(" Jebkura transportlīdzekļa apturēšana uz laiku līdz 5 minūtēm." , 57777 , " 6147012795dc8ca9ebf36794bd80877a",  false ));
        answerDAOs.add(new AnswerDAO(" Transportlīdzekļa apturēšana uz laiku līdz 5 minūtēm vai ilgāk, ja tas nepieciešams kravas iekraušanai transportlīdzeklī." , 57813 , " f84f9c086165453b8f570829b98cc451",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.4.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  265" );
        questionDAO = new QuestionDAO(" Ko nozīmē termins \"Priekšroka\"?", " dd38aeb12bab4928fc467c79b5b3bd96", 67777287 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Tiesības braukt iecerētajā virzienā pirmajam attiecībā pret citiem ceļu satiksmes dalībniekiem." , 57739 , " 5cfdb76de3a80cd69071796d7106f984",  true ));
        answerDAOs.add(new AnswerDAO(" Tiesības izvēlēties braukšanas trajektoriju, neņemot vērā citus ceļu satiksmes dalībniekus." , 57778 , " fffa7b5cc8dd2601fd81a4b534347740",  false ));
        answerDAOs.add(new AnswerDAO(" Tiesības izvēlēties izdevīgāko braukšanas maršrutu." , 57814 , " b06173e7cdff2d0b1b0028187c18815e",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.37.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  305" );
        questionDAO = new QuestionDAO(" Ko nozīmē \"Transportlīdzekļa pilna masa\"?", " 306ed9fa2a52a3a47360e300a139982c", 67778274 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Izgatavotāja noteikta transportlīdzekļa maksimālā pieļaujamā masa kopā ar vadītāju, pasažieriem un kravu." , 57740 , " bb4534595f3711b7f977e8d681f047fd",  true ));
        answerDAOs.add(new AnswerDAO(" Pilnībā nokomplektēta transportlīdzekļa masa kopā ar vadītāju, pasažieriem, bet bez kravas." , 57779 , " 04a9e98cfdcce16dad582717fb3c07db",  false ));
        answerDAOs.add(new AnswerDAO(" Pilnībā nokomplektēta transportlīdzekļa masa kopā ar vadītāju un maksimāli pieļaujamo kravu." , 57815 , " 7820da5b0de7313c6b1a1e07671e5cf5",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.19.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  695" );
        questionDAO = new QuestionDAO(" Vai vadītājs ir izpildījis prasību \"Dot ceļu\", ja viņa izdarītā manevra dēļ šķērsojošā brauktuvē braucošais transportlīdzeklis ir spiests pārkārtoties uz otro joslu?", " 91bc9b03d6759573d78a1eda21667dc6", 67869844 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Ir izpildījis." , 57763 , " c7177857f3aa62e33a8d71950041dc31",  false ));
        answerDAOs.add(new AnswerDAO(" Nav izpildījis." , 57802 , " 45b1cd896a5b50484b9b81e13a2e898b",  true ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.14.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  657" );
        questionDAO = new QuestionDAO(" Ko nozīmē termins \"Piespiedu apstāšanās\"?", " 3697581b0ccdc1e7b6f6a15a28914890", 67842341 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Transportlīdzekļa apturēšana uz laiku ilgāku par 5 min., ja tas nav saistīts ar pasažieru iekāpšanu transportlīdzeklī vai izkāpšanu no tā." , 57743 , " 56f24a28272cda60f693a218dc366867",  false ));
        answerDAOs.add(new AnswerDAO(" Transportlīdzekļa apturēšana pēc ceļu policijas darbinieka pieprasījuma." , 57782 , " 7c449c0ea73f6c90e3235bfe6c088b13",  false ));
        answerDAOs.add(new AnswerDAO(" Transportlīdzekļa apturēšana sakarā ar tehnisku bojājumu vai bīstamību, ko radījusi vedamā krava vai pasažiera veselības stāvoklis." , 57818 , " b6a53dbc3800d77ae2c2a3a314581643",  true ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.35.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  740" );
        questionDAO = new QuestionDAO(" Uzņēmuma teritorijā notiek transportlīdzekļu sadursme un tiek bojāti divi automobiļi. Vai tas ir uzskatāms par \"Ceļu satiksmes negadījumu\"?", " e8dae44fa9fc5e5c292841d5558902a8", 67912625 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Ir uzskatāms." , 57762 , " e037ec73f8bc8c943ac1f1145a2e3d87",  true ));
        answerDAOs.add(new AnswerDAO(" Nav uzskatāms." , 57801 , " eeac8714be308720a0b5a758a112f451",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.7.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  559" );
        questionDAO = new QuestionDAO(" Ko nozīmē termins \"Krustojums\"?", " 4f9cb6253c6f686b793bd71283cac217", 67790354 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Vieta, kur uz brauktuves izbrauc no stāvvietas." , 57745 , " 38a5e35587d1537363adf46d71f445ee",  false ));
        answerDAOs.add(new AnswerDAO(" Vieta, kur krustojas, piekļaujas vai atzarojas ceļi vienā līmenī." , 57784 , " c23a76820c8661e8604e634103730013",  true ));
        answerDAOs.add(new AnswerDAO(" Vieta, kur šķērsojās ceļi dažādos līmeņos." , 57819 , " 48f6d838d7094f7b4102525669fed49b",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.24")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  56" );
        questionDAO = new QuestionDAO(" Ko nozīmē termins \" Apdzīšana\"?", " 19c908f5c18aebcb55fa40aa23c2c6ee", 67764539 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Braukšana garām vienam vai vairākiem braucošiem transportlīdzekļiem, iebraucot citā joslā." , 57746 , " 2b4c289b9b11b3044b4036dff0670150",  false ));
        answerDAOs.add(new AnswerDAO(" Braukšana garām vienam vai vairākiem braucošiem transportlīdzekļiem, iebraucot citā joslā un atgriežoties iepriekšējā braukšanas joslā." , 57785 , " 955d6a066c81a7d1d4a4aa467c79e656",  false ));
        answerDAOs.add(new AnswerDAO(" Braukšana garām vienam vai vairākiem braucošiem transportlīdzekļiem, iebraucot pretējā braukšanas joslā un atgriežoties iepriekšējā braukšanas joslā." , 57820 , " ec0e7b2e99990d0f1135bd9d671292d1",  true ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.2.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  283" );
        questionDAO = new QuestionDAO(" Ko nozīmē termins \"Pašmasa\"?", " 017c77816ef4fa09436dd03ce0db480d", 67777767 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Atbilstoši valsts standartam nokomplektēta transportlīdzekļa masa bez vadītāja, pasažieriem un bez kravas." , 57752 , " 8b68c11e64d3c61046240098c83f52dc",  false ));
        answerDAOs.add(new AnswerDAO(" Atbilstoši valsts standartam nokomplektēta transportlīdzekļa masa kopā ar vadītāju un pasažieriem,bet bez kravas." , 57791 , " 68cb371b06c1a068cc101826de224961",  false ));
        answerDAOs.add(new AnswerDAO(" Atbilstoši valsts standartam nokomplektēta transportlīdzekļa masa kopā ar vadītāju, bet bez pasažieriem un kravas." , 57826 , " a0f4fde826b6bb7ec251068dd9ad44bc",  true ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.17.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  837" );
        questionDAO = new QuestionDAO(" Nepietiekama redzamība ir tādi laika apstākļi (migla, lietus, sniegs u.tml. apstākļi), kuru dēļ redzamība uz ceļa ir tuvāka par:", " f130e1041a53e04be4b5197657e5ed30", 68063210 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" 600 m." , 57750 , " 8fea1e87b423e0eadc243c658fffe39f",  false ));
        answerDAOs.add(new AnswerDAO(" 500 m." , 57789 , " f7145ae3c46729898fdbe34595f3cbbc",  false ));
        answerDAOs.add(new AnswerDAO(" 300 m." , 57824 , " 87d00c7f287d5437b897f250a0655045",  true ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.14.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  1073" );
        questionDAO = new QuestionDAO(" Uz kuru no minētajiem ceļiem attiecināms termins \"Galvenais ceļš\"?", " 49824b8f0a2ab9bc9da592132281c24d", 68471940 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Ceļš ar asfalta vai bruģa segumu attiecībā pret ceļu ar grants vai šķembu segumu." , 57748 , " 8a1b9229524f9500633a81751811e1ef",  false ));
        answerDAOs.add(new AnswerDAO(" Ceļš ar asfalta segumu attiecībā pret ceļu bez seguma." , 57787 , " f8091edceb3d365cc2b2b078f18c2d71",  false ));
        answerDAOs.add(new AnswerDAO(" Jebkurš ceļš attiecībā pret vietu, kur uz ceļa izbrauc no blakusteritorijas." , 57822 , " abd2420ebc8293cb85785fb00b77d4c5",  false ));
        answerDAOs.add(new AnswerDAO(" Uz visiem iepriekšminētajiem ceļiem." , 57841 , " 774af59720eb28195d4168dd5e7e27cb",  true ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.18.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  339" );
        questionDAO = new QuestionDAO(" Vai vieta, kur uz brauktuves izbrauc no blakusteritorijas (uzņēmuma u.tml.) ir uzskatāma par krustojumu?", " 6ffae5aa67e2c27ac0a54262873cff3f", 67778663 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Ir uzskatāma." , 57749 , " c94a7dd1820a17a019def267df2b7c41",  false ));
        answerDAOs.add(new AnswerDAO(" Nav uzskatāma." , 57788 , " ade32eca6e3923e20218a5918aaa92f0",  true ));
        answerDAOs.add(new AnswerDAO(" Ir uzskatāma, ja pirms izbraukšanas uzstādīta zīme \"Dodiet ceļu\"." , 57823 , " 29ec350e7de0562697269975834d8369",  false ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.24.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);

        sb.append("//------------------------------------  157" );
        questionDAO = new QuestionDAO(" Uz kuru no gadījumiem attiecināms termins \"Apbraukšana\"?", " f2b97ccb72bf4df12db7c8102f1cde5e", 67773515 );
        answerDAOs = new ArrayList<AnswerDAO>();
        answerDAOs.add(new AnswerDAO(" Braukšana garām uz brauktuves apturētam transportlīdzeklim, kas saistīta ar braukšanas virziena maiņu un iebraukšanu pretējā virziena brauktuves pusē." , 57759 , " 6a9850907ba28cb504f2f048a18efb01",  false ));
        answerDAOs.add(new AnswerDAO(" Braukšana garām uz brauktuves apturētam transportlīdzeklim, kas saistīta ar braukšanas virziena maiņu un iebraukšanu tā paša virziena blakus joslā." , 57798 , " 5cee2cdd359f195ef8977bcf8d1b1d95",  false ));
        answerDAOs.add(new AnswerDAO(" Uz abiem iepriekšminētajiem gadījumiem." , 57833 , " affa5596f65958e510274405d2ec6446",  true ));
        answersDAO = new AnswersDAO(answerDAOs);
        pm.makePersistent(questionDAO);
        pm.makePersistent(answersDAO);
        tCaseDAO = new TCaseDAO(questionDAO.getKey(), answersDAO.getKey());
        tCaseDAO.setParagraphs(new ArrayList<String>(Arrays.asList("1.1.")));
        tCaseDAO.settCaseTypeIdx(caseTypeIdx);
        pm.makePersistent(tCaseDAO);






















        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return sb.toString();


    }

}

