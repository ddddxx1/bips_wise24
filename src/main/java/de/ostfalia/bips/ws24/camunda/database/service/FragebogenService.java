package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.FragebogenRepository;
import org.springframework.stereotype.Service;

@Service
public class FragebogenService {
    private final FragebogenRepository fragebogenRepository;

    public FragebogenService(FragebogenRepository fragebogenRepository) {
        this.fragebogenRepository = fragebogenRepository;
    }

    public FragebogenRepository getRepository() {
        return fragebogenRepository;
    }
}
