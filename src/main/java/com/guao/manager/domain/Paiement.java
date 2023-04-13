package com.guao.manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guao.manager.domain.enumeration.emodepaiement;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Paiement.
 */
@Entity
@Table(name = "paiement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "modepaiement")
    private emodepaiement modepaiement;

    @Column(name = "numcheque")
    private String numcheque;

    @NotNull
    @Column(name = "datepaiement", nullable = false)
    private LocalDate datepaiement;

    @Column(name = "numrecu")
    private String numrecu;

    @Column(name = "idinscription")
    private String idinscription;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "classe" }, allowSetters = true)
    private Eleve eleve;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paiement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Paiement montant(Double montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public emodepaiement getModepaiement() {
        return this.modepaiement;
    }

    public Paiement modepaiement(emodepaiement modepaiement) {
        this.setModepaiement(modepaiement);
        return this;
    }

    public void setModepaiement(emodepaiement modepaiement) {
        this.modepaiement = modepaiement;
    }

    public String getNumcheque() {
        return this.numcheque;
    }

    public Paiement numcheque(String numcheque) {
        this.setNumcheque(numcheque);
        return this;
    }

    public void setNumcheque(String numcheque) {
        this.numcheque = numcheque;
    }

    public LocalDate getDatepaiement() {
        return this.datepaiement;
    }

    public Paiement datepaiement(LocalDate datepaiement) {
        this.setDatepaiement(datepaiement);
        return this;
    }

    public void setDatepaiement(LocalDate datepaiement) {
        this.datepaiement = datepaiement;
    }

    public String getNumrecu() {
        return this.numrecu;
    }

    public Paiement numrecu(String numrecu) {
        this.setNumrecu(numrecu);
        return this;
    }

    public void setNumrecu(String numrecu) {
        this.numrecu = numrecu;
    }

    public String getIdinscription() {
        return this.idinscription;
    }

    public Paiement idinscription(String idinscription) {
        this.setIdinscription(idinscription);
        return this;
    }

    public void setIdinscription(String idinscription) {
        this.idinscription = idinscription;
    }

    public Eleve getEleve() {
        return this.eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Paiement eleve(Eleve eleve) {
        this.setEleve(eleve);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiement)) {
            return false;
        }
        return id != null && id.equals(((Paiement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", modepaiement='" + getModepaiement() + "'" +
            ", numcheque='" + getNumcheque() + "'" +
            ", datepaiement='" + getDatepaiement() + "'" +
            ", numrecu='" + getNumrecu() + "'" +
            ", idinscription='" + getIdinscription() + "'" +
            "}";
    }
}
