package com.fdlv.gmd.api.dto.fdlv;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.fdlv.gmd.api.domain.enumeration.Profil;

public class FdlvUserDTO implements Serializable {

    private static final long serialVersionUID = 381434561947587996L;

    @Override
    public String toString() {
        return "FdlvUserDTO{" +
                "id=" + id +
                ", profil=" + profil +
                ", login='" + login + '\'' +
                ", mdpHash='" + mdpHash + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", structure='" + structure + '\'' +
                ", service='" + service + '\'' +
                ", email='" + email + '\'' +
                ", numTel='" + numTel + '\'' +
                ", actif=" + actif +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", permanent=" + permanent +
                ", resetKey='" + resetKey + '\'' +
                ", creePar='" + creePar + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateReset=" + dateReset +
                ", modifiePar='" + modifiePar + '\'' +
                ", dateModif=" + dateModif +
                ", fromFDLV=" + fromFDLV +
                '}';
    }

    private Long id;
    private Profil profil;
    private String login;
    private String mdpHash;
    private String prenom;
    private String nom;
    private String structure;
    private String service;
    private String email;
    private String numTel;
    private Boolean actif;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Boolean permanent;
    private String resetKey;
    private String creePar;
    private LocalDate dateCreation;
    private Instant dateReset;
    private String modifiePar;
    private LocalDate dateModif;

    // propriétés spécifiques 
    // pour savoir si le User vient du site FDLV ou du site GMD-admin
    private boolean fromFDLV;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdpHash() {
        return mdpHash;
    }

    public void setMdpHash(String mdpHash) {
        this.mdpHash = mdpHash;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Boolean getPermanent() {
        return permanent;
    }

    public void setPermanent(Boolean permanent) {
        this.permanent = permanent;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public String getCreePar() {
        return creePar;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Instant getDateReset() {
        return dateReset;
    }

    public void setDateReset(Instant dateReset) {
        this.dateReset = dateReset;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public LocalDate getDateModif() {
        return dateModif;
    }

    public void setDateModif(LocalDate dateModif) {
        this.dateModif = dateModif;
    }

    public boolean getFromFDLV() {
        return fromFDLV;
    }

    public void setFromFDLV(Boolean fromFDLV) {
        this.fromFDLV = fromFDLV;
    }

}
