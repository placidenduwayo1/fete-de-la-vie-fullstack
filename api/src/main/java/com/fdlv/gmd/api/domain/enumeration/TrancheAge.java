package com.fdlv.gmd.api.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TrancheAge {
    UNKNOWN(0, "Inconnue"),
    LESS_THAN_10(1, "10 ans et moins"),
    FROM_11_TO_14(2, "De 11 à 14 ans"),
    FROM_15_TO_17(3, "De 15 à 17 ans"),
    FROM_18_TO_25(4, "De 18 à 25 ans"),
    FROM_26_TO_49(5, "De 26 à 49 ans"),
    MORE_THAN_50(6, "50 ans et plus");

    private final Integer dbValue;
    private final String ihmName;

    TrancheAge(Integer value, String name) {
        this.dbValue = value;
        this.ihmName = name;
    }

    public Integer getDbValue() {
        return this.dbValue;
    }

    public String getIhmName() {
        return this.ihmName;
    }

    public static TrancheAge getTrancheAgeFromValue(Integer value){
        return Arrays.stream(values()).filter(trancheAge -> trancheAge.dbValue.equals(value)).findFirst().get();

    }
}
