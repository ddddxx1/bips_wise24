package de.ostfalia.bips.ws24.camunda.database.repository;


import de.ostfalia.bips.ws24.camunda.database.domain.Lieferant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LieferantRepository extends JpaRepository<Lieferant, Integer> {
}
