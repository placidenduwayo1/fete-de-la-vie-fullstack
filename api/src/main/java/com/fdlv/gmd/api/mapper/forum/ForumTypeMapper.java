package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.ForumType;
import com.fdlv.gmd.api.dto.forum.ForumTypeDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { ForumTypeMapper.class })
public interface ForumTypeMapper extends EntityMapper<ForumTypeDTO, ForumType> {
    ForumTypeMapper INSTANCE = Mappers.getMapper(ForumTypeMapper.class);

    ForumTypeDTO toDto(ForumType forumType);
    ForumType toEntity(ForumTypeDTO forumTypeDTO);
}