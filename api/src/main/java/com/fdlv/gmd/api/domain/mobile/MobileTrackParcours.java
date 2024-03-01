package com.fdlv.gmd.api.domain.mobile;

import com.fdlv.gmd.api.domain.Event;
import com.fdlv.gmd.api.domain.Question;
import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.domain.Stage;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "mobile_track_parcours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MobileTrackParcours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mtp_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mtp_msp_id", referencedColumnName = "msp_id")
    private MobileStatParcours mobileStatParcours;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mtp_event_id", referencedColumnName = "id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mtp_stage_id", referencedColumnName = "id")
    private Stage stage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mtp_quizz_id", referencedColumnName = "id")
    private Quizz quizz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mtp_question_id", referencedColumnName = "id")
    private Question question;

    @Column(name = "mtp_reponse_ok", length = 1)
    private String reponseOk;

    @Column(name = "mtp_date_action", nullable = false)
    private LocalDateTime dateAction = LocalDateTime.now();

    @Column(name = "mtp_defi_partage", nullable = false, columnDefinition = "bit default b'1'")
    private boolean defiPartage;

    @Column(name = "mtp_challenge_accepte")
    private boolean challengeAccepte;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MobileStatParcours getMobileStatParcours() {
        return mobileStatParcours;
    }

    public void setMobileStatParcours(MobileStatParcours mobileStatParcours) {
        this.mobileStatParcours = mobileStatParcours;
    }

    public boolean isChallengeAccepte() {
        return challengeAccepte;
    }

    public void setChallengeAccepte(boolean challengeAccepte) {
        this.challengeAccepte = challengeAccepte;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Quizz getQuizz() {
        return quizz;
    }

    public void setQuizz(Quizz quizz) {
        this.quizz = quizz;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
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
        return "MobileTrackParcours{" +
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
