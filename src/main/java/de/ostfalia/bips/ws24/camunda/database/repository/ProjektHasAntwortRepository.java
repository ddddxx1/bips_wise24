package de.ostfalia.bips.ws24.camunda.database.repository;

import de.ostfalia.bips.ws24.camunda.database.domain.ProjektHasAntwort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface ProjektHasAntwortRepository extends JpaRepository<ProjektHasAntwort, Integer>, JpaSpecificationExecutor<ProjektHasAntwort> {

    @Query("SELECT COUNT(pa.ist_ko_kriterium) > 0 FROM ProjektHasAntwort pa WHERE pa.id.projekt.idProjekt = :idProjekt AND pa.id.antwort.idAntwort = :idAntwort")
    boolean existsByProjektIdAndAntwortId(@Param("idProjekt") int idProjekt, @Param("idAntwort") int idAntwort);
}
