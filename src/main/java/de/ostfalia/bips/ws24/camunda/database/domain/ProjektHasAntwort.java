package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

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
}
