package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.ModuleApplicatif;
import com.fdlv.gmd.api.dto.forum.ModuleApplicatifDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ModuleApplicatifMapper extends EntityMapper<ModuleApplicatifDTO, ModuleApplicatif> {
    ModuleApplicatifMapper INSTANCE = Mappers.getMapper(ModuleApplicatifMapper.class);

    ModuleApplicatifDTO toDto(ModuleApplicatif moduleApplicatif);
    ModuleApplicatif toEntity(ModuleApplicatifDTO moduleApplicatifDTO);
}