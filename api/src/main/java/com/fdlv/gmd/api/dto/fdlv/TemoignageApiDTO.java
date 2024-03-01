package com.fdlv.gmd.api.dto.fdlv;

import java.time.LocalDate;
import java.util.Objects;

public class TemoignageApiDTO {
    private Long tge_id;
    private Integer tge_ordre_affichage;
    private String tge_auteur;
    private String tge_lien_image_auteur;
    private String tge_domaine_metier;
    private String tge_compagnie;
    private String tge_texte_temoignage;


    public TemoignageApiDTO(TemoignageDTO temoignageDTO) {
        this.tge_id = temoignageDTO.getId();
        this.tge_ordre_affichage = temoignageDTO.getOrdreAffichage();
        this.tge_auteur = temoignageDTO.getAuteur();
        this.tge_lien_image_auteur = temoignageDTO.getLienImageAuteur();
        this.tge_domaine_metier = temoignageDTO.getDomaineMetier();
        this.tge_compagnie = temoignageDTO.getCompagnie();
        this.tge_texte_temoignage = temoignageDTO.getTexteTemoignage();
    }

    public Long getTge_id() {
        return tge_id;
    }

    public void setTge_id(Long tge_id) {
        this.tge_id = tge_id;
    }

    public Integer getTge_ordre_affichage() {
        return tge_ordre_affichage;
    }

    public void setTge_ordre_affichage(Integer tge_ordre_affichage) {
        this.tge_ordre_affichage = tge_ordre_affichage;
    }

    public String getTge_auteur() {
        return tge_auteur;
    }

    public void setTge_auteur(String tge_auteur) {
        this.tge_auteur = tge_auteur;
    }

    public String getTge_lien_image_auteur() {
        return tge_lien_image_auteur;
    }

    public void setTge_lien_image_auteur(String tge_lien_image_auteur) {
        this.tge_lien_image_auteur = tge_lien_image_auteur;
    }

    public String getTge_domaine_metier() {
        return tge_domaine_metier;
    }

    public void setTge_domaine_metier(String tge_domaine_metier) {
        this.tge_domaine_metier = tge_domaine_metier;
    }

    public String getTge_compagnie() {
        return tge_compagnie;
    }

    public void setTge_compagnie(String tge_compagnie) {
        this.tge_compagnie = tge_compagnie;
    }

    public String getTge_texte_temoignage() {
        return tge_texte_temoignage;
    }

    public void setTge_texte_temoignage(String tge_texte_temoignage) {
        this.tge_texte_temoignage = tge_texte_temoignage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemoignageApiDTO that = (TemoignageApiDTO) o;
        return Objects.equals(tge_id, that.tge_id) && Objects.equals(tge_ordre_affichage, that.tge_ordre_affichage) && Objects.equals(tge_auteur, that.tge_auteur) && Objects.equals(tge_lien_image_auteur, that.tge_lien_image_auteur) && Objects.equals(tge_domaine_metier, that.tge_domaine_metier) && Objects.equals(tge_compagnie, that.tge_compagnie) && Objects.equals(tge_texte_temoignage, that.tge_texte_temoignage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tge_id, tge_ordre_affichage, tge_auteur, tge_lien_image_auteur, tge_domaine_metier, tge_compagnie, tge_texte_temoignage);
    }

    @Override
    public String toString() {
        return "TemoignageApiDTO{" +
                "tge_id=" + tge_id +
                ", tge_ordre_affichage=" + tge_ordre_affichage +
                ", tge_auteur='" + tge_auteur + '\'' +
                ", tge_lien_image_auteur='" + tge_lien_image_auteur + '\'' +
                ", tge_domaine_metier='" + tge_domaine_metier + '\'' +
                ", tge_compagnie='" + tge_compagnie + '\'' +
                ", tge_texte_temoignage='" + tge_texte_temoignage + '\'' +
                '}';
    }

}
