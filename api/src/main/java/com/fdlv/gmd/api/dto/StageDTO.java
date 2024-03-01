package com.fdlv.gmd.api.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fdlv.gmd.api.dto.fdlv.ListeVideosThemeDTO;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Stage} entity.
 */
public class StageDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    @NotNull
    private Integer sequence;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private String videoDescription;

    private String videoImageUrl;

    private String videoUrl;

    private String typeMedia;

    private EventDTO event;

    private QuizzDTO quizz;

    private ListeVideosThemeDTO video;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getVideoImageUrl() {
        return videoImageUrl;
    }

    public void setVideoImageUrl(String videoImageUrl) {
        this.videoImageUrl = videoImageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public QuizzDTO getQuizz() {
        return quizz;
    }

    public void setQuizz(QuizzDTO quizz) {
        this.quizz = quizz;
    }

    private String stage_defi_video;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StageDTO)) {
            return false;
        }

        StageDTO stageDTO = (StageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StageDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", sequence=" + getSequence() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", typeMedia=" + getTypeMedia() +
            ", videoDescription=" + getVideoDescription() +
            ", videoImageUrl=" + getVideoImageUrl() +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", event=" + getEvent() +
            ", quizz=" + getQuizz() +
            ", video=" + getVideo() +
            "}";
    }

    public ListeVideosThemeDTO getVideo() {
        return video;
    }

    public void setVideo(ListeVideosThemeDTO video) {
        this.video = video;
    }

    
}
