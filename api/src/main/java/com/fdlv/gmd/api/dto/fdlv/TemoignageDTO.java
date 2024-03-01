package com.fdlv.gmd.api.dto.fdlv;

import java.io.Serializable;
import java.time.LocalDate;

public class TemoignageDTO implements Serializable {

    private static final long serialVersionUID = -5268204360548095626L;

    private Long id;
    private Integer ordreAffichage;
    private String auteur;
    private String lienImageAuteur;
    private String domaineMetier;
    private String compagnie;
    private String texteTemoignage;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Boolean publie;
    private String commentaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdreAffichage() {
        return ordreAffichage;
    }

    public void setOrdreAffichage(Integer ordreAffichage) {
        this.ordreAffichage = ordreAffichage;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getLienImageAuteur() {
        return lienImageAuteur;
    }

    public void setLienImageAuteur(String lienImageAuteur) {
        this.lienImageAuteur = lienImageAuteur;
    }

    public String getDomaineMetier() {
        return domaineMetier;
    }

    public void setDomaineMetier(String domaineMetier) {
        this.domaineMetier = domaineMetier;
    }

    public String getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(String compagnie) {
        this.compagnie = compagnie;
    }

    public String getTexteTemoignage() {
        return texteTemoignage;
    }

    public void setTexteTemoignage(String texteTemoignage) {
        this.texteTemoignage = texteTemoignage;
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

    public Boolean getPublie() {
        return publie;
    }

    public void setPublie(Boolean publie) {
        this.publie = publie;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

}
