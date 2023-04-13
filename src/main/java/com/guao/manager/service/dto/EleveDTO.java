package com.guao.manager.service.dto;

import com.guao.manager.domain.enumeration.esexe;
import com.guao.manager.domain.enumeration.etypedepaiement;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.guao.manager.domain.Eleve} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EleveDTO implements Serializable {

    private Long id;

    @NotNull
    private String matricule;

    private etypedepaiement typedepaiement;

    private esexe sexe;

    @NotNull
    private String nom;

    private String prenom;

    private LocalDate datedenaissance;

    private String email;

    private String adresse;

    private String telephone;

    private String codepostale;

    private String ville;

    private String pays;

    private ClasseDTO classe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public etypedepaiement getTypedepaiement() {
        return typedepaiement;
    }

    public void setTypedepaiement(etypedepaiement typedepaiement) {
        this.typedepaiement = typedepaiement;
    }

    public esexe getSexe() {
        return sexe;
    }

    public void setSexe(esexe sexe) {
        this.sexe = sexe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDatedenaissance() {
        return datedenaissance;
    }

    public void setDatedenaissance(LocalDate datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCodepostale() {
        return codepostale;
    }

    public void setCodepostale(String codepostale) {
        this.codepostale = codepostale;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
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
        if (!(o instanceof EleveDTO)) {
            return false;
        }

        EleveDTO eleveDTO = (EleveDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eleveDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EleveDTO{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", typedepaiement='" + getTypedepaiement() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", datedenaissance='" + getDatedenaissance() + "'" +
            ", email='" + getEmail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", codepostale='" + getCodepostale() + "'" +
            ", ville='" + getVille() + "'" +
            ", pays='" + getPays() + "'" +
            ", classe=" + getClasse() +
            "}";
    }
}
