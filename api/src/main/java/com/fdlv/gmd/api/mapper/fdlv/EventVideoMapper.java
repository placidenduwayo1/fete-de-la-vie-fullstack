package com.fdlv.gmd.api.mapper.fdlv;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.fdlv.EventVideo;
import com.fdlv.gmd.api.dto.fdlv.EventVideoDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;

/**
 * Mapper for the entity {@link EventVideo} and its DTO {@link EventVideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventVideoMapper extends EntityMapper<EventVideoDTO, EventVideo> {
}
