package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "katagorie")
public class Kategorie {
    @Id
    @Column(name = "id_kategorie", nullable = false)
    private Integer idKategorie;

    @Column(name = "beschreibung", nullable = false)
    private String beschreibung;

    public void setIdKatagorie(Integer idKategorie) {
        this.idKategorie = idKategorie;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Integer getIdKategorie() {
        return idKategorie;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public String toString() {
        return "Kategorie{" +
                "idKategorie=" + idKategorie +
                ", beschreibung='" + beschreibung + '\'' +
                '}';
    }
}
