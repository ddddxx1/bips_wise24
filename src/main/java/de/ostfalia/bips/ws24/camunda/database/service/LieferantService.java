package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.LieferantRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class LieferantService {
    private final LieferantRepository lieferantRepository;

    public LieferantService(LieferantRepository lieferantRepository) {
        this.lieferantRepository = lieferantRepository;
    }

    public LieferantRepository getRepository() {
        return lieferantRepository;
    }

    public String getLieferantNameById(int idLieferant) {
        return lieferantRepository.findLieferantByIdLieferant(idLieferant);
    }
}
