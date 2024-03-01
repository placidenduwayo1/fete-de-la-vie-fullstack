package com.fdlv.gmd.api.domain.fdlv;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "video_quizz")
public class VideoQuizz implements Serializable {

    @Id
    @Column(name = "vqu_flv_id")
    private Long idVideo;

    public Long getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Long idVideo) {
        this.idVideo = idVideo;
    }

    public Long getIdQuizz() {
        return idQuizz;
    }

    public void setIdQuizz(Long idQuizz) {
        this.idQuizz = idQuizz;
    }

    @Column(name = "vqu_quizz_id")
    private Long idQuizz;


}
