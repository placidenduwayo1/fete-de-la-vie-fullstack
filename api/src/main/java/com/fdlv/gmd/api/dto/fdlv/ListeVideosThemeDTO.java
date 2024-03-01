package com.fdlv.gmd.api.dto.fdlv;

import com.fdlv.gmd.api.domain.Theme;
import com.fdlv.gmd.api.dto.QuizzDTO;

import java.io.Serializable;
import java.util.List;

/**
 * DTO used for reaching the list of videos with the associated theme
 */
public class ListeVideosThemeDTO implements Serializable {

    private static final long serialVersionUID = 2233837041675129696L;

    private Long id;
    private Theme theme;
    private Integer numOrdre;
    private String urlImage;
    private String urlVideo;
    private String description;
    private Boolean active;
    private String nomVideo;
    private String typeMedia;
    private Long flvFusId;

    private String flvMediaValide;
    private List<QuizzDTO> quizzs;

    public List<QuizzDTO> getQuizzs() {
        return quizzs;
    }

    public void setQuizzs(List<QuizzDTO> quizzs) {
        this.quizzs = quizzs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Integer getNumOrdre() {
        return numOrdre;
    }

    public void setNumOrdre(Integer numOrdre) {
        this.numOrdre = numOrdre;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNomVideo() {
        return nomVideo;
    }

    public void setNomVideo(String nomVideo) {
        this.nomVideo = nomVideo;
    }

    public Long getFlvFusId() {
        return flvFusId;
    }

    public void setFlvFusId(Long flvFusId) {
        this.flvFusId = flvFusId;
    }

    public String getFlvMediaValide() {
        return flvMediaValide;
    }

    public void setFlvMediaValide(String flvMediaValide) {
        this.flvMediaValide = flvMediaValide;
    }

    @Override
    public String toString() {
        return "ListeVideosThemeDTO{" +
                "id=" + id +
                ", theme=" + theme +
                ", numOrdre=" + numOrdre +
                ", urlImage='" + urlImage + '\'' +
                ", urlVideo='" + urlVideo + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", nomVideo='" + nomVideo + '\'' +
                ", typeMedia='" + typeMedia + '\'' +
                ", flvFusId=" + flvFusId +
                ", flvMediaValide='" + flvMediaValide + '\'' +
                ", quizzs=" + quizzs +
                '}';
    }
}
