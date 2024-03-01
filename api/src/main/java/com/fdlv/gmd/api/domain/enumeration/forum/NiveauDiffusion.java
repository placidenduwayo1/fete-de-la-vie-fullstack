package com.fdlv.gmd.api.domain.enumeration.forum;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NiveauDiffusion {

    INTERNE_FDLV(1, "Document à diffuser uniquement en interne FDLV"),
    RESTREINT_1_2(2, "Document à diffusion restreinte auprès de certaines structures/acteurs de niveau 2"),
    RESTREINT_1_2_3(3, "Document à diffusion restreinte auprès de certaines structures/acteurs de niveau 3"),
    RESTREINT_1_2_3_4(4, "Document à diffusion restreinte auprès de certaines structures/acteurs de niveaux 4"),
    RESTREINT_1_2_3_4_5(5,"Document à diffusion uniquement aux acteurs intervenant au sein du Forum et aux structures/acteurs de niveau 5"),
    PUBLIQUE(9,"Document à diffuser au niveau tout public");

    private final Integer dbValue;
    private final String ihmName;

    NiveauDiffusion(Integer value, String name) {
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
