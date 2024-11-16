package de.ostfalia.bips.ws24.camunda;

import java.util.*;
import java.util.stream.Collectors;

import de.ostfalia.bips.ws24.camunda.database.domain.Fragebogen;
import de.ostfalia.bips.ws24.camunda.database.domain.FragebogenHasFrage;
import de.ostfalia.bips.ws24.camunda.database.domain.Projekt;
import de.ostfalia.bips.ws24.camunda.database.domain.ProjektHasAntwort;
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


    public Worker(UserRepository userRepository, AntwortService antwortService, FragebogenHasFrageService fragebogenHasFrageService, FragebogenService fragebogenService, FrageService frageService, KategoriegewichtInProjektRepository kategoriegewichtInProjektRepository, KategorieService kategorieService, LieferantService lieferantService, ProjektHasAntwortService projektHasAntwortService, ProjektHasLieferantHasAntwortService projektHasLieferantHasAntwortService, ProjektHasLieferantService projektHasLieferantService, ProjektService projektService) {
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

        final List<Option<Integer>> Projekt = projektService.getRepository().findProjektByIdProjekt(newProjektId).stream()
                .map(e -> new Option<>(e.getName(), e.getIdProjekt()))
                .collect(Collectors.toList());
//        todo
        return Map.of("projektData", projekt);
    }

    @JobWorker(type = "projekt-speichern1")
    public Map<String, Object> projektSpeichern1(final ActivatedJob job) {
        return Map.of();
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
        return Map.of();
    }

    @JobWorker(type = "lieferant-uberprufen")
    public Map<String, Object> lieferantUberprufen(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "daten-vom-lieferanten-laden")
    public Map<String, Object> datenVomLieferantenLaden(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "projekt-speichern2")
    public Map<String, Object> projektSpeichern2(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "lieferant-zum-projekt-hinzufugen")
    public Map<String, Object> lieferantZumProjektHinzufugen(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "prufung-auf-vollstandigkeit1")
    public Map<String, Object> prufungAufVollstandigkeit1(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "email-senden")
    public Map<String, Object> emailSenden(final ActivatedJob job) {
        return Map.of();
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
