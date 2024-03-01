package com.fdlv.gmd.api.dto.mobile;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class MobileTrackParcoursFlatDTO {
    private Long id;
    private Long mobileStatParcoursId;
    private Long eventId;
    private Long stageId;

    private LocalDateTime dateAction;
    private boolean defiPartage;

    private boolean challengeAccepte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMobileStatParcoursId() {
        return mobileStatParcoursId;
    }

    public void setMobileStatParcoursId(Long mobileStatParcoursId) {
        this.mobileStatParcoursId = mobileStatParcoursId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
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

    public boolean isChallengeAccepte() {
        return challengeAccepte;
    }

    public void setChallengeAccepte(boolean challengeAccepte) {
        this.challengeAccepte = challengeAccepte;
    }

    @Override
    public String toString() {
        return "MobileTrackParcoursFlatDTO{" +
                "id=" + id +
                ", mobileStatParcoursId=" + mobileStatParcoursId +
                ", eventId=" + eventId +
                ", stageId=" + stageId +
                ", dateAction=" + dateAction +
                ", defiPartage=" + defiPartage +
                '}';
    }
}
