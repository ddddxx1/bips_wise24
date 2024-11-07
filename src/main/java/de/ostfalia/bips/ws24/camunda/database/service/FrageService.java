package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.FrageRepository;
import org.springframework.stereotype.Service;

@Service
public class FrageService {
    private final FrageRepository frageRepository;

    public FrageService(FrageRepository frageRepository) {
        this.frageRepository = frageRepository;
    }
}
