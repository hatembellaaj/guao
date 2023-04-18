package com.guao.manager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.guao.manager.domain.Professeur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfesseurDTO implements Serializable {

    private Long id;

    private String grade;

    private String specialite;

    private String typecontrat;

    private String annecontrat;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getTypecontrat() {
        return typecontrat;
    }

    public void setTypecontrat(String typecontrat) {
        this.typecontrat = typecontrat;
    }

    public String getAnnecontrat() {
        return annecontrat;
    }

    public void setAnnecontrat(String annecontrat) {
        this.annecontrat = annecontrat;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfesseurDTO)) {
            return false;
        }

        ProfesseurDTO professeurDTO = (ProfesseurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, professeurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfesseurDTO{" +
            "id=" + getId() +
            ", grade='" + getGrade() + "'" +
            ", specialite='" + getSpecialite() + "'" +
            ", typecontrat='" + getTypecontrat() + "'" +
            ", annecontrat='" + getAnnecontrat() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
