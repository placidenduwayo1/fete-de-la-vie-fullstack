package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.Stand;
import com.fdlv.gmd.api.dto.forum.StandDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = StandSecteurMapper.class)
public interface StandMapper extends EntityMapper<StandDTO, Stand> {
    StandDTO toDto(Stand stand);

    default void updateStandFromDto(Stand existingStand, StandDTO standDTO) {
        existingStand.setStandPhysique(standDTO.getStandPhysique());
        existingStand.setLibelle(standDTO.getLibelle());
        existingStand.setMateriel(standDTO.getMateriel());
        existingStand.setObservation(standDTO.getObservation());
        existingStand.setBesoinElectricite(standDTO.getBesoinElectricite());
        existingStand.setCommentaire(standDTO.getCommentaire());
    }

}
