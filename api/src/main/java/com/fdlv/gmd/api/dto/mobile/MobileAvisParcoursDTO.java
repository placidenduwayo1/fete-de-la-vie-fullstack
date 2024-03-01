package com.fdlv.gmd.api.dto.mobile;

import java.io.Serializable;

public class MobileAvisParcoursDTO implements Serializable {

    private Long id;

    private Long questionFinParcoursId;

    private Long mobileUserId;

    private Long eventId;

    private String question;

    private String avis;

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
