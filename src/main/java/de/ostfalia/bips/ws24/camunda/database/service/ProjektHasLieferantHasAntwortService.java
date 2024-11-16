package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.ProjektHasLieferantHasAntwortRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjektHasLieferantHasAntwortService {
    private final ProjektHasLieferantHasAntwortRepository projektHasLieferantHasAntwortRepository;

    public ProjektHasLieferantHasAntwortService(ProjektHasLieferantHasAntwortRepository projektHasLieferantHasAntwortRepository) {
        this.projektHasLieferantHasAntwortRepository = projektHasLieferantHasAntwortRepository;
    }

    public boolean isLieferantInProjekt(int idProjekt, int idLieferant) {
        return projektHasLieferantHasAntwortRepository.existsByProjektIdAndLieferantId(idProjekt, idLieferant);
    }

    public List<Integer> findFrageIdsByProjektAndLieferant(int idProjekt, int idLieferant) {
        return projektHasLieferantHasAntwortRepository.findFrageIdsByProjektAndLieferant(idProjekt, idLieferant);
    }

    public List<Integer> findAntwortIdsByProjektAndLieferant(int idProjekt, int idLieferant) {
        return projektHasLieferantHasAntwortRepository.findAntwortIdsByProjektAndLieferant(idProjekt, idLieferant);
    }

    public List<Integer> findlLieferantIdsByProjekt(int idProjekt) {
        return projektHasLieferantHasAntwortRepository.findlLieferantIdsByProjekt(idProjekt);
    }

    public ProjektHasLieferantHasAntwortRepository getRepository() {
        return projektHasLieferantHasAntwortRepository;
    }

}
