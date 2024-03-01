package com.fdlv.gmd.api.domain.forum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fdlv.gmd.api.domain.enumeration.forum.NiveauDiffusion;

@Converter
public class NiveauDIffusionConverter  implements AttributeConverter<NiveauDiffusion,Integer>{

    @Override
    public Integer convertToDatabaseColumn(NiveauDiffusion niveauDiffusion) {
        if (niveauDiffusion == null)
            return null;

        switch (niveauDiffusion) {
            case INTERNE_FDLV:
                return 1;
            case RESTREINT_1_2:
                return 2;
            case RESTREINT_1_2_3:
                return 3;
            case RESTREINT_1_2_3_4:
                return 4;
            case RESTREINT_1_2_3_4_5:
                return 5;
            case PUBLIQUE:
                return 9;
            default:
                throw new IllegalArgumentException(niveauDiffusion + " not supported.");
        }
    }

    @Override
    public NiveauDiffusion convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null)
            return null;

        switch (dbValue) {
        case 1:
            return NiveauDiffusion.INTERNE_FDLV;
        case 2:
            return NiveauDiffusion.RESTREINT_1_2;
        case 3:
            return NiveauDiffusion.RESTREINT_1_2_3;
        case 4:
            return NiveauDiffusion.RESTREINT_1_2_3_4;
        case 5:
            return NiveauDiffusion.RESTREINT_1_2_3_4_5;
        case 9:
            return NiveauDiffusion.PUBLIQUE;    
        default:
            throw new IllegalArgumentException(dbValue + " not supported.");
        }
    }
    
}
