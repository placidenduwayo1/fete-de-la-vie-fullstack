package com.fdlv.gmd.api.dto.fdlv;

import javax.annotation.Nullable;
import java.io.Serializable;



public class ScenarioEtapeDTO implements Serializable {



    private Long fse_id;
    private Long fse_sps_id; // Remplacé par l'ID plutôt que l'entité complète
    private Integer fse_sequence;
    private String fse_titre;
    private Double fse_latitude;
    private Double fse_longitude;
    private Long fse_video_id; // Remplacé par l'ID plutôt que l'entité complète
    private Long fse_quizz_id; // Remplacé par l'ID plutôt que l'entité complète
    private String fse_defi;

    private Long fse_flv_id;
    private String fse_defi_video;
    private String fse_defi_partage;

    public Long getFse_id() {
        return fse_id;
    }

    public void setFse_id(Long fse_id) {
        this.fse_id = fse_id;
    }

    public Long getFse_sps_id() {
        return fse_sps_id;
    }

    public void setFse_sps_id(Long fse_sps_id) {
        this.fse_sps_id = fse_sps_id;
    }

    public Integer getFse_sequence() {
        return fse_sequence;
    }

    public void setFse_sequence(Integer fse_sequence) {
        this.fse_sequence = fse_sequence;
    }

    public String getFse_titre() {
        return fse_titre;
    }

    public void setFse_titre(String fse_titre) {
        this.fse_titre = fse_titre;
    }

    public Long getFse_video_id() {
        return fse_video_id;
    }

    public void setFse_video_id(Long fse_video_id) {
        this.fse_video_id = fse_video_id;
    }

    public Double getFse_latitude() {
        return fse_latitude;
    }

    public void setFse_latitude(Double fse_latitude) {
        this.fse_latitude = fse_latitude;
    }

    public Double getFse_longitude() {
        return fse_longitude;
    }

    public void setFse_longitude(Double fse_longitude) {
        this.fse_longitude = fse_longitude;
    }

    public Long getFse_quizz_id() {
        return fse_quizz_id;
    }

    public void setFse_quizz_id(Long fse_quizz_id) {
        this.fse_quizz_id = fse_quizz_id;
    }

    public String getFse_defi() {
        return fse_defi;
    }

    public void setFse_defi(String fse_defi) {
        this.fse_defi = fse_defi;
    }

    public Long getFse_flv_id() {
        return fse_flv_id;
    }

    public void setFse_flv_id(Long fse_flv_id) {
        this.fse_flv_id = fse_flv_id;
    }

    public String getFse_defi_video() {
        return fse_defi_video;
    }

    public void setFse_defi_video(String fse_defi_video) {
        this.fse_defi_video = fse_defi_video;
    }

    public String getFse_defi_partage() {
        return fse_defi_partage;
    }

    public void setFse_defi_partage(String fse_defi_partage) {
        this.fse_defi_partage = fse_defi_partage;
    }

    @Override
    public String toString() {
        return "ScenarioEtapeDTO{" +
                "fse_id=" + fse_id +
                ", fse_sps_id=" + fse_sps_id +
                ", fse_sequence='" + fse_sequence + '\'' +
                ", fse_titre='" + fse_titre + '\'' +
                ", fse_latitude=" + fse_latitude +
                ", fse_longitude=" + fse_longitude +
                ", fse_video_id=" + fse_video_id +
                ", fse_quizz_id=" + fse_quizz_id +
                ", fse_defi='" + fse_defi + '\'' +
                ", fse_flv_id=" + fse_flv_id +
                ", fse_defi_video='" + fse_defi_video + '\'' +
                ", fse_defi_partage='" + fse_defi_partage + '\'' +
                '}';
    }
}
