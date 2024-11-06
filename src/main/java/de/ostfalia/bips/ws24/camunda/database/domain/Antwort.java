package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Antwort {

    @Id
    @Column(name = "id_antwort", nullable = false)
    private Integer idAntwort;

    @Column(name = "antworttext", nullable = false)
    private String antwortText;

    @Column(name = "punkte", nullable = false)
    private Integer punkte;


    public void setIdAntwort(Integer idAntwort) {
        this.idAntwort = idAntwort;
    }

    public void setAntwortText(String antwortText) {
        this.antwortText = antwortText;
    }

    public void setPunkte(Integer punkte) {
        this.punkte = punkte;
    }

    public Integer getIdAntwort() {
        return idAntwort;
    }

    public String getAntwortText() {
        return antwortText;
    }

    public Integer getPunkte() {
        return punkte;
    }
}
