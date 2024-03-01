package com.fdlv.gmd.api.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.domain.Theme;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Event} entity.
 */
public class EventDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    @NotNull
    private String description;

    @NotNull
    private Theme theme;

    @NotNull
    private String address;

    @NotNull
    private String zipCode;

    @NotNull
    private String city;

    private String cityLogoUrl;

    @NotNull
    private LocalDate startAt;

    @NotNull
    private LocalDate endAt;

    private String labelFinParcours;

    private String finParcoursPdf;

    @NotNull
    private Boolean otherEvent;

    @NotNull
    private Boolean validatedEvent;

    @NotNull
    private Boolean fixOrder;

    private String usefulInformation;

    private String eventTeaserUrl;
    private String codeParcours;

    @NotNull
    private Boolean evtDemo;

    private Long numParcours;

    private Long evtFcoFusId;

    private String evtFcoDatePropose;

    private String evtFcoDateValide;

    private Long evtFcoId;

    private Set<StageDetailsDTO> stages;

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

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
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

    public Boolean isOtherEvent() {
        return otherEvent;
    }

    public void setOtherEvent(Boolean otherEvent) {
        this.otherEvent = otherEvent;
    }

    public Boolean isValidatedEvent() {
        return validatedEvent;
    }

    public void setValidatedEvent(Boolean validatedEvent) {
        this.validatedEvent = validatedEvent;
    }

    public Boolean isFixOrder() {
        return fixOrder;
    }

    public void setFixOrder(Boolean fixOrder) {
        this.fixOrder = fixOrder;
    }

    public String getUsefulInformation() {
        return usefulInformation;
    }

    public void setUsefulInformation(String usefulInformation) {
        this.usefulInformation = usefulInformation;
    }

    public String getCodeParcours() {
        return codeParcours;
    }

    public void setCodeParcours(String codeParcours) {
        this.codeParcours = codeParcours;
    }

    public Boolean isEvtDemo() {
        return evtDemo;
    }

    public void setEvtDemo(Boolean evtDemo) {
        this.evtDemo = evtDemo;
    }

    public String getEventTeaserUrl() {
        return eventTeaserUrl;
    }

    public void setEventTeaserUrl(String eventTeaserUrl) {
        this.eventTeaserUrl = eventTeaserUrl;
    }

    public Long getNumParcours() {
        return this.numParcours;
    }

    public void setNumParcours(Long numParcours) {
        this.numParcours = numParcours;
    }

    public Long getevtFcoFusId() {
        return this.evtFcoFusId;
    }

    public void setevtFcoFusId(Long evtFcoFusId) {
        this.evtFcoFusId = evtFcoFusId;
    }

    public String getevtFcoDatePropose() {
        return this.evtFcoDatePropose;
    }

    public void setevtFcoDatePropose(String evtFcoDatePropose) {
        this.evtFcoDatePropose = evtFcoDatePropose;
    }

    public String getevtFcoDateValide() {
        return this.evtFcoDateValide;
    }

    public void setevtFcoDateValide(String evtFcoDateValide) {
        this.evtFcoDateValide = evtFcoDateValide;
    }

    public Long getevtFcoId() {
        return this.evtFcoId;
    }

    public void setevtFcoId(Long evtFcoId) {
        this.evtFcoId = evtFcoId;
    }

    public Set<StageDetailsDTO> getStages() {
        return stages;
    }

    public void setStages(Set<StageDetailsDTO> stages) {
        this.stages = stages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDetailsDTO)) {
            return false;
        }

        EventDetailsDTO eventDTO = (EventDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + id +
            ", label='" + label + '\'' +
            ", description='" + description + '\'' +
            ", theme='" + theme + '\'' +
            ", address='" + address + '\'' +
            ", zipCode='" + zipCode + '\'' +
            ", city='" + city + '\'' +
            ", cityLogoUrl='" + cityLogoUrl + '\'' +
            ", startAt=" + startAt +
            ", endAt=" + endAt +
            ", labelFinParcours=" + labelFinParcours +
            ", finParcoursPdf=" + finParcoursPdf +
            ", stages=" + stages +
            ", usefulInformation='" + getUsefulInformation() + "'" +
            ", eventTeaserUrl='" + getEventTeaserUrl() + "'" +
            ", codeParcours='" + getCodeParcours() + "'" +
            ", evtDemo='" + isEvtDemo() + "'" +
            ", evtFcoFusId='" + getevtFcoFusId() + "'" +
            ", numParcours='" + getNumParcours() + "'" +
            ", evtFcoDatePropose='" + getevtFcoDatePropose() + "'" +
            ", evtFcoDateValide='" + getevtFcoDateValide() + "'" +
            ", evtFcoId='" + getevtFcoId() + "'" +
            '}';
    }
}
