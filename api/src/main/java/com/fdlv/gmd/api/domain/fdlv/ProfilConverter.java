package com.fdlv.gmd.api.domain.fdlv;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fdlv.gmd.api.domain.enumeration.Profil;

@Converter
public class ProfilConverter implements AttributeConverter<Profil, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Profil profil) {
        if (profil == null)
            return null;

        switch (profil) {
        case ORGANISATEUR:
            return 1;
        case PROFESSIONNEL:
            return 2;
        case BENEVOLE:
            return 3;
        case PUBLIC:
            return 4;
        case ADMINISTRATEUR:
            return 9;
        default:
            throw new IllegalArgumentException(profil + " not supported.");
        }
    }

    @Override
    public Profil convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null)
            return null;

        switch (dbValue) {
        case 1:
            return Profil.ORGANISATEUR;
        case 2:
            return Profil.PROFESSIONNEL;
        case 3:
            return Profil.BENEVOLE;
        case 4:
            return Profil.PUBLIC;
        case 9:
            return Profil.ADMINISTRATEUR;
        default:
            throw new IllegalArgumentException(dbValue + " not supported.");
        }
    }

}
