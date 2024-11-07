package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.KategorieRepository;
import org.springframework.stereotype.Service;

@Service
public class KategorieService {
    private final KategorieRepository kategorieRepository;

    public KategorieService(KategorieRepository kategorieRepository) {
        this.kategorieRepository = kategorieRepository;
    }
}
