package com.fdlv.gmd.api.dto.forum;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class PlanningActionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;

    private ForumDTO forum;

    @NotNull
    private String typeOrganisation;

    private String reference;

    private ActeurDTO acteur;

    private StructureDTO structure;

    @NotNull
    private String action;

    private StructureDTO structureResponsable;

    private ActeurDTO acteurResponsable;

    private String niveauDiffusion;

    private String retroAction;

    private LocalDate dateAction;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private String commentaire1;

    private String commentaire2;

    private String commentaire3;

    private String isValid;

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

    public ForumDTO getForum() {
        return forum;
    }

    public void setForum(ForumDTO forum) {
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

    public ActeurDTO getActeur() {
        return acteur;
    }

    public void setActeur(ActeurDTO acteur) {
        this.acteur = acteur;
    }

    public StructureDTO getStructure() {
        return structure;
    }

    public void setStructure(StructureDTO structure) {
        this.structure = structure;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public StructureDTO getStructureResponsable() {
        return structureResponsable;
    }

    public void setStructureResponsable(StructureDTO structureResponsable) {
        this.structureResponsable = structureResponsable;
    }

    public ActeurDTO getActeurResponsable() {
        return acteurResponsable;
    }

    public void setActeurResponsable(ActeurDTO acteurResponsable) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanningActionDTO)) {
            return false;
        }

        PlanningActionDTO planingActionDTO = (PlanningActionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, planingActionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "PlanningActionDTO {" +
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
