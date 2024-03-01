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
@Table(name = "fdlv_evt_video")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EventVideo implements Serializable {

    private static final long serialVersionUID = 8618186037853129252L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evo_id")
    private Long id;

    @Column(name = "evo_ordre_affichage")
    private Integer ordreAffichage;

    @Column(name = "evo_lien_video", length = 100)
    private String urlVideo;

    @Column(name = "evo_date_debut")
    private LocalDate dateDebut;

    @Column(name = "evo_date_fin")
    private LocalDate dateFin;

    @Column(name = "evo_publie", columnDefinition = "BOOLEAN")
    private Boolean publie;

    @Column(name = "evo_commentaire")
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

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
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
