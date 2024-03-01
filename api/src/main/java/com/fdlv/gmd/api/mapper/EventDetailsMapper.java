package com.fdlv.gmd.api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.fdlv.gmd.api.domain.Event;
import com.fdlv.gmd.api.dto.EventDTO;
import com.fdlv.gmd.api.dto.EventDetailsDTO;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring", uses = { StageDetailsMapper.class })
public interface EventDetailsMapper extends EntityMapper<EventDetailsDTO, Event> {
    @Named("label")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    EventDetailsDTO toDtoLabel(Event event);
}
