package com.fdlv.gmd.api.domain.forum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Intervention structure.
 */
@Entity
@Table(name = "forum_intervention_structure")
public class InterventionStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fsn_id", nullable = false)
    private Long fsnId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fsn_fse_id", referencedColumnName = "fse_id", foreignKey = @ForeignKey(name = "fk_fsn_fse_id"),
            nullable = false, columnDefinition = "bigint DEFAULT NULL COMMENT 'Structure intervennat dans le cadre du forum'")
    private Structure structure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fsn_ffo_id", referencedColumnName = "ffo_id", foreignKey = @ForeignKey(name = "fk_fsn_ffo_id"),
            nullable = false, columnDefinition = "bigint DEFAULT NULL COMMENT 'Forum concerné'")
    private Forum forum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fsn_fro_id", referencedColumnName = "fro_id", foreignKey = @ForeignKey(name = "fk_fsn_fro_id"),
            nullable = false, columnDefinition = "bigint DEFAULT NULL COMMENT 'Référence le rôle de la structure'")
    private RoleStructure roleStructure;

    @Column(name = "fsn_description", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Description de l'intervention'")
    private String fsnDescription;

    @Column(name = "fsn_convention_signe", nullable = false, length = 3, columnDefinition = "varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Oui Non'")
    private String fsnConventionSigne;

    @Column(name = "fsn_date_convention_signe", columnDefinition = "datetime NULL DEFAULT NULL COMMENT 'Date signature de la convention'")
    private LocalDate fsnDateConventionSigne;

    @Column(name = "fsn_date_relance_01", columnDefinition = "datetime NULL DEFAULT NULL COMMENT 'Date 01 relance pour signature'")
    private LocalDate fsnDateRelance01;

    @Column(name = "fsn_date_relance_02", columnDefinition = "datetime NULL DEFAULT NULL COMMENT 'Date 02 relance pour signature'")
    private LocalDate fsnDateRelance02;

    @Column(name = "fsn_date_relance_03", columnDefinition = "datetime NULL DEFAULT NULL COMMENT 'Date 03 relance pour signature'")
    private LocalDate fsnDateRelance03;


    @Column(name = "fsn_date_relance_04", columnDefinition = "datetime NULL DEFAULT NULL COMMENT 'Date 04 relance pour signature'")
    private LocalDate fsnDateRelance04;

    @Column(name = "fsn_date_relance_05", columnDefinition = "datetime NULL DEFAULT NULL COMMENT 'Date 05 relance pour signature'")
    private LocalDate fsnDateRelance05;

    @Column(name = "fsn_commentaire", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String fsnCommentaire;


    public Long getFsnId() {
        return fsnId;
    }

    public void setFsnId(Long fsnId) {
        this.fsnId = fsnId;
    }

    public String getFsnDescription() {
        return fsnDescription;
    }

    public void setFsnDescription(String fsnDescription) {
        this.fsnDescription = fsnDescription;
    }

    public String getFsnConventionSigne() {
        return fsnConventionSigne;
    }

    public void setFsnConventionSigne(String fsnConventionSigne) {
        this.fsnConventionSigne = fsnConventionSigne;
    }

    public LocalDate getFsnDateConventionSigne() {
        return fsnDateConventionSigne;
    }

    public void setFsnDateConventionSigne(LocalDate fsnDateConventionSigne) {
        this.fsnDateConventionSigne = fsnDateConventionSigne;
    }

    public LocalDate getFsnDateRelance01() {
        return fsnDateRelance01;
    }

    public void setFsnDateRelance01(LocalDate fsnDateRelance01) {
        this.fsnDateRelance01 = fsnDateRelance01;
    }

    public LocalDate getFsnDateRelance02() {
        return fsnDateRelance02;
    }

    public void setFsnDateRelance02(LocalDate fsnDateRelance02) {
        this.fsnDateRelance02 = fsnDateRelance02;
    }

    public LocalDate getFsnDateRelance03() {
        return fsnDateRelance03;
    }

    public void setFsnDateRelance03(LocalDate fsnDateRelance03) {
        this.fsnDateRelance03 = fsnDateRelance03;
    }

    public LocalDate getFsnDateRelance04() {
        return fsnDateRelance04;
    }

    public void setFsnDateRelance04(LocalDate fsnDateRelance04) {
        this.fsnDateRelance04 = fsnDateRelance04;
    }

    public LocalDate getFsnDateRelance05() {
        return fsnDateRelance05;
    }

    public void setFsnDateRelance05(LocalDate fsnDateRelance05) {
        this.fsnDateRelance05 = fsnDateRelance05;
    }

    public String getFsnCommentaire() {
        return fsnCommentaire;
    }

    public void setFsnCommentaire(String fsnCommentaire) {
        this.fsnCommentaire = fsnCommentaire;
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

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {

        return "Intervention structure{" +
                "id=" + getFsnId() +
                ", description='" + getFsnDescription() + "'" +
                ", date convention signé= '" + getFsnDateConventionSigne() + "'" +
                ", date relance 1= '" + getFsnDateRelance01() + "'" +
                ", date relance 2= '" + getFsnDateRelance02() + "'" +
                ", date relance 3= '" + getFsnDateRelance03() + "'" +
                ", date relance 4= '" + getFsnDateRelance04() + "'" +
                ", date relance 5= '" + getFsnDateRelance05() + "'" +
                ", commentaire= '" + getFsnCommentaire() + "'" +
                ", structure= '" + getStructure() + "'";
    }

}
