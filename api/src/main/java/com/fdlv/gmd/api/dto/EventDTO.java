package com.fdlv.gmd.api.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Event} entity.
 */
public class EventDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    @NotNull
    private String description;

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
    private boolean otherEvent;

    @NotNull
    private boolean validatedEvent;

    @NotNull
    private boolean fixOrder;

    private String usefulInformation;

    private String eventTeaserUrl;

    private String codeParcours;

    @NotNull
    private boolean evtDemo;

    private Long numParcours;

    private Long evtFcoFusId;

    private String evtFcoDatePropose;

    private String evtFcoDateValide;

    private Long evtFcoId;

    private ThemeDTO theme;

    private Double evtLongitude;

    private Double evtLatitude;



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

    public void setFixOrder(boolean fixOrder) {
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

    public ThemeDTO getTheme() {
        return theme;
    }

    public void setTheme(ThemeDTO theme) {
        this.theme = theme;
    }

    public String getCodeParcours() {
        return codeParcours;
    }

    public void setCodeParcours(String codeParcours) {
        this.codeParcours = codeParcours;
    }

    public boolean isEvtDemo() {
        return evtDemo;
    }

    public void setEvtDemo(boolean evtDemo) {
        this.evtDemo = evtDemo;
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

    public Double getEvtLongitude() {
        return evtLongitude;
    }

    public void setEvtLongitude(Double evtLongitude) {
        this.evtLongitude = evtLongitude;
    }

    public Double getEvtLatitude() {
        return evtLatitude;
    }

    public void setEvtLatitude(Double evtLatitude) {
        this.evtLatitude = evtLatitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
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
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", city='" + getCity() + "'" +
            ", cityLogoUrl='" + getCityLogoUrl() + "'" +
            ", startAt='" + getStartAt() + "'" +
            ", endAt='" + getEndAt() + "'" +
            ", labelFinParcours='" + getLabelFinParcours() + "'" +
            ", finParcoursPdf='" + getFinParcoursPdf() + "'" +
            ", otherEvent='" + isOtherEvent() + "'" +
            ", validated='" + isValidatedEvent() + "'" +
            ", fixOrder='" + isFixOrder() + "'" +
            ", theme=" + getTheme() +
            ", usefulInformation='" + getUsefulInformation() + "'" +
            ", eventTeaserUrl='" + getEventTeaserUrl() + "'" +
            ", codeParcours='" + getCodeParcours() + "'" +
            ", evtDemo='" + isEvtDemo() + "'" +
            ", evtFcoFusId='" + getevtFcoFusId() + "'" +
            ", numParcours='" + getNumParcours() + "'" +
            ", evtFcoDatePropose='" + getevtFcoDatePropose() + "'" +
            ", evtFcoDateValide='" + getevtFcoDateValide() + "'" +
            ", evtFcoId='" + getevtFcoId() + "'" +
            "}";
    }
}
