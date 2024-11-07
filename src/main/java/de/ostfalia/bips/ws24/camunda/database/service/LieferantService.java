package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.LieferantRepository;
import org.springframework.stereotype.Service;

@Service
public class LieferantService {
    private final LieferantRepository lieferantRepository;

    public LieferantService(LieferantRepository lieferantRepository) {
        this.lieferantRepository = lieferantRepository;
    }
}
