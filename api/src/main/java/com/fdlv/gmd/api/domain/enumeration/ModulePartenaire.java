package com.fdlv.gmd.api.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ModulePartenaire {
    WEB(1,"Site Web"),
    MOBILE(2,"App Mobile"),
    WEB_MOBILE(3,"Site Web et App Mobile"),
    NON_AFFICHE(0,"Non affiché");

    private final Integer dbValue;
    private String ihmName;

    ModulePartenaire(Integer value, String name){
        this.dbValue=value;
        this.ihmName=name;

    }

    public Integer getDbValue() {
        return this.dbValue;
    }

    public String getIhmName() {
        return this.ihmName;
    }
    
}
