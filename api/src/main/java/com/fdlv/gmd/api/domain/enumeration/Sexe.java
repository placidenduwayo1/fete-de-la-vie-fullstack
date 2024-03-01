package com.fdlv.gmd.api.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Sexe {
    UNKNOWN(0, "Inconnu"),
    MALE(1, "Masculin"),
    FEMALE(2, "Féminin");

    private final Integer dbValue;
    private final String ihmName;

    Sexe(Integer value, String name) {
        this.dbValue = value;
        this.ihmName = name;
    }

    public Integer getDbValue() {
        return this.dbValue;
    }

    public String getIhmName() {
        return this.ihmName;
    }

    public static Sexe getSexeFromValue(Integer value){

        return Arrays.stream(values()).filter(sx -> sx.dbValue.equals(value)).findFirst().get();

    }
}
