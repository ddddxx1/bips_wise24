package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.FrageRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class FrageService {
    private final FrageRepository frageRepository;

    public FrageService(FrageRepository frageRepository) {
        this.frageRepository = frageRepository;
    }

    public FrageRepository getRepository() {
        return frageRepository;
    }

    public String findFrageTextByIdFrage(Integer idFrage) {
        return frageRepository.findFrageTextByIdFrage(idFrage);
    }

    public int findKategorieIdByIdFrage(Integer IdFrage) {
        return frageRepository.findKategorieIdByIdFrage(IdFrage);
    }
}
