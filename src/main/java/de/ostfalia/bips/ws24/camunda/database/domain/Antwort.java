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

    @ManyToOne(targetEntity = Frage.class, optional = false)
    @JoinColumn(name = "id_frage", referencedColumnName = "id_frage", nullable = false)
    private Frage frage;


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

    @Override
    public String toString() {
        return "Antwort{" +
                "idAntwort=" + idAntwort +
                ", antwortText='" + antwortText + '\'' +
                ", punkte=" + punkte +
                '}';
    }
}
