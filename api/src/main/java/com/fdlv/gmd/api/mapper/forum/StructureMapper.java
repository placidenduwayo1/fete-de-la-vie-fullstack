package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.Structure;
import com.fdlv.gmd.api.dto.forum.StructureDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { ActeurMapper.class,  })
public interface StructureMapper extends EntityMapper<StructureDTO, Structure> {
    StructureMapper INSTANCE = Mappers.getMapper(StructureMapper.class);

    StructureDTO toDto(Structure structure);
    Structure toEntity(StructureDTO structureDTO);
}