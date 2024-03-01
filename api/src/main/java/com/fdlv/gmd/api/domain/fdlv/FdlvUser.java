package com.fdlv.gmd.api.domain.fdlv;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fdlv.gmd.api.domain.enumeration.Profil;

@Entity
@Table(name = "fdlv_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FdlvUser implements Serializable {

	private static final long serialVersionUID = -42336667889630650L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fus_id")
	private Long id;

	@Column(name = "fus_profil")
	@Convert(converter = ProfilConverter.class)
	private Profil profil;

	@Column(name = "fus_login", length = 50)
	private String login;

	@JsonIgnore
	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "fus_password_hash", length = 60, nullable = false)
	private String mdpHash;

	@Column(name = "fus_first_name", length = 50)
	private String prenom;

	@Column(name = "fus_last_name", length = 50)
	private String nom;

	@Column(name = "fus_structure", length = 50)
	private String structure;

	@Column(name = "fus_service", length = 50)
	private String service;

	@Column(name = "fus_email", length = 50)
	private String email;

	@Column(name = "fus_num_tel", length = 50)
	private String numTel;

	@Column(name = "fus_activated", columnDefinition = "BOOLEAN")
	private Boolean actif;

	@Column(name = "fus_date_debut")
	private LocalDate dateDebut;

	@Column(name = "fus_date_fin")
	private LocalDate dateFin;

	@Column(name = "fus_compte_permanent", columnDefinition = "BOOLEAN")
	private Boolean permanent;

	// In db, there exists a fus_activation_key and fus_lang_key
	// AFAIK, these are never used, nowhere. Only reset_key is useful
	// to reset a pwd
	@Column(name = "fus_reset_key", length = 50)
	private String resetKey;

	@Column(name = "fus_created_by", length = 50)
	private String creePar;

	@Column(name = "fus_created_date")
	private LocalDate dateCreation;

	@Column(name = "fus_reset_date")
	private Instant dateReset;

	@Column(name = "fus_last_modified_by", length = 50)
	private String modifiePar;

	@Column(name = "fus_last_modified_date")
	private LocalDate dateModif;

	@OneToMany(mappedBy = "fco")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnoreProperties(value = { "theme" }, allowSetters = true)
	private Set<ChoixOrganisateur> choixOrganisateur = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdpHash() {
		return mdpHash;
	}

	public void setMdpHash(String mdpHash) {
		this.mdpHash = mdpHash;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public Boolean getPermanent() {
		return permanent;
	}

	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	public String getResetKey() {
		return resetKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	public String getCreePar() {
		return creePar;
	}

	public void setCreePar(String creePar) {
		this.creePar = creePar;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Instant getDateReset() {
		return dateReset;
	}

	public void setDateReset(Instant dateReset) {
		this.dateReset = dateReset;
	}

	public String getModifiePar() {
		return modifiePar;
	}

	public void setModifiePar(String modifiePar) {
		this.modifiePar = modifiePar;
	}

	public LocalDate getDateModif() {
		return dateModif;
	}

	public void setDateModif(LocalDate dateModif) {
		this.dateModif = dateModif;
	}

	public Set<ChoixOrganisateur> getChoixOrganisateur() {
		return choixOrganisateur;
	}

	public void setChoixOrganisateur(Set<ChoixOrganisateur> choixOrganisateur) {
		this.choixOrganisateur = choixOrganisateur;
	}

}
