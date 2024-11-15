package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.Projekt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface ProjektRepository extends JpaRepository<Projekt, Integer>, JpaSpecificationExecutor<Projekt> {
    Projekt findProjektByIdProjekt(int id);

    @Query("SELECT COUNT(p) FROM Projekt p")
    Integer countAllRecords();
}
