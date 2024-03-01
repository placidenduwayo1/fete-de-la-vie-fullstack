package com.fdlv.gmd.api.mapper.forum;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.fdlv.gmd.api.domain.forum.Acteur;
import com.fdlv.gmd.api.dto.forum.ActeurDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;

@Mapper(componentModel = "spring", uses = { RoleActeurMapper.class, InterventionActeurMapper.class })
public interface ActeurMapper extends EntityMapper<ActeurDTO, Acteur> {
	ActeurMapper INSTANCE = Mappers.getMapper(ActeurMapper.class);

	@Override
	@Mapping(target = "createdByActor.passwordHash", ignore = true)
	@Mapping(target = "createdByActor.createdByActor", ignore = true)
	@Mapping(target = "createdByActor.structureId", ignore = true)
	@Mapping(target = "createdByActor.responsableActor", ignore = true)
	@Mapping(target = "responsableActor.passwordHash", ignore = true)
	@Mapping(target = "responsableActor.createdByActor", ignore = true)
	@Mapping(target = "responsableActor.structureId", ignore = true)
	@Mapping(target = "responsableActor.responsableActor", ignore = true)
	@Mapping(target = "structureId.contact.createdByActor", ignore = true)
	@Mapping(target = "structureId.contact.passwordHash", ignore = true)
	@Mapping(target = "structureId.contact.structureId", ignore = true)
	@Mapping(target = "structureId.contact.service", ignore = true)
	@Mapping(target = "structureId.contact.responsableActor", ignore = true)
	ActeurDTO toDto(Acteur acteur);

	@Override
	Acteur toEntity(ActeurDTO acteurDTO);
}