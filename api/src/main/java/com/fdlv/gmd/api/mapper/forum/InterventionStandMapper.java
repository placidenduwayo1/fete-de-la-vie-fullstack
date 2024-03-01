package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.InterventionStand;
import com.fdlv.gmd.api.dto.forum.InterventionStandDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { ActeurMapper.class, RoleActeurMapper.class, ForumMapper.class, StandMapper.class, StructureMapper.class, RoleStructureMapper.class, StandBanniereMapper.class })
public interface InterventionStandMapper extends EntityMapper<InterventionStandDTO, InterventionStand> {
    InterventionStandMapper INSTANCE = Mappers.getMapper(InterventionStandMapper.class);

    InterventionStandDTO toDto(InterventionStand interventionStand);
    InterventionStand toEntity(InterventionStandDTO interventionStandDTO);
}