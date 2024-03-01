package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;
import java.time.LocalDate;

public class InterventionStructureDTO implements Serializable {

    private Long fsnId;
    private StructureDTO structure;
    private ForumDTO forum;
    private RoleStructureDTO roleStructure;
    private String fsnDescription;
    private String fsnConventionSigne;
    private LocalDate fsnDateConventionSigne;
    private LocalDate fsnDateRelance01;
    private LocalDate fsnDateRelance02;
    private LocalDate fsnDateRelance03;
    private LocalDate fsnDateRelance04;
    private LocalDate fsnDateRelance05;
    private String fsnCommentaire;

    public Long getFsnId() {
        return fsnId;
    }

    public void setFsnId(Long fsnId) {
        this.fsnId = fsnId;
    }

    public StructureDTO getStructure() {
        return structure;
    }

    public void setStructure(StructureDTO structure) {
        this.structure = structure;
    }

    public ForumDTO getForum() {
        return forum;
    }

    public void setForum(ForumDTO forum) {
        this.forum = forum;
    }

    public RoleStructureDTO getRoleStructure() {
        return roleStructure;
    }

    public void setRoleStructure(RoleStructureDTO roleStructure) {
        this.roleStructure = roleStructure;
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

    @Override
    public String toString() {
        return "InterventionStructureDTO{" +
                "fsnId=" + fsnId +
                ", structure=" + structure +
                ", forum=" + forum +
                ", roleStructure=" + roleStructure +
                ", fsnDescription='" + fsnDescription + '\'' +
                ", fsnConventionSigne='" + fsnConventionSigne + '\'' +
                ", fsnDateConventionSigne=" + fsnDateConventionSigne +
                ", fsnDateRelance01=" + fsnDateRelance01 +
                ", fsnDateRelance02=" + fsnDateRelance02 +
                ", fsnDateRelance03=" + fsnDateRelance03 +
                ", fsnDateRelance04=" + fsnDateRelance04 +
                ", fsnDateRelance05=" + fsnDateRelance05 +
                ", fsnCommentaire='" + fsnCommentaire + '\'' +
                '}';
    }
}