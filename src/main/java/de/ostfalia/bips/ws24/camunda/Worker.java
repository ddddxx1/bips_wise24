package de.ostfalia.bips.ws24.camunda;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import de.ostfalia.bips.ws24.camunda.database.domain.*;
import de.ostfalia.bips.ws24.camunda.database.service.*;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.camunda.feel.syntaxtree.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
//import io.camunda.zeebe.spring.client.annotation.Variable;

import de.ostfalia.bips.ws24.camunda.database.repository.UserRepository;

import de.ostfalia.bips.ws24.camunda.database.Model.Option;
import scala.Int;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Component
public class Worker {
    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private final UserRepository userRepository;
    private final AntwortService antwortService;
    private final FragebogenHasFrageService fragebogenHasFrageService;
    private final FragebogenService fragebogenService;
    private final FrageService frageService;
    private final KategoriegewichtInProjektService kategoriegewichtInProjektService;
    private final KategorieService kategorieService;
    private final LieferantService lieferantService;
    private final ProjektHasAntwortService projektHasAntwortService;
    private final ProjektHasLieferantHasAntwortService projektHasLieferantHasAntwortService;
    private final ProjektHasLieferantService projektHasLieferantService;
    private final ProjektService projektService;


    public Worker(UserRepository userRepository, AntwortService antwortService, FragebogenHasFrageService fragebogenHasFrageService, FragebogenService fragebogenService, FrageService frageService, KategoriegewichtInProjektService kategoriegewichtInProjektService, KategorieService kategorieService, LieferantService lieferantService, ProjektHasAntwortService projektHasAntwortService, ProjektHasLieferantHasAntwortService projektHasLieferantHasAntwortService, ProjektHasLieferantService projektHasLieferantService, ProjektService projektService) {
        this.userRepository = userRepository;
        this.antwortService = antwortService;
        this.fragebogenHasFrageService = fragebogenHasFrageService;
        this.fragebogenService = fragebogenService;
        this.frageService = frageService;
        this.kategoriegewichtInProjektService = kategoriegewichtInProjektService;
        this.kategorieService = kategorieService;
        this.lieferantService = lieferantService;
        this.projektHasAntwortService = projektHasAntwortService;
        this.projektHasLieferantHasAntwortService = projektHasLieferantHasAntwortService;
        this.projektHasLieferantService = projektHasLieferantService;
        this.projektService = projektService;
    }

//    @JobWorker(type = "hello-world")
//    public Map<String, Object> hello(@Variable(name = "username") String username) {
//        LOGGER.info("Hello, World: {}", username);
//        return Map.of("hello-world", true);
//    }

//    @JobWorker(type = "login")
//    public Map<String, Object> login(@Variable(name = "username") String username, @Variable(name = "password") String password) {
//        return Map.of("login", userRepository.findByUsername(username)
//                .filter(e -> Objects.equals(hash(password), e.getPassword()))
//                .isPresent());
//    }

    @JobWorker(type = "projekte-laden")
    public Map<String, Object> projekteLaden(final ActivatedJob job) {
        LOGGER.info("Lade_Stichpunkte");
        final List<Option<Integer>> projekt_lade = projektService.getRepository().findAll().stream()
                .map(e -> new Option<>(e.getName(), e.getIdProjekt()))
                .collect(Collectors.toList());

        // Probably add some process variables
        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("projekt_lade", projekt_lade);
        return variables;

    }

    @JobWorker(type = "fragebogen-laden")
    public Map<String, Object> fragebogenLaden(final ActivatedJob job) {
        LOGGER.info("Lade_Fragebogen");
        final List<Option<Integer>> fragebogen_lade = fragebogenService.getRepository().findAll().stream()
                .map(e -> new Option<>(e.getBeschreibung(), e.getIdFragebogen()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Fragen = frageService.getRepository().findAll().stream()
                .map(e -> new Option<>(e.getFrageText(), e.getIdFrage()))
                .collect(Collectors.toList());

        // Probably add some process variables
        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("fragebogen_lade", fragebogen_lade);
        variables.put("Fragen", Fragen);
        return variables;
    }

    @JobWorker(type = "daten-zum-fragebogen-laden")
    public Map<String, Object> datenZumFragebogenLaden(final ActivatedJob job) {
        LOGGER.info("Daten_Zum_Fragebogen_Laden");

        //final Object projekte = job.getVariablesAsMap().get("projekt_lade");
        final Object projektName= job.getVariablesAsMap().get("projekt_name");
        final Object frageboge= job.getVariablesAsMap().get("frageboge");

        //List<Object[]> resultList = countRecordsByProjektId();//diese drei zeile muss man noch anpassen

        Integer maxIdProjekt = projektService.getRepository().countAllRecords();

        Integer newIdProjekt = maxIdProjekt+1;


        final Projekt projekt=new Projekt();
        final Fragebogen fragebogen=new Fragebogen();

        projekt.setFragebogen(fragebogen);
        fragebogen.setIdFragebogen(parseInt(frageboge.toString()));
        //projekt.setName(projektService.getRepository().findById(parseInt(projektName.toString())).get().getName());
        projekt.setName(projektName.toString());
        projekt.setIdProjekt(newIdProjekt);
        projekt.setKomponente(String.valueOf(newIdProjekt));

        projektService.getRepository().save(projekt);   //写入数据库
        LOGGER.info("newIDProjekt"+ newIdProjekt);

        //Projekt Projekte = projektService.getRepository().findProjektByProjektID(newIdProjekt);

        final List<Option<Integer>> Projekte = projektService.getRepository().findProjektByProjektID(newIdProjekt).stream()
                .map(e -> new Option<>(e.getName(), e.getIdProjekt()))
                .collect(Collectors.toList());
// 改动
//        final List<Option<Integer>> Fragen = frageService.getRepository().findAll().stream()
//                .map(e -> new Option<>(e.getFrageText(), e.getIdFrage()))
//                .collect(Collectors.toList());

//        final List<Option<Integer>> Antworten = antwortService.getRepository().findAntwortIdsByFrageId(Fragen.).stream()
//                .map(e -> new Option<>(e.getFrageText(), e.getIdFrage()))
//                .collect(Collectors.toList());

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("newIdProjekt", projekt.getIdProjekt());
        variables.put("newIdFragebogen", projekt.getFragebogen());
        variables.put("Projekte", Projekte);
//        改动
//        variables.put("Fragen", Fragen);

//        ---------------------------------
        LOGGER.info("Antworten_Laden");

//        variables.put("frage_auswahl", 1);
//        这个frage_auswahl在projekt-speichern1中被使用.它是设置为KO的frage,该frageId被存储在projekt_has_antwort表中

        //final Object projekte = job.getVariablesAsMap().get("projekt_lade");
        final Object frage= job.getVariablesAsMap().get("frage_auswahl");   // 这里的frage不需要读取用户输入，
        final Object projekt_id= job.getVariablesAsMap().get("newIdProjekt");


//        final List<Option<Integer>> Antworten = antwortService.getRepository().findAntwortIdsByFrageId(parseInt(frage.toString())).stream()
//                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
//                .collect(Collectors.toList());

//        Antworten = Fragebogen -> Frage -> Antwort
        final List<Option<Integer>> Frage = fragebogenHasFrageService.getRepository().findAllFrageOderById(projekt.getFragebogen().getIdFragebogen()).stream()
                .map(e -> new Option<>(e.getFrageText(), e.getIdFrage()))
                .collect(Collectors.toList());



        int frageCount = Frage.size();

//        List<List<Option<Integer>>> Antworten = new ArrayList<>();
//        for (int i = 0; i < frageCount; i++) {
//            final List<Option<Integer>> Antwort = antwortService.getRepository().findAntwortIdsByFrageId(parseInt(Frage.get(i).getValue().toString())).stream()
//                    .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
//                    .collect(Collectors.toList());
//
//            Antworten.add(Antwort);
//        }
        List<Option<Integer>> Antworten = new ArrayList<>();
        for (int i = 0; i < frageCount; i++) {
            List<Option<Integer>> Antwort = antwortService.getRepository().findAntwortIdsByFrageId(parseInt(Frage.get(i).getValue().toString())).stream()
                    .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                    .collect(Collectors.toList());

            Antworten.addAll(Antwort);
        }

        //Projekt Projekte2 = projektService.getRepository().findProjektByProjektID(parseInt(projekt_id.toString()));

        final List<Option<Integer>> Projekte2 = projektService.getRepository().findProjektByProjektID(parseInt(projekt.getIdProjekt().toString())).stream()
                .map(e -> new Option<>(e.getName(), e.getIdProjekt()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Katergorien = kategorieService.getRepository().findAll().stream()
                .map(e -> new Option<>(e.getBeschreibung(), e.getIdKategorie()))
                .collect(Collectors.toList());


//        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("Antworten", Antworten);  // 这个Antworten供用户在 K.O. Kriterium中选择，所以这里的Antworten应该根据fragebogen来选择
        variables.put("frage_auswahl", Frage);
        variables.put("Projekte2", Projekte2);
        variables.put("Katergorien", Katergorien);
        variables.put("projekt_auswahl2", Projekte2.get(0).getValue());



        return variables;
    }

    @JobWorker(type = "projekt-speichern1")
    public Map<String, Object> projektSpeichern1(final ActivatedJob job) {
//        could not execute statement [Cannot add or update a child row: a foreign key constraint fails (`bips_wise24`.`projekt_has_antwort`, CONSTRAINT `fk_Projekt_has_Antwort_Antwort1` FOREIGN KEY (`id_antwort`, `id_frage`) REFERENCES `antwort` (`id_antwort`, `id_frage`))] [insert into projekt_has_antwort (ist_ko_kriterium,id_antwort,id_frage,id_projekt) values (?,?,?,?)]; SQL [insert into projekt_has_antwort (ist_ko_kriterium,id_antwort,id_frage,id_projekt) values (?,?,?,?)]; constraint [null]
        // 原因: idAntwort和idFrage必须在数据库中存在外键对应关系, 但是现在idFrage始终为1, 导致外键约束失败

        LOGGER.info("Projekt_Speichern1");

        //Daten Eintragung in DB"projekt_has_antwort"
//        final Object projektID_KO= job.getVariablesAsMap().get("projekt_auswahl1");
        final Object projektID_KO = ((List<Map<String, Object>>) job.getVariablesAsMap().get("Projekte")).get(0).get("value");
        final Object antwortID_KO= job.getVariablesAsMap().get("antwort_auswahl");
//        final Object frageID_KO= job.getVariablesAsMap().get("frage_auswahl");
        final Object ergebnisKO_KO= job.getVariablesAsMap().get("ergebnisKO");


//        通过antwortID_KO找到对应的frageID_KO
        int ausgewahlteFrageID_KO = antwortService.getRepository().findidFrageByidAntwort(parseInt(antwortID_KO.toString()));


        final ProjektHasAntwort projektHasAntwort = new ProjektHasAntwort();
        final Projekt projekt=new Projekt();
        final Frage frage=new Frage();
        final Antwort antwort=new Antwort();


        projekt.setIdProjekt(parseInt(projektID_KO.toString()));
        antwort.setIdAntwort(parseInt(antwortID_KO.toString()));
//        frage.setIdFrage(parseInt(frageID_KO.toString()));
        frage.setIdFrage(ausgewahlteFrageID_KO);


        if (parseInt(ergebnisKO_KO.toString()) == 1) {
            projektHasAntwort.setIst_ko_kriterium(1);
            projektHasAntwort.getId().setProjekt(projekt);
            projektHasAntwort.getId().setAntwort(antwort);
            projektHasAntwort.getId().setFrage(frage);

            projektHasAntwortService.getRepository().save(projektHasAntwort);
        }

        final Object antwortID_KO1= job.getVariablesAsMap().get("antwort_auswahl1");
        final Object antwortID_KO2= job.getVariablesAsMap().get("antwort_auswahl2");
        final Object ergebnisKO_KO1= job.getVariablesAsMap().get("ergebnisKO1");
        final Object ergebnisKO_KO2= job.getVariablesAsMap().get("ergebnisKO2");

        if (antwortID_KO1 != null && ergebnisKO_KO1 != null && parseInt(ergebnisKO_KO1.toString()) == 1) {
            int ausgewahlteFrageID_KO1 = antwortService.getRepository().findidFrageByidAntwort(parseInt(antwortID_KO1.toString()));
            antwort.setIdAntwort(parseInt(antwortID_KO1.toString()));
            frage.setIdFrage(ausgewahlteFrageID_KO1);

            projektHasAntwort.setIst_ko_kriterium(1);
            projektHasAntwort.getId().setProjekt(projekt);
            projektHasAntwort.getId().setAntwort(antwort);
            projektHasAntwort.getId().setFrage(frage);

            projektHasAntwortService.getRepository().save(projektHasAntwort);
        }

        if (antwortID_KO2 != null && ergebnisKO_KO2 != null && parseInt(ergebnisKO_KO2.toString()) == 1) {
            int ausgewahlteFrageID_KO2 = antwortService.getRepository().findidFrageByidAntwort(parseInt(antwortID_KO2.toString()));
            antwort.setIdAntwort(parseInt(antwortID_KO2.toString()));
            frage.setIdFrage(ausgewahlteFrageID_KO2);

            projektHasAntwort.setIst_ko_kriterium(1);
            projektHasAntwort.getId().setProjekt(projekt);
            projektHasAntwort.getId().setAntwort(antwort);
            projektHasAntwort.getId().setFrage(frage);

            projektHasAntwortService.getRepository().save(projektHasAntwort);
        }


        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("projektID_KO", projektHasAntwort.getId().getProjekt().getIdProjekt());
        variables.put("antwortID_KO", projektHasAntwort.getId().getAntwort().getIdAntwort());
        variables.put("frageID_KO", projektHasAntwort.getId().getFrage().getIdFrage());
        variables.put("istKO_KO", projektHasAntwort.getIst_ko_kriterium());


        //Daten Eintragung in DB"kategoriegewicht_in_projekt"

        final Object kategorieID1_ge= job.getVariablesAsMap().get("katergorie_auswahl1");
        final Object kategorieID2_ge= job.getVariablesAsMap().get("katergorie_auswahl2");
        final Object kategorieID3_ge= job.getVariablesAsMap().get("katergorie_auswahl3");
        final Object kategorieID4_ge= job.getVariablesAsMap().get("katergorie_auswahl4");
        final Object projektID_ge= job.getVariablesAsMap().get("projekt_auswahl2");
        final Object gewicht1_ge= job.getVariablesAsMap().get("gewicht_eingeben1");
        final Object gewicht2_ge= job.getVariablesAsMap().get("gewicht_eingeben2");
        final Object gewicht3_ge= job.getVariablesAsMap().get("gewicht_eingeben3");
        final Object gewicht4_ge= job.getVariablesAsMap().get("gewicht_eingeben4");




        final KategoriegewichtInProjekt kategoriegewichtInProjekt1 = new KategoriegewichtInProjekt();
        final Kategorie kategorie1=new Kategorie();
        final Projekt projekt1=new Projekt();
        kategorie1.setIdKategorie(parseInt(kategorieID1_ge.toString()));
        projekt1.setIdProjekt(parseInt(projektID_ge.toString()));
        kategoriegewichtInProjekt1.setGewicht(parseDouble(gewicht1_ge.toString()));
        kategoriegewichtInProjekt1.getId().setKategorie(kategorie1);
        kategoriegewichtInProjekt1.getId().setProjekt(projekt1);
        kategoriegewichtInProjektService.getRepository().save(kategoriegewichtInProjekt1);

        final KategoriegewichtInProjekt kategoriegewichtInProjekt2 = new KategoriegewichtInProjekt();
        final Kategorie kategorie2=new Kategorie();
        final Projekt projekt2=new Projekt();
        kategorie2.setIdKategorie(parseInt(kategorieID2_ge.toString()));
        projekt2.setIdProjekt(parseInt(projektID_ge.toString()));
        kategoriegewichtInProjekt2.setGewicht(parseDouble(gewicht2_ge.toString()));
        kategoriegewichtInProjekt2.getId().setKategorie(kategorie2);
        kategoriegewichtInProjekt2.getId().setProjekt(projekt2);
        kategoriegewichtInProjektService.getRepository().save(kategoriegewichtInProjekt2);

        final KategoriegewichtInProjekt kategoriegewichtInProjekt3 = new KategoriegewichtInProjekt();
        final Kategorie kategorie3=new Kategorie();
        final Projekt projekt3=new Projekt();
        kategorie3.setIdKategorie(parseInt(kategorieID3_ge.toString()));
        projekt3.setIdProjekt(parseInt(projektID_ge.toString()));
        kategoriegewichtInProjekt3.setGewicht(parseDouble(gewicht3_ge.toString()));
        kategoriegewichtInProjekt3.getId().setKategorie(kategorie3);
        kategoriegewichtInProjekt3.getId().setProjekt(projekt3);
        kategoriegewichtInProjektService.getRepository().save(kategoriegewichtInProjekt3);

        final KategoriegewichtInProjekt kategoriegewichtInProjekt4 = new KategoriegewichtInProjekt();
        final Kategorie kategorie4=new Kategorie();
        final Projekt projekt4=new Projekt();
        kategorie4.setIdKategorie(parseInt(kategorieID4_ge.toString()));
        projekt4.setIdProjekt(parseInt(projektID_ge.toString()));
        kategoriegewichtInProjekt4.setGewicht(parseDouble(gewicht4_ge.toString()));
        kategoriegewichtInProjekt4.getId().setKategorie(kategorie4);
        kategoriegewichtInProjekt4.getId().setProjekt(projekt4);
        kategoriegewichtInProjektService.getRepository().save(kategoriegewichtInProjekt4);



        variables.put("projektID_ge", kategoriegewichtInProjekt1.getId().getProjekt().getIdProjekt());
        variables.put("kategorieID1_ge", kategoriegewichtInProjekt1.getId().getKategorie().getIdKategorie());
        variables.put("gewicht1_ge", kategoriegewichtInProjekt1.getGewicht());
        variables.put("kategorieID2_ge", kategoriegewichtInProjekt2.getId().getKategorie().getIdKategorie());
        variables.put("gewicht2_ge", kategoriegewichtInProjekt2.getGewicht());
        variables.put("kategorieID3_ge", kategoriegewichtInProjekt3.getId().getKategorie().getIdKategorie());
        variables.put("gewicht3_ge", kategoriegewichtInProjekt3.getGewicht());
        variables.put("kategorieID4_ge", kategoriegewichtInProjekt4.getId().getKategorie().getIdKategorie());
        variables.put("gewicht4_ge", kategoriegewichtInProjekt4.getGewicht());



        return variables;
    }

    @JobWorker(type = "daten-zum-projekt-laden")
    public Map<String, Object> datenZumProjektLaden(final ActivatedJob job) {
        LOGGER.info("Daten_ZumProjekt_Laden");
        final Object projektId = job.getVariablesAsMap().get("projekt_auswahl");

        int id = Integer.parseInt(projektId.toString());
        Projekt projekt = projektService.getProjektById(id);

        final List<Option<Integer>> Katergorien = kategorieService.getRepository().findAll().stream()
                .map(e -> new Option<>(e.getBeschreibung(), e.getIdKategorie()))
                .collect(Collectors.toList());


        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("projektData", projekt);
        variables.put("Katergorien", Katergorien);
        return variables;
    }

    @JobWorker(type = "lieferanten-laden")
    public Map<String, Object> lieferantenLaden(final ActivatedJob job) {
        LOGGER.info("Lieferanten_Laden");


        final List<Option<Integer>> lieferanten_laden = lieferantService.getRepository().findAll().stream()
                .map(e -> new Option<>(e.getName(), e.getIdLieferant()))
                .collect(Collectors.toList());

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("lieferanten_laden", lieferanten_laden);
        return variables;
    }

    @JobWorker(type = "lieferant-uberprufen")
    public Map<String, Object> lieferantUberprufen(final ActivatedJob job) {
        LOGGER.info("Lieferant_uberprufen");
        final Object projektId1 = job.getVariablesAsMap().get("projekt_auswahl");
        int projektIdInt1 = Integer.parseInt(projektId1.toString());

        final Object projektId;


        final Object lieferantId = job.getVariablesAsMap().get("lieferanter_auswahl");


        if (projektIdInt1 > 0) {
            projektId = job.getVariablesAsMap().get("projekt_auswahl");
        } else {
            projektId = job.getVariablesAsMap().get("projekt_auswahl2");
        }



        int projektIdInt = Integer.parseInt(projektId.toString());
        int lieferantIdInt = Integer.parseInt(lieferantId.toString());




        boolean isLieferantInProjekt = projektHasLieferantHasAntwortService.isLieferantInProjekt(projektIdInt, lieferantIdInt);
        boolean lieferInProjekt;

        HashMap<String, Object> variables = new HashMap<>();

        if ((isLieferantInProjekt))
            lieferInProjekt =true;
        else lieferInProjekt = false;

        String lieferInProjektStr = lieferInProjekt ? "1" : "0";

        variables.put("id",projektIdInt);

        variables.put("LieferInProjekt", lieferInProjektStr);


        return variables;
    }

    @JobWorker(type = "daten-vom-lieferanten-laden")
    public Map<String, Object> datenVomLieferantenLaden(final ActivatedJob job) {
        LOGGER.info("Daten_VomLieferanten_Laden");

        final Object projektId = job.getVariablesAsMap().get("projekt_auswahl");

        final Object lieferantId = job.getVariablesAsMap().get("lieferanter_auswahl");

        int projektIdInt = Integer.parseInt(projektId.toString());

        int lieferantIdInt = Integer.parseInt(lieferantId.toString());



        List<Integer> frageIds = projektHasLieferantHasAntwortService.findFrageIdsByProjektAndLieferant(projektIdInt, lieferantIdInt);
        List<Integer> antwortIds = projektHasLieferantHasAntwortService.findAntwortIdsByProjektAndLieferant(projektIdInt, lieferantIdInt);

        List<Option<Integer>> fragenUndAntworten = new ArrayList<>();

        for (Integer frageId : frageIds) {
            String frageText = frageService.findFrageTextByIdFrage(frageId);
            fragenUndAntworten.add(new Option<>("Frage:" + frageText,frageId));
        }
        for (Integer antwortId : antwortIds) {
            String antwortText = antwortService.findAntowrtTextByidAntwort(antwortId);
            fragenUndAntworten.add(new Option<>( antwortText,antwortId));
        }

        String lieferant1 = lieferantService.getLieferantNameById(lieferantIdInt);

        float punkt1 = 0;
        int rankOfPunkt1 = 0;
        boolean isLieferant_KO_auswahlen= false;
        String KO = null;

        List<String> Lieferant = new ArrayList<>();

        List<Float> Punkt = new ArrayList<>();
        List<Float> Punkt1 = new ArrayList<>();

        List<Integer> Rank = new ArrayList<>();

        List<String> KO_Kriterien = new ArrayList<>();


        boolean ispunktEmpty = projektHasLieferantService.existScoreByProjectIdAndAntwortId(projektIdInt,lieferantIdInt);
        boolean isrankEmpty = projektHasLieferantService.existRankByProjectIdAndAntwortId(projektIdInt,lieferantIdInt);

        if( !ispunktEmpty|| !isrankEmpty ){

            List<Integer> lieferantids = projektHasLieferantHasAntwortService.findlLieferantIdsByProjekt(projektIdInt);
            for(Integer lieferantid : lieferantids){
                int i = 0;
                float sum = 0;
                List<Integer> antwortids = projektHasLieferantHasAntwortService.findAntwortIdsByProjektAndLieferant(projektIdInt, lieferantid);
                for (Integer antwortid : antwortids) {
                    Integer frageid = antwortService.findidFrageByidAntwort(antwortid);
                    int idKategorie = frageService.findKategorieIdByIdFrage(frageid);
                    float gewicht = kategoriegewichtInProjektService.findGewichtByIdProjektAndIdKategorie(projektIdInt, idKategorie);
                    int punktValue = antwortService.findPunktByidAntwort(antwortid);
                    sum += punktValue * gewicht;
                    isLieferant_KO_auswahlen = projektHasAntwortService.existsByProjektIdAndAntwortId(projektIdInt,antwortid);
                    String ko = isLieferant_KO_auswahlen ? "1" : "0";
                    i = i+ parseInt(ko);
                }
                if(i > 0){
                    KO = "Dieser Leiferant hat sich für die K.O. Kriterien auswählen";
                }else { KO = "Alles gute!!!";}
                String lieferants = lieferantService.getLieferantNameById(lieferantid);


                DecimalFormat decimalFormat = new DecimalFormat("#.0");
                float punkt = Float.parseFloat(decimalFormat.format(sum));


                ProjektHasLieferant projektHasLieferant = new ProjektHasLieferant();
                Projekt projekt=new Projekt();
                Lieferant lieferant=new Lieferant();

                projekt.setIdProjekt(projektIdInt);
                lieferant.setIdLieferant(lieferantid);

                projektHasLieferant.getId().setProjekt(projekt);
                projektHasLieferant.getId().setLieferant(lieferant);
                projektHasLieferant.setScore(punkt);

                projektHasLieferantService.getRepository().save(projektHasLieferant);
                Lieferant.add(lieferants);
                Punkt.add(punkt);
                Punkt1.add(punkt);
                KO_Kriterien.add(KO);

            }


            Collections.sort(Punkt1, Collections.reverseOrder());


            for(Integer lieferantid : lieferantids){
                punkt1 = projektHasLieferantService.findScoreByProjectIdAndAntwortId(projektIdInt,lieferantid);
                rankOfPunkt1 = Punkt1.indexOf(punkt1) + 1;
                Rank.add(rankOfPunkt1);

                ProjektHasLieferant projektHasLieferant = new ProjektHasLieferant();
                Projekt projekt=new Projekt();
                Lieferant lieferant=new Lieferant();

                projekt.setIdProjekt(projektIdInt);
                lieferant.setIdLieferant(lieferantid);

                projektHasLieferant.getId().setProjekt(projekt);
                projektHasLieferant.getId().setLieferant(lieferant);
                projektHasLieferant.setScore(punkt1);
                projektHasLieferant.setRank(rankOfPunkt1);

                projektHasLieferantService.getRepository().save(projektHasLieferant);
            }


        }else{
            punkt1 = projektHasLieferantService.findScoreByProjectIdAndAntwortId(projektIdInt,lieferantIdInt);
            rankOfPunkt1 = projektHasLieferantService.findRankByProjectIdAndAntwortId(projektIdInt,lieferantIdInt);
            int i = 0;

            for(Integer antwortId :antwortIds){
                isLieferant_KO_auswahlen = projektHasAntwortService.existsByProjektIdAndAntwortId(projektIdInt,antwortId);
                String ko = isLieferant_KO_auswahlen ? "1" : "0";
                i = i+ parseInt(ko);
            }
            if(i > 0){
                KO = "Dieser Leiferant hat sich für die K.O. Kriterien auswählen";
            }else { KO = "Alles gute!!!";}
            Lieferant.add(lieferant1);
            Punkt.add(punkt1);
            Rank.add(rankOfPunkt1);
            KO_Kriterien.add(KO);
        }




        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("FragenUndAntworten", fragenUndAntworten);
        variables.put("LieferantName", Lieferant);
        variables.put("Punkt",Punkt);
        variables.put("RankOfPunkt1", Rank);
        variables.put("KO",KO_Kriterien);
        return variables;
    }

    @JobWorker(type = "projekt-speichern2")
    public Map<String, Object> projektSpeichern2(final ActivatedJob job) {
        LOGGER.info("Projekt_Speichern2");

        final Object kategorieID1_up_temp= job.getVariablesAsMap().get("katergorie_update1");
        final Object kategorieID2_up_temp= job.getVariablesAsMap().get("katergorie_update2");
        final Object kategorieID3_up_temp= job.getVariablesAsMap().get("katergorie_update3");
        final Object kategorieID4_up_temp = job.getVariablesAsMap().get("katergorie_update4");

        final Object projektID_up_temp= job.getVariablesAsMap().get("projekt_auswahl");
        int projekt_test = parseInt(projektID_up_temp.toString());

        final Object projektID_up;

        if (projekt_test > 0) {
            projektID_up = job.getVariablesAsMap().get("projekt_auswahl");

        }else {
            projektID_up = job.getVariablesAsMap().get("projekt_auswahl2");
        }

        final Object gewicht1_up_temp= job.getVariablesAsMap().get("gewicht_update1");
        final Object gewicht2_up_temp= job.getVariablesAsMap().get("gewicht_update2");
        final Object gewicht3_up_temp= job.getVariablesAsMap().get("gewicht_update3");
        final Object gewicht4_up_temp = job.getVariablesAsMap().get("gewicht_update4");

        Integer kategorieID1_up = parseInt(kategorieID1_up_temp.toString());//checkid for finding the gewicht
        Integer kategorieID2_up = parseInt(kategorieID2_up_temp.toString());
        Integer kategorieID3_up = parseInt(kategorieID3_up_temp.toString());
        Integer kategorieID4_up = parseInt(kategorieID4_up_temp.toString());

        Integer projektID = parseInt(projektID_up.toString());//checkid for finding the gewicht

        Double gewicht1_up = parseDouble(gewicht1_up_temp.toString());//gewicht value for writing to database
        Double gewicht2_up = parseDouble(gewicht2_up_temp.toString());
        Double gewicht3_up = parseDouble(gewicht3_up_temp.toString());
        Double gewicht4_up = parseDouble(gewicht4_up_temp.toString());

        kategoriegewichtInProjektService.getRepository().setGewicht(projektID,kategorieID1_up,gewicht1_up);
        kategoriegewichtInProjektService.getRepository().setGewicht(projektID,kategorieID2_up,gewicht2_up);
        kategoriegewichtInProjektService.getRepository().setGewicht(projektID,kategorieID3_up,gewicht3_up);
        kategoriegewichtInProjektService.getRepository().setGewicht(projektID, kategorieID4_up, gewicht4_up);

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("projektID", projektID);
        variables.put("kategorieID1_up", kategorieID1_up);
        variables.put("gewicht1_up",gewicht1_up);
        variables.put("kategorieID2_up", kategorieID2_up);
        variables.put("gewicht2_up",gewicht2_up);
        variables.put("kategorieID3_up", kategorieID3_up);
        variables.put("gewicht3_up",gewicht3_up);
        variables.put("kategorieID4_up", kategorieID4_up);
        variables.put("gewicht4_up", gewicht4_up);

        return variables;
    }

    @JobWorker(type = "lieferant-zum-projekt-hinzufugen")
    public Map<String, Object> lieferantZumProjektHinzufugen(final ActivatedJob job) {

        LOGGER.info("Lieferant_ZumProjekt_Hinzufugen");


        final Object projektID_liefpro_temp = job.getVariablesAsMap().get("projekt_auswahl");
        int projekt_test = parseInt(projektID_liefpro_temp.toString());

        final Object projektID_liefpro;


        if (projekt_test > 0) {
            projektID_liefpro = job.getVariablesAsMap().get("projekt_auswahl");

        } else {
            projektID_liefpro = job.getVariablesAsMap().get("projekt_auswahl2");
        }

        final Object lieferantID_liefpro = job.getVariablesAsMap().get("lieferanter_auswahl");


        final ProjektHasLieferant projektHasLieferant = new ProjektHasLieferant();
        final Projekt projekt = new Projekt();
        final Lieferant lieferant = new Lieferant();


        projekt.setIdProjekt(parseInt(projektID_liefpro.toString()));
        lieferant.setIdLieferant(parseInt(lieferantID_liefpro.toString()));

        projektHasLieferant.getId().setProjekt(projekt);
        projektHasLieferant.getId().setLieferant(lieferant);

        projektHasLieferantService.getRepository().save(projektHasLieferant);


        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("projektID_liefpro", projektHasLieferant.getId().getProjekt().getIdProjekt());
        variables.put("lieferantID_liefpro", projektHasLieferant.getId().getLieferant().getIdLieferant());

        return variables;
    }

    @JobWorker(type = "prufung-auf-vollstandigkeit1")
    public Map<String, Object> prufungAufVollstandigkeit1(final ActivatedJob job) {
        LOGGER.info("Prufung_AufVollstandigkeit1");

        //Wählen die Vorhandene Projekt oder neue angelegtes Projekt aus

        final Object projektID_vollstandig1_temp= job.getVariablesAsMap().get("projekt_auswahl");
        final Object lieferanterID_vollstandig1= job.getVariablesAsMap().get("lieferanter_auswahl");
        int projekt_test = parseInt(projektID_vollstandig1_temp.toString());

        final Object projektID_vollstandig1;


        if (projekt_test > 0) {
            projektID_vollstandig1 = job.getVariablesAsMap().get("projekt_auswahl");

        }else {
            projektID_vollstandig1 = job.getVariablesAsMap().get("projekt_auswahl2");
        }

        Integer projektID = parseInt(projektID_vollstandig1.toString());//checkid_projekt for the vollstandigkeit of antwort
        Integer lieferanterID = parseInt(lieferanterID_vollstandig1.toString());//checkid_lieferanter for the vollstandigkeit of antwort

        Fragebogen fragebogen = projektService.getRepository().findFragebogenByProjektID(projektID);

        Integer fragebogenID = fragebogen.getIdFragebogen();

        List<Frage> frage_all = fragebogenHasFrageService.getRepository().findAllFrageOderById(fragebogenID);

        Frage frage1 = frage_all.get(0);
        Frage frage2 = frage_all.get(1);
        Frage frage3 = frage_all.get(2);
        Frage frage4 = frage_all.get(3);
        Frage frage5 = frage_all.get(4);
        Frage frage6 = frage_all.get(5);
        Frage frage7 = frage_all.get(6);
        Frage frage8 = frage_all.get(7);
        Frage frage9 = frage_all.get(8);
        Frage frage10 = frage_all.get(9);
        Frage frage11 = frage_all.get(10);
        Frage frage12 = frage_all.get(11);
        Frage frage13 = frage_all.get(12);
        Frage frage14 = frage_all.get(13);
        Frage frage15 = frage_all.get(14);
        Frage frage16 = frage_all.get(15);
        Frage frage17 = frage_all.get(16);
        Frage frage18 = frage_all.get(17);
        Frage frage19 = frage_all.get(18);
        Frage frage20 = frage_all.get(19);

        Integer frageID1 = frage1.getIdFrage();//checkid_frage for the vollstandigkeit of antwort
        Integer frageID2 = frage2.getIdFrage();
        Integer frageID3 = frage3.getIdFrage();
        Integer frageID4 = frage4.getIdFrage();
        Integer frageID5 = frage5.getIdFrage();
        Integer frageID6 = frage6.getIdFrage();
        Integer frageID7 = frage7.getIdFrage();
        Integer frageID8 = frage8.getIdFrage();
        Integer frageID9 = frage9.getIdFrage();
        Integer frageID10 = frage10.getIdFrage();
        Integer frageID11 = frage11.getIdFrage();
        Integer frageID12 = frage12.getIdFrage();
        Integer frageID13 = frage13.getIdFrage();
        Integer frageID14 = frage14.getIdFrage();
        Integer frageID15 = frage15.getIdFrage();
        Integer frageID16 = frage16.getIdFrage();
        Integer frageID17 = frage17.getIdFrage();
        Integer frageID18 = frage18.getIdFrage();
        Integer frageID19 = frage19.getIdFrage();
        Integer frageID20 = frage20.getIdFrage();

        Antwort antwort1 = projektHasLieferantHasAntwortService.getRepository().findAntwortByProjektIDLieferanterIDFrageID(projektID,lieferanterID,frageID1);
        Integer Fragebogenvollstandig_temp;


        if (antwort1==null){
            Fragebogenvollstandig_temp=0;
        }else{
            Fragebogenvollstandig_temp=1;
        }


        String Fragebogenvollstandig = String.valueOf(Fragebogenvollstandig_temp);



        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("Fragebogenvollstandig", Fragebogenvollstandig);

        return variables;
    }

    @JobWorker(type = "email-senden")
    public Map<String, Object> emailSenden(final ActivatedJob job) {
        LOGGER.info("Email_Senden");



        String Info = "Email zur lieferant senden, um die Unvollstandigkeit von Fragebogen zu informieren";



        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("Info für Email:", Info);

        return variables;
    }
//-----------------
    @JobWorker(type = "zugange-fur-lieferanten-verschicken")
    public Map<String, Object> zugangeFurLieferantenVerschicken(final ActivatedJob job) {
        LOGGER.info("Zugang_FurLieferanten_Verschicken");
        String Info = "Email zu Lieferant schicken";

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("email_info", Info);

        return variables;
    }

    @JobWorker(type = "anmeldedaten-uberprufen")
    public Map<String, Object> anmeldedatenUberprufen(final ActivatedJob job) {
        LOGGER.info("Anmeldedaten_uberprufen");

        final Object lieferantName_login= job.getVariablesAsMap().get("name");
        final Object lieferantPassword_login= job.getVariablesAsMap().get("passwort");
        final Object lieferantID_login= job.getVariablesAsMap().get("lieferanter_auswahl");

        int anmeldungErfolgreich = 0;

        Integer lieferantID = parseInt(lieferantID_login.toString());
        String nameLogin = lieferantName_login.toString();
        String passwortLogin = lieferantPassword_login.toString();

        Lieferant lieferant = lieferantService.getRepository().findLiferantByUsername(nameLogin);
        String passwort = lieferantService.getRepository().findPasswordByUsername(nameLogin);
        String name = lieferantService.getRepository().findLieferantNameById(lieferantID);

        if (lieferant == null) {
            anmeldungErfolgreich = 0;
        } else if (!Objects.equals(passwort, passwortLogin)) {
            anmeldungErfolgreich = 0;
        } else if (!Objects.equals(name, nameLogin)) {
            anmeldungErfolgreich = 0;
        } else {
            anmeldungErfolgreich = 1;
        }

        String anmeldungErfolgreichString = String.valueOf(anmeldungErfolgreich);

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("anmeldung_erfolgreich", anmeldungErfolgreichString);

        return variables;
    }

    @JobWorker(type = "antworten-laden1")
    public Map<String, Object> antwortenLaden1(final ActivatedJob job) {
        LOGGER.info("Antworten_Laden1");

        final Object projektID_antlade1_temp= job.getVariablesAsMap().get("projekt_auswahl");
        int projekt_test = parseInt(projektID_antlade1_temp.toString());

        final Object projektID_antlade1;

        if (projekt_test > 0) {
            projektID_antlade1 = job.getVariablesAsMap().get("projekt_auswahl");

        }else {
            projektID_antlade1 = job.getVariablesAsMap().get("projekt_auswahl2");
        }

        Integer projektID = parseInt(projektID_antlade1.toString());

        Fragebogen fragebogen = projektService.getRepository().findFragebogenByProjektID(projektID);

        Integer fragebogenID = fragebogen.getIdFragebogen();

        List<Frage> frage_all = fragebogenHasFrageService.getRepository().findAllFrageOderById(fragebogenID);

        Frage frage1 = frage_all.get(0);
        Frage frage2 = frage_all.get(1);
        Frage frage3 = frage_all.get(2);
        Frage frage4 = frage_all.get(3);
        Frage frage5 = frage_all.get(4);
        Frage frage6 = frage_all.get(5);
        Frage frage7 = frage_all.get(6);
        Frage frage8 = frage_all.get(7);
        Frage frage9 = frage_all.get(8);
        Frage frage10 = frage_all.get(9);
        Frage frage11 = frage_all.get(10);
        Frage frage12 = frage_all.get(11);
        Frage frage13 = frage_all.get(12);
        Frage frage14 = frage_all.get(13);
        Frage frage15 = frage_all.get(14);
        Frage frage16 = frage_all.get(15);
        Frage frage17 = frage_all.get(16);
        Frage frage18 = frage_all.get(17);
        Frage frage19 = frage_all.get(18);
        Frage frage20 = frage_all.get(19);

        final List<Option<Integer>> Frageliste = fragebogenHasFrageService.getRepository().findAllFrageOderById(fragebogenID).stream()
                .map(e -> new Option<>(e.getFrageText(), e.getIdFrage()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort1 = antwortService.getRepository().findAntwortIdsByFrageId(frage1.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort2 = antwortService.getRepository().findAntwortIdsByFrageId(frage2.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort3 = antwortService.getRepository().findAntwortIdsByFrageId(frage3.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort4 = antwortService.getRepository().findAntwortIdsByFrageId(frage4.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort5 = antwortService.getRepository().findAntwortIdsByFrageId(frage5.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort6 = antwortService.getRepository().findAntwortIdsByFrageId(frage6.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort7 = antwortService.getRepository().findAntwortIdsByFrageId(frage7.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort8 = antwortService.getRepository().findAntwortIdsByFrageId(frage8.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort9 = antwortService.getRepository().findAntwortIdsByFrageId(frage9.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort10 = antwortService.getRepository().findAntwortIdsByFrageId(frage10.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort11 = antwortService.getRepository().findAntwortIdsByFrageId(frage11.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort12 = antwortService.getRepository().findAntwortIdsByFrageId(frage12.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort13 = antwortService.getRepository().findAntwortIdsByFrageId(frage13.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort14 = antwortService.getRepository().findAntwortIdsByFrageId(frage14.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort15 = antwortService.getRepository().findAntwortIdsByFrageId(frage15.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort16 = antwortService.getRepository().findAntwortIdsByFrageId(frage16.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort17 = antwortService.getRepository().findAntwortIdsByFrageId(frage17.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort18 = antwortService.getRepository().findAntwortIdsByFrageId(frage18.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort19 = antwortService.getRepository().findAntwortIdsByFrageId(frage19.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Antwort20 = antwortService.getRepository().findAntwortIdsByFrageId(frage20.getIdFrage()).stream()
                .map(e -> new Option<>(e.getAntwortText(), e.getIdAntwort()))
                .collect(Collectors.toList());

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("Frageliste", Frageliste);
        variables.put("Antwort1", Antwort1);
        variables.put("Antwort2", Antwort2);
        variables.put("Antwort3", Antwort3);
        variables.put("Antwort4", Antwort4);
        variables.put("Antwort5", Antwort5);
        variables.put("Antwort6", Antwort6);
        variables.put("Antwort7", Antwort7);
        variables.put("Antwort8", Antwort8);
        variables.put("Antwort9", Antwort9);
        variables.put("Antwort10", Antwort10);
        variables.put("Antwort11", Antwort11);
        variables.put("Antwort12", Antwort12);
        variables.put("Antwort13", Antwort13);
        variables.put("Antwort14", Antwort14);
        variables.put("Antwort15", Antwort15);
        variables.put("Antwort16", Antwort16);
        variables.put("Antwort17", Antwort17);
        variables.put("Antwort18", Antwort18);
        variables.put("Antwort19", Antwort19);
        variables.put("Antwort20", Antwort20);

        return variables;
    }

    @JobWorker(type = "antworten-speichern")
    public Map<String, Object> antwortenSpeichern(final ActivatedJob job) {
        LOGGER.info("Antworten_Speichern");

        final Object projektID_speichern_temp= job.getVariablesAsMap().get("projekt_auswahl");
        final Object lieferanterID_speichern_temp= job.getVariablesAsMap().get("lieferanter_auswahl");
        final Object antwort1_temp= job.getVariablesAsMap().get("antwort1");
        final Object antwort2_temp= job.getVariablesAsMap().get("antwort2");
        final Object antwort3_temp= job.getVariablesAsMap().get("antwort3");
        final Object antwort4_temp= job.getVariablesAsMap().get("antwort4");
        final Object antwort5_temp= job.getVariablesAsMap().get("antwort5");
        final Object antwort6_temp= job.getVariablesAsMap().get("antwort6");
        final Object antwort7_temp= job.getVariablesAsMap().get("antwort7");
        final Object antwort8_temp= job.getVariablesAsMap().get("antwort8");
        final Object antwort9_temp= job.getVariablesAsMap().get("antwort9");
        final Object antwort10_temp= job.getVariablesAsMap().get("antwort10");
        final Object antwort11_temp= job.getVariablesAsMap().get("antwort11");
        final Object antwort12_temp= job.getVariablesAsMap().get("antwort12");
        final Object antwort13_temp= job.getVariablesAsMap().get("antwort13");
        final Object antwort14_temp= job.getVariablesAsMap().get("antwort14");
        final Object antwort15_temp= job.getVariablesAsMap().get("antwort15");
        final Object antwort16_temp= job.getVariablesAsMap().get("antwort16");
        final Object antwort17_temp= job.getVariablesAsMap().get("antwort17");
        final Object antwort18_temp= job.getVariablesAsMap().get("antwort18");
        final Object antwort19_temp= job.getVariablesAsMap().get("antwort19");
        final Object antwort20_temp= job.getVariablesAsMap().get("antwort20");

        int projekt_test = parseInt(projektID_speichern_temp.toString());

        final Object projektID_speichern;

        if (projekt_test > 0) {
            projektID_speichern = job.getVariablesAsMap().get("projekt_auswahl");

        }else {
            projektID_speichern = job.getVariablesAsMap().get("projekt_auswahl2");
        }

        Integer projektID = parseInt(projektID_speichern.toString());

        Integer lieferanterID = parseInt(lieferanterID_speichern_temp.toString());

        Fragebogen fragebogen = projektService.getRepository().findFragebogenByProjektID(projektID);

        Integer fragebogenID = fragebogen.getIdFragebogen();

        List<Frage> frage_all = fragebogenHasFrageService.getRepository().findAllFrageOderById(fragebogenID);

        Frage frage1 = frage_all.get(0);
        Frage frage2 = frage_all.get(1);
        Frage frage3 = frage_all.get(2);
        Frage frage4 = frage_all.get(3);
        Frage frage5 = frage_all.get(4);
        Frage frage6 = frage_all.get(5);
        Frage frage7 = frage_all.get(6);
        Frage frage8 = frage_all.get(7);
        Frage frage9 = frage_all.get(8);
        Frage frage10 = frage_all.get(9);
        Frage frage11 = frage_all.get(10);
        Frage frage12 = frage_all.get(11);
        Frage frage13 = frage_all.get(12);
        Frage frage14 = frage_all.get(13);
        Frage frage15 = frage_all.get(14);
        Frage frage16 = frage_all.get(15);
        Frage frage17 = frage_all.get(16);
        Frage frage18 = frage_all.get(17);
        Frage frage19 = frage_all.get(18);
        Frage frage20 = frage_all.get(19);

        Integer frageID1 = frage1.getIdFrage();
        Integer frageID2 = frage2.getIdFrage();
        Integer frageID3 = frage3.getIdFrage();
        Integer frageID4 = frage4.getIdFrage();
        Integer frageID5 = frage5.getIdFrage();
        Integer frageID6 = frage6.getIdFrage();
        Integer frageID7 = frage7.getIdFrage();
        Integer frageID8 = frage8.getIdFrage();
        Integer frageID9 = frage9.getIdFrage();
        Integer frageID10 = frage10.getIdFrage();
        Integer frageID11 = frage11.getIdFrage();
        Integer frageID12 = frage12.getIdFrage();
        Integer frageID13 = frage13.getIdFrage();
        Integer frageID14 = frage14.getIdFrage();
        Integer frageID15 = frage15.getIdFrage();
        Integer frageID16 = frage16.getIdFrage();
        Integer frageID17 = frage17.getIdFrage();
        Integer frageID18 = frage18.getIdFrage();
        Integer frageID19 = frage19.getIdFrage();
        Integer frageID20 = frage20.getIdFrage();

        Integer antwortID1 = parseInt(antwort1_temp.toString());
        Integer antwortID2 = parseInt(antwort2_temp.toString());
        Integer antwortID3 = parseInt(antwort3_temp.toString());
        Integer antwortID4 = parseInt(antwort4_temp.toString());
        Integer antwortID5 = parseInt(antwort5_temp.toString());
        Integer antwortID6 = parseInt(antwort6_temp.toString());
        Integer antwortID7 = parseInt(antwort7_temp.toString());
        Integer antwortID8 = parseInt(antwort8_temp.toString());
        Integer antwortID9 = parseInt(antwort9_temp.toString());
        Integer antwortID10 = parseInt(antwort10_temp.toString());
        Integer antwortID11 = parseInt(antwort11_temp.toString());
        Integer antwortID12 = parseInt(antwort12_temp.toString());
        Integer antwortID13 = parseInt(antwort13_temp.toString());
        Integer antwortID14 = parseInt(antwort14_temp.toString());
        Integer antwortID15 = parseInt(antwort15_temp.toString());
        Integer antwortID16 = parseInt(antwort16_temp.toString());
        Integer antwortID17 = parseInt(antwort17_temp.toString());
        Integer antwortID18 = parseInt(antwort18_temp.toString());
        Integer antwortID19 = parseInt(antwort19_temp.toString());
        Integer antwortID20 = parseInt(antwort20_temp.toString());

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort1 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt1=new Projekt();
        final Lieferant lieferant1=new Lieferant();
        final Frage frage1_speichern=new Frage();
        final Antwort antwort1=new Antwort();
        projekt1.setIdProjekt(projektID);
        lieferant1.setIdLieferant(lieferanterID);
        frage1_speichern.setIdFrage(frageID1);
        antwort1.setIdAntwort(antwortID1);
        projektHasLieferantHasAntwort1.getId().setProjekt(projekt1);
        projektHasLieferantHasAntwort1.getId().setLieferant(lieferant1);
        projektHasLieferantHasAntwort1.getId().setFrage(frage1_speichern);
        projektHasLieferantHasAntwort1.getId().setAntwort(antwort1);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort1);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort2 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt2=new Projekt();
        final Lieferant lieferant2=new Lieferant();
        final Frage frage2_speichern=new Frage();
        final Antwort antwort2=new Antwort();
        projekt2.setIdProjekt(projektID);
        lieferant2.setIdLieferant(lieferanterID);
        frage2_speichern.setIdFrage(frageID2);
        antwort2.setIdAntwort(antwortID2);
        projektHasLieferantHasAntwort2.getId().setProjekt(projekt2);
        projektHasLieferantHasAntwort2.getId().setLieferant(lieferant2);
        projektHasLieferantHasAntwort2.getId().setFrage(frage2_speichern);
        projektHasLieferantHasAntwort2.getId().setAntwort(antwort2);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort2);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort3 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt3=new Projekt();
        final Lieferant lieferant3=new Lieferant();
        final Frage frage3_speichern=new Frage();
        final Antwort antwort3=new Antwort();
        projekt3.setIdProjekt(projektID);
        lieferant3.setIdLieferant(lieferanterID);
        frage3_speichern.setIdFrage(frageID3);
        antwort3.setIdAntwort(antwortID3);
        projektHasLieferantHasAntwort3.getId().setProjekt(projekt3);
        projektHasLieferantHasAntwort3.getId().setLieferant(lieferant3);
        projektHasLieferantHasAntwort3.getId().setFrage(frage3_speichern);
        projektHasLieferantHasAntwort3.getId().setAntwort(antwort3);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort3);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort4 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt4=new Projekt();
        final Lieferant lieferant4=new Lieferant();
        final Frage frage4_speichern=new Frage();
        final Antwort antwort4=new Antwort();
        projekt4.setIdProjekt(projektID);
        lieferant4.setIdLieferant(lieferanterID);
        frage4_speichern.setIdFrage(frageID4);
        antwort4.setIdAntwort(antwortID4);
        projektHasLieferantHasAntwort4.getId().setProjekt(projekt4);
        projektHasLieferantHasAntwort4.getId().setLieferant(lieferant4);
        projektHasLieferantHasAntwort4.getId().setFrage(frage4_speichern);
        projektHasLieferantHasAntwort4.getId().setAntwort(antwort4);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort4);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort5 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt5=new Projekt();
        final Lieferant lieferant5=new Lieferant();
        final Frage frage5_speichern=new Frage();
        final Antwort antwort5=new Antwort();
        projekt5.setIdProjekt(projektID);
        lieferant5.setIdLieferant(lieferanterID);
        frage5_speichern.setIdFrage(frageID5);
        antwort5.setIdAntwort(antwortID5);
        projektHasLieferantHasAntwort5.getId().setProjekt(projekt5);
        projektHasLieferantHasAntwort5.getId().setLieferant(lieferant5);
        projektHasLieferantHasAntwort5.getId().setFrage(frage5_speichern);
        projektHasLieferantHasAntwort5.getId().setAntwort(antwort5);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort5);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort6 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt6=new Projekt();
        final Lieferant lieferant6=new Lieferant();
        final Frage frage6_speichern=new Frage();
        final Antwort antwort6=new Antwort();
        projekt6.setIdProjekt(projektID);
        lieferant6.setIdLieferant(lieferanterID);
        frage6_speichern.setIdFrage(frageID6);
        antwort6.setIdAntwort(antwortID6);
        projektHasLieferantHasAntwort6.getId().setProjekt(projekt6);
        projektHasLieferantHasAntwort6.getId().setLieferant(lieferant6);
        projektHasLieferantHasAntwort6.getId().setFrage(frage6_speichern);
        projektHasLieferantHasAntwort6.getId().setAntwort(antwort6);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort6);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort7 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt7=new Projekt();
        final Lieferant lieferant7=new Lieferant();
        final Frage frage7_speichern=new Frage();
        final Antwort antwort7=new Antwort();
        projekt7.setIdProjekt(projektID);
        lieferant7.setIdLieferant(lieferanterID);
        frage7_speichern.setIdFrage(frageID7);
        antwort7.setIdAntwort(antwortID7);
        projektHasLieferantHasAntwort7.getId().setProjekt(projekt7);
        projektHasLieferantHasAntwort7.getId().setLieferant(lieferant7);
        projektHasLieferantHasAntwort7.getId().setFrage(frage7_speichern);
        projektHasLieferantHasAntwort7.getId().setAntwort(antwort7);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort7);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort8 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt8=new Projekt();
        final Lieferant lieferant8=new Lieferant();
        final Frage frage8_speichern=new Frage();
        final Antwort antwort8=new Antwort();
        projekt8.setIdProjekt(projektID);
        lieferant8.setIdLieferant(lieferanterID);
        frage8_speichern.setIdFrage(frageID8);
        antwort8.setIdAntwort(antwortID8);
        projektHasLieferantHasAntwort8.getId().setProjekt(projekt8);
        projektHasLieferantHasAntwort8.getId().setLieferant(lieferant8);
        projektHasLieferantHasAntwort8.getId().setFrage(frage8_speichern);
        projektHasLieferantHasAntwort8.getId().setAntwort(antwort8);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort8);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort9 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt9=new Projekt();
        final Lieferant lieferant9=new Lieferant();
        final Frage frage9_speichern=new Frage();
        final Antwort antwort9=new Antwort();
        projekt9.setIdProjekt(projektID);
        lieferant9.setIdLieferant(lieferanterID);
        frage9_speichern.setIdFrage(frageID9);
        antwort9.setIdAntwort(antwortID9);
        projektHasLieferantHasAntwort9.getId().setProjekt(projekt9);
        projektHasLieferantHasAntwort9.getId().setLieferant(lieferant9);
        projektHasLieferantHasAntwort9.getId().setFrage(frage9_speichern);
        projektHasLieferantHasAntwort9.getId().setAntwort(antwort9);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort9);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort10 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt10=new Projekt();
        final Lieferant lieferant10=new Lieferant();
        final Frage frage10_speichern=new Frage();
        final Antwort antwort10=new Antwort();
        projekt10.setIdProjekt(projektID);
        lieferant10.setIdLieferant(lieferanterID);
        frage10_speichern.setIdFrage(frageID10);
        antwort10.setIdAntwort(antwortID10);
        projektHasLieferantHasAntwort10.getId().setProjekt(projekt10);
        projektHasLieferantHasAntwort10.getId().setLieferant(lieferant10);
        projektHasLieferantHasAntwort10.getId().setFrage(frage10_speichern);
        projektHasLieferantHasAntwort10.getId().setAntwort(antwort10);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort10);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort11 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt11=new Projekt();
        final Lieferant lieferant11=new Lieferant();
        final Frage frage11_speichern=new Frage();
        final Antwort antwort11=new Antwort();
        projekt11.setIdProjekt(projektID);
        lieferant11.setIdLieferant(lieferanterID);
        frage11_speichern.setIdFrage(frageID11);
        antwort11.setIdAntwort(antwortID11);
        projektHasLieferantHasAntwort11.getId().setProjekt(projekt11);
        projektHasLieferantHasAntwort11.getId().setLieferant(lieferant11);
        projektHasLieferantHasAntwort11.getId().setFrage(frage11_speichern);
        projektHasLieferantHasAntwort11.getId().setAntwort(antwort11);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort11);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort12 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt12=new Projekt();
        final Lieferant lieferant12=new Lieferant();
        final Frage frage12_speichern=new Frage();
        final Antwort antwort12=new Antwort();
        projekt12.setIdProjekt(projektID);
        lieferant12.setIdLieferant(lieferanterID);
        frage12_speichern.setIdFrage(frageID12);
        antwort12.setIdAntwort(antwortID12);
        projektHasLieferantHasAntwort12.getId().setProjekt(projekt12);
        projektHasLieferantHasAntwort12.getId().setLieferant(lieferant12);
        projektHasLieferantHasAntwort12.getId().setFrage(frage12_speichern);
        projektHasLieferantHasAntwort12.getId().setAntwort(antwort12);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort12);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort13 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt13=new Projekt();
        final Lieferant lieferant13=new Lieferant();
        final Frage frage13_speichern=new Frage();
        final Antwort antwort13=new Antwort();
        projekt13.setIdProjekt(projektID);
        lieferant13.setIdLieferant(lieferanterID);
        frage13_speichern.setIdFrage(frageID13);
        antwort13.setIdAntwort(antwortID13);
        projektHasLieferantHasAntwort13.getId().setProjekt(projekt13);
        projektHasLieferantHasAntwort13.getId().setLieferant(lieferant13);
        projektHasLieferantHasAntwort13.getId().setFrage(frage13_speichern);
        projektHasLieferantHasAntwort13.getId().setAntwort(antwort13);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort13);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort14 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt14=new Projekt();
        final Lieferant lieferant14=new Lieferant();
        final Frage frage14_speichern=new Frage();
        final Antwort antwort14=new Antwort();
        projekt14.setIdProjekt(projektID);
        lieferant14.setIdLieferant(lieferanterID);
        frage14_speichern.setIdFrage(frageID14);
        antwort14.setIdAntwort(antwortID14);
        projektHasLieferantHasAntwort14.getId().setProjekt(projekt14);
        projektHasLieferantHasAntwort14.getId().setLieferant(lieferant14);
        projektHasLieferantHasAntwort14.getId().setFrage(frage14_speichern);
        projektHasLieferantHasAntwort14.getId().setAntwort(antwort14);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort14);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort15 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt15=new Projekt();
        final Lieferant lieferant15=new Lieferant();
        final Frage frage15_speichern=new Frage();
        final Antwort antwort15=new Antwort();
        projekt15.setIdProjekt(projektID);
        lieferant15.setIdLieferant(lieferanterID);
        frage15_speichern.setIdFrage(frageID15);
        antwort15.setIdAntwort(antwortID15);
        projektHasLieferantHasAntwort15.getId().setProjekt(projekt15);
        projektHasLieferantHasAntwort15.getId().setLieferant(lieferant15);
        projektHasLieferantHasAntwort15.getId().setFrage(frage15_speichern);
        projektHasLieferantHasAntwort15.getId().setAntwort(antwort15);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort15);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort16 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt16=new Projekt();
        final Lieferant lieferant16=new Lieferant();
        final Frage frage16_speichern=new Frage();
        final Antwort antwort16=new Antwort();
        projekt16.setIdProjekt(projektID);
        lieferant16.setIdLieferant(lieferanterID);
        frage16_speichern.setIdFrage(frageID16);
        antwort16.setIdAntwort(antwortID16);
        projektHasLieferantHasAntwort16.getId().setProjekt(projekt16);
        projektHasLieferantHasAntwort16.getId().setLieferant(lieferant16);
        projektHasLieferantHasAntwort16.getId().setFrage(frage16_speichern);
        projektHasLieferantHasAntwort16.getId().setAntwort(antwort16);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort16);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort17 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt17=new Projekt();
        final Lieferant lieferant17=new Lieferant();
        final Frage frage17_speichern=new Frage();
        final Antwort antwort17=new Antwort();
        projekt17.setIdProjekt(projektID);
        lieferant17.setIdLieferant(lieferanterID);
        frage17_speichern.setIdFrage(frageID17);
        antwort17.setIdAntwort(antwortID17);
        projektHasLieferantHasAntwort17.getId().setProjekt(projekt17);
        projektHasLieferantHasAntwort17.getId().setLieferant(lieferant17);
        projektHasLieferantHasAntwort17.getId().setFrage(frage17_speichern);
        projektHasLieferantHasAntwort17.getId().setAntwort(antwort17);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort17);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort18 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt18=new Projekt();
        final Lieferant lieferant18=new Lieferant();
        final Frage frage18_speichern=new Frage();
        final Antwort antwort18=new Antwort();
        projekt18.setIdProjekt(projektID);
        lieferant18.setIdLieferant(lieferanterID);
        frage18_speichern.setIdFrage(frageID18);
        antwort18.setIdAntwort(antwortID18);
        projektHasLieferantHasAntwort18.getId().setProjekt(projekt18);
        projektHasLieferantHasAntwort18.getId().setLieferant(lieferant18);
        projektHasLieferantHasAntwort18.getId().setFrage(frage18_speichern);
        projektHasLieferantHasAntwort18.getId().setAntwort(antwort18);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort18);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort19 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt19=new Projekt();
        final Lieferant lieferant19=new Lieferant();
        final Frage frage19_speichern=new Frage();
        final Antwort antwort19=new Antwort();
        projekt19.setIdProjekt(projektID);
        lieferant19.setIdLieferant(lieferanterID);
        frage19_speichern.setIdFrage(frageID19);
        antwort19.setIdAntwort(antwortID19);
        projektHasLieferantHasAntwort19.getId().setProjekt(projekt19);
        projektHasLieferantHasAntwort19.getId().setLieferant(lieferant19);
        projektHasLieferantHasAntwort19.getId().setFrage(frage19_speichern);
        projektHasLieferantHasAntwort19.getId().setAntwort(antwort19);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort19);

        final ProjektHasLieferantHasAntwort projektHasLieferantHasAntwort20 = new ProjektHasLieferantHasAntwort();
        final Projekt projekt20=new Projekt();
        final Lieferant lieferant20=new Lieferant();
        final Frage frage20_speichern=new Frage();
        final Antwort antwort20=new Antwort();
        projekt20.setIdProjekt(projektID);
        lieferant20.setIdLieferant(lieferanterID);
        frage20_speichern.setIdFrage(frageID20);
        antwort20.setIdAntwort(antwortID20);
        projektHasLieferantHasAntwort20.getId().setProjekt(projekt20);
        projektHasLieferantHasAntwort20.getId().setLieferant(lieferant20);
        projektHasLieferantHasAntwort20.getId().setFrage(frage20_speichern);
        projektHasLieferantHasAntwort20.getId().setAntwort(antwort20);
        projektHasLieferantHasAntwortService.getRepository().save(projektHasLieferantHasAntwort20);

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("projektID_antwortspeichern", projektID);
        variables.put("lieferanterID_antwortspeichern", lieferanterID);

        return variables;
    }

    @JobWorker(type = "prufung-auf-vollstandigkeit2")
    public Map<String, Object> prufungAufVollstandigkeit2(final ActivatedJob job) {
        LOGGER.info("Prufung_AufVollstandigkeit2");

        final Object projektID_vollstandig_temp= job.getVariablesAsMap().get("projekt_auswahl");
        final Object lieferanterID_vollstandig= job.getVariablesAsMap().get("lieferanter_auswahl");
        int projekt_test = parseInt(projektID_vollstandig_temp.toString());

        final Object projektID_vollstandig;

        if (projekt_test > 0) {
            projektID_vollstandig = job.getVariablesAsMap().get("projekt_auswahl");

        }else {
            projektID_vollstandig = job.getVariablesAsMap().get("projekt_auswahl2");
        }

        Integer projektID = parseInt(projektID_vollstandig.toString());
        Integer lieferanterID = parseInt(lieferanterID_vollstandig.toString());

        Fragebogen fragebogen = projektService.getRepository().findFragebogenByProjektID(projektID);

        Integer fragebogenID = fragebogen.getIdFragebogen();

        List<Frage> frage_all = fragebogenHasFrageService.getRepository().findAllFrageOderById(fragebogenID);

        Frage frage1 = frage_all.get(0);
        Frage frage2 = frage_all.get(1);
        Frage frage3 = frage_all.get(2);
        Frage frage4 = frage_all.get(3);
        Frage frage5 = frage_all.get(4);
        Frage frage6 = frage_all.get(5);
        Frage frage7 = frage_all.get(6);
        Frage frage8 = frage_all.get(7);
        Frage frage9 = frage_all.get(8);
        Frage frage10 = frage_all.get(9);
        Frage frage11 = frage_all.get(10);
        Frage frage12 = frage_all.get(11);
        Frage frage13 = frage_all.get(12);
        Frage frage14 = frage_all.get(13);
        Frage frage15 = frage_all.get(14);
        Frage frage16 = frage_all.get(15);
        Frage frage17 = frage_all.get(16);
        Frage frage18 = frage_all.get(17);
        Frage frage19 = frage_all.get(18);
        Frage frage20 = frage_all.get(19);

        Integer frageID1 = frage1.getIdFrage();
        Integer frageID2 = frage2.getIdFrage(); // todo: 为什么这些对象没有使用
        Integer frageID3 = frage3.getIdFrage();
        Integer frageID4 = frage4.getIdFrage();
        Integer frageID5 = frage5.getIdFrage();
        Integer frageID6 = frage6.getIdFrage();
        Integer frageID7 = frage7.getIdFrage();
        Integer frageID8 = frage8.getIdFrage();
        Integer frageID9 = frage9.getIdFrage();
        Integer frageID10 = frage10.getIdFrage();
        Integer frageID11 = frage11.getIdFrage();
        Integer frageID12 = frage12.getIdFrage();
        Integer frageID13 = frage13.getIdFrage();
        Integer frageID14 = frage14.getIdFrage();
        Integer frageID15 = frage15.getIdFrage();
        Integer frageID16 = frage16.getIdFrage();
        Integer frageID17 = frage17.getIdFrage();
        Integer frageID18 = frage18.getIdFrage();
        Integer frageID19 = frage19.getIdFrage();
        Integer frageID20 = frage20.getIdFrage();

        Antwort antwort1 = projektHasLieferantHasAntwortService.getRepository().findAntwortByProjektIDLieferanterIDFrageID(projektID,lieferanterID,frageID1);
        Integer IstVollstandig_temp;

        if (antwort1==null){
            IstVollstandig_temp=0;
        }else{
            IstVollstandig_temp=1;
        }

        String IstVollstandig = String.valueOf(IstVollstandig_temp);

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("IstVollstandig", IstVollstandig);

        return variables;
    }

    @JobWorker(type = "lieferant-uber-unvollstandigkeit-informieren")
    public Map<String, Object> lieferantUberUnvollstandigkeitInformieren(final ActivatedJob job) {
        LOGGER.info("lieferant-uber-unvollstandigkeit-informieren");

        String Info = "Lieferant über Unvollständigkeit informieren";

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("Info über unvollständigen Antwort", Info);

        return variables;
    }

    @JobWorker(type = "nutzwertanalyse-durchfuhren-und-speichern")
    public Map<String, Object> nutzwertanalyseDurchfuhrenUndSpeichern(final ActivatedJob job) {
        LOGGER.info("Nutzwertanalyse_Durchfuhren_Speichern");

        final Object projektId1 = job.getVariablesAsMap().get("projekt_auswahl");
        final Object projektId;
        final Object lieferantId1 = job.getVariablesAsMap().get("lieferanter_auswahl");

        int lieferantId1Int = Integer.parseInt(lieferantId1.toString());

        int projektIdInt1 = Integer.parseInt(projektId1.toString());

        if (projektIdInt1 > 0) {
            projektId = job.getVariablesAsMap().get("projekt_auswahl");
        } else {
            projektId = job.getVariablesAsMap().get("projekt_auswahl2");
        }

        int projektIdInt = Integer.parseInt(projektId.toString());

        List<String> Lieferant = new ArrayList<>();

        List<Float> Punkt = new ArrayList<>();
        List<Float> Punkt1 = new ArrayList<>();

        List<Integer> Rank = new ArrayList<>();

        List<String> KO_Kriterien = new ArrayList<>();

        float punkt1 = 0;
        int rankOfPunkt1 = 0;
        boolean isLieferant_KO_auswahlen= false;
        String KO = null;

        List<Integer> frageId = projektHasLieferantHasAntwortService.findFrageIdsByProjektAndLieferant(projektIdInt, lieferantId1Int);
        List<Integer> antwortId = projektHasLieferantHasAntwortService.findAntwortIdsByProjektAndLieferant(projektIdInt, lieferantId1Int);

        List<Option<Integer>> fragenUndAntworten = new ArrayList<>();

        for (Integer frageIds : frageId) {
            String frageText = frageService.findFrageTextByIdFrage(frageIds);
            fragenUndAntworten.add(new Option<>("Frage:" + frageText,frageIds));
        }
        for (Integer antwortIds : antwortId) {
            String antwortText = antwortService.findAntowrtTextByidAntwort(antwortIds);
            fragenUndAntworten.add(new Option<>( antwortText,antwortIds));
        }

        List<Integer> lieferantIds = projektHasLieferantHasAntwortService.findlLieferantIdsByProjekt(projektIdInt);
        for(Integer lieferantId : lieferantIds){
            int i = 0;
            float sum = 0;
            List<Integer> antwortIds = projektHasLieferantHasAntwortService.findAntwortIdsByProjektAndLieferant(projektIdInt, lieferantId);
            for (Integer antwortId1 : antwortIds) {
                Integer frageId1 = antwortService.findidFrageByidAntwort(antwortId1);
                int idKategorie = frageService.findKategorieIdByIdFrage(frageId1);
                float gewicht = kategoriegewichtInProjektService.findGewichtByIdProjektAndIdKategorie(projektIdInt, idKategorie);
                int punktValue = antwortService.findPunktByidAntwort(antwortId1);
                sum += punktValue * gewicht;
                isLieferant_KO_auswahlen = projektHasAntwortService.existsByProjektIdAndAntwortId(projektIdInt,antwortId1);     // 这里通过数据库中的条目数量来判断是否KO而不考虑数据库中is_ko字段的值  所以只有KO为ja才会被写入数据库
                String ko = isLieferant_KO_auswahlen ? "1" : "0";
                i = i+ parseInt(ko);
            }
            if(i > 0){
                KO = "Dieser Leiferant hat sich für die K.O. Kriterien auswählen";
            }else { KO = "Alles gute!!!";}
            String lieferants = lieferantService.getLieferantNameById(lieferantId);

            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            float punkt = Float.parseFloat(decimalFormat.format(sum));

            ProjektHasLieferant projektHasLieferant = new ProjektHasLieferant();
            Projekt projekt=new Projekt();
            Lieferant lieferant=new Lieferant();

            projekt.setIdProjekt(projektIdInt);
            lieferant.setIdLieferant(lieferantId);

            projektHasLieferant.getId().setProjekt(projekt);
            projektHasLieferant.getId().setLieferant(lieferant);
            projektHasLieferant.setScore(punkt);

            projektHasLieferantService.getRepository().save(projektHasLieferant);
            Lieferant.add(lieferants);
            Punkt.add(punkt);
            Punkt1.add(punkt);
            KO_Kriterien.add(KO);
        }

        Collections.sort(Punkt1, Collections.reverseOrder());

        for(Integer lieferantid : lieferantIds){
            punkt1 = projektHasLieferantService.findScoreByProjectIdAndAntwortId(projektIdInt,lieferantid);
            rankOfPunkt1 = Punkt1.indexOf(punkt1) + 1;
            Rank.add(rankOfPunkt1);

            ProjektHasLieferant projektHasLieferant = new ProjektHasLieferant();
            Projekt projekt=new Projekt();
            Lieferant lieferant=new Lieferant();

            projekt.setIdProjekt(projektIdInt);
            lieferant.setIdLieferant(lieferantid);

            projektHasLieferant.getId().setProjekt(projekt);
            projektHasLieferant.getId().setLieferant(lieferant);
            projektHasLieferant.setScore(punkt1);
            projektHasLieferant.setRank(rankOfPunkt1);

            projektHasLieferantService.getRepository().save(projektHasLieferant);
        }

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("FragenUndAntworten", fragenUndAntworten);
        variables.put("LieferantName", Lieferant);
        variables.put("Punkt",Punkt);
        variables.put("RankOfPunkt1", Rank);
        variables.put("KO",KO_Kriterien);
        return variables;
    }

    @JobWorker(type = "neues-ranking-liegt-vor")
    public Map<String, Object> neuesRankingLiegtVor(final ActivatedJob job) {
        LOGGER.info("Neues_Ranking_Liegt_Vor");

        String Info = "Neues Ranking liegt vor";

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("Info über Neu Ranking", Info);

        return variables;
    }

    private String hash(String password) {
        return password;
    }
}
