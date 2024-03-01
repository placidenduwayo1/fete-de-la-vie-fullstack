package com.fdlv.gmd.api.mapper.fdlv;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.fdlv.InfoPageWeb;
import com.fdlv.gmd.api.domain.fdlv.ListeVideos;
import com.fdlv.gmd.api.dto.fdlv.InfoPageWebDTO;
import com.fdlv.gmd.api.dto.fdlv.ListeVideosThemeDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import com.fdlv.gmd.api.mapper.ThemeMapper;

/**
 * Mapper for the entity {@link InfoPageWeb} and its DTO {@link InfoPageWebDTO}.
 */
@Mapper(componentModel = "spring", uses = { ThemeMapper.class })
public interface ListeVideosThemeMapper extends EntityMapper<ListeVideosThemeDTO, ListeVideos> {
}
