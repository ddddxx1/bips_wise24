package de.ostfalia.bips.ws24.camunda.database.repository;


import de.ostfalia.bips.ws24.camunda.database.domain.Lieferant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface LieferantRepository extends JpaRepository<Lieferant, Integer>, JpaSpecificationExecutor<Lieferant> {
    @Query("SELECT l.name FROM Lieferant l WHERE  l.idLieferant = :idLieferant")
    String findLieferantByIdLieferant( @Param("idLieferant") int idLieferant);
}
