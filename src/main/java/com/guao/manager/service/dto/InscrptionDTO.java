package com.guao.manager.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.guao.manager.domain.Inscrption} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InscrptionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateinscription;

    private String commentaire;

    private Double tarifinscription;

    private ClasseDTO classe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateinscription() {
        return dateinscription;
    }

    public void setDateinscription(LocalDate dateinscription) {
        this.dateinscription = dateinscription;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Double getTarifinscription() {
        return tarifinscription;
    }

    public void setTarifinscription(Double tarifinscription) {
        this.tarifinscription = tarifinscription;
    }

    public ClasseDTO getClasse() {
        return classe;
    }

    public void setClasse(ClasseDTO classe) {
        this.classe = classe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InscrptionDTO)) {
            return false;
        }

        InscrptionDTO inscrptionDTO = (InscrptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inscrptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InscrptionDTO{" +
            "id=" + getId() +
            ", dateinscription='" + getDateinscription() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", tarifinscription=" + getTarifinscription() +
            ", classe=" + getClasse() +
            "}";
    }
}
