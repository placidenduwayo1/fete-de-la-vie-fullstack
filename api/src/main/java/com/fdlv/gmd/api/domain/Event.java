package com.fdlv.gmd.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "city_logo_url")
    private String cityLogoUrl;

    @Column(name = "label_fin_parcours")
    private String labelFinParcours;

    @Column(name = "fin_parcours_pdf")
    private String finParcoursPdf;

    @NotNull
    @Column(name = "start_at", nullable = false)
    private LocalDate startAt;

    @NotNull
    @Column(name = "end_at", nullable = false)
    private LocalDate endAt;

    @NotNull
    @Column(name = "other_event", nullable = false, columnDefinition = "BOOLEAN")
    private boolean otherEvent;

    @NotNull
    @Column(name = "validated_event", nullable = false, columnDefinition = "BOOLEAN")
    private boolean validatedEvent;

    @NotNull
    @Column(name = "fix_order", nullable = false, columnDefinition = "BOOLEAN")
    private boolean fixOrder;

    @Column(name = "useful_information", nullable = false)
    private String usefulInformation;

    @Column(name = "event_teaser_url")
    private String eventTeaserUrl;

    @Column(name = "code_parcours")
    private String codeParcours;

    @Column(name = "evt_demo", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean evtDemo;

    @Column(name = "num_parcours", nullable = true)
    private Long numParcours;

    @Column(name = "event_fco_fus_id", nullable = true)
    private Long evtFcoFusId;


    @Column(name = "event_fco_date_propose", nullable = true)
    private LocalDateTime evtFcoDatePropose;

    @JsonIgnore
    @Column(name = "event_fco_date_validation", nullable = true)
    private LocalDateTime evtFcoDateValide;

    @Column(name = "event_fco_id", nullable = true)
    private Long evtFcoId;

    @Column(name = "event_latitude", nullable = false)
    private Double evtLatitude;

    @Column(name = "event_longitude", nullable = false)
    private Double evtLongitude;

    @ManyToOne
    @JsonIgnoreProperties(value = { "events" }, allowSetters = true)
    private Theme theme;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REFRESH)
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "quizz" }, allowSetters = true)
    private Set<Stage> stages = new HashSet<>();


    public Event id(Long id) {
        this.id = id;
        return this;
    }

    public Event label(String label){
        this.label = label;
        return this;
    }
    public Boolean isEvtDemo() {
        return evtDemo;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public Event address(String address) {
        this.address = address;
        return this;
    }
    public Event zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }
    public Event finParcoursPdf(String finParcoursPdf) {
        this.finParcoursPdf = finParcoursPdf;
        return this;
    }
    public Event startAt(LocalDate startAt) {
        this.startAt = startAt;
        return this;
    }
    public Event endAt(LocalDate endAt) {
        this.endAt = endAt;
        return this;
    }
    public Event otherEvent(boolean otherEvent) {
        this.otherEvent = otherEvent;
        return this;
    }

    public Event validatedEvent(boolean validatedEvent) {
        this.validatedEvent = validatedEvent;
        return this;
    }
    public Event fixOrder(boolean fixOrder) {
        this.fixOrder = fixOrder;
        return this;
    }
    public Event usefulInformation(String usefulInformation) {
        this.usefulInformation = usefulInformation;
        return this;
    }
    public Event eventTeaserUrl(String eventTeaserUrl) {
        this.eventTeaserUrl = eventTeaserUrl;
        return this;
    }
    public Event codeParcours(String codeParcours) {
        this.codeParcours = codeParcours;
        return this;
    }
    public Event numParcours(Long numParcours) {
        this.numParcours = numParcours;
        return this;
    }
    public Event evtFcoFusId(Long evtFcoFusId) {
        this.evtFcoFusId = evtFcoFusId;
        return this;
    }
    public Event evtFcoDatePropose(String evtFcoDatePropose) {
        this.evtFcoDatePropose = LocalDateTime.parse(evtFcoDatePropose);
        return this;
    }
    public Event evtevtFcoDateValide(String evtFcoDateValide) {
        this.evtFcoDateValide = LocalDateTime.parse(evtFcoDateValide);
        return this;
    }
    public Event theme(Theme theme) {
        this.setTheme(theme);
        return this;
    }
    public Event stages(Set<Stage> stages) {
        this.setStages(stages);
        return this;
    }
    public Event addStage(Stage stage) {
        this.stages.add(stage);
        stage.setEvent(this);
        return this;
    }

    public Event removeStage(Stage stage) {
        this.stages.remove(stage);
        stage.setEvent(null);
        return this;
    }

    public Event labelFinParcours(String labelFinParcours) {
        this.labelFinParcours = labelFinParcours;
        return this;
    }

    public Event city(String city) {
        this.city = city;
        return this;
    }
    public Event cityLogoUrl(String cityLogoUrl) {
        this.cityLogoUrl = cityLogoUrl;
        return this;
    }
    public Event evtDemo(Boolean evtDemo) {
        this.evtDemo = evtDemo;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityLogoUrl() {
        return cityLogoUrl;
    }

    public void setCityLogoUrl(String cityLogoUrl) {
        this.cityLogoUrl = cityLogoUrl;
    }

    public String getLabelFinParcours() {
        return labelFinParcours;
    }

    public void setLabelFinParcours(String labelFinParcours) {
        this.labelFinParcours = labelFinParcours;
    }

    public String getFinParcoursPdf() {
        return finParcoursPdf;
    }

    public void setFinParcoursPdf(String finParcoursPdf) {
        this.finParcoursPdf = finParcoursPdf;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDate startAt) {
        this.startAt = startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDate endAt) {
        this.endAt = endAt;
    }

    public boolean isOtherEvent() {
        return otherEvent;
    }

    public void setOtherEvent(boolean otherEvent) {
        this.otherEvent = otherEvent;
    }

    public boolean isValidatedEvent() {
        return validatedEvent;
    }

    public void setValidatedEvent(boolean validatedEvent) {
        this.validatedEvent = validatedEvent;
    }

    public boolean isFixOrder() {
        return fixOrder;
    }

    public void setfixOrder(boolean fixOrder) {
        this.fixOrder = fixOrder;
    }

    public String getUsefulInformation() {
        return usefulInformation;
    }

    public void setUsefulInformation(String usefulInformation) {
        this.usefulInformation = usefulInformation;
    }

    public String getEventTeaserUrl() {
        return eventTeaserUrl;
    }

    public void setEventTeaserUrl(String eventTeaserUrl) {
        this.eventTeaserUrl = eventTeaserUrl;
    }

    public String getCodeParcours() {
        return codeParcours;
    }

    public void setCodeParcours(String codeParcours) {
        this.codeParcours = codeParcours;
    }

    public Boolean getEvtDemo() {
        return evtDemo;
    }

    public void setEvtDemo(Boolean evtDemo) {
        this.evtDemo = evtDemo;
    }

    public Long getNumParcours() {
        return numParcours;
    }

    public void setNumParcours(Long numParcours) {
        this.numParcours = numParcours;
    }

    public Long getEvtFcoFusId() {
        return evtFcoFusId;
    }

    public void setEvtFcoFusId(Long evtFcoFusId) {
        this.evtFcoFusId = evtFcoFusId;
    }

    public String getevtFcoDatePropose() {
        if(evtFcoDatePropose != null)
            return evtFcoDatePropose.toString();
        return null;
    }

    public void setEvtFcoDatePropose(LocalDateTime evtFcoDatePropose) {
        this.evtFcoDatePropose = evtFcoDatePropose;
    }

    public LocalDateTime getEvtFcoDateValide() {
        return evtFcoDateValide;
    }

    public void setEvtFcoDateValide(LocalDateTime evtFcoDateValide) {
        this.evtFcoDateValide = evtFcoDateValide;
    }

    public Long getEvtFcoId() {
        return evtFcoId;
    }

    public void setEvtFcoId(Long evtFcoId) {
        this.evtFcoId = evtFcoId;
    }

    public Double getEvtLatitude() {
        return evtLatitude;
    }

    public void setEvtLatitude(Double evtLatitude) {
        this.evtLatitude = evtLatitude;
    }

    public Double getEvtLongitude() {
        return evtLongitude;
    }

    public void setEvtLongitude(Double evtLongitude) {
        this.evtLongitude = evtLongitude;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Set<Stage> getStages() {
        return stages;
    }

    public void setStages(Set<Stage> stages) {
        this.stages = stages;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", cityLogoUrl='" + cityLogoUrl + '\'' +
                ", labelFinParcours='" + labelFinParcours + '\'' +
                ", finParcoursPdf='" + finParcoursPdf + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", otherEvent=" + otherEvent +
                ", validatedEvent=" + validatedEvent +
                ", fixOrder=" + fixOrder +
                ", usefulInformation='" + usefulInformation + '\'' +
                ", eventTeaserUrl='" + eventTeaserUrl + '\'' +
                ", codeParcours='" + codeParcours + '\'' +
                ", evtDemo=" + evtDemo +
                ", numParcours=" + numParcours +
                ", evtFcoFusId=" + evtFcoFusId +
                ", evtFcoDatePropose=" + evtFcoDatePropose +
                ", evtFcoDateValide=" + evtFcoDateValide +
                ", evtFcoId=" + evtFcoId +
                ", evtLatitude=" + evtLatitude +
                ", evtLongitude=" + evtLongitude +
                ", theme=" + theme +
                ", stages=" + stages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, label, description, address, zipCode, city, cityLogoUrl, labelFinParcours, finParcoursPdf, startAt, endAt, otherEvent, validatedEvent, fixOrder, usefulInformation, eventTeaserUrl, codeParcours, evtDemo, numParcours, evtFcoFusId, evtFcoDatePropose, evtFcoDateValide, evtFcoId, evtLatitude, evtLongitude, theme, stages);
    }
}
