package com.guao.manager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.guao.manager.domain.Matiere} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatiereDTO implements Serializable {

    private Long id;

    @NotNull
    private String nommatiere;

    private Integer coefficient;

    private Integer nombreheure;

    private Integer nombreexamen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNommatiere() {
        return nommatiere;
    }

    public void setNommatiere(String nommatiere) {
        this.nommatiere = nommatiere;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public Integer getNombreheure() {
        return nombreheure;
    }

    public void setNombreheure(Integer nombreheure) {
        this.nombreheure = nombreheure;
    }

    public Integer getNombreexamen() {
        return nombreexamen;
    }

    public void setNombreexamen(Integer nombreexamen) {
        this.nombreexamen = nombreexamen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatiereDTO)) {
            return false;
        }

        MatiereDTO matiereDTO = (MatiereDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matiereDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatiereDTO{" +
            "id=" + getId() +
            ", nommatiere='" + getNommatiere() + "'" +
            ", coefficient=" + getCoefficient() +
            ", nombreheure=" + getNombreheure() +
            ", nombreexamen=" + getNombreexamen() +
            "}";
    }
}
