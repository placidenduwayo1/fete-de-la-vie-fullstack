package com.fdlv.gmd.api.domain.mobile;

import com.fdlv.gmd.api.domain.Event;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "mobile_stat_parcours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MobileStatParcours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msp_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "msp_mus_id_user", referencedColumnName = "mus_id_user", nullable = false)
    private MobileUser mobileUser;

    @ManyToOne
    @JoinColumn(name = "msp_event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    @Column(name = "msp_date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "msp_date_fin", nullable = false)
    private LocalDateTime dateFin;

    @Column(name = "msp_parcours_partage", nullable = false, columnDefinition = "bit default b'1'")
    private boolean parcoursPartage;


    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MobileUser getMobileUser() {
        return mobileUser;
    }

    public void setMobileUser(MobileUser mobileUser) {
        this.mobileUser = mobileUser;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
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
    public int hashCode() {

        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MobileStatParcours)) {
            return false;
        }
        return id != null && id.equals(((MobileStatParcours) obj).id);
    }

    @Override
    public String toString() {
        return "MobileStatParcours{" +
                "id=" + id +
                ", mobileUser=" + mobileUser +
                ", event=" + event +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", parcoursPartage=" + parcoursPartage +
                '}';
    }
}
