package com.fdlv.gmd.api.mapper.forum;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.fdlv.gmd.api.domain.forum.InterventionActeur;
import com.fdlv.gmd.api.dto.forum.InterventionActeurDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;

@Mapper(componentModel = "spring", uses = { ActeurMapper.class, ForumMapper.class, RoleActeurMapper.class,
		StructureMapper.class })
public interface InterventionActeurMapper extends EntityMapper<InterventionActeurDTO, InterventionActeur> {
	InterventionActeurMapper INSTANCE = Mappers.getMapper(InterventionActeurMapper.class);

	@Override
	InterventionActeurDTO toDto(InterventionActeur interventionActeur);

	@Override
	InterventionActeur toEntity(InterventionActeurDTO interventionActeurDTO);
}