package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.Forum;
import com.fdlv.gmd.api.dto.forum.ForumDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ForumMapper extends EntityMapper<ForumDTO, Forum> {
    ForumMapper INSTANCE = Mappers.getMapper(ForumMapper.class);

    ForumDTO toDto(Forum forum);
    Forum toEntity(ForumDTO forumDTO);
}