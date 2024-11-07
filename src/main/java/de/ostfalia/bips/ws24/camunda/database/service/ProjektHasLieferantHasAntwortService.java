package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.ProjektHasLieferantHasAntwortRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjektHasLieferantHasAntwortService {
    private final ProjektHasLieferantHasAntwortRepository projektHasLieferantHasAntwortRepository;

    public ProjektHasLieferantHasAntwortService(ProjektHasLieferantHasAntwortRepository projektHasLieferantHasAntwortRepository) {
        this.projektHasLieferantHasAntwortRepository = projektHasLieferantHasAntwortRepository;
    }
}
