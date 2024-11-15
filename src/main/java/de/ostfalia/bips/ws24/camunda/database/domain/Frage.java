package de.ostfalia.bips.ws24.camunda.database.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "frage")
public class Frage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_frage", nullable = false)
    private Integer idFrage;

    @Column(name = "fragetext", nullable = false)
    private String frageText;


    public Integer getIdFrage() {
        return idFrage;
    }

    public String getFrageText() {
        return frageText;
    }

    public void setIdFrage(Integer idFrage) {
        this.idFrage = idFrage;
    }

    public void setFrageText(String frageText) {
        this.frageText = frageText;
    }

    @Override
    public String toString() {
        return "Frage{" +
                "idFrage=" + idFrage +
                ", frageText='" + frageText + '\'' +
                '}';
    }
}
