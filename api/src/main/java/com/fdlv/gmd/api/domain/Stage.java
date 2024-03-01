package com.fdlv.gmd.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fdlv.gmd.api.domain.fdlv.ListeVideos;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Stage.
 */
@Entity
@Table(name = "stage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Stage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "video_description")
    private String videoDescription;

    @Column(name = "video_image_url")
    private String videoImageUrl;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "type_media")
    private String typeMedia;

   
    @ManyToOne
    @JsonIgnoreProperties(value = { "stages" }, allowSetters = true)
    private Event event;

    @ManyToOne()
    @JsonIgnoreProperties(value = { "stages", "questions" }, allowSetters = true)
    private Quizz quizz;

    @OneToOne
    @JoinColumn(name = "stage_flv_id")
    private ListeVideos video;
    @JsonIgnore
   @Column(name = "challenge_id")
   private Long challenge_id;

    @JsonIgnore
    @Column(name = "stage_defi_video")
    private String stage_defi_video;

    @JsonIgnore
    @Column(name = "stage_defi_partage")
    private String stage_defi_partage;


    public String getStage_defi_video() {
        return stage_defi_video;
    }

    public void setStage_defi_video(String stage_defi_video) {
        this.stage_defi_video = stage_defi_video;
    }

    public String getStage_defi_partage() {
        return stage_defi_partage;
    }

    public void setStage_defi_partage(String stage_defi_partage) {
        this.stage_defi_partage = stage_defi_partage;
    }

    public Long getChallenge_id() {
        return challenge_id;
    }

    public void setChallenge_id(Long challenge_id) {
        this.challenge_id = challenge_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stage id(Long id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return this.label;
    }

    public Stage label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public Stage sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Stage latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Stage longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getvideoDescription() {
        return this.videoDescription;
    }

    public Stage videoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
        return this;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getVideoImageUrl() {
        return this.videoImageUrl;
    }

    public Stage videoImageUrl(String videoImageUrl) {
        this.videoImageUrl = videoImageUrl;
        return this;
    }

    public void setVideoImageUrl(String videoImageUrl) {
        this.videoImageUrl = videoImageUrl;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public Stage videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTypeMedia() {
        return this.typeMedia;
    }

    public Stage typeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
        return this;
    }

    public void setTypeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
    }

    public Event getEvent() {
        return this.event;
    }

    public Stage event(Event event) {
        this.setEvent(event);
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Quizz getQuizz() {
        return this.quizz;
    }

    public Stage quizz(Quizz quizz) {
        this.setQuizz(quizz);
        return this;
    }

    public void setQuizz(Quizz quizz) {
        this.quizz = quizz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stage)) {
            return false;
        }
        return id != null && id.equals(((Stage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    public ListeVideos getVideo() {
        return video;
    }

    public void setVideo(ListeVideos video) {
        this.video = video;
        this.videoDescription = video.getDescription();
        this.videoImageUrl = video.getUrlImage();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stage{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", sequence=" + getSequence() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", videoImageUrl=" + getVideoImageUrl() +
            ", videoUrl='" + getVideoUrl() + "'" +
            "}";
    }
}
