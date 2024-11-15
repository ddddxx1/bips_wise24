package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "projekt_has_antwort")
public class ProjektHasAntwort {
    @EmbeddedId
    private Id id;

    @Column(name = "ist_ko_kriterium", nullable = false)
    private boolean istKoKriterium;

    public void setId(Id id) {
        this.id = id;
    }

    public void setIstKoKriterium(boolean istKoKriterium) {
        this.istKoKriterium = istKoKriterium;
    }

    public Id getId() {
        return id;
    }

    public boolean isIstKoKriterium() {
        return istKoKriterium;
    }

    @Embeddable
    public static class Id implements Serializable {
        @ManyToOne(targetEntity = Projekt.class, optional = false)
        @JoinColumn(name = "id_projekt", referencedColumnName = "id_projekt", nullable = false)
        private Projekt projekt;

        @ManyToOne(targetEntity = Antwort.class, optional = false)
        @JoinColumn(name = "id_antwort", referencedColumnName = "id_antwort", nullable = false)
        private Antwort antwort;

        @ManyToOne(targetEntity = Frage.class, optional = false)
        @JoinColumn(name = "id_frage", referencedColumnName = "id_frage", nullable = false)
        private Frage frage;

        public Id() {
        }

        public Id(Projekt projekt, Antwort antwort, Frage frage) {
            this.projekt = projekt;
            this.antwort = antwort;
            this.frage = frage;
        }

        public Projekt getProjekt() {
            return projekt;
        }

        public Antwort getAntwort() {
            return antwort;
        }

        public Frage getFrage() {
            return frage;
        }

        public void setProjekt(Projekt projekt) {
            this.projekt = projekt;
        }

        public void setAntwort(Antwort antwort) {
            this.antwort = antwort;
        }

        public void setFrage(Frage frage) {
            this.frage = frage;
        }

        @Override
        public String toString() {
            return "Id{" +
                    "projekt=" + projekt +
                    ", antwort=" + antwort +
                    ", frage=" + frage +
                    '}';
        }
    }
}
