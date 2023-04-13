package com.guao.manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Absence.
 */
@Entity
@Table(name = "absence")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Absence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "dateabsence", nullable = false)
    private LocalDate dateabsence;

    @Column(name = "justifie")
    private Boolean justifie;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "classe" }, allowSetters = true)
    private Eleve eleve;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Absence id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateabsence() {
        return this.dateabsence;
    }

    public Absence dateabsence(LocalDate dateabsence) {
        this.setDateabsence(dateabsence);
        return this;
    }

    public void setDateabsence(LocalDate dateabsence) {
        this.dateabsence = dateabsence;
    }

    public Boolean getJustifie() {
        return this.justifie;
    }

    public Absence justifie(Boolean justifie) {
        this.setJustifie(justifie);
        return this;
    }

    public void setJustifie(Boolean justifie) {
        this.justifie = justifie;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Absence commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Eleve getEleve() {
        return this.eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Absence eleve(Eleve eleve) {
        this.setEleve(eleve);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Absence)) {
            return false;
        }
        return id != null && id.equals(((Absence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Absence{" +
            "id=" + getId() +
            ", dateabsence='" + getDateabsence() + "'" +
            ", justifie='" + getJustifie() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
