package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.ForumTheme;
import com.fdlv.gmd.api.dto.forum.ForumThemeDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { ForumThemeMapper.class })
public interface ForumThemeMapper extends EntityMapper<ForumThemeDTO, ForumTheme> {
    ForumThemeMapper INSTANCE = Mappers.getMapper(ForumThemeMapper.class);

    ForumThemeDTO toDto(ForumTheme ft);
    ForumTheme toEntity(ForumThemeDTO ftTdo);
}