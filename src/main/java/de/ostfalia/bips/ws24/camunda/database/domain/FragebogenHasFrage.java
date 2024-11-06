package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
}
