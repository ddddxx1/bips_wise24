package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.Fragebogen;
import de.ostfalia.bips.ws24.camunda.database.domain.Projekt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProjektRepository extends JpaRepository<Projekt, Integer>, JpaSpecificationExecutor<Projekt> {
    Projekt findProjektByIdProjekt(int id);

    @Query("SELECT COUNT(p) FROM Projekt p")
    Integer countAllRecords();

    @Query("SELECT p.fragebogen from Projekt p " +
            "WHERE p.idProjekt = :idProjekt")
    Fragebogen findFragebogenByProjektID(@Param("idProjekt") Integer idProjekt);

    @Query("SELECT p from Projekt p " +
            "WHERE p.idProjekt = :idProjekt")
    List<Projekt> findProjektByProjektID(@Param("idProjekt") Integer idProjekt);
}
