package com.fdlv.gmd.api.dto.fdlv;

import com.fdlv.gmd.api.domain.Theme;

import java.util.Objects;

public class FdlvVideoDTO {

    private Long flv_id;
    private Long flv_theme_id;
    private Integer flv_num_ordre;
    private String flv_url_image;
    private String flv_url_video;
    private String flv_description;
    private Boolean flv_active;
    private String flv_nom_video;
    private String flv_type_media;
    private Long flv_fus_id;

    private String flv_media_valide;

    public FdlvVideoDTO(ListeVideosThemeDTO listeVideosThemeDTO) {
        this.flv_id = listeVideosThemeDTO.getId();
        this.flv_theme_id = listeVideosThemeDTO.getTheme().getId();
        this.flv_num_ordre = listeVideosThemeDTO.getNumOrdre();
        this.flv_url_image = listeVideosThemeDTO.getUrlImage();
        this.flv_url_video = listeVideosThemeDTO.getUrlVideo();
        this.flv_description = listeVideosThemeDTO.getDescription();
        this.flv_active = listeVideosThemeDTO.getActive();
        this.flv_nom_video = listeVideosThemeDTO.getNomVideo();
        this.flv_type_media = listeVideosThemeDTO.getTypeMedia();
        this.flv_fus_id = listeVideosThemeDTO.getFlvFusId();
        this.flv_media_valide = listeVideosThemeDTO.getFlvMediaValide();
    }

    public Long getFlv_id() {
        return flv_id;
    }

    public void setFlv_id(Long flv_id) {
        this.flv_id = flv_id;
    }

    public Long getFlv_theme_id() {
        return flv_theme_id;
    }

    public void setFlv_theme_id(Long flv_theme_id) {
        this.flv_theme_id = flv_theme_id;
    }

    public Integer getFlv_num_ordre() {
        return flv_num_ordre;
    }

    public void setFlv_num_ordre(Integer flv_num_ordre) {
        this.flv_num_ordre = flv_num_ordre;
    }

    public String getFlv_url_image() {
        return flv_url_image;
    }

    public void setFlv_url_image(String flv_url_image) {
        this.flv_url_image = flv_url_image;
    }

    public String getFlv_url_video() {
        return flv_url_video;
    }

    public void setFlv_url_video(String flv_url_video) {
        this.flv_url_video = flv_url_video;
    }

    public String getFlv_description() {
        return flv_description;
    }

    public void setFlv_description(String flv_description) {
        this.flv_description = flv_description;
    }

    public Boolean getFlv_active() {
        return flv_active;
    }

    public void setFlv_active(Boolean flv_active) {
        this.flv_active = flv_active;
    }

    public String getFlv_nom_video() {
        return flv_nom_video;
    }

    public void setFlv_nom_video(String flv_nom_video) {
        this.flv_nom_video = flv_nom_video;
    }

    public String getFlv_type_media() {
        return flv_type_media;
    }

    public void setFlv_type_media(String flv_type_media) {
        this.flv_type_media = flv_type_media;
    }

    public Long getFlv_fus_id() {
        return flv_fus_id;
    }

    public void setFlv_fus_id(Long flv_fus_id) {
        this.flv_fus_id = flv_fus_id;
    }

    public String getFlv_media_valide() {
        return flv_media_valide;
    }

    public void setFlv_media_valide(String flv_media_valide) {
        this.flv_media_valide = flv_media_valide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FdlvVideoDTO that = (FdlvVideoDTO) o;
        return Objects.equals(flv_id, that.flv_id) && Objects.equals(flv_theme_id, that.flv_theme_id) && Objects.equals(flv_num_ordre, that.flv_num_ordre) && Objects.equals(flv_url_image, that.flv_url_image) && Objects.equals(flv_url_video, that.flv_url_video) && Objects.equals(flv_description, that.flv_description) && Objects.equals(flv_active, that.flv_active) && Objects.equals(flv_nom_video, that.flv_nom_video) && Objects.equals(flv_type_media, that.flv_type_media) && Objects.equals(flv_fus_id, that.flv_fus_id) && Objects.equals(flv_media_valide, that.flv_media_valide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flv_id, flv_theme_id, flv_num_ordre, flv_url_image, flv_url_video, flv_description, flv_active, flv_nom_video, flv_type_media, flv_fus_id, flv_media_valide);
    }

    @Override
    public String toString() {
        return "FdlvVideoDTO{" +
                "flv_id=" + flv_id +
                ", flv_theme_id=" + flv_theme_id +
                ", flv_num_ordre=" + flv_num_ordre +
                ", flv_url_image='" + flv_url_image + '\'' +
                ", flv_url_video='" + flv_url_video + '\'' +
                ", flv_description='" + flv_description + '\'' +
                ", flv_active=" + flv_active +
                ", flv_nom_video='" + flv_nom_video + '\'' +
                ", flv_type_media='" + flv_type_media + '\'' +
                ", flv_fus_id=" + flv_fus_id +
                ", flv_media_valide='" + flv_media_valide + '\'' +
                '}';
    }
}
