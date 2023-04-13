package com.guao.manager.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.guao.manager.domain.Absence} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AbsenceDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateabsence;

    private Boolean justifie;

    private String commentaire;

    private EleveDTO eleve;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateabsence() {
        return dateabsence;
    }

    public void setDateabsence(LocalDate dateabsence) {
        this.dateabsence = dateabsence;
    }

    public Boolean getJustifie() {
        return justifie;
    }

    public void setJustifie(Boolean justifie) {
        this.justifie = justifie;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
        if (!(o instanceof AbsenceDTO)) {
            return false;
        }

        AbsenceDTO absenceDTO = (AbsenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, absenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbsenceDTO{" +
            "id=" + getId() +
            ", dateabsence='" + getDateabsence() + "'" +
            ", justifie='" + getJustifie() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", eleve=" + getEleve() +
            "}";
    }
}
