package com.guao.manager.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Inscrption.
 */
@Entity
@Table(name = "inscrption")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inscrption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "dateinscription", nullable = false)
    private LocalDate dateinscription;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "tarifinscription")
    private Double tarifinscription;

    @ManyToOne(optional = false)
    @NotNull
    private Classe classe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inscrption id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateinscription() {
        return this.dateinscription;
    }

    public Inscrption dateinscription(LocalDate dateinscription) {
        this.setDateinscription(dateinscription);
        return this;
    }

    public void setDateinscription(LocalDate dateinscription) {
        this.dateinscription = dateinscription;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Inscrption commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Double getTarifinscription() {
        return this.tarifinscription;
    }

    public Inscrption tarifinscription(Double tarifinscription) {
        this.setTarifinscription(tarifinscription);
        return this;
    }

    public void setTarifinscription(Double tarifinscription) {
        this.tarifinscription = tarifinscription;
    }

    public Classe getClasse() {
        return this.classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Inscrption classe(Classe classe) {
        this.setClasse(classe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inscrption)) {
            return false;
        }
        return id != null && id.equals(((Inscrption) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inscrption{" +
            "id=" + getId() +
            ", dateinscription='" + getDateinscription() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", tarifinscription=" + getTarifinscription() +
            "}";
    }
}
