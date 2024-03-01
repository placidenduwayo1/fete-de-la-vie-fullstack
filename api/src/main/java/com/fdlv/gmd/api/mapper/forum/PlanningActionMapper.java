package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.PlanningAction;
import com.fdlv.gmd.api.dto.forum.PlanningActionDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { ForumMapper.class, ActeurMapper.class, StructureMapper.class })
public interface PlanningActionMapper extends EntityMapper<PlanningActionDTO, PlanningAction> {

    PlanningActionMapper INSTANCE = Mappers.getMapper(PlanningActionMapper.class);

    PlanningActionDTO toDto(PlanningAction planningAction);
    PlanningAction toEntity(PlanningActionDTO planingActionDTO);
}
