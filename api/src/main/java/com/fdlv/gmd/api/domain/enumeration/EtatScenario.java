package com.fdlv.gmd.api.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EtatScenario {
    SAUVEGARDE(1, "Sauvegardé"),
    SOUMIS(2, "Soumis"),
    VALIDE(3, "Validé"),
    REJETE(4, "Rejeté"),
    DESACTIVE(9,"Desactivé");

    private final Integer dbValue;
    private final String ihmName;

    EtatScenario(Integer value, String name) {
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
