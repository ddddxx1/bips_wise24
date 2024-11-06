package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projekt")
public class Projekt {
    @Id
    @Column(name = "id_projekt", nullable = false)
    private Integer idProjekt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "komponente", nullable = false)
    private String komponente;

    public void setIdProjekt(Integer idProjekt) {
        this.idProjekt = idProjekt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKomponente(String komponente) {
        this.komponente = komponente;
    }

    public Integer getIdProjekt() {
        return idProjekt;
    }

    public String getName() {
        return name;
    }

    public String getKomponente() {
        return komponente;
    }
}
