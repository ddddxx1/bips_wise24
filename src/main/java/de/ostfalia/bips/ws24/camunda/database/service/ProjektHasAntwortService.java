package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.ProjektHasAntwortRepository;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class ProjektHasAntwortService {
    private final ProjektHasAntwortRepository projektHasAntwortRepository;

    public ProjektHasAntwortService(ProjektHasAntwortRepository projektHasAntwortRepository) {
        this.projektHasAntwortRepository = projektHasAntwortRepository;
    }

    public ProjektHasAntwortRepository getRepository() {
        return projektHasAntwortRepository;
    }

    public boolean existsByProjektIdAndAntwortId(int idProjekt, int idAntwort) {
        return projektHasAntwortRepository.existsByProjektIdAndAntwortId(idProjekt,idAntwort);
    }
}
