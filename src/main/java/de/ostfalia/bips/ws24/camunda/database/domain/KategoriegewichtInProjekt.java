package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "kategoriegewicht_in_projekt")
public class KategoriegewichtInProjekt {
    @EmbeddedId
    private Id id;

    @Column(name = "gewicht", nullable = false)
    private float gewicht;

    public void setId(Id id) {
        this.id = id;
    }

    public void setGewicht(float gewicht) {
        this.gewicht = gewicht;
    }

    public Id getId() {
        return id;
    }

    public float getGewicht() {
        return gewicht;
    }

    @Embeddable
    public static class Id implements Serializable {
        @ManyToOne(targetEntity = Kategorie.class, optional = false)
        @JoinColumn(name = "id_kategorie", referencedColumnName = "id_kategorie", nullable = false)
        private Kategorie kategorie;

        @ManyToOne(targetEntity = Projekt.class, optional = false)
        @JoinColumn(name = "id_projekt", referencedColumnName = "id_projekt", nullable = false)
        private Projekt projekt;

        public Id() {
        }

        public Id(Kategorie kategorie, Projekt projekt) {
            this.kategorie = kategorie;
            this.projekt = projekt;
        }

        public Kategorie getKategorie() {
            return kategorie;
        }

        public Projekt getProjekt() {
            return projekt;
        }

        public void setKategorie(Kategorie kategorie) {
            this.kategorie = kategorie;
        }

        public void setProjekt(Projekt projekt) {
            this.projekt = projekt;
        }
    }
}
