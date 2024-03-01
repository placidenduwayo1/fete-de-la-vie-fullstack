package com.fdlv.gmd.api.domain.mobile;

import java.time.LocalDateTime;

public class EventStat {
    
    private Long eventId;
    private String eventLabel;
    private LocalDateTime firstParticipationDate;
    private LocalDateTime lastParticipationDate;
    private Long nbrOfParticipants;
    private Long shareCount;
    
    public EventStat(Long eventId, String eventLabel, LocalDateTime firstParticipationDate,
            LocalDateTime lastParticipationDate, Long nbrOfParticipants, Long shareCount) {
        this.eventId = eventId;
        this.eventLabel = eventLabel;
        this.firstParticipationDate = firstParticipationDate;
        this.lastParticipationDate = lastParticipationDate;
        this.nbrOfParticipants = nbrOfParticipants;
        this.shareCount = shareCount;
    }

    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public String getEventLabel() {
        return eventLabel;
    }
    public void setEventLabel(String eventLabel) {
        this.eventLabel = eventLabel;
    }
    public LocalDateTime getFirstParticipationDate() {
        return firstParticipationDate;
    }
    public void setFirstParticipationDate(LocalDateTime firstParticipationDate) {
        this.firstParticipationDate = firstParticipationDate;
    }
    public LocalDateTime getLastParticipationDate() {
        return lastParticipationDate;
    }
    public void setLastParticipationDate(LocalDateTime lastParticipationDate) {
        this.lastParticipationDate = lastParticipationDate;
    }
    public Long getNbrOfParticipants() {
        return nbrOfParticipants;
    }
    public void setNbrOfParticipants(Long nbrOfParticipants) {
        this.nbrOfParticipants = nbrOfParticipants;
    }
    public Long getShareCount() {
        return shareCount;
    }
    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }

    
}
