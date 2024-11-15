package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.domain.Projekt;
import de.ostfalia.bips.ws24.camunda.database.repository.ProjektRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class ProjektService {
    private final ProjektRepository projektRepository;

    public ProjektService(ProjektRepository projektRepository) {
        this.projektRepository = projektRepository;
    }

    public ProjektRepository getRepository() {
        return projektRepository;
    }

    public Projekt getProjektById(int id) {
        return projektRepository.findProjektByIdProjekt(id);
    }
}
