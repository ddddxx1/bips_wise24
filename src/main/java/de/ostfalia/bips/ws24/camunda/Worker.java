package de.ostfalia.bips.ws24.camunda;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import de.ostfalia.bips.ws24.camunda.database.domain.*;
import de.ostfalia.bips.ws24.camunda.database.repository.KategoriegewichtInProjektRepository;
import de.ostfalia.bips.ws24.camunda.database.service.*;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
//import io.camunda.zeebe.spring.client.annotation.Variable;

import de.ostfalia.bips.ws24.camunda.database.repository.UserRepository;

import de.ostfalia.bips.ws24.camunda.database.Model.Option;

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
    private final KategoriegewichtInProjektRepository kategoriegewichtInProjektRepository;
    private final KategorieService kategorieService;
    private final LieferantService lieferantService;
    private final ProjektHasAntwortService projektHasAntwortService;
    private final ProjektHasLieferantHasAntwortService projektHasLieferantHasAntwortService;
    private final ProjektHasLieferantService projektHasLieferantService;
    private final ProjektService projektService;
    private final KategoriegewichtInProjektService kategoriegewichtInProjektService;


    public Worker(UserRepository userRepository, AntwortService antwortService, FragebogenHasFrageService fragebogenHasFrageService, FragebogenService fragebogenService, FrageService frageService, KategoriegewichtInProjektRepository kategoriegewichtInProjektRepository, KategorieService kategorieService, LieferantService lieferantService, ProjektHasAntwortService projektHasAntwortService, ProjektHasLieferantHasAntwortService projektHasLieferantHasAntwortService, ProjektHasLieferantService projektHasLieferantService, ProjektService projektService, KategoriegewichtInProjektService kategoriegewichtInProjektService) {
        this.userRepository = userRepository;
        this.antwortService = antwortService;
        this.fragebogenHasFrageService = fragebogenHasFrageService;
        this.fragebogenService = fragebogenService;
        this.frageService = frageService;
        this.kategoriegewichtInProjektRepository = kategoriegewichtInProjektRepository;
        this.kategorieService = kategorieService;
        this.lieferantService = lieferantService;
        this.projektHasAntwortService = projektHasAntwortService;
        this.projektHasLieferantHasAntwortService = projektHasLieferantHasAntwortService;
        this.projektHasLieferantService = projektHasLieferantService;
        this.projektService = projektService;
        this.kategoriegewichtInProjektService = kategoriegewichtInProjektService;
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
        LOGGER.info("Projekte laden");

//        repository从数据库加载数据
        List<Projekt> projekte = projektService.getRepository().findAll();

        List<Map<String, Object>> projectList = new ArrayList<>();
        for (Projekt projekt : projekte) {
            Map<String, Object> projectData = new HashMap<>();
            projectData.put("id", projekt.getIdProjekt());
            projectData.put("name", projekt.getName());
            projectList.add(projectData);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("projekte", projectList);
        return result;

    }

    @JobWorker(type = "fragebogen-laden")
    public Map<String, Object> fragebogenLaden(final ActivatedJob job) {
        LOGGER.info("Fragebogen laden");
        final List<Option<Integer>> fragebogen_laden = fragebogenService.getRepository().findAll().stream()
                .map(e -> new Option<>(e.getBeschreibung(), e.getIdFragebogen()))
                .collect(Collectors.toList());

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("fragebogen_laden", fragebogen_laden);
        return variables;
    }

    @JobWorker(type = "daten-zum-fragebogen-laden")
    public Map<String, Object> datenZumFragebogenLaden(final ActivatedJob job) {
        LOGGER.info("Daten_Zum_Fragebogen_Laden");
        final Object projektName = job.getVariablesAsMap().get(("projekt_name"));
        final Object frageboge = job.getVariablesAsMap().get("frageboge");

        Integer maxProjektId = projektService.getRepository().countAllRecords();
        Integer newProjektId = maxProjektId + 1;

        final Projekt projekt = new Projekt();
        final Fragebogen fragebogen = new Fragebogen();

        projekt.setFragebogen(fragebogen);
        fragebogen.setIdFragebogen(parseInt(frageboge.toString()));

        projekt.setName(projektName.toString());
        projekt.setIdProjekt(newProjektId);
        projekt.setKomponente(String.valueOf(newProjektId));

        projektService.getRepository().save(projekt);
        LOGGER.info("newProjektId" + newProjektId);

        final List<Option<Integer>> Projekt = projektService.getRepository().findProjektByProjektID(newProjektId).stream()
                .map(e -> new Option<>(e.getName(), e.getIdProjekt()))
                .collect(Collectors.toList());

        final List<Option<Integer>> Fragen = frageService.getRepository().findAll().stream()
                .map(e -> new Option<>(e.getFrageText(), e.getIdFrage()))
                .collect(Collectors.toList());

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("newIdProjekt", projekt.getIdProjekt());
        variables.put("newIdFragebogen", projekt.getFragebogen());
        variables.put("Projekt", Projekt);
        variables.put("Fragen", Fragen);

        return variables;
    }

    @JobWorker(type = "projekt-speichern1")
    public Map<String, Object> projektSpeichern1(final ActivatedJob job) {
        LOGGER.info("Projekt_Speichern1");

        final Object projektID_KO = job.getVariablesAsMap().get("projekt_auswahl1");
        final Object antwortID_KO= job.getVariablesAsMap().get("antwort_auswahl");
        final Object frageID_KO= job.getVariablesAsMap().get("frage_auswahl");
        final Object ergebnisKO_KO= job.getVariablesAsMap().get("ergebnisKO");

        final ProjektHasAntwort projektHasAntwort = new ProjektHasAntwort();
        final Projekt projekt = new Projekt();
        final Frage frage = new Frage();
        final Antwort antwort = new Antwort();

        projekt.setIdProjekt(parseInt(projektID_KO.toString()));
        antwort.setIdAntwort(parseInt(antwortID_KO.toString()));
        frage.setIdFrage(parseInt(frageID_KO.toString()));

        projektHasAntwort.setIstKoKriterium(true);
        projektHasAntwort.getId().setProjekt(projekt);
        projektHasAntwort.getId().setAntwort(antwort);
        projektHasAntwort.getId().setFrage(frage);

        projektHasAntwortService.getRepository().save(projektHasAntwort);

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("projektID_KO", projektHasAntwort.getId().getProjekt().getIdProjekt());
        variables.put("antwortID_KO", projektHasAntwort.getId().getAntwort().getIdAntwort());
        variables.put("frageID_KO", projektHasAntwort.getId().getFrage().getIdFrage());
        variables.put("istKO_KO", projektHasAntwort.isIstKoKriterium());

        final Object kategorieID1_ge = job.getVariablesAsMap().get("kategorie_auswahl1");
        final Object kategorieID2_ge = job.getVariablesAsMap().get("kategorie_auswahl2");
        final Object kategorieID3_ge= job.getVariablesAsMap().get("katergorie_auswahl3");
        final Object kategorieID4_ge= job.getVariablesAsMap().get("katergorie_auswahl4");
        final Object projektID_ge= job.getVariablesAsMap().get("projekt_auswahl2");
        final Object gewicht1_ge= job.getVariablesAsMap().get("gewicht_eingeben1");
        final Object gewicht2_ge= job.getVariablesAsMap().get("gewicht_eingeben2");
        final Object gewicht3_ge= job.getVariablesAsMap().get("gewicht_eingeben3");
        final Object gewicht4_ge= job.getVariablesAsMap().get("gewicht_eingeben4");

        final KategoriegewichtInProjekt kategoriegewichtInProjekt1 = new KategoriegewichtInProjekt();
        final Kategorie kategorie1 = new Kategorie();
        final Projekt projekt1 = new Projekt();
        kategorie1.setIdKatagorie(parseInt(kategorieID1_ge.toString()));
        projekt1.setIdProjekt(parseInt(projektID_ge.toString()));
        kategoriegewichtInProjekt1.setGewicht((float) parseDouble(gewicht1_ge.toString()));
        kategoriegewichtInProjekt1.getId().setKategorie(kategorie1);
        kategoriegewichtInProjekt1.getId().setProjekt(projekt1);
        kategoriegewichtInProjektService.getRepository().save(kategoriegewichtInProjekt1);

        final KategoriegewichtInProjekt kategoriegewichtInProjekt2 = new KategoriegewichtInProjekt();
        final Kategorie kategorie2 = new Kategorie();
        final Projekt projekt2 = new Projekt();
        kategorie2.setIdKatagorie(parseInt(kategorieID2_ge.toString()));
        projekt2.setIdProjekt(parseInt(projektID_ge.toString()));
        kategoriegewichtInProjekt2.setGewicht((float) parseDouble(gewicht2_ge.toString()));
        kategoriegewichtInProjekt2.getId().setKategorie(kategorie2);
        kategoriegewichtInProjekt2.getId().setProjekt(projekt2);
        kategoriegewichtInProjektService.getRepository().save(kategoriegewichtInProjekt2);

        final KategoriegewichtInProjekt kategoriegewichtInProjekt3 = new KategoriegewichtInProjekt();
        final Kategorie kategorie3 = new Kategorie();
        final Projekt projekt3 = new Projekt();
        kategorie3.setIdKatagorie(parseInt(kategorieID3_ge.toString()));
        projekt3.setIdProjekt(parseInt(projektID_ge.toString()));
        kategoriegewichtInProjekt3.setGewicht((float) parseDouble(gewicht3_ge.toString()));
        kategoriegewichtInProjekt3.getId().setKategorie(kategorie3);
        kategoriegewichtInProjekt3.getId().setProjekt(projekt3);
        kategoriegewichtInProjektService.getRepository().save(kategoriegewichtInProjekt3);

        final KategoriegewichtInProjekt kategoriegewichtInProjekt4 = new KategoriegewichtInProjekt();
        final Kategorie kategorie4 = new Kategorie();
        final Projekt projekt4 = new Projekt();
        kategorie4.setIdKatagorie(parseInt(kategorieID4_ge.toString()));
        projekt4.setIdProjekt(parseInt(projektID_ge.toString()));
        kategoriegewichtInProjekt4.setGewicht((float) parseDouble(gewicht4_ge.toString()));
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

    @JobWorker(type = "daten-zu-projekt-laden")
    public Map<String, Object> datenZumProjektLaden(final ActivatedJob job) {
        LOGGER.info("Daten_ZumProjekt_Laden");
        final Object projektId = job.getVariablesAsMap().get("projekt_auswahl");

        int id = parseInt(projektId.toString());
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
        boolean lieferantInProjekt;

        HashMap<String, Object> variables = new HashMap<>();

        if (isLieferantInProjekt) {
            lieferantInProjekt = true;
        } else {
            lieferantInProjekt = false;
        }

        String lieferantInProjektString = lieferantInProjekt ? "1" : "0";
        variables.put("id", projektIdInt);
        variables.put("lieferantInProjekt", lieferantInProjektString);
        return variables;
    }

    @JobWorker(type = "daten-vom-lieferanten-laden")
    public Map<String, Object> datenVomLieferantenLaden(final ActivatedJob job) {
        LOGGER.info("Daten_Vom_Lieferanten_Laden");
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
        boolean isrankEmpty = projektHasLieferantService.existRankByProjectIdAndAntwortId(projektIdInt, lieferantIdInt);

        if (!ispunktEmpty || !isrankEmpty) {
            List<Integer> lieferantids = projektHasLieferantHasAntwortService.findlLieferantIdsByProjekt(projektIdInt);

            for (Integer lieferantid : lieferantids) {
                int i = 0;
                float sum = 0;
                List<Integer> antwortids = projektHasLieferantHasAntwortService.findAntwortIdsByProjektAndLieferant(projektIdInt, lieferantid);

                for (Integer antwortid : antwortids) {
                    Integer frageid = antwortService.findidFrageByidAntwort(antwortid);
                    int idKategorie = frageService.findKategorieIdByIdFrage(frageid);
                    float gewicht = kategoriegewichtInProjektService.findGewichtByIdProjektAndIdKategorie(projektIdInt, idKategorie);
                    int punktValue = antwortService.findPunktByidAntwort(antwortid);
                    sum += punktValue * gewicht;
                    isLieferant_KO_auswahlen = projektHasAntwortService.existsByProjektIdAndAntwortId(projektIdInt, antwortid);
                    String ko = isLieferant_KO_auswahlen ? "1" : "0";
                    i = i + parseInt(ko);
                }

                if (i > 0) {
                    KO = "Dieser Leiferant hat sich für die K.O. Kriterien auswählen";
                } else {
                    KO = "Alles gut!";
                }
                String lieferants = lieferantService.getLieferantNameById(lieferantid);

                DecimalFormat decimalFormat = new DecimalFormat("#.0");
                float punkt = Float.parseFloat(decimalFormat.format(sum));

                ProjektHasLieferant projektHasLieferant = new ProjektHasLieferant();
                Projekt projekt = new Projekt();
                Lieferant lieferant = new Lieferant();

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

            for (Integer lieferantid : lieferantids) {
                punkt1 = projektHasLieferantService.findScoreByProjectIdAndAntwortId(projektIdInt, lieferantid);
                rankOfPunkt1 = Punkt1.indexOf(punkt1) + 1;
                Rank.add(rankOfPunkt1);

                ProjektHasLieferant projektHasLieferant = new ProjektHasLieferant();
                Projekt projekt = new Projekt();
                Lieferant lieferant = new Lieferant();

                projekt.setIdProjekt(projektIdInt);
                lieferant.setIdLieferant(lieferantid);

                projektHasLieferant.getId().setProjekt(projekt);
                projektHasLieferant.getId().setLieferant(lieferant);
                projektHasLieferant.setScore(punkt1);
                projektHasLieferant.setRank(rankOfPunkt1);

                projektHasLieferantService.getRepository().save(projektHasLieferant);
            }
        } else {
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



        final Object projektID_liefpro_temp= job.getVariablesAsMap().get("projekt_auswahl");
        int projekt_test = parseInt(projektID_liefpro_temp.toString());

        final Object projektID_liefpro;


        if (projekt_test > 0) {
            projektID_liefpro = job.getVariablesAsMap().get("projekt_auswahl");

        }else {
            projektID_liefpro = job.getVariablesAsMap().get("projekt_auswahl2");
        }

        final Object lieferantID_liefpro= job.getVariablesAsMap().get("lieferanter_auswahl");




        final ProjektHasLieferant projektHasLieferant = new ProjektHasLieferant();
        final Projekt projekt=new Projekt();
        final Lieferant lieferant=new Lieferant();


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
        return Map.of();
    }

    @JobWorker(type = "anmeldedaten-uberprufen")
    public Map<String, Object> anmeldedatenUberprufen(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "antworten-laden1")
    public Map<String, Object> antwortenLaden1(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "antworten-speichern")
    public Map<String, Object> antwortenSpeichern(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "prufung-auf-vollstandigkeit2")
    public Map<String, Object> prufungAufVollstandigkeit2(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "lieferant-uber-unvollstandigkeit-informieren")
    public Map<String, Object> lieferantUberUnvollstandigkeitInformieren(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "nutzwertanalyse-durchfuhren-und-speichern")
    public Map<String, Object> nutzwertanalyseDurchfuhrenUndSpeichern(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "neues-ranking-liegt-vor")
    public Map<String, Object> neuesRankingLiegtVor(final ActivatedJob job) {
        return Map.of();
    }


    private String hash(String password) {
        return password;
    }
}
