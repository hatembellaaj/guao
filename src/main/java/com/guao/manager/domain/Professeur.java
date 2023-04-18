package com.guao.manager.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Professeur.
 */
@Entity
@Table(name = "professeur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Professeur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "grade")
    private String grade;

    @Column(name = "specialite")
    private String specialite;

    @Column(name = "typecontrat")
    private String typecontrat;

    @Column(name = "annecontrat")
    private String annecontrat;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Professeur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrade() {
        return this.grade;
    }

    public Professeur grade(String grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSpecialite() {
        return this.specialite;
    }

    public Professeur specialite(String specialite) {
        this.setSpecialite(specialite);
        return this;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getTypecontrat() {
        return this.typecontrat;
    }

    public Professeur typecontrat(String typecontrat) {
        this.setTypecontrat(typecontrat);
        return this;
    }

    public void setTypecontrat(String typecontrat) {
        this.typecontrat = typecontrat;
    }

    public String getAnnecontrat() {
        return this.annecontrat;
    }

    public Professeur annecontrat(String annecontrat) {
        this.setAnnecontrat(annecontrat);
        return this;
    }

    public void setAnnecontrat(String annecontrat) {
        this.annecontrat = annecontrat;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Professeur user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professeur)) {
            return false;
        }
        return id != null && id.equals(((Professeur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Professeur{" +
            "id=" + getId() +
            ", grade='" + getGrade() + "'" +
            ", specialite='" + getSpecialite() + "'" +
            ", typecontrat='" + getTypecontrat() + "'" +
            ", annecontrat='" + getAnnecontrat() + "'" +
            "}";
    }
}
