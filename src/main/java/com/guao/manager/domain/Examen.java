package com.guao.manager.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Examen.
 */
@Entity
@Table(name = "examen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "numexamen", nullable = false)
    private Integer numexamen;

    @Column(name = "pourcentage")
    private Double pourcentage;

    @Column(name = "valide")
    private Boolean valide;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "duree")
    private Integer duree;

    @ManyToOne(optional = false)
    @NotNull
    private Matiere matiere;

    @ManyToOne(optional = false)
    @NotNull
    private Classe classe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Examen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumexamen() {
        return this.numexamen;
    }

    public Examen numexamen(Integer numexamen) {
        this.setNumexamen(numexamen);
        return this;
    }

    public void setNumexamen(Integer numexamen) {
        this.numexamen = numexamen;
    }

    public Double getPourcentage() {
        return this.pourcentage;
    }

    public Examen pourcentage(Double pourcentage) {
        this.setPourcentage(pourcentage);
        return this;
    }

    public void setPourcentage(Double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Boolean getValide() {
        return this.valide;
    }

    public Examen valide(Boolean valide) {
        this.setValide(valide);
        return this;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Examen date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDuree() {
        return this.duree;
    }

    public Examen duree(Integer duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Matiere getMatiere() {
        return this.matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Examen matiere(Matiere matiere) {
        this.setMatiere(matiere);
        return this;
    }

    public Classe getClasse() {
        return this.classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Examen classe(Classe classe) {
        this.setClasse(classe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examen)) {
            return false;
        }
        return id != null && id.equals(((Examen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Examen{" +
            "id=" + getId() +
            ", numexamen=" + getNumexamen() +
            ", pourcentage=" + getPourcentage() +
            ", valide='" + getValide() + "'" +
            ", date='" + getDate() + "'" +
            ", duree=" + getDuree() +
            "}";
    }
}
