package com.fdlv.gmd.api.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Stage} entity.
 */
public class StageDetailsDTO implements Serializable {

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

    private QuizzDetailsDTO quizz;

    private String challengeString;

    private String challengeShareString;

    public String getChallengeString() {
        return challengeString;
    }

    public void setChallengeString(String challengeString) {
        this.challengeString = challengeString;
    }

    public String getChallengeShareString() {
        return challengeShareString;
    }

    public void setChallengeShareString(String challengeShareString) {
        this.challengeShareString = challengeShareString;
    }

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

    public QuizzDetailsDTO getQuizz() {
        return quizz;
    }

    public void setQuizz(QuizzDetailsDTO quizz) {
        this.quizz = quizz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StageDetailsDTO)) {
            return false;
        }

        StageDetailsDTO stageDTO = (StageDetailsDTO) o;
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

}
