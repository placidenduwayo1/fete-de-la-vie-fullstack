package com.fdlv.gmd.api.dto.mobile;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MobileStatParcoursFlatDTO implements Serializable {

    private Long id;
    private Long mobileUserId;
    private Long eventId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private boolean parcoursPartage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isParcoursPartage() {
        return parcoursPartage;
    }

    public void setParcoursPartage(boolean parcoursPartage) {
        this.parcoursPartage = parcoursPartage;
    }

    @Override
    public String toString() {
        return "MobileStatParcoursFlatDTO{" +
                "id=" + id +
                ", mobileUserId=" + mobileUserId +
                ", eventId=" + eventId +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", parcoursPartage=" + parcoursPartage +
                '}';
    }
}
