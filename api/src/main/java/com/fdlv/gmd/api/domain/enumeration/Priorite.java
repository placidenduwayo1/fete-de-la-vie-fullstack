package com.fdlv.gmd.api.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Priorite {
    HAUT_DE_LA_PAGE(2,"Haut de la Page"),
    BAS_DE_LA_PAGE( 1,"Bas de la Page");

    private final Integer dbValue;
    private String ihmName;

    Priorite(Integer value, String name){
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
