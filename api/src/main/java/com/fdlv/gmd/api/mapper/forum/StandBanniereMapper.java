package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.StandBanniere;
import com.fdlv.gmd.api.dto.forum.StandBanniereDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { StructureMapper.class })
public interface StandBanniereMapper extends EntityMapper<StandBanniereDTO, StandBanniere> {
    StandBanniereMapper INSTANCE = Mappers.getMapper(StandBanniereMapper.class);

    StandBanniereDTO toDto(StandBanniere standBanniere);
    StandBanniere toEntity(StandBanniereDTO standBanniereDTO);
}