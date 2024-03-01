package com.fdlv.gmd.api.domain.enumeration.forum;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Suivi {
    EN_COURS_DE_MISE_EN_OEUVRE(0, "En cours de mise en oeuvre"),
    AVANCEMENT_PARTIEL(1, "Avancement partiel"),
    AVANCEMENT_COMPLET(2, "Avancement complet"),
    VALIDE(3, "Validé / A organiser"),
    TERMINE(9,"Terminé");

    private final Integer dbValue;
    private final String ihmName;

    Suivi(Integer value, String name) {
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
