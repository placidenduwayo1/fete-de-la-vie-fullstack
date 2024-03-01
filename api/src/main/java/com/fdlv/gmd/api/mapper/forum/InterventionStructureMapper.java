package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.InterventionStructure;
import com.fdlv.gmd.api.dto.forum.InterventionStructureDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { StructureMapper.class, ForumMapper.class, RoleStructureMapper.class })
public interface InterventionStructureMapper extends EntityMapper<InterventionStructureDTO, InterventionStructure> {
    InterventionStructureMapper INSTANCE = Mappers.getMapper(InterventionStructureMapper.class);

    InterventionStructureDTO toDto(InterventionStructure interventionStructure);
    InterventionStructure toEntity(InterventionStructureDTO interventionStructureDTO);
}