package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "projekt_has_lieferant_has_antwort")
public class ProjektHasLieferantHasAntwort {
    @EmbeddedId
    private Id id;

    public void setId(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }

    @Embeddable
    public static class Id implements Serializable {
        @ManyToOne(targetEntity = Projekt.class, optional = false)
        @JoinColumn(name = "id_projekt", referencedColumnName = "id_projekt", nullable = false)
        private Projekt projekt;

        @ManyToOne(targetEntity = Lieferant.class, optional = false)
        @JoinColumn(name = "id_lieferant", referencedColumnName = "id_lieferant", nullable = false)
        private Lieferant lieferant;

        @ManyToOne(targetEntity = Frage.class, optional = false)
        @JoinColumn(name = "id_frage", referencedColumnName = "id_frage", nullable = false)
        private Frage frage;

        @ManyToOne(targetEntity = Antwort.class, optional = false)
        @JoinColumn(name = "id_antwort", referencedColumnName = "id_antwort", nullable = false)
        private Antwort antwort;

        public Id() {
        }

        public Id(Projekt projekt, Lieferant lieferant, Frage frage, Antwort antwort) {
            this.projekt = projekt;
            this.lieferant = lieferant;
            this.frage = frage;
            this.antwort = antwort;
        }

        public Projekt getProjekt() {
            return projekt;
        }

        public void setProjekt(Projekt projekt) {
            this.projekt = projekt;
        }

        public Lieferant getLieferant() {
            return lieferant;
        }

        public void setLieferant(Lieferant lieferant) {
            this.lieferant = lieferant;
        }

        public Frage getFrage() {
            return frage;
        }

        public void setFrage(Frage frage) {
            this.frage = frage;
        }

        public Antwort getAntwort() {
            return antwort;
        }

        public void setAntwort(Antwort antwort) {
            this.antwort = antwort;
        }

        @Override
        public String toString() {
            return "Id{" +
                    "projekt=" + projekt +
                    ", lieferant=" + lieferant +
                    ", frage=" + frage +
                    ", antwort=" + antwort +
                    '}';
        }
    }
}
