package com.fdlv.gmd.api.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Profil {
    ORGANISATEUR(1, "Organisateur"),
    PROFESSIONNEL(2, "Professionnel"),
    BENEVOLE(3, "Bénévole"),
    PUBLIC(4, "Public"),
    ADMINISTRATEUR(9, "Administrateur Organisateur");

    private final Integer dbValue;
    private final String ihmName;

    Profil(Integer value, String name) {
        this.dbValue = value;
        this.ihmName = name;
    }

    public Integer getDbValue() {
        return this.dbValue;
    }

    public String getIhmName() {
        return this.ihmName;
    }
}
