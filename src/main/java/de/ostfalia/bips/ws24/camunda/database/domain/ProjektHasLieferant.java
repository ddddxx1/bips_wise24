package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "projekt_has_lieferant")
public class ProjektHasLieferant {
    @EmbeddedId
    private Id id;

    @Column(name = "score", nullable = true)
    private Float score;

    @Column(name = "rank", nullable = true)
    private Integer rank;

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
}
