package com.fdlv.gmd.api.domain.forum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "forum_planning_action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlanningAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fpa_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn (name="fpa_ffo_id", nullable=false, foreignKey = @ForeignKey(name="fk_fpa_ffo_id"))
    private Forum forum;

    @Column(name="fpa_type_organisation", nullable=false, columnDefinition="VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Type de l'organisation (Installation, Décoration, jour Evt, Accueil, ..)'")
    private String typeOrganisation;

    @Column(name="fpa_reference", length=15, columnDefinition="VARCHAR(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Référence du planning composée de fpa_date_action (sous format YYYYMMJJ)-fpa_ffo_id-fpa-fse_id-fpa_id-fpa_fac_id'")
    private String reference;

    @ManyToOne
    @JoinColumn (name="fpa_fac_id", foreignKey = @ForeignKey(name="fk_fma_fac_id"), columnDefinition = "COMMENT 'Acteur responsable de l'action'")
    private Acteur acteur;

    @ManyToOne
    @JoinColumn (name="fpa_fse_id", foreignKey = @ForeignKey(name="fk_fma_fse_id"), columnDefinition = "COMMENT 'Structure responsable de l'action'")
    private Structure structure;

    @Column(name="fpa_action", nullable=false, columnDefinition="VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Action à effectuer (Ouverture portes, installation, ..)'")
    private String action;

    @ManyToOne
    @JoinColumn (name="fpa_fse_id_responsable", foreignKey = @ForeignKey(name="fk_fpa_fse_id_responsable"), columnDefinition = "COMMENT 'Structure concernée par l'action à effectuer'")
    private Structure structureResponsable;

    @ManyToOne
    @JoinColumn (name="fpa_fac_id_responsable", foreignKey = @ForeignKey(name="fk_fpa_fac_id_responsable"), columnDefinition = "COMMENT 'Acteur concerné par l'action à effectuer'")
    private Acteur acteurResponsable;

    @Column(name="fpa_niveau_diffusion", length=1, columnDefinition="VARCHAR(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Niveau Diffusion :\r\n1 = Action à diffuser uniquement en interne FDLV (Acteurs Niveau de responsabilité = 1)\r\n2 = Action à diffusion restreinte auprès de certaines structures/acteurs de niveau 2 (Acteurs Niveau de responsabilité 1 et 2)\r\n3 = Action à diffusion restreinte auprès de certaines structures/acteurs de niveau 3 (Acteurs Niveau de responsabilité 1, 2 et 3)\r\n4 = Action à diffusion restreinte auprès de certaines structures/acteurs de niveaux 4 (Acteurs Niveau de responsabilité 4)\r\n5 = Action à diffusion uniquement aux acteurs intervenant au sein du Forum et aux structures/acteurs de niveau 5 (Acteurs Niveau de responsabilité 5)\r\n\r\n9  = Action à diffuser au niveau tout public\r\n\r\n'")
    private String niveauDiffusion;

    @Column(name="fpa_retro_action", columnDefinition="VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Période du Retroplanning de l'action :\r\nExemple : \r\nM-1 : mail à envoyer aux bénévoles FDLV pour ....\r\nJ-21 : Rappel : Affichage magasins\r\n(J-10) rappel : Tractage toutes boîtes\r\nS-1 :Rappel vérification matériel spécifique  Installation - décoration\r\n'")
    private String retroAction;

    @Column(name="fpa_date_action", columnDefinition="DATE NULL DEFAULT NULL")
    private LocalDate dateAction;

    @Column(name="fpa_heure_debut", columnDefinition="TIME(6) NULL DEFAULT NULL COMMENT 'Heure début Action'")
    private LocalTime heureDebut;

    @Column(name="fpa_heure_fin", columnDefinition="TIME(6) NULL DEFAULT NULL COMMENT 'Heure fin Action'")
    private LocalTime heureFin;

    @Column(name="fpa_ligne1", columnDefinition="VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Commentaire et précisions'")
    private String commentaire1;

    @Column(name="fpa_ligne2", columnDefinition="VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL")
    private String commentaire2;

    @Column(name="fpa_ligne3", columnDefinition="VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL")
    private String commentaire3;

    @Column(name="fpa_valide", length=1, columnDefinition="VARCHAR(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Valeur 1 = Actif / validé, 0 = Inactif / en attente de validation'")
    private String isValid;

//    @Column(name = "fpa_suivi")
//    @Convert(converter = SuiviConverter.class)
//    private Suivi suivi;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public String getTypeOrganisation() {
        return typeOrganisation;
    }

    public void setTypeOrganisation(String typeOrganisation) {
        this.typeOrganisation = typeOrganisation;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Structure getStructureResponsable() {
        return structureResponsable;
    }

    public void setStructureResponsable(Structure structureResponsable) {
        this.structureResponsable = structureResponsable;
    }

    public Acteur getActeurResponsable() {
        return acteurResponsable;
    }

    public void setActeurResponsable(Acteur acteurResponsable) {
        this.acteurResponsable = acteurResponsable;
    }

    public String getNiveauDiffusion() {
        return niveauDiffusion;
    }

    public void setNiveauDiffusion(String niveauDiffusion) {
        this.niveauDiffusion = niveauDiffusion;
    }

    public String getRetroAction() {
        return retroAction;
    }

    public void setRetroAction(String retroAction) {
        this.retroAction = retroAction;
    }

    public LocalDate getDateAction() {
        return dateAction;
    }

    public void setDateAction(LocalDate dateAction) {
        this.dateAction = dateAction;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public String getCommentaire1() {
        return commentaire1;
    }

    public void setCommentaire1(String commentaire1) {
        this.commentaire1 = commentaire1;
    }

    public String getCommentaire2() {
        return commentaire2;
    }

    public void setCommentaire2(String commentaire2) {
        this.commentaire2 = commentaire2;
    }

    public String getCommentaire3() {
        return commentaire3;
    }

    public void setCommentaire3(String commentaire3) {
        this.commentaire3 = commentaire3;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

//    public Suivi getSuivi() {
//        return suivi;
//    }
//
//    public void setSuivi(Suivi suivi) {
//        this.suivi = suivi;
//    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlanningAction)) {
            return false;
        }
        return id != null && id.equals(((PlanningAction) obj).id);
    }

    @Override
    public String toString() {
        return "PlanningAction {" +
                "id=" + getId() +
                ", forum='" + getForum() + "'" +
                ", typeOrganisation='" + getTypeOrganisation() + "'" +
                ", reference='" + getReference() + "'" +
                ", acteur='" + getActeur() + "'" +
                ", structure='" + getStructure() + "'" +
                ", Action='" + getAction() + "'" +
                ", structureResponsable='" + getStructureResponsable() + "'" +
                ", acteurResponsable='" + getActeurResponsable() + "'" +
                ", niveauDiffusion='" + getNiveauDiffusion() + "'" +
                ", retroAction='" + getRetroAction() + "'" +
                ", dateAction='" + getDateAction() + "'" +
                ", heureDebut='" + getHeureDebut() + "'" +
                ", heureFin='" + getHeureFin() + "'" +
                ", commentaire1='" + getCommentaire1() + "'" +
                ", commentaire2='" + getCommentaire2() + "'" +
                ", commentaire3='" + getCommentaire3() + "'" +
                ", isValid='" + getIsValid() + "'" +
//                ", suivi='" + getSuivi() + "'" +
                "}";
    }
}
