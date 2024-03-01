package com.fdlv.gmd.api.domain.forum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "forum_fiche")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"interventionActeurs"})
public class Forum implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ffo_id")
	private Long id;

	@Column(name = "ffo_num_forum", nullable = false, precision = 5, columnDefinition = "decimal(5, 0) NOT NULL COMMENT 'Numéro du Forum'")
	private BigDecimal numForum;

	@Column(name = "ffo_libelle", nullable = false, columnDefinition = "varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Libellé du Forum'")
	private String libelle;

	@Column(name = "ffo_reference", nullable = false, length = 15, columnDefinition = "varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Référence de la Fiche Forum composée de la concaténation de ffo_id-ffo_num_forum-ffo_cp-(3première lettres et deux dernières lettres de ffo_libelle).\r\nCette réréference est générée automatiquement et non modifiable par l\'utilsiateur (sauf changement d\'un de ces champs : champ de ffo_num_forum,  ffo_cp, ffo_libelle.)'")
	private String reference;

	// TODO: Samir's SQL seems to use a String here and is not linked to a typeForum
	// table? To be discussed and reverted as needed
	@Column(name = "ffo_type_forum", nullable = false, length = 10, columnDefinition = "varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Mini ou Grand'")
	private String typeForum;

	@Column(name = "ffo_slogan_forum", columnDefinition = "varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Slogan du Forum'")
	private String sloganForum;

	@Column(name = "ffo_dd_evt", nullable = false, columnDefinition = "datetime NOT NULL COMMENT 'Date Début Evènement'")
	private LocalDateTime dateDebutEvenement;

	@Column(name = "ffo_df_evt", columnDefinition = "datetime NULL DEFAULT NULL COMMENT 'Date Fin Evènement'")
	private LocalDateTime dateFinEvenement;

	@Column(name = "ffo_dd_resa", columnDefinition = "datetime NULL DEFAULT NULL COMMENT 'Date début réservation de la salle pour l\'Evènement'")
	private LocalDateTime dateDebutReservation;

	@Column(name = "ffo_df_resa", columnDefinition = "datetime NULL DEFAULT NULL COMMENT 'Date fin réservation de la salle pour l\'Evènement'")
	private LocalDateTime dateFinReservation;

	@Column(name = "ffo_date_ouverture", columnDefinition = "date NULL DEFAULT NULL COMMENT 'date Ouverture du Forum'")
	private LocalDate dateOuverture;

	@Column(name = "ffo_heure_ouverture", nullable = false, columnDefinition = "time(6) NOT NULL COMMENT 'Heure Ouverture Forum _ Evènement'")
	private LocalTime heureOuverture;

	@Column(name = "ffo_date_fermeture", columnDefinition = "date NULL DEFAULT NULL COMMENT 'Date Fermeture Forum _ Evènement'")
	private LocalDate dateFermeture;

	@Column(name = "ffo_heure_fermeture", columnDefinition = "time(6) NULL DEFAULT NULL COMMENT 'Heure Fermeture Forum _ Evènement'")
	private LocalTime heureFermeture;

//    Todo: This is not found in Samir's code - deleted, maybe after discussions with samir we can revert
//    @Column(name = "ffo_date_fin_logistique", columnDefinition = "COMMENT 'Date de fin de la logistique du forum = Date >= date de fermeture du forum'")
//    private LocalDateTime dateFinLogistique;

	@Column(name = "ffo_lieu_forum", length = 50, columnDefinition = "varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Lieu du Forum (Nom de salle, ...)'")
	private String lieuForum;

	@Column(name = "ffo_adresse_forum", length = 100, columnDefinition = "varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Adresse du Forum'")
	private String adresseForum;

	@Column(name = "ffo_cpl_adresse", columnDefinition = "varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Complément Adresse du Forum'")
	private String complementAdresseForum;

	@Column(name = "ffo_cp", length = 6, columnDefinition = "varchar(6) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Code Postal de la commune du Forum'")
	private String codePostal;

	@Column(name = "ffo_commune", columnDefinition = "varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Nom Commune'")
	private String commune;

	@Column(name = "ffo_desc_convention", length = 100, columnDefinition = "varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Description du document de la convention liée au Forum'")
	private String descriptionConvention;

	@Column(name = "ffo_convention_url", columnDefinition = "varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Lien du document de la convention'")
	private String conventionUrl;

	@Column(name = "ffo_valide", length = 1, columnDefinition = "varchar(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Valeur 1 = Actif / validé, 0 = Inactif / en attente de validation'")
	// Todo: String or enum instead of Boolean, because we might have many different
	// validation states: in progress, non validé that are unplanned right now
	private String isValid;

	// Je la trouve pas dans les bouts sql de samir
//    @Column(name = "ffo_suivi", length = 1, columnDefinition = "CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT '0' COMMENT 'Valeurs \r\n0 = en cours de mise en oeuvre (valeur par défaut), \r\n1 = avancement partiel avec certaines institutions, \r\n2 = avancement complet, \r\n3 = Forum validé / à organiser \r\n9 = Forum terminé '")
//    // Todo: Needs verification if it works
//    @Convert(converter = SuiviConverter.class)
//    private Suivi suivi;

	@Column(name = "ffo_plan_forum_url", columnDefinition = "varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'URL du plan du forum'")
	private String planForumUrl;

	@Column(name = "ffo_plan_salle", columnDefinition = "varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'URL du plan de la Salle'")
	private String planSalle;

	@Column(name = "ffo_commentaire", columnDefinition = "varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL")
	private String commentaire;

	// Reverse fields from other entities

//    @OneToMany(mappedBy = "forum")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "forum"}, allowSetters = true)
//    private Set<Document> documents = new HashSet<>();
//
//    @OneToMany(mappedBy = "forum")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "forum"}, allowSetters = true)
//    private Set<PlanningAction> planingActions= new HashSet<>();
//
@OneToMany(mappedBy = "forum")
@JsonIgnore()
private Set<InterventionActeur> interventionActeurs = new HashSet<>();
//    @OneToMany(mappedBy = "forum")
//	@JsonIgnoreProperties()
//	private Set<InterventionStand> interventionsStand = new HashSet<>();
//
//    @OneToMany(mappedBy = "structure")
//	@JsonIgnoreProperties()
//	private Set<InterventionStructure> interventionsStructure = new HashSet<>();

}
