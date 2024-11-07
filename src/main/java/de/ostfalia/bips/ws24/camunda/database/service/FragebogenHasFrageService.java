package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.FrageRepository;
import org.springframework.stereotype.Service;

@Service
public class FragebogenHasFrageService {
    private final FrageRepository frageRepository;

    public FragebogenHasFrageService(FrageRepository frageRepository) {
        this.frageRepository = frageRepository;
    }
}
