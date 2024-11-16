package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.FrageRepository;
import de.ostfalia.bips.ws24.camunda.database.repository.FragebogenHasFrageRepository;
import org.springframework.stereotype.Service;

@Service
public class FragebogenHasFrageService {
    private final FragebogenHasFrageRepository fragebogenHasFrageRepository;

    public FragebogenHasFrageService(FragebogenHasFrageRepository fragebogenHasFrageRepository) {
        this.fragebogenHasFrageRepository = fragebogenHasFrageRepository;
    }

    public FragebogenHasFrageRepository getRepository() {
        return fragebogenHasFrageRepository;
    }
}
