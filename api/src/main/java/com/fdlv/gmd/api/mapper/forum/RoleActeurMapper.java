package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.RoleActeur;
import com.fdlv.gmd.api.dto.forum.RoleActeurDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleActeurMapper extends EntityMapper<RoleActeurDTO, RoleActeur> {
    RoleActeurMapper INSTANCE = Mappers.getMapper(RoleActeurMapper.class);

    RoleActeurDTO toDto(RoleActeur roleActeur);

    RoleActeur toEntity(RoleActeurDTO roleActeurDTO);

}