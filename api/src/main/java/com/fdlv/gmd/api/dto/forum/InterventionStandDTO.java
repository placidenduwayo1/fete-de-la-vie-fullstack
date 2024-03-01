package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class InterventionStandDTO implements Serializable {

    private Long fsiId;
    private ActeurDTO acteur;
    private RoleActeurDTO roleActeur;
    private ForumDTO forum;
    private StandDTO stand;
    private StructureDTO structure;
    private RoleStructureDTO roleStructure;
    private StandBanniereDTO standBanniere;
    private String fsiDescription;
    private LocalDate fsiDateDebut;
    private LocalDate fsiDateFin;
    private BigDecimal fsiNbRepas;
    private String fsiCommentaire;

    public Long getFsiId() {
        return fsiId;
    }

    public void setFsiId(Long fsiId) {
        this.fsiId = fsiId;
    }

    public ActeurDTO getActeur() {
        return acteur;
    }

    public void setActeur(ActeurDTO acteur) {
        this.acteur = acteur;
    }

    public RoleActeurDTO getRoleActeur() {
        return roleActeur;
    }

    public void setRoleActeur(RoleActeurDTO roleActeur) {
        this.roleActeur = roleActeur;
    }

    public ForumDTO getForum() {
        return forum;
    }

    public void setForum(ForumDTO forum) {
        this.forum = forum;
    }

    public StandDTO getStand() {
        return stand;
    }

    public void setStand(StandDTO stand) {
        this.stand = stand;
    }

    public StructureDTO getStructure() {
        return structure;
    }

    public void setStructure(StructureDTO structure) {
        this.structure = structure;
    }

    public RoleStructureDTO getRoleStructure() {
        return roleStructure;
    }

    public void setRoleStructure(RoleStructureDTO roleStructure) {
        this.roleStructure = roleStructure;
    }

    public StandBanniereDTO getStandBanniere() {
        return standBanniere;
    }

    public void setStandBanniere(StandBanniereDTO standBanniere) {
        this.standBanniere = standBanniere;
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

    @Override
    public String toString() {
        return "InterventionStandDTO{" +
                "fsiId=" + fsiId +
                ", acteur=" + acteur +
                ", roleActeur=" + roleActeur +
                ", forum=" + forum +
                ", stand=" + stand +
                ", structure=" + structure +
                ", roleStructure=" + roleStructure +
                ", standBanniere=" + standBanniere +
                ", fsiDescription='" + fsiDescription + '\'' +
                ", fsiDateDebut=" + fsiDateDebut +
                ", fsiDateFin=" + fsiDateFin +
                ", fsiNbRepas=" + fsiNbRepas +
                ", fsiCommentaire='" + fsiCommentaire + '\'' +
                '}';
    }
}