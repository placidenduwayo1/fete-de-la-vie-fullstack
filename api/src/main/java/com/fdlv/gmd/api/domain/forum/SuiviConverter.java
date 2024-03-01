package com.fdlv.gmd.api.domain.forum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fdlv.gmd.api.domain.enumeration.forum.Suivi;

@Converter
public class SuiviConverter  implements AttributeConverter<Suivi,Integer>{

    @Override
    public Integer convertToDatabaseColumn(Suivi etatForum) {
        if (etatForum == null)
            return null;

        switch (etatForum) {
            case EN_COURS_DE_MISE_EN_OEUVRE:
                return 0;
            case AVANCEMENT_PARTIEL:
                return 1;
            case AVANCEMENT_COMPLET:
                return 2;
            case VALIDE:
                return 3;
            case TERMINE:
                return 9;
            default:
                throw new IllegalArgumentException(etatForum + " not supported.");
        }
    }

    @Override
    public Suivi convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null)
            return null;

        switch (dbValue) {
        case 1:
            return Suivi.AVANCEMENT_PARTIEL;
        case 2:
            return Suivi.AVANCEMENT_COMPLET;
        case 3:
            return Suivi.VALIDE;
        case 0:
            return Suivi.EN_COURS_DE_MISE_EN_OEUVRE;
        case 9:
            return Suivi.TERMINE;    
        default:
            throw new IllegalArgumentException(dbValue + " not supported.");
        }
    }
    
}
