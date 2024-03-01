package com.fdlv.gmd.api.domain.fdlv;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fdlv.gmd.api.domain.enumeration.EtatScenario;
import org.springframework.stereotype.Component;

@Component
@Converter
public class EtatScenarioConverter implements AttributeConverter<EtatScenario, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EtatScenario profil) {
        if (profil == null)
            return null;

        switch (profil) {
        case SAUVEGARDE:
            return 1;
        case SOUMIS:
            return 2;
        case VALIDE:
            return 3;
        case REJETE:
            return 4;
        case DESACTIVE:
            return 9;
        default:
            throw new IllegalArgumentException(profil + " not supported.");
        }
    }

    @Override
    public EtatScenario convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null)
            return null;

        switch (dbValue) {
        case 1:
            return EtatScenario.SAUVEGARDE;
        case 2:
            return EtatScenario.SOUMIS;
        case 3:
            return EtatScenario.VALIDE;
        case 4:
            return EtatScenario.REJETE;
        case 9:
            return EtatScenario.DESACTIVE;
        default:
            throw new IllegalArgumentException(dbValue + " not supported.");
        }
    }

}
