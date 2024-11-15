package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.annotation.TypeAlias;

@Entity
@Table(name = "lieferant")
public class Lieferant {
    @Id
    @Column(name = "id_lieferant", nullable = false)
    private Integer idLieferant;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "passwort", nullable = false)
    private String passwort;

    public void setIdLieferant(Integer idLieferant) {
        this.idLieferant = idLieferant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Integer getIdLieferant() {
        return idLieferant;
    }

    public String getName() {
        return name;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPasswort() {
        return passwort;
    }

    @Override
    public String toString() {
        return "Lieferant{" +
                "idLieferant=" + idLieferant +
                ", name='" + name + '\'' +
                ", adresse='" + adresse + '\'' +
                ", passwort='" + passwort + '\'' +
                '}';
    }
}
