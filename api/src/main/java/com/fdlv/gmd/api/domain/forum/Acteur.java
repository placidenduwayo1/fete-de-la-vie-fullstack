package com.fdlv.gmd.api.domain.forum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "forum_acteur")
@DynamicUpdate
@DynamicInsert
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"interventionActeurs","responsableActor","createdByActor","structureId","forumRoleActeur"})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(exclude = {"createdByActor","responsableActor", "forumRoleActeur","structureId","interventionActeurs"})
public class Acteur implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fac_id", nullable = false, unique = true, columnDefinition = "bigint  COMMENT 'Id de l\'acteur du Forum'")
	private Long id;

	@Column(name = "fac_reference", length = 25, nullable = false)
	private String reference;

	@Column(name = "fac_login", length = 20)
	private String login;

	@Column(name = "fac_nom", nullable = false)
	private String name;

	@Column(name = "fac_prenom", nullable = false)
	private String lastName;

	@Column(name = "fac_password_hash", nullable = false, length = 100)
	private String passwordHash;

	@Column(name = "fac_created_by")
	private String createdBy;

	@Column(name = "fac_photo_url")
	private String photoUrl;

	@Column(name = "fac_num_tel_portable", nullable = false)
	@Length(min = 10, max = 21, message = "Le numéro de téléphone portable doit contenir entre 10 et 21 caractères.")
	private String numTelPortable;

	@Column(name = "fac_num_tel_bureau")
	@Length(max = 21, message = "Le numéro de téléphone bureau doit etre inferieur a 21 caractères.")
	private String numTelBureau;

	@Column(name = "fac_email", nullable = false)
	private String email;

	@Column(name = "fac_acteur_FDLV", length = 1)
	private String acteurFDLV;

	@Column(name = "fac_responsable", length = 1)
	private String responsable;

	@Column(name = "fac_creer_users", length = 1)
	private String creerUsers;

	@Column(name = "fac_benevole", length = 1)
	private String benevole;

	@Column(name = "fac_correspondant",length = 1)
	private String correspondant;


	@Column(name = "fac_service")
	private String service;

	@Column(name = "fac_commentaire")
	private String commentaire;

	@Column(name = "fac_date_debut_validite")
	private LocalDateTime dateDebutValidite;

	@Column(name = "fac_date_fin_validite")
	private LocalDateTime dateFinValidite;

	@Column(name = "fac_actif", length = 1)
	private String actif;

	@ManyToOne
	@JoinColumn(name = "fac_fac_fra_id", referencedColumnName = "fra_id", foreignKey = @ForeignKey(name = "fk_fac_fra_id"))
	private RoleActeur forumRoleActeur;

	@ManyToOne
	@JoinColumn(name = "fac_fse_id", referencedColumnName = "fse_id", foreignKey = @ForeignKey(name = "fk_fac_fse_id"))
	@JsonIgnoreProperties(value = {"contact"})
	private Structure structureId;

	@ManyToOne
	@JoinColumn(name = "fac_created_by_fac_id", referencedColumnName = "fac_id", foreignKey = @ForeignKey(name = "fk_fac_created_by_fac_id"))
	@JsonIgnoreProperties(value = {"createdByActor","responsableActor","interventionActeurs","structureId"})
	private Acteur createdByActor;

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "fac_fac_id_responsable", referencedColumnName = "fac_id", foreignKey = @ForeignKey(name = "fk_fac_resp_id"))
	@JsonIgnoreProperties(value = {"createdByActor","responsableActor","interventionActeurs","structureId"})
	private Acteur responsableActor;

	@OneToMany(mappedBy = "acteur")
	@JsonIgnoreProperties(value = { "acteur", "roleActeur", "structure" })
	private Set<InterventionActeur> interventionActeurs = new HashSet<>();

	@Override
	public String toString() {
		return "Acteur{" +
				"id=" + id +
				", reference='" + reference + '\'' +
				", login='" + login + '\'' +
				", name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				", passwordHash='" + passwordHash + '\'' +
				", createdBy='" + createdBy + '\'' +
				", photoUrl='" + photoUrl + '\'' +
				", numTelPortable='" + numTelPortable + '\'' +
				", numTelBureau='" + numTelBureau + '\'' +
				", email='" + email + '\'' +
				", acteurFDLV='" + acteurFDLV + '\'' +
				", responsable='" + responsable + '\'' +
				", creerUsers='" + creerUsers + '\'' +
				", benevole='" + benevole + '\'' +
				", correspondant='" + correspondant + '\'' +
				", service='" + service + '\'' +
				", commentaire='" + commentaire + '\'' +
				", dateDebutValidite=" + dateDebutValidite +
				", dateFinValidite=" + dateFinValidite +
				", actif='" + actif + '\'' +
				", forumRoleActeur=" + forumRoleActeur +
				", structureId=" + structureId +
				", createdByActor=" + createdByActor +
				", responsableActor=" + responsableActor +
				", interventionActeurs=" + interventionActeurs +
				'}';
	}
}