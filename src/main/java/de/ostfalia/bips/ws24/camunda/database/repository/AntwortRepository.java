package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.Antwort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface AntwortRepository extends JpaRepository<Antwort, Integer>, JpaSpecificationExecutor<Antwort> {
    @Query("SELECT a.antwortText FROM Antwort a WHERE a.idAntwort = :idAntwort")
    String findAntwortTextByIdAntwort(@Param("idAntwort") Integer idAntwort);

    @Query("SELECT a.frage.idFrage FROM Antwort a WHERE a.idAntwort = :idAntwort")
    Integer findidFrageByidAntwort(@Param("idAntwort") Integer idAntwort);

    @Query("SELECT a.punkte FROM Antwort a WHERE a.idAntwort = :idAntwort")
    int findPunktByidAntwort(@Param("idAntwort") Integer idAntwort);
}
