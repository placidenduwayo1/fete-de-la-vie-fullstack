package com.fdlv.gmd.api.domain.fdlv;

import com.fdlv.gmd.api.domain.Quizz;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fdlv_scenario_etape")
@Getter @Setter
public class ScenarioEtape implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fse_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fse_sps_id", referencedColumnName = "fco_id")
    private ChoixOrganisateur choixOrganisateur;

    @Column(name = "fse_sequence", nullable = false, length = 45)
    private Integer sequence;

    @Column(name = "fse_titre", nullable = false, length = 100)
    private String titre;

    @ManyToOne
    @JoinColumn(name = "fse_video_id", referencedColumnName = "flv_id")
    private ListeVideos listeVideos;

    @Column(name = "fse_latitude")
    private Double latitude;

    @Column(name = "fse_longitude")
    private Double longitude;

    @ManyToOne(cascade = CascadeType.MERGE,optional = true)
    @JoinColumn(name = "fse_quizz_id", referencedColumnName = "id",nullable = true)
    private Quizz quizz;

    @Column(name = "fse_defi", length = 80)
    private String defi;

    @Column(name = "fse_flv_id")
    private Long flvId;
    @Column(name = "fse_defi_video", length = 80)
    private String defiVideo;

    @Column(name = "fse_defi_partage", length = 255)
    private String defiPartage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChoixOrganisateur getChoixOrganisateur() {
        return choixOrganisateur;
    }

    public void setChoixOrganisateur(ChoixOrganisateur choixOrganisateur) {
        this.choixOrganisateur = choixOrganisateur;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public ListeVideos getListeVideos() {
        return listeVideos;
    }

    public void setListeVideos(ListeVideos listeVideos) {
        this.listeVideos = listeVideos;
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

    public Quizz getQuizz() {
        return quizz;
    }

    public void setQuizz(Quizz quizz) {
        this.quizz = quizz;
    }

    public String getDefi() {
        return defi;
    }

    public void setDefi(String defi) {
        this.defi = defi;
    }

    public Long getFlvId() {
        return flvId;
    }

    public void setFlvId(Long flvId) {
        this.flvId = flvId;
    }

    public String getDefiVideo() {
        return defiVideo;
    }

    public void setDefiVideo(String defiVideo) {
        this.defiVideo = defiVideo;
    }

    public String getDefiPartage() {
        return defiPartage;
    }

    public void setDefiPartage(String defiPartage) {
        this.defiPartage = defiPartage;
    }

    @Override
    public String toString() {
        return "ScenarioEtape{" +
                "id=" + id +
                ", choixOrganisateur=" + choixOrganisateur +
                ", sequence='" + sequence + '\'' +
                ", titre='" + titre + '\'' +
                ", listeVideos=" + listeVideos +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", quizz=" + quizz +
                ", defi='" + defi + '\'' +
                ", flvId=" + flvId +
                ", defiVideo='" + defiVideo + '\'' +
                ", defiPartage='" + defiPartage + '\'' +
                '}';
    }
}
