package com.fdlv.gmd.api.domain.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

/**
 * Cette classe décrit l'intervention d'un acteur.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(exclude = {"acteur", "forum","roleActeur","structure"})
@ToString(exclude = {"acteur","forum","roleActeur","structure"})
@Table(name = "forum_intervention_acteur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InterventionActeur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fin_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long finId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fin_fac_id", nullable = false, foreignKey = @ForeignKey(name = "fk_fin_fac_id", foreignKeyDefinition = "FOREIGN KEY (`fin_fac_id`) REFERENCES `forum_acteur` (`fac_id`) ON DELETE RESTRICT ON UPDATE RESTRICT"))
	@JsonIgnoreProperties(value = {"interventionActeurs","createdByActor","structureId","responsableActor"})
	private Acteur acteur;

	@ManyToOne
	@JsonIgnore()
	@JoinColumn(name = "fin_ffo_id",referencedColumnName = "ffo_id", foreignKey = @ForeignKey(name = "fk_fin_ffo_id", foreignKeyDefinition = "FOREIGN KEY (`fin_ffo_id`) REFERENCES `forum_fiche` (`ffo_id`) ON DELETE RESTRICT ON UPDATE RESTRICT"))
	private Forum forum;

	@ManyToOne
	@JoinColumn(name = "fin_fra_id", nullable = false, foreignKey = @ForeignKey(name = "fk_fin_fra_id", foreignKeyDefinition = "FOREIGN KEY (`fin_fra_id`) REFERENCES `forum_role_acteur` (`fra_id`) ON DELETE RESTRICT ON UPDATE RESTRICT"))
	private RoleActeur roleActeur;

	@ManyToOne
	@JoinColumn(name = "fin_fse_id", nullable = false, foreignKey = @ForeignKey(name = "fk_fin_fse_id", foreignKeyDefinition = "FOREIGN KEY (`fin_fse_id`) REFERENCES `forum_structure` (`fse_id`) ON DELETE RESTRICT ON UPDATE RESTRICT"))
	@JsonIgnoreProperties(value = {"contact"})
	private Structure structure;

	@Column(name = "fin_description", nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Description de l'intervention'")
	private String finDescription;

	@Column(name = "fin_date_debut", nullable = false, columnDefinition = "DATE NOT NULL COMMENT 'date début validité'")
	private LocalDate finDateDebut;

	@Column(name = "fin_date_fin", columnDefinition = "DATE NULL DEFAULT NULL COMMENT 'date fin de validité'")
	private LocalDate finDateFin;

	@Column(name = "fin_commentaire", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
	private String finCommentaire;

	@Column(name = "fin_charte_asso_signe", length = 1, columnDefinition = "VARCHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Oui / Non : Obligatoire pour tout intervenant dans le cadre du Forum'")
	private String finCharteAssoSigne;

	@Column(name = "fin_charte_asso_signe_date", columnDefinition = "DATETIME NULL DEFAULT NULL COMMENT 'Date Signature de la Charte de lAssociation'")
	private LocalDate finCharteAssoSigneDate;

	@Override
	public String toString() {
		return "InterventionActeur{" +
				"finId=" + finId +
				", acteur=" + acteur +
				", forum=" + forum +
				", roleActeur=" + roleActeur +
				", structure=" + structure +
				", finDescription='" + finDescription + '\'' +
				", finDateDebut=" + finDateDebut +
				", finDateFin=" + finDateFin +
				", finCommentaire='" + finCommentaire + '\'' +
				", finCharteAssoSigne='" + finCharteAssoSigne + '\'' +
				", finCharteAssoSigneDate=" + finCharteAssoSigneDate +
				'}';
	}
}
