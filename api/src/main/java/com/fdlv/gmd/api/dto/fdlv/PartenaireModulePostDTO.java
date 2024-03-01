package com.fdlv.gmd.api.dto.fdlv;

import java.util.Objects;

/**
 * This class is use to convert the body from the weird post on /partenaires/module to a understable class by java
 */
public class PartenaireModulePostDTO {
    private Integer partner_mobile;
    private Integer partner_web_mobile;
    private Integer partner_web;


    public Integer getPartner_mobile() {
        return partner_mobile;
    }

    public void setPartner_mobile(Integer partner_mobile) {
        this.partner_mobile = partner_mobile;
    }

    public Integer getPartner_web_mobile() {
        return partner_web_mobile;
    }

    public void setPartner_web_mobile(Integer partner_web_mobile) {
        this.partner_web_mobile = partner_web_mobile;
    }

    public Integer getPartner_web() {
        return partner_web;
    }

    public void setPartner_web(Integer partner_web) {
        this.partner_web = partner_web;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartenaireModulePostDTO that = (PartenaireModulePostDTO) o;
        return Objects.equals(partner_mobile, that.partner_mobile) && Objects.equals(partner_web_mobile, that.partner_web_mobile) && Objects.equals(partner_web, that.partner_web);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partner_mobile, partner_web_mobile, partner_web);
    }

    @Override
    public String toString() {
        return "PartenaireModulePostDTO{" +
                "partner_mobile=" + partner_mobile +
                ", partner_web_mobile=" + partner_web_mobile +
                ", partner_web=" + partner_web +
                '}';
    }
}
