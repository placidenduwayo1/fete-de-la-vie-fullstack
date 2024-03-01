package com.fdlv.gmd.api.domain.fdlv;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "fdlv_info_pageweb")
public class InfoPageWeb implements Serializable {

    private static final long serialVersionUID = 1952365481282442573L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ipb_id")
    private Long id;

    @Column(name = "ipb_pageweb", length = 255)
    private String pageWeb;

    @Column(name = "ipb_rub_pageweb", length = 255)
    private String rubriquePageWeb;

    @Column(name = "ipb_ordre_affichage")
    private Integer ordreAffichage;

    @Column(name = "ipb_titre", length = 255)
    private String titre;

    @Column(name = "ipb_texte")
    private String texte;

    @Column(name = "ipb_commentaire")
    private String commentaire;

    @Column(name = "ipb_publie", columnDefinition = "BOOLEAN")
    private Boolean publie;

    @Column(name = "ipb_url_media")
    private String urlMedia;

    @Column(name = "ipb_date_debut")
    private LocalDate dateDebut;

    @Column(name = "ipb_date_fin")
    private LocalDate dateFin;

    public String getRubriquePageWeb() {
        return rubriquePageWeb;
    }

    public void setRubriquePageWeb(String rubriquePageWeb) {
        this.rubriquePageWeb = rubriquePageWeb;
    }

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

    @Override
    public String toString() {
        return "InfoPageWeb{" +
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
