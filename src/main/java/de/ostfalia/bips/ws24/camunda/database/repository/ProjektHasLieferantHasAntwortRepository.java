package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.Antwort;
import de.ostfalia.bips.ws24.camunda.database.domain.ProjektHasLieferantHasAntwort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProjektHasLieferantHasAntwortRepository extends JpaRepository<ProjektHasLieferantHasAntwort, Integer>, JpaSpecificationExecutor<ProjektHasLieferantHasAntwort> {

    @Query("SELECT COUNT(p) > 0 FROM ProjektHasLieferantHasAntwort p WHERE p.id.projekt.idProjekt = :idProjekt AND p.id.lieferant.idLieferant = :idLieferant")
    boolean existsByProjektIdAndLieferantId(@Param("idProjekt") int idProjekt, @Param("idLieferant") int idLieferant);

    @Query("SELECT p.id.frage.idFrage FROM ProjektHasLieferantHasAntwort p WHERE p.id.projekt.idProjekt = :idProjekt AND p.id.lieferant.idLieferant = :idLieferant")
    List<Integer> findFrageIdsByProjektAndLieferant(@Param("idProjekt") int idProjekt, @Param("idLieferant") int idLieferant);

    @Query("SELECT p.id.antwort.idAntwort FROM ProjektHasLieferantHasAntwort p WHERE p.id.projekt.idProjekt = :idProjekt AND p.id.lieferant.idLieferant = :idLieferant")
    List<Integer> findAntwortIdsByProjektAndLieferant(@Param("idProjekt") int idProjekt, @Param("idLieferant") int idLieferant);

    @Query("SELECT DISTINCT p.id.lieferant.idLieferant FROM ProjektHasLieferantHasAntwort p WHERE p.id.projekt.idProjekt = :idProjekt")
    List<Integer> findlLieferantIdsByProjekt(@Param("idProjekt") int idProjekt);

    @Query("SELECT p.id.antwort from ProjektHasLieferantHasAntwort p " +
            "WHERE p.id.projekt.idProjekt = :idProjekt AND p.id.lieferant.idLieferant = :idLieferant AND p.id.frage.idFrage = :idFrage")
    Antwort findAntwortByProjektIDLieferanterIDFrageID(@Param("idProjekt") Integer idProjekt, @Param("idLieferant") Integer idLieferant, @Param("idFrage") Integer idFrage);}
