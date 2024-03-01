package com.fdlv.gmd.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ref_logo_teaser")
public class RefLogoTeaser implements Serializable {

    private static final long serialVersionUID = -3814947435717776403L;

    @Id
    @Column(name = "rlt_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rlt_zip_code")
    private String zipCode;

    @Column(name = "rlt_city")
    private String city;

    @Column(name = "rlt_type_media")
    private String typeMedia;

    @Column(name = "rlt_label")
    private String label;

    @Column(name = "rlt_url")
    private String url;

    @Column(name = "rlt_fus_id")
    private Long rltFusId;

    @Column(name = "rlt_logo_teaser_valide")
    private String rltLogoTeaserValide;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRltFusId() {
        return rltFusId;
    }

    public void setRltFusId(Long rltFusId) {
        this.rltFusId = rltFusId;
    }

    public String getRltLogoTeaserValide() {
        return rltLogoTeaserValide;
    }

    public void setRltLogoTeaserValide(String rltLogoTeaserValide) {
        this.rltLogoTeaserValide = rltLogoTeaserValide;
    }

}
