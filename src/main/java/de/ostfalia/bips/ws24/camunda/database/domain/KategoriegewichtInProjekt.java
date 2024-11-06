package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

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
}
