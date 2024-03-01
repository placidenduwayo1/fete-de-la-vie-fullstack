package com.fdlv.gmd.api.domain.forum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Intervention stand.
 */

@Entity
@Table(name = "forum_intervention_stand")
public class InterventionStand implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fsi_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fsiId;

	@ManyToOne
	@JoinColumn(name = "fsi_fac_id", foreignKey = @ForeignKey(name = "fk_fsi_fac_id", value = ConstraintMode.CONSTRAINT), nullable = false, referencedColumnName = "fac_id", columnDefinition = "bigint NOT NULL COMMENT 'id acteur'")
	private Acteur acteur;

	@ManyToOne
	@JoinColumn(name = "fsi_fra_id", foreignKey = @ForeignKey(name = "fk_fsi_fra_id", value = ConstraintMode.CONSTRAINT), nullable = false, referencedColumnName = "fra_id", columnDefinition = "bigint NOT NULL COMMENT 'Rôle de l\'acteur'")
	private RoleActeur roleActeur;

	@ManyToOne
	@JoinColumn(name = "fsi_ffo_id", foreignKey = @ForeignKey(name = "fk_fsi_ffo_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "ffo_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'id forum'")
	private Forum forum;

	@ManyToOne
	@JoinColumn(name = "fsi_fsd_id", foreignKey = @ForeignKey(name = "fk_fsi_fsd_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "fsd_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'id du Stand'")
	private Stand stand;

	@ManyToOne
	@JoinColumn(name = "fsi_fse_id", foreignKey = @ForeignKey(name = "fk_fsi_fse_id", value = ConstraintMode.CONSTRAINT), nullable = false, referencedColumnName = "fse_id", columnDefinition = "bigint NOT NULL COMMENT 'id structure de l\'acteur'")
	private Structure structure;

	@ManyToOne
	@JoinColumn(name = "fsi_fro_id", foreignKey = @ForeignKey(name = "fk_fsi_fro_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "fro_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'Id rôle Structure qui intervient'")
	private RoleStructure roleStructure;

	@ManyToOne
	@JoinColumn(name = "fsi_fsb_id", foreignKey = @ForeignKey(name = "fk_fsi_fsb_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "fsb_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'Id de la bannière du stand'")
	private StandBanniere standBanniere;

	@Column(name = "fsi_description", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Description de l\'intervention'")
    private String fsiDescription;

	@Column(name = "fsi_date_debut", columnDefinition = "date NOT NULL COMMENT 'date début validité'")
    private LocalDate fsiDateDebut;

	@Column(name = "fsi_date_fin", columnDefinition = "date NULL DEFAULT NULL COMMENT 'date fin de validité'")
    private LocalDate fsiDateFin;

    @Column(name = "fsi_nb_repas", precision = 3, scale = 0)
	private BigDecimal fsiNbRepas;

	@Column(name = "fsi_commentaire", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String fsiCommentaire;

   
    

	public Long getFsiId() {
		return fsiId;
	}

	public void setFsiId(Long fsiId) {
		this.fsiId = fsiId;
	}

	public String getFsiDescription() {
		return fsiDescription;
	}

	public void setFsiDescription(String fsiDescription) {
		this.fsiDescription = fsiDescription;
	}

	public LocalDate getFsiDateDebut() {
		return fsiDateDebut;
	}

	public void setFsiDateDebut(LocalDate fsiDateDebut) {
		this.fsiDateDebut = fsiDateDebut;
	}

	public LocalDate getFsiDateFin() {
		return fsiDateFin;
	}

	public void setFsiDateFin(LocalDate fsiDateFin) {
		this.fsiDateFin = fsiDateFin;
	}

	public BigDecimal getFsiNbRepas() {
		return fsiNbRepas;
	}

	public void setFsiNbRepas(BigDecimal fsiNbRepas) {
		this.fsiNbRepas = fsiNbRepas;
	}

	public String getFsiCommentaire() {
		return fsiCommentaire;
	}

	public void setFsiCommentaire(String fsiCommentaire) {
		this.fsiCommentaire = fsiCommentaire;
	}
	
	
	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public RoleStructure getRoleStructure() {
		return roleStructure;
	}

	public void setRoleStructure(RoleStructure roleStructure) {
		this.roleStructure = roleStructure;
	}

	public Acteur getActeur() {
		return acteur;
	}

	public void setActeur(Acteur acteur) {
		this.acteur = acteur;
	}

	public Stand getStand() {
		return stand;
	}

	public void setStand(Stand stand) {
		this.stand = stand;
	}
	
	public RoleActeur getRoleActeur() {
		return roleActeur;
	}

	public void setRoleActeur(RoleActeur roleActeur) {
		this.roleActeur = roleActeur;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public StandBanniere getStandBanniere() {
		return standBanniere;
	}

	public void setStandBanniere(StandBanniere standBanniere) {
		this.standBanniere = standBanniere;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return "InterventionStand{" +
				"fsiId=" + fsiId +
				", fsiDescription='" + fsiDescription + '\'' +
				", fsiDateDebut=" + fsiDateDebut +
				", fsiDateFin=" + fsiDateFin +
				", fsiNbRepas=" + fsiNbRepas +
				", fsiCommentaire='" + fsiCommentaire + '\'' +
				", structure=" + structure +
				", roleStructure=" + roleStructure +
				", acteur=" + acteur +
				", stand=" + stand +
				'}';
	}
}
