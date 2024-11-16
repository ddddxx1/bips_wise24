package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.ProjektHasLieferantRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjektHasLieferantService {
    private final ProjektHasLieferantRepository projektHasLieferantRepository;

    public ProjektHasLieferantService(ProjektHasLieferantRepository projektHasLieferantRepository) {
        this.projektHasLieferantRepository = projektHasLieferantRepository;
    }

    public float findScoreByProjectIdAndAntwortId(int idProjekt, int idLieferant) {
        return projektHasLieferantRepository.findScoreByProjectIdAndAntwortId(idProjekt, idLieferant);
    }

    public ProjektHasLieferantRepository getRepository() {
        return projektHasLieferantRepository;
    }
}
