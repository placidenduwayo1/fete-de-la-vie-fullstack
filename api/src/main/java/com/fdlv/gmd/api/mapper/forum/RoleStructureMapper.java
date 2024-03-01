package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.RoleStructure;
import com.fdlv.gmd.api.dto.forum.RoleStructureDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleStructureMapper extends EntityMapper<RoleStructureDTO, RoleStructure> {
    RoleStructureMapper INSTANCE = Mappers.getMapper(RoleStructureMapper.class);

    RoleStructureDTO toDto(RoleStructure roleStructure);
    RoleStructure toEntity(RoleStructureDTO roleStructureDTO);
}