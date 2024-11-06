package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projekt_has_lieferant_has_antwort")
public class ProjektHasLieferantHasAntwort {
    @EmbeddedId
    private Id id;
}
