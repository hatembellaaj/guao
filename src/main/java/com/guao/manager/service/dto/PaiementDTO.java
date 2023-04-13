package com.guao.manager.service.dto;

import com.guao.manager.domain.enumeration.emodepaiement;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.guao.manager.domain.Paiement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaiementDTO implements Serializable {

    private Long id;

    @NotNull
    private Double montant;

    private emodepaiement modepaiement;

    private String numcheque;

    @NotNull
    private LocalDate datepaiement;

    private String numrecu;

    private String idinscription;

    private EleveDTO eleve;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public emodepaiement getModepaiement() {
        return modepaiement;
    }

    public void setModepaiement(emodepaiement modepaiement) {
        this.modepaiement = modepaiement;
    }

    public String getNumcheque() {
        return numcheque;
    }

    public void setNumcheque(String numcheque) {
        this.numcheque = numcheque;
    }

    public LocalDate getDatepaiement() {
        return datepaiement;
    }

    public void setDatepaiement(LocalDate datepaiement) {
        this.datepaiement = datepaiement;
    }

    public String getNumrecu() {
        return numrecu;
    }

    public void setNumrecu(String numrecu) {
        this.numrecu = numrecu;
    }

    public String getIdinscription() {
        return idinscription;
    }

    public void setIdinscription(String idinscription) {
        this.idinscription = idinscription;
    }

    public EleveDTO getEleve() {
        return eleve;
    }

    public void setEleve(EleveDTO eleve) {
        this.eleve = eleve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaiementDTO)) {
            return false;
        }

        PaiementDTO paiementDTO = (PaiementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paiementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", modepaiement='" + getModepaiement() + "'" +
            ", numcheque='" + getNumcheque() + "'" +
            ", datepaiement='" + getDatepaiement() + "'" +
            ", numrecu='" + getNumrecu() + "'" +
            ", idinscription='" + getIdinscription() + "'" +
            ", eleve=" + getEleve() +
            "}";
    }
}
