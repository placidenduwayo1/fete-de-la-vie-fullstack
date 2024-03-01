package com.fdlv.gmd.api.domain.fdlv;

import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.domain.Theme;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fdlv_liste_videos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ListeVideos implements Serializable {

    private static final long serialVersionUID = 1940299371179867069L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flv_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "flv_theme_id")
    private Theme theme;

    @Column(name = "flv_num_ordre")
    private Integer numOrdre;

    @NotNull
    @Column(name = "flv_url_image", length = 150)
    private String urlImage;

    @NotNull
    @Column(name = "flv_url_video", length = 150)
    private String urlVideo;

    @NotNull
    @Column(name = "flv_description", length = 100)
    private String description;

    @Column(name = "flv_active")
    private Boolean active;

    @Column(name = "flv_nom_video", length = 50)
    private String nomVideo;

    @Column(name = "flv_type_media")
    private String typeMedia;

    @Column(name = "flv_fus_id")
    private Long flvFusId;

    @Column(name = "flv_media_valide")
    private String flvMediaValide;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "video_quizz",
            joinColumns = {@JoinColumn(name = "vqu_flv_id")},
            inverseJoinColumns = {@JoinColumn(name = "vqu_quizz_id")}
    )
    private List<Quizz> quizzs = new ArrayList<>();

    public ListeVideos() {
        super();
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

    public String getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
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

    public List<Quizz> getQuizzs() {
        return quizzs;
    }

    public void setQuizzs(List<Quizz> quizzs) {
        this.quizzs = quizzs;
    }
}
