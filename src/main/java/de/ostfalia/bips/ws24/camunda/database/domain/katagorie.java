package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "katagorie")
public class katagorie {
    @Id
    @Column(name = "id_katagorie", nullable = false)
    private Integer idKatagorie;

    @Column(name = "beschreibung", nullable = false)
    private String beschreibung;

    public void setIdKatagorie(Integer idKatagorie) {
        this.idKatagorie = idKatagorie;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Integer getIdKatagorie() {
        return idKatagorie;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
