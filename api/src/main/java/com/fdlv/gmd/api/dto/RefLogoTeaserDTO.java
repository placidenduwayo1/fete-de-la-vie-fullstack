package com.fdlv.gmd.api.dto;

import java.io.Serializable;

public class RefLogoTeaserDTO implements Serializable {

    private static final long serialVersionUID = -7404785502616095941L;

    private Long id;
    private String zipCode;
    private String city;
    private String typeMedia;
    private String label;
    private String url;
    private Long rltFusId;
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

    @Override
    public String toString() {
        return "RefLogoTeaserDTO{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", typeMedia='" + typeMedia + '\'' +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                ", rltFusId=" + rltFusId +
                ", rltLogoTeaserValide='" + rltLogoTeaserValide + '\'' +
                '}';
    }
}
