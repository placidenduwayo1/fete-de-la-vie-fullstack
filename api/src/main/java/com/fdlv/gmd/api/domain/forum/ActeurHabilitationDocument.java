package com.fdlv.gmd.api.domain.forum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "forum_acteur_hab_doc")
public class ActeurHabilitationDocument implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fad_id", unique = true, nullable = false, columnDefinition = "bigint NOT NULL")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fad_fra_id", referencedColumnName = "fra_id", foreignKey = @ForeignKey(name = "fk_fad_fra_id", value = ConstraintMode.CONSTRAINT),
            columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'Référence le rôle des acteurs pour diffusion et communication'")
    private RoleActeur forumRoleActeur;

    @ManyToOne
    @JoinColumn(name = "fad_fdt_id", referencedColumnName = "fdt_id", foreignKey = @ForeignKey(name = "fk_fad_fdt_id", value = ConstraintMode.CONSTRAINT),
            columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'Référence le document concerné'")
    private Document document;

    @Column(name = "fad_habilitation", length = 2, nullable = false, columnDefinition = "varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'R/W/D/U  \r\nRead / Write/ Dowload / Upload'")
    private String habilitation;


    @Column(name = "fad_commentaire", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String commentaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleActeur getForumRoleActeur() {
        return forumRoleActeur;
    }

    public void setForumRoleActeur(RoleActeur forumRoleActeur) {
        this.forumRoleActeur = forumRoleActeur;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getHabilitation() {
        return habilitation;
    }

    public void setHabilitation(String habilitation) {
        this.habilitation = habilitation;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "ActeurHabilitationDocument{" +
                "id=" + id +
                ", forumRoleActeur=" + forumRoleActeur +
                ", document=" + document +
                ", habilitation='" + habilitation + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}