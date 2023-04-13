package com.guao.manager.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Matiere.
 */
@Entity
@Table(name = "matiere")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Matiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nommatiere", nullable = false)
    private String nommatiere;

    @Column(name = "coefficient")
    private Integer coefficient;

    @Column(name = "nombreheure")
    private Integer nombreheure;

    @Column(name = "nombreexamen")
    private Integer nombreexamen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Matiere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNommatiere() {
        return this.nommatiere;
    }

    public Matiere nommatiere(String nommatiere) {
        this.setNommatiere(nommatiere);
        return this;
    }

    public void setNommatiere(String nommatiere) {
        this.nommatiere = nommatiere;
    }

    public Integer getCoefficient() {
        return this.coefficient;
    }

    public Matiere coefficient(Integer coefficient) {
        this.setCoefficient(coefficient);
        return this;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public Integer getNombreheure() {
        return this.nombreheure;
    }

    public Matiere nombreheure(Integer nombreheure) {
        this.setNombreheure(nombreheure);
        return this;
    }

    public void setNombreheure(Integer nombreheure) {
        this.nombreheure = nombreheure;
    }

    public Integer getNombreexamen() {
        return this.nombreexamen;
    }

    public Matiere nombreexamen(Integer nombreexamen) {
        this.setNombreexamen(nombreexamen);
        return this;
    }

    public void setNombreexamen(Integer nombreexamen) {
        this.nombreexamen = nombreexamen;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matiere)) {
            return false;
        }
        return id != null && id.equals(((Matiere) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Matiere{" +
            "id=" + getId() +
            ", nommatiere='" + getNommatiere() + "'" +
            ", coefficient=" + getCoefficient() +
            ", nombreheure=" + getNombreheure() +
            ", nombreexamen=" + getNombreexamen() +
            "}";
    }
}
