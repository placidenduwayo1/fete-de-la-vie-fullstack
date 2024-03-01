package com.fdlv.gmd.api.domain.fdlv;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fdlv.gmd.api.domain.enumeration.ModulePartenaire;

@Converter
public class ModulePartenaireConverter implements AttributeConverter<ModulePartenaire, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ModulePartenaire modulePartenaire) {
        if (modulePartenaire == null)
            return null;

        switch (modulePartenaire) {
            case WEB:
                return 1;
            case MOBILE:
                return 2;
            case WEB_MOBILE:
                return 3;
            case NON_AFFICHE:
                return 0;
            default:
                throw new IllegalArgumentException(modulePartenaire + " not supported.");
        }
    }

    @Override
    public ModulePartenaire convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null)
            return null;

        switch (dbValue) {
        case 1:
            return ModulePartenaire.WEB;
        case 2:
            return ModulePartenaire.MOBILE;
        case 3:
            return ModulePartenaire.WEB_MOBILE;
        case 0:
            return ModulePartenaire.NON_AFFICHE;
        default:
            throw new IllegalArgumentException(dbValue + " not supported.");
        }
    }
    
}
