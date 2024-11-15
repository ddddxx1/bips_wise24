package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "fragebogen_has_frage")
public class FragebogenHasFrage {
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
        @ManyToOne(targetEntity = Fragebogen.class, optional = false)
        @JoinColumn(name = "id_fragebogen", referencedColumnName = "id_fragebogen", nullable = false)
        private Fragebogen fragebogen;

        @ManyToOne(targetEntity = Frage.class, optional = false)
        @JoinColumn(name = "id_frage", referencedColumnName = "id_frage", nullable = false)
        private Frage frage;

        public Id() {
        }

        public Id(Fragebogen fragebogen, Frage frage) {
            this.fragebogen = fragebogen;
            this.frage = frage;
        }

        public Fragebogen getFragebogen() {
            return fragebogen;
        }

        public Frage getFrage() {
            return frage;
        }

        public void setFragebogen(Fragebogen fragebogen) {
            this.fragebogen = fragebogen;
        }

        public void setFrage(Frage frage) {
            this.frage = frage;
        }

        @Override
        public String toString() {
            return "Id{" +
                    "fragebogen=" + fragebogen +
                    ", frage=" + frage +
                    '}';
        }
    }
}
