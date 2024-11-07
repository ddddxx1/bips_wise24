package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.ProjektHasLieferantHasAntwort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


public interface ProjektHasLieferantHasAntwortRepository extends JpaRepository<ProjektHasLieferantHasAntwort, Integer>, JpaSpecificationExecutor<ProjektHasLieferantHasAntwort> {
}
