package com.fdlv.gmd.api.domain.forum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "forum_acteur_hab_module")
public class ActeurHabilitationModule implements Serializable {

    @Id
    @Column(name = "fhm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fhm_fac_id", foreignKey = @ForeignKey(name = "fk_fhm_fac_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "fac_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'référence l'acteur'")
    private Acteur acteur;

    @ManyToOne
    @JoinColumn(name = "fhm_fam_id", foreignKey = @ForeignKey(name = "fk_fhm_fam_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "fam_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'référence le module applicatif'")
    private ModuleApplicatif moduleApplicatif;

    @Column(name = "fhm_habilitation", nullable = false, length = 1, columnDefinition = "varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'N = NULL, R = Read, W= Write, D = Download, U = Upload'")
    private String habilitation;

    @Column(name = "fhm_commentaire", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String commentaire;

// getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long fhmId) {
        this.id = fhmId;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur forumActeur) {
        this.acteur = forumActeur;
    }

    public ModuleApplicatif getModuleApplicatif() {
        return moduleApplicatif;
    }

    public void setModuleApplicatif(ModuleApplicatif forumModuleApplicatif) {
        this.moduleApplicatif = forumModuleApplicatif;
    }

    public String getHabilitation() {
        return habilitation;
    }

    public void setHabilitation(String fhmHabilitation) {
        this.habilitation = fhmHabilitation;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String fhmCommentaire) {
        this.commentaire = fhmCommentaire;
    }

    @Override
    public String toString() {
        return "ActeurHabilitationModule{" +
                "id=" + id +
                ", forumActeur=" + acteur +
                ", forumModuleApplicatif=" + moduleApplicatif +
                ", fhmHabilitation='" + habilitation + '\'' +
                ", fhmCommentaire='" + commentaire + '\'' +
                '}';
    }
}