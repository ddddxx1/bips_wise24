package de.ostfalia.bips.ws24.camunda.database.repository;


import de.ostfalia.bips.ws24.camunda.database.domain.KategoriegewichtInProjekt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KategoriegewichtInProjektRepository extends JpaRepository<KategoriegewichtInProjekt, Integer> {
}
