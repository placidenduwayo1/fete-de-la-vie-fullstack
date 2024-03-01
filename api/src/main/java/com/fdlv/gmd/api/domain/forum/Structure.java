package com.fdlv.gmd.api.domain.forum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(exclude = {"contact"})
@ToString(exclude = {"contact"})
@Table(name="forum_structure")
public class Structure implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fse_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fse_code", length = 10, columnDefinition = "varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Code Structure _ Organsation'")
	private String code;

	@Column(name = "fse_libelle", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Nom _ Lbellé Structue'")
	private String libelle;

	@Column(name = "fse_reference", length = 25, columnDefinition = "varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Référence de l\'acteur composée de la concaténation de fse_id-fse_code_structure_fse_cp_commune-(5 premières lettres de fse_libelle_structure).\r\nCette réréference est générée automatiquement et non modifiable par l\'utilsiateur (sauf changement d\'un de ces champs : champ de ffo_num_forum,  ffo_cp, ffo_libelle.)'")
	private String reference;

	@Column(name = "fse_niveau_responsabilite", length = 1, columnDefinition = "varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Le Niveau de responsabilité définit le type de communication pour les interventions et des actions à effectuer au sein du Forum\r\n1 = Action à diffuser uniquement en interne FDLV\r\n2 = Action à diffusion restreinte auprès de certaines structures/acteurs de niveau 2 (structure financier du Forum)\r\n3 = Action à diffusion restreinte auprès de certaines structures/acteurs de niveau 3 (structure intervenant dans l’organisation d’un Forum)\r\n4 = Action à diffusion restreinte auprès de certaines structures/acteurs de niveaux 2 et 3 \r\n5 = Action à diffusion uniquement aux acteurs intervenant au sein du Forum et aux structures/acteurs de niveau 2 et 3\r\n9  = Action à diffuser au niveau tout public\r\n'")
	private Long niveauResponsabilite;

	@Column(name = "fse_logo_description", length = 50, columnDefinition = "varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Description Logo de la Structue'")
	private String logoDescription;

	@Column(name = "fse_logo_url", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Lien du Logo de la Structure'")
	private String logoUrl;

	@Column(name = "fse_charte_description", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Description de la charte de la structure (obligatoire si FDLV)'")
	private String charteDescription;

	@Column(name = "fse_charte_url", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'URL de la charte de la structure (obligatoire si FDLV)'")
	private String charteUrl;

	@Column(name = "fse_adresse_01", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
	private String adresse01;

	@Column(name = "fse_adresse_02", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
	private String adresse02;

	@Column(name = "fse_cp", length = 5, columnDefinition = "varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Code Postal '")
	private String cp;

	@Column(name = "fse_commune", length = 50, columnDefinition = "varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
	private String commune;

	@ManyToOne
	@JoinColumn(name = "fse_fac_id_contact", foreignKey = @ForeignKey(name = "fk_fse_fac_id_contact", value = ConstraintMode.CONSTRAINT),
			referencedColumnName = "fac_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'Id contact de l\'acteur'")
	@JsonIgnoreProperties(value = {"structureId","interventionActeurs","createdByActor","responsableActor"})
	private Acteur contact;

	@Column(name = "fse_tel_accueil_structure", length = 15, columnDefinition = "varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Num Tel de l\'accueil de la Structure'")
	private String telAccueilStructure;

	@Column(name = "fse_email_accueil_structure", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'email de contact de la structure'")
	private String emailAccueilStructure;

	@Column(name = "fse_commentaire", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
	private String commentaire;

	@Column(name = "fse_convention_description", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Description de la convention de la structure UNIQUEMENT si structure =  FDLV'")
	private String conventionDescription;

	@Column(name = "fse_convention_url", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'URL de la convention de la structure UNIQUEMENT si structure =  FDLV'")
	private String conventionUrl;
	// Direction Inverse
	
//	@OneToMany(mappedBy = "structure")
//	@JsonIgnoreProperties()
//	private Set<InterventionStructure> interventionsActeur = new HashSet<>();
//
//	@OneToMany(mappedBy = "structure")
//	@JsonIgnoreProperties()
//	private Set<InterventionStructure> interventionsStructure = new HashSet<>();
//
//	@OneToMany(mappedBy = "structure")
//	@JsonIgnoreProperties()
//	private Set<InterventionStand> interventionsStand = new HashSet<>();
//
//	@OneToMany(mappedBy = "structure")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "structure"}, allowSetters = true)
//    private Set<Document> documents = new HashSet<>();
//
//	@OneToMany(mappedBy = "structure")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "planingActions"}, allowSetters = true)
//    private Set<Document> planingActions = new HashSet<>();


	@Override
	public String toString() {
		return "Structure{" +
				"id=" + id +
				", code='" + code + '\'' +
				", libelle='" + libelle + '\'' +
				", reference='" + reference + '\'' +
				", niveauResponsabilite='" + niveauResponsabilite + '\'' +
				", logoDescription='" + logoDescription + '\'' +
				", logoUrl='" + logoUrl + '\'' +
				", charteDescription='" + charteDescription + '\'' +
				", charteUrl='" + charteUrl + '\'' +
				", adresse01='" + adresse01 + '\'' +
				", adresse02='" + adresse02 + '\'' +
				", cp='" + cp + '\'' +
				", commune='" + commune + '\'' +
				", contact=" + contact +
				", telAccueilStructure='" + telAccueilStructure + '\'' +
				", emailAccueilStructure='" + emailAccueilStructure + '\'' +
				", commentaire='" + commentaire + '\'' +
				", conventionDescription='" + conventionDescription + '\'' +
				", conventionUrl='" + conventionUrl + '\'' +
				'}';
	}
}
