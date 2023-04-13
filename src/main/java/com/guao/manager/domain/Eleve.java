package com.guao.manager.domain;

import com.guao.manager.domain.enumeration.esexe;
import com.guao.manager.domain.enumeration.etypedepaiement;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Eleve.
 */
@Entity
@Table(name = "eleve")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Eleve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;

    @Enumerated(EnumType.STRING)
    @Column(name = "typedepaiement")
    private etypedepaiement typedepaiement;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private esexe sexe;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "datedenaissance")
    private LocalDate datedenaissance;

    @Column(name = "email")
    private String email;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "codepostale")
    private String codepostale;

    @Column(name = "ville")
    private String ville;

    @Column(name = "pays")
    private String pays;

    @ManyToOne
    private Classe classe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Eleve id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Eleve matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public etypedepaiement getTypedepaiement() {
        return this.typedepaiement;
    }

    public Eleve typedepaiement(etypedepaiement typedepaiement) {
        this.setTypedepaiement(typedepaiement);
        return this;
    }

    public void setTypedepaiement(etypedepaiement typedepaiement) {
        this.typedepaiement = typedepaiement;
    }

    public esexe getSexe() {
        return this.sexe;
    }

    public Eleve sexe(esexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(esexe sexe) {
        this.sexe = sexe;
    }

    public String getNom() {
        return this.nom;
    }

    public Eleve nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Eleve prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDatedenaissance() {
        return this.datedenaissance;
    }

    public Eleve datedenaissance(LocalDate datedenaissance) {
        this.setDatedenaissance(datedenaissance);
        return this;
    }

    public void setDatedenaissance(LocalDate datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public String getEmail() {
        return this.email;
    }

    public Eleve email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Eleve adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Eleve telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCodepostale() {
        return this.codepostale;
    }

    public Eleve codepostale(String codepostale) {
        this.setCodepostale(codepostale);
        return this;
    }

    public void setCodepostale(String codepostale) {
        this.codepostale = codepostale;
    }

    public String getVille() {
        return this.ville;
    }

    public Eleve ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return this.pays;
    }

    public Eleve pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Classe getClasse() {
        return this.classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Eleve classe(Classe classe) {
        this.setClasse(classe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eleve)) {
            return false;
        }
        return id != null && id.equals(((Eleve) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Eleve{" +
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
            "}";
    }
}
