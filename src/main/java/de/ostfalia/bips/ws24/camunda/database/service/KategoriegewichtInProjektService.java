package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.KategoriegewichtInProjektRepository;
import org.springframework.stereotype.Service;

@Service
public class KategoriegewichtInProjektService {
    private final KategoriegewichtInProjektRepository kategoriegewichtInProjektRepository;

    public KategoriegewichtInProjektService(KategoriegewichtInProjektRepository kategoriegewichtInProjektRepository) {
        this.kategoriegewichtInProjektRepository = kategoriegewichtInProjektRepository;
    }
}
