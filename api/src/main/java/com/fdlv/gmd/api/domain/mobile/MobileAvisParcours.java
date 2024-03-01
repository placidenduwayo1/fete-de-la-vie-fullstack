package com.fdlv.gmd.api.domain.mobile;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mobile_avis_parcours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MobileAvisParcours implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apa_id")
    private Long id;

    @Column(name = "apa_qfp_id")
    private Long questionFinParcoursId;

    @Column(name = "apa_mus_id_user")
    private Long mobileUserId;

    @Column(name = "apa_event_id")
    private Long eventId;

    @Column(name = "apa_question")
    private String question;

    @Column(name = "apa_avis")
    private String avis;

    @Column(name = "apa_datetime")
    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionFinParcoursId() {
        return questionFinParcoursId;
    }

    public void setQuestionFinParcoursId(Long questionFinParcoursId) {
        this.questionFinParcoursId = questionFinParcoursId;
    }

    public Long getMobileUserId() {
        return mobileUserId;
    }

    public void setMobileUserId(Long mobileUserId) {
        this.mobileUserId = mobileUserId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
