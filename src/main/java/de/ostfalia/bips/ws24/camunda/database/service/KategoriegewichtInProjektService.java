package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.KategoriegewichtInProjektRepository;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KategoriegewichtInProjektService {
    private final KategoriegewichtInProjektRepository kategoriegewichtInProjektRepository;

    public KategoriegewichtInProjektService(KategoriegewichtInProjektRepository kategoriegewichtInProjektRepository) {
        this.kategoriegewichtInProjektRepository = kategoriegewichtInProjektRepository;
    }

    public KategoriegewichtInProjektRepository getRepository() {
        return kategoriegewichtInProjektRepository;
    }

    public float findGewichtByIdProjektAndIdKategorie(int idProjekt, int idKategorie) {
        return kategoriegewichtInProjektRepository.findGewichtByIdProjektAndIdKategorie(idProjekt, idKategorie);
    }
}
