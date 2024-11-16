package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "projekt_has_lieferant")
public class ProjektHasLieferant {
    @EmbeddedId
    private Id id;

    @Column(name = "score", nullable = true)
    private Float score;

    @Column(name = "rank", nullable = true)
    private Integer rank;

    public ProjektHasLieferant() {
        this.id = new Id();
    }

    public void setId(Id id) {
        this.id = id;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Id getId() {
        return id;
    }

    public Float getScore() {
        return score;
    }

    public Integer getRank() {
        return rank;
    }

    @Embeddable
    public static class Id implements Serializable {
        @ManyToOne(targetEntity = Projekt.class, optional = false)
        @JoinColumn(name = "id_projekt", referencedColumnName = "id_projekt", nullable = false)
        private Projekt projekt;

        @ManyToOne(targetEntity = Lieferant.class, optional = false)
        @JoinColumn(name = "id_lieferant", referencedColumnName = "id_lieferant", nullable = false)
        private Lieferant lieferant;

        public Id() {
        }

        public Id(Projekt projekt, Lieferant lieferant) {
            this.projekt = projekt;
            this.lieferant = lieferant;
        }

        public Projekt getProjekt() {
            return projekt;
        }

        public Lieferant getLieferant() {
            return lieferant;
        }

        public void setProjekt(Projekt projekt) {
            this.projekt = projekt;
        }

        public void setLieferant(Lieferant lieferant) {
            this.lieferant = lieferant;
        }

        @Override
        public String toString() {
            return "Id{" +
                    "projekt=" + projekt +
                    ", lieferant=" + lieferant +
                    '}';
        }
    }
}
