package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.ActeurHabilitationModule;
import com.fdlv.gmd.api.dto.forum.ActeurHabilitationModuleDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { ActeurMapper.class, ModuleApplicatifMapper.class })
public interface ActeurHabilitationModuleMapper extends EntityMapper<ActeurHabilitationModuleDTO, ActeurHabilitationModule> {
    ActeurHabilitationModuleMapper INSTANCE = Mappers.getMapper(ActeurHabilitationModuleMapper.class);

    ActeurHabilitationModuleDTO toDto(ActeurHabilitationModule acteurHabilitationModule);
    ActeurHabilitationModule toEntity(ActeurHabilitationModuleDTO acteurHabilitationModuleDTO);
}