package de.ostfalia.bips.ws24.camunda;

import java.util.Map;
import java.util.Objects;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;

import de.ostfalia.bips.ws24.camunda.database.repository.UserRepository;

@Component
public class Worker {
    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private final UserRepository userRepository;

    public Worker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @JobWorker(type = "hello-world")
//    public Map<String, Object> hello(@Variable(name = "username") String username) {
//        LOGGER.info("Hello, World: {}", username);
//        return Map.of("hello-world", true);
//    }

    @JobWorker(type = "login")
    public Map<String, Object> login(@Variable(name = "username") String username, @Variable(name = "password") String password) {
        return Map.of("login", userRepository.findByUsername(username)
                .filter(e -> Objects.equals(hash(password), e.getPassword()))
                .isPresent());
    }

    @JobWorker(type = "projekte-laden")
    public Map<String, Object> projekteLaden(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "fragebogen-laden")
    public Map<String, Object> fragebogenLaden(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "daten-zum-fragebogen-laden")
    public Map<String, Object> datenZumFragebogenLaden(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "projekt-speichern1")
    public Map<String, Object> projektSpeichern1(final ActivatedJob job) {
        return Map.of();
    }

    @JobWorker(type = "daten-zu-projekt-laden")
    public Map<String, Object> datenZumProjektLaden(final ActivatedJob job) {
        return Map.of();
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
