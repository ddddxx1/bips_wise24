package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.ProjektHasLieferantRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjektHasLieferantService {
    private final ProjektHasLieferantRepository projektHasLieferantRepository;

    public ProjektHasLieferantService(ProjektHasLieferantRepository projektHasLieferantRepository) {
        this.projektHasLieferantRepository = projektHasLieferantRepository;
    }

    public boolean existScoreByProjectIdAndAntwortId(int idProjekt, int idLieferant) {
        return projektHasLieferantRepository.existScoreByProjectIdAndAntwortId(idProjekt, idLieferant);
    }

    public boolean existRankByProjectIdAndAntwortId(int idProjekt, int idLieferant) {
        return projektHasLieferantRepository.existRankByProjectIdAndAntwortId(idProjekt, idLieferant);
    }

    public ProjektHasLieferantRepository getRepository() {
        return projektHasLieferantRepository;
    }

    public float findScoreByProjectIdAndAntwortId(int idProjekt, int idLieferant) {
        return projektHasLieferantRepository.findScoreByProjectIdAndAntwortId(idProjekt, idLieferant);
    }

    public Integer findRankByProjectIdAndAntwortId(int idProjekt, int idLieferant) {
        return projektHasLieferantRepository.findRankByProjectIdAndAntwortId(idProjekt, idLieferant);
    }
}
