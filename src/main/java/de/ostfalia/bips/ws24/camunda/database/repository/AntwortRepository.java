package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.Antwort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AntwortRepository extends JpaRepository<Antwort, Integer>, JpaSpecificationExecutor<Antwort> {

    @Query("SELECT a.frage.idFrage FROM Antwort a WHERE a.idAntwort = :idAntwort")
    Integer findidFrageByidAntwort(@Param("idAntwort") Integer idAntwort);

    @Query(value = "SELECT a  FROM Antwort  a WHERE a.frage.idFrage = :idFrage")
    List<Antwort> findAntwortIdsByFrageId(@Param("idFrage") Integer idFrage);

    @Query("SELECT a.antwortText FROM Antwort a WHERE a.idAntwort = :idAntwort")
    String findAntowrtTextByidAntwort(@Param("idAntwort") Integer idAntwort);

    @Query("SELECT a.punkte FROM Antwort a WHERE a.idAntwort = :idAntwort")
    int findPunktByidAntwort(@Param("idAntwort") Integer idAntwort);
}
