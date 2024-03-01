package com.fdlv.gmd.api.dto.fdlv;

import com.fdlv.gmd.api.dto.ThemeDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Event} entity.
 */
public class EventFDLVDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    @NotNull
    private String description;

    @NotNull
    private String address;

    @NotNull
    private String zip_code;

    @NotNull
    private String city;

    private String city_logo_url;

    @NotNull
    private LocalDate start_at;

    @NotNull
    private LocalDate end_at;

    private String label_fin_parcours;

    private String fin_parcours_pdf;

    @NotNull
    private boolean other_event;

    @NotNull
    private boolean validated_event;

    @NotNull
    private boolean fix_order;

    private String useful_information;

    private String event_teaser_url;

    private String code_parcours;

    @NotNull
    private boolean evt_demo;

    private Long num_parcours;

    private Long event_fco_fus_id;

    @Nullable
    private LocalDateTime event_fco_date_propose;
    @Nullable
    private LocalDateTime event_fco_date_validation;

    private Long event_fco_id;

    private ThemeDTO theme;

    private Double event_longitude;

    private Double event_latitude;
    private Long theme_id;

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

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_logo_url() {
        return city_logo_url;
    }

    public void setCity_logo_url(String city_logo_url) {
        this.city_logo_url = city_logo_url;
    }

    public LocalDate getStart_at() {
        return start_at;
    }

    public void setStart_at(LocalDate start_at) {
        this.start_at = start_at;
    }

    public LocalDate getEnd_at() {
        return end_at;
    }

    public void setEnd_at(LocalDate end_at) {
        this.end_at = end_at;
    }

    public String getLabel_fin_parcours() {
        return label_fin_parcours;
    }

    public void setLabel_fin_parcours(String label_fin_parcours) {
        this.label_fin_parcours = label_fin_parcours;
    }

    public String getFin_parcours_pdf() {
        return fin_parcours_pdf;
    }

    public void setFin_parcours_pdf(String fin_parcours_pdf) {
        this.fin_parcours_pdf = fin_parcours_pdf;
    }

    public boolean isOther_event() {
        return other_event;
    }

    public void setOther_event(boolean other_event) {
        this.other_event = other_event;
    }

    public boolean isValidated_event() {
        return validated_event;
    }

    public void setValidated_event(boolean validated_event) {
        this.validated_event = validated_event;
    }

    public boolean isFix_order() {
        return fix_order;
    }

    public void setFix_order(boolean fix_order) {
        this.fix_order = fix_order;
    }

    public String getUseful_information() {
        return useful_information;
    }

    public void setUseful_information(String useful_information) {
        this.useful_information = useful_information;
    }

    public String getEvent_teaser_url() {
        return event_teaser_url;
    }

    public void setEvent_teaser_url(String event_teaser_url) {
        this.event_teaser_url = event_teaser_url;
    }

    public String getCode_parcours() {
        return code_parcours;
    }

    public void setCode_parcours(String code_parcours) {
        this.code_parcours = code_parcours;
    }

    public boolean isEvt_demo() {
        return evt_demo;
    }

    public void setEvt_demo(boolean evt_demo) {
        this.evt_demo = evt_demo;
    }

    public Long getNum_parcours() {
        return num_parcours;
    }

    public void setNum_parcours(Long num_parcours) {
        this.num_parcours = num_parcours;
    }

    public Long getEvent_fco_fus_id() {
        return event_fco_fus_id;
    }

    public void setEvent_fco_fus_id(Long event_fco_fus_id) {
        this.event_fco_fus_id = event_fco_fus_id;
    }

    public LocalDateTime getEvent_fco_date_propose() {
        return event_fco_date_propose;
    }

    public void setEvent_fco_date_propose(LocalDateTime event_fco_date_propose) {
        this.event_fco_date_propose = event_fco_date_propose;
    }

    public LocalDateTime getEvent_fco_date_validation() {
        return event_fco_date_validation;
    }

    public void setEvent_fco_date_validation(LocalDateTime event_fco_date_validation) {
        this.event_fco_date_validation = event_fco_date_validation;
    }

    public Long getEvent_fco_id() {
        return event_fco_id;
    }

    public void setEvent_fco_id(Long event_fco_id) {
        this.event_fco_id = event_fco_id;
    }

    public ThemeDTO getTheme() {
        return theme;
    }

    public void setTheme(ThemeDTO theme) {
        this.theme = theme;
    }

    public Double getEvent_longitude() {
        return event_longitude;
    }

    public void setEvent_longitude(Double event_longitude) {
        this.event_longitude = event_longitude;
    }

    public Double getEvent_latitude() {
        return event_latitude;
    }

    public void setEvent_latitude(Double event_latitude) {
        this.event_latitude = event_latitude;
    }

    public Long getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(Long theme_id) {
        this.theme_id = theme_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventFDLVDTO that = (EventFDLVDTO) o;
        return other_event == that.other_event && validated_event == that.validated_event && fix_order == that.fix_order && evt_demo == that.evt_demo && Objects.equals(id, that.id) && Objects.equals(label, that.label) && Objects.equals(description, that.description) && Objects.equals(address, that.address) && Objects.equals(zip_code, that.zip_code) && Objects.equals(city, that.city) && Objects.equals(city_logo_url, that.city_logo_url) && Objects.equals(start_at, that.start_at) && Objects.equals(end_at, that.end_at) && Objects.equals(label_fin_parcours, that.label_fin_parcours) && Objects.equals(fin_parcours_pdf, that.fin_parcours_pdf) && Objects.equals(useful_information, that.useful_information) && Objects.equals(event_teaser_url, that.event_teaser_url) && Objects.equals(code_parcours, that.code_parcours) && Objects.equals(num_parcours, that.num_parcours) && Objects.equals(event_fco_fus_id, that.event_fco_fus_id) && Objects.equals(event_fco_date_propose, that.event_fco_date_propose) && Objects.equals(event_fco_date_validation, that.event_fco_date_validation) && Objects.equals(event_fco_id, that.event_fco_id) && Objects.equals(theme, that.theme) && Objects.equals(event_longitude, that.event_longitude) && Objects.equals(event_latitude, that.event_latitude) && Objects.equals(theme_id, that.theme_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, description, address, zip_code, city, city_logo_url, start_at, end_at, label_fin_parcours, fin_parcours_pdf, other_event, validated_event, fix_order, useful_information, event_teaser_url, code_parcours, evt_demo, num_parcours, event_fco_fus_id, event_fco_date_propose, event_fco_date_validation, event_fco_id, theme, event_longitude, event_latitude, theme_id);
    }

    @Override
    public String toString() {
        return "EventFDLVDTO{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", city='" + city + '\'' +
                ", city_logo_url='" + city_logo_url + '\'' +
                ", start_at=" + start_at +
                ", end_at=" + end_at +
                ", label_fin_parcours='" + label_fin_parcours + '\'' +
                ", fin_parcours_pdf='" + fin_parcours_pdf + '\'' +
                ", other_event=" + other_event +
                ", validated_event=" + validated_event +
                ", fix_order=" + fix_order +
                ", useful_information='" + useful_information + '\'' +
                ", event_teaser_url='" + event_teaser_url + '\'' +
                ", code_parcours='" + code_parcours + '\'' +
                ", evt_demo=" + evt_demo +
                ", num_parcours=" + num_parcours +
                ", event_fco_fus_id=" + event_fco_fus_id +
                ", event_fco_date_propose=" + event_fco_date_propose +
                ", event_fco_date_validation=" + event_fco_date_validation +
                ", event_fco_id=" + event_fco_id +
                ", theme=" + theme +
                ", event_longitude=" + event_longitude +
                ", event_latitude=" + event_latitude +
                ", theme_id=" + theme_id +
                '}';
    }
}
