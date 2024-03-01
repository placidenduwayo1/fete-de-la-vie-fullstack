package com.fdlv.gmd.api.dto.mobile;

import com.fdlv.gmd.api.dto.EventDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MobileStatParcoursDTO implements Serializable {

    private Long id;
    private MobileUserDTO mobileUser;
    private EventDTO event;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private boolean parcoursPartage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MobileUserDTO getMobileUser() {
        return mobileUser;
    }

    public void setMobileUser(MobileUserDTO mobileUser) {
        this.mobileUser = mobileUser;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
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
        return "MobileStatParcoursDTO{" +
                "id=" + id +
                ", mobileUser=" + mobileUser +
                ", event=" + event +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", parcoursPartage=" + parcoursPartage +
                '}';
    }
}
