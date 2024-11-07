package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.FragebogenHasFrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FragebogenHasFrageRepository extends JpaRepository<FragebogenHasFrage, Integer>, JpaSpecificationExecutor<FragebogenHasFrage> {
}
