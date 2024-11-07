package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "fragebogen")
public class Fragebogen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fragebogen", nullable = false)
    private Integer idFragebogen;

    @Column(name = "beschreibung", nullable = false)
    private String beschreibung;

    public void setIdFragebogen(Integer idFragebogen) {
        this.idFragebogen = idFragebogen;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Integer getIdFragebogen() {
        return idFragebogen;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
