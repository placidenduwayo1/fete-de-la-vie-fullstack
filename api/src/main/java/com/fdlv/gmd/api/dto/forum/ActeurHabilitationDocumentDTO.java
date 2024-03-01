package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;

public class ActeurHabilitationDocumentDTO implements Serializable {

    private Long id;
    private RoleActeurDTO forumRoleActeur;
    private DocumentDTO document;
    private String habilitation;
    private String commentaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleActeurDTO getForumRoleActeur() {
        return forumRoleActeur;
    }

    public void setForumRoleActeur(RoleActeurDTO forumRoleActeur) {
        this.forumRoleActeur = forumRoleActeur;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
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
        return "ActeurHabilitationDocumentDTO{" +
                "id=" + id +
                ", forumRoleActeur=" + forumRoleActeur +
                ", document=" + document +
                ", habilitation='" + habilitation + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}