package com.guao.manager.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Classe.
 */
@Entity
@Table(name = "classe")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    //@SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nomclasse", nullable = false)
    private String nomclasse;

    @Column(name = "nombreeleves")
    private Integer nombreeleves;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Classe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomclasse() {
        return this.nomclasse;
    }

    public Classe nomclasse(String nomclasse) {
        this.setNomclasse(nomclasse);
        return this;
    }

    public void setNomclasse(String nomclasse) {
        this.nomclasse = nomclasse;
    }

    public Integer getNombreeleves() {
        return this.nombreeleves;
    }

    public Classe nombreeleves(Integer nombreeleves) {
        this.setNombreeleves(nombreeleves);
        return this;
    }

    public void setNombreeleves(Integer nombreeleves) {
        this.nombreeleves = nombreeleves;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classe)) {
            return false;
        }
        return id != null && id.equals(((Classe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classe{" +
            "id=" + getId() +
            ", nomclasse='" + getNomclasse() + "'" +
            ", nombreeleves=" + getNombreeleves() +
            "}";
    }
}
