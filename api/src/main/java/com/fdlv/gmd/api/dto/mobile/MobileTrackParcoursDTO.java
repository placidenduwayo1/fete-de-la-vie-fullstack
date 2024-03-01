package com.fdlv.gmd.api.dto.mobile;

import com.fdlv.gmd.api.dto.EventDTO;
import com.fdlv.gmd.api.dto.QuestionDTO;
import com.fdlv.gmd.api.dto.QuizzDTO;
import com.fdlv.gmd.api.dto.StageDTO;

import java.time.LocalDateTime;

public class MobileTrackParcoursDTO {
    private Long id;
    private MobileStatParcoursDTO mobileStatParcours;
    private EventDTO event;
    private StageDTO stage;
    private QuizzDTO quizz;
    private QuestionDTO question;
    private String reponseOk;
    private LocalDateTime dateAction;
    private boolean defiPartage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MobileStatParcoursDTO getMobileStatParcours() {
        return mobileStatParcours;
    }

    public void setMobileStatParcours(MobileStatParcoursDTO mobileStatParcours) {
        this.mobileStatParcours = mobileStatParcours;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public StageDTO getStage() {
        return stage;
    }

    public void setStage(StageDTO stage) {
        this.stage = stage;
    }

    public QuizzDTO getQuizz() {
        return quizz;
    }

    public void setQuizz(QuizzDTO quizz) {
        this.quizz = quizz;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    public String getReponseOk() {
        return reponseOk;
    }

    public void setReponseOk(String reponseOk) {
        this.reponseOk = reponseOk;
    }

    public LocalDateTime getDateAction() {
        return dateAction;
    }

    public void setDateAction(LocalDateTime dateAction) {
        this.dateAction = dateAction;
    }

    public boolean isDefiPartage() {
        return defiPartage;
    }

    public void setDefiPartage(boolean defiPartage) {
        this.defiPartage = defiPartage;
    }

    @Override
    public String toString() {
        return "MobileTrackParcoursDTO{" +
                "id=" + id +
                ", mobileStatParcours=" + mobileStatParcours +
                ", event=" + event +
                ", stage=" + stage +
                ", quizz=" + quizz +
                ", question=" + question +
                ", reponseOk='" + reponseOk + '\'' +
                ", dateAction=" + dateAction +
                ", defiPartage=" + defiPartage +
                '}';
    }
}
