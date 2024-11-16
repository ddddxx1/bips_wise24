package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.AntwortRepository;
import org.springframework.stereotype.Service;

@Service
public class AntwortService {
    private final AntwortRepository antwortRepository;

    public AntwortService(AntwortRepository antwortRepository) {
        this.antwortRepository = antwortRepository;
    }

    public String findAntowrtTextByidAntwort(Integer idAntwort) {
        return antwortRepository.findAntwortTextByIdAntwort(idAntwort);
    }

    public Integer findidFrageByidAntwort(Integer idAntwort) {
        return antwortRepository.findidFrageByidAntwort(idAntwort);
    }

    public int findPunktByidAntwort(Integer idAntwort) {
        return antwortRepository.findPunktByidAntwort(idAntwort);
    }
}
