package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.Frage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface FrageRepository extends JpaRepository<Frage, Integer>, JpaSpecificationExecutor<Frage> {

    @Query("SELECT f.frageText FROM Frage f WHERE f.idFrage = :idFrage")
    String findFrageTextByIdFrage(@Param("idFrage") Integer idFrage);

    @Query("SELECT f.kategorie.idKategorie FROM Frage f WHERE f.idFrage = :idFrage")
    int findKategorieIdByIdFrage(@Param("idFrage") Integer idFrage);
}
