package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;

public class StandBanniereDTO implements Serializable {

    private Long id;
    private StructureDTO structure;
    private String banniereLibelle;
    private String banniereUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StructureDTO getStructure() {
        return structure;
    }

    public void setStructure(StructureDTO structure) {
        this.structure = structure;
    }

    public String getBanniereLibelle() {
        return banniereLibelle;
    }

    public void setBanniereLibelle(String banniereLibelle) {
        this.banniereLibelle = banniereLibelle;
    }

    public String getBanniereUrl() {
        return banniereUrl;
    }

    public void setBanniereUrl(String banniereUrl) {
        this.banniereUrl = banniereUrl;
    }

    @Override
    public String toString() {
        return "StandBanniereDTO{" +
                "id=" + id +
                ", structure=" + structure +
                ", banniereLibelle='" + banniereLibelle + '\'' +
                ", banniereUrl='" + banniereUrl + '\'' +
                '}';
    }
}