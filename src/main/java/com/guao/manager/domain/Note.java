package com.guao.manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "noteexamen", nullable = false)
    private Double noteexamen;

    @Column(name = "remarque")
    private String remarque;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "classe" }, allowSetters = true)
    private Eleve eleve;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "matiere", "classe" }, allowSetters = true)
    private Examen examen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Note id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNoteexamen() {
        return this.noteexamen;
    }

    public Note noteexamen(Double noteexamen) {
        this.setNoteexamen(noteexamen);
        return this;
    }

    public void setNoteexamen(Double noteexamen) {
        this.noteexamen = noteexamen;
    }

    public String getRemarque() {
        return this.remarque;
    }

    public Note remarque(String remarque) {
        this.setRemarque(remarque);
        return this;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Eleve getEleve() {
        return this.eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Note eleve(Eleve eleve) {
        this.setEleve(eleve);
        return this;
    }

    public Examen getExamen() {
        return this.examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Note examen(Examen examen) {
        this.setExamen(examen);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }
        return id != null && id.equals(((Note) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Note{" +
            "id=" + getId() +
            ", noteexamen=" + getNoteexamen() +
            ", remarque='" + getRemarque() + "'" +
            "}";
    }
}
