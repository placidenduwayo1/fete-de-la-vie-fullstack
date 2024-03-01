package com.fdlv.gmd.api.domain.fdlv;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fdlv.gmd.api.domain.enumeration.Priorite;

@Converter
public class PrioriteConverter implements AttributeConverter<Priorite, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Priorite priorite) {
        if (priorite == null)
            return null;

        switch (priorite) {
            case BAS_DE_LA_PAGE:
                return 1;
            case HAUT_DE_LA_PAGE:
                return 2;
            default:
                throw new IllegalArgumentException(priorite + " not supported.");
        }
    }

    @Override
    public Priorite convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null)
            return null;

        switch (dbValue) {
        case 1:
            return Priorite.BAS_DE_LA_PAGE;
        case 2:
            return Priorite.HAUT_DE_LA_PAGE;
        default:
            throw new IllegalArgumentException(dbValue + " not supported.");
        }
    }
    
}
