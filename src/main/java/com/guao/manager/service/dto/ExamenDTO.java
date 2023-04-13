package com.guao.manager.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.guao.manager.domain.Examen} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExamenDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer numexamen;

    private Double pourcentage;

    private Boolean valide;

    private LocalDate date;

    private Integer duree;

    private MatiereDTO matiere;

    private ClasseDTO classe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumexamen() {
        return numexamen;
    }

    public void setNumexamen(Integer numexamen) {
        this.numexamen = numexamen;
    }

    public Double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public MatiereDTO getMatiere() {
        return matiere;
    }

    public void setMatiere(MatiereDTO matiere) {
        this.matiere = matiere;
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
        if (!(o instanceof ExamenDTO)) {
            return false;
        }

        ExamenDTO examenDTO = (ExamenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, examenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamenDTO{" +
            "id=" + getId() +
            ", numexamen=" + getNumexamen() +
            ", pourcentage=" + getPourcentage() +
            ", valide='" + getValide() + "'" +
            ", date='" + getDate() + "'" +
            ", duree=" + getDuree() +
            ", matiere=" + getMatiere() +
            ", classe=" + getClasse() +
            "}";
    }
}
