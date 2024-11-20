package de.ostfalia.bips.ws24.camunda.database.repository;


import de.ostfalia.bips.ws24.camunda.database.domain.KategoriegewichtInProjekt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface KategoriegewichtInProjektRepository extends JpaRepository<KategoriegewichtInProjekt, Integer>, JpaSpecificationExecutor<KategoriegewichtInProjekt> {

    @Query("SELECT kgip.gewicht FROM KategoriegewichtInProjekt kgip WHERE kgip.id.projekt.idProjekt = :idProjekt AND kgip.id.kategorie.idKategorie = :idKategorie")
    float findGewichtByIdProjektAndIdKategorie(@Param("idProjekt") Integer idProjekt, @Param("idKategorie") Integer idKategorie);

    @Modifying
    @Query("update KategoriegewichtInProjekt k set k.gewicht=:gewicht where k.id.projekt.idProjekt=:idProjekt AND k.id.kategorie.idKategorie=:idKategorie")
    void setGewicht(@Param("idProjekt")Integer idProjekt,@Param("idKategorie") Integer idKategorie, @Param("gewicht") Double gewicht);
}
