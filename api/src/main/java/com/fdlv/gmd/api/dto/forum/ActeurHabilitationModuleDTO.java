package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;

public class ActeurHabilitationModuleDTO implements Serializable {

    private Long id;
    private ActeurDTO acteur;
    private ModuleApplicatifDTO moduleApplicatif;
    private String habilitation;
    private String commentaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActeurDTO getActeur() {
        return acteur;
    }

    public void setActeur(ActeurDTO acteur) {
        this.acteur = acteur;
    }

    public ModuleApplicatifDTO getModuleApplicatif() {
        return moduleApplicatif;
    }

    public void setModuleApplicatif(ModuleApplicatifDTO moduleApplicatif) {
        this.moduleApplicatif = moduleApplicatif;
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
        return "ActeurHabilitationModuleDTO{" +
                "id=" + id +
                ", acteur=" + acteur +
                ", moduleApplicatif=" + moduleApplicatif +
                ", habilitation='" + habilitation + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}