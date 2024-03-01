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
@Table(name = "fdlv_temoignage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Temoignage implements Serializable {

    private static final long serialVersionUID = -7822930342179280299L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tge_id")
    private Long id;
    
    @Column(name = "tge_ordre_affichage")
    private Integer ordreAffichage;

    @Column(name = "tge_auteur", length = 50)
    private String auteur;

    @Column(name = "tge_lien_image_auteur", length = 100)
    private String lienImageAuteur;

    @Column(name = "tge_domaine_metier", length = 50)
    private String domaineMetier;

    @Column(name = "tge_compagnie", length = 50)
    private String compagnie;
    
    @Column(name = "tge_texte_temoignage")
    private String texteTemoignage;

    @Column(name = "tge_date_debut")
    private LocalDate dateDebut;

    @Column(name = "tge_date_fin")
    private LocalDate dateFin;
    
    @Column(name = "tge_publie", columnDefinition = "BOOLEAN")
    private Boolean publie;

    @Column(name = "tge_commentaire")
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
