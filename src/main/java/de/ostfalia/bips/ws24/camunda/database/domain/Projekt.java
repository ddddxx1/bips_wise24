package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Transactional
@Table(name = "projekt")
public class Projekt {
    @Id
    @Column(name = "id_projekt", nullable = false)
    private Integer idProjekt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "komponente", nullable = false)
    private String komponente;

    @ManyToOne(targetEntity = Fragebogen.class, optional = false)
    @JoinColumn(name = "id_fragebogen", referencedColumnName = "id_fragebogen", nullable = false)
    private Fragebogen fragebogen;


    public void setIdProjekt(Integer idProjekt) {
        this.idProjekt = idProjekt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKomponente(String komponente) {
        this.komponente = komponente;
    }

    public Integer getIdProjekt() {
        return idProjekt;
    }

    public String getName() {
        return name;
    }

    public String getKomponente() {
        return komponente;
    }

    public Fragebogen getFragebogen() {
        return fragebogen;
    }

    public void setFragebogen(Fragebogen fragebogen) {
        this.fragebogen = fragebogen;
    }

    @Override
    public String toString() {
        return "Projekt{" +
                "idProjekt=" + idProjekt +
                ", name='" + name + '\'' +
                ", komponente='" + komponente + '\'' +
                ", fragebogen=" + fragebogen +
                '}';
    }

}
