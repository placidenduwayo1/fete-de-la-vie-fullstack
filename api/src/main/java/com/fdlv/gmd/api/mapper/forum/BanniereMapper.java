package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.Banniere;
import com.fdlv.gmd.api.dto.forum.BanniereDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BanniereMapper extends EntityMapper<BanniereDTO, Banniere> {
    ForumMapper INSTANCE = Mappers.getMapper(ForumMapper.class);

    BanniereDTO toDto(Banniere banniere);
    Banniere toEntity(BanniereDTO banniereDTO);
}