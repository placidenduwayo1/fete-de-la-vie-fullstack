package com.fdlv.gmd.api.mapper.fdlv;

import com.fdlv.gmd.api.domain.fdlv.EventVideo;
import com.fdlv.gmd.api.dto.fdlv.DisplayableEventVideoDTO;
import com.fdlv.gmd.api.dto.fdlv.EventVideoDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link EventVideo} and its DTO {@link EventVideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DisplayableEventVideoMapper extends EntityMapper<DisplayableEventVideoDTO, EventVideo> {
}
