package com.fdlv.gmd.api.mapper;

import org.mapstruct.*;

import com.fdlv.gmd.api.domain.*;
import com.fdlv.gmd.api.dto.EventDTO;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring", uses = { ThemeMapper.class })
public interface EventMapper extends EntityMapper<EventDTO, Event> {
    EventDTO toDto(Event s);

    @Named("label")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    EventDTO toDtoLabel(Event event);
}
