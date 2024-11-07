package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.ProjektHasLieferant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


public interface ProjektHasLieferantRepository extends JpaRepository<ProjektHasLieferant, Integer>, JpaSpecificationExecutor<ProjektHasLieferant> {
}
