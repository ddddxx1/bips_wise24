package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.ProjektRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjektService {
    private final ProjektRepository projektRepository;

    public ProjektService(ProjektRepository projektRepository) {
        this.projektRepository = projektRepository;
    }
}
