package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.Frage;
import de.ostfalia.bips.ws24.camunda.database.domain.FragebogenHasFrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FragebogenHasFrageRepository extends JpaRepository<FragebogenHasFrage, Integer>, JpaSpecificationExecutor<FragebogenHasFrage> {
    @Query("select f.id.frage from FragebogenHasFrage f where f.id.fragebogen.idFragebogen = :idFragebogen ORDER BY f.id.frage.idFrage")
    List<Frage> findAllFrageOderById(@Param("idFragebogen") int idFragebogen);
}
