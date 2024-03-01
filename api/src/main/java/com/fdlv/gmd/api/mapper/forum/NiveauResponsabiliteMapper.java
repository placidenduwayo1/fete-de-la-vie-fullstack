package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.NiveauResponsabilite;
import com.fdlv.gmd.api.dto.forum.NiveauResponsabiliteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NiveauResponsabiliteMapper extends EntityMapper<NiveauResponsabilite, NiveauResponsabiliteDTO>{
    NiveauResponsabiliteMapper MAPPER = Mappers.getMapper(NiveauResponsabiliteMapper.class);
    NiveauResponsabilite toModel(NiveauResponsabiliteDTO dto);
    NiveauResponsabiliteDTO toDto(NiveauResponsabilite model);
}
