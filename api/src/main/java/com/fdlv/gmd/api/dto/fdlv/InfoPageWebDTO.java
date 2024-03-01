package com.fdlv.gmd.api.dto.fdlv;

import java.io.Serializable;
import java.time.LocalDate;

public class InfoPageWebDTO implements Serializable {

    private static final long serialVersionUID = -4039607989657138256L;

    private Long id;
    private String pageWeb;

    private String rubriquePageWeb;
    private Integer ordreAffichage;
    private String titre;
    private String texte;
    private String commentaire;
    private Boolean publie;
    private String urlMedia;
    private LocalDate dateDebut;
    private LocalDate dateFin;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPageWeb() {
        return pageWeb;
    }

    public void setPageWeb(String pageWeb) {
        this.pageWeb = pageWeb;
    }

    public Integer getOrdreAffichage() {
        return ordreAffichage;
    }

    public void setOrdreAffichage(Integer ordreAffichage) {
        this.ordreAffichage = ordreAffichage;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean getPublie() {
        return publie;
    }

    public void setPublie(Boolean publie) {
        this.publie = publie;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
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

    public String getRubriquePageWeb() {
        return rubriquePageWeb;
    }

    public void setRubriquePageWeb(String rubriquePageWeb) {
        this.rubriquePageWeb = rubriquePageWeb;
    }

    @Override
    public String toString() {
        return "InfoPageWebDTO{" +
                "id=" + id +
                ", pageWeb='" + pageWeb + '\'' +
                ", rubriquePageWeb='" + rubriquePageWeb + '\'' +
                ", ordreAffichage=" + ordreAffichage +
                ", titre='" + titre + '\'' +
                ", texte='" + texte + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", publie=" + publie +
                ", urlMedia='" + urlMedia + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}
