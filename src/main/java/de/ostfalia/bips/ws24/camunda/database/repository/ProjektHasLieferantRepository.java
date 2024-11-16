package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.ProjektHasLieferant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface ProjektHasLieferantRepository extends JpaRepository<ProjektHasLieferant, Integer>, JpaSpecificationExecutor<ProjektHasLieferant> {
    @Query("SELECT COUNT(p.score) > 0 FROM ProjektHasLieferant p WHERE p.id.projekt.idProjekt = :idProjekt AND p.id.lieferant.idLieferant = :idLieferant")
    boolean existScoreByProjectIdAndAntwortId(@Param("idProjekt") int idProjekt, @Param("idLieferant") int idLieferant);

    @Query("SELECT COUNT(p.rank) > 0 FROM ProjektHasLieferant p WHERE p.id.projekt.idProjekt = :idProjekt AND p.id.lieferant.idLieferant = :idLieferant")
    boolean existRankByProjectIdAndAntwortId(@Param("idProjekt") int idProjekt, @Param("idLieferant") int idLieferant);

    @Query("SELECT p.score FROM ProjektHasLieferant p WHERE p.id.projekt.idProjekt = :idProjekt AND p.id.lieferant.idLieferant = :idLieferant")
    float findScoreByProjectIdAndAntwortId(@Param("idProjekt") int idProjekt, @Param("idLieferant") int idLieferant);

    @Query("SELECT p.rank FROM ProjektHasLieferant p WHERE p.id.projekt.idProjekt = :idProjekt AND p.id.lieferant.idLieferant = :idLieferant")
    Integer findRankByProjectIdAndAntwortId(@Param("idProjekt") int idProjekt, @Param("idLieferant") int idLieferant);
}
