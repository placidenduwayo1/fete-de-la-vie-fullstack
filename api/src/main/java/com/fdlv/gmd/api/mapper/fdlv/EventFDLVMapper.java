package com.fdlv.gmd.api.mapper.fdlv;

import com.fdlv.gmd.api.dto.fdlv.EventFDLVDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import com.fdlv.gmd.api.mapper.ThemeMapper;
import org.mapstruct.*;

import com.fdlv.gmd.api.domain.*;
import com.fdlv.gmd.api.dto.EventDTO;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventFDLVDTO}.
 */
@Mapper(componentModel = "spring", uses = { ThemeMapper.class })
public interface EventFDLVMapper extends EntityMapper<EventFDLVDTO, Event> {
    @Mapping(source = "zipCode",target = "zip_code")
    @Mapping(source = "cityLogoUrl",target = "city_logo_url")
    @Mapping(source = "labelFinParcours",target = "label_fin_parcours")
    @Mapping(source = "finParcoursPdf",target = "fin_parcours_pdf")
    @Mapping(source = "startAt",target = "start_at")
    @Mapping(source = "endAt",target = "end_at")
    @Mapping(source = "otherEvent",target = "other_event")
    @Mapping(source = "validatedEvent",target = "validated_event")
    @Mapping(source = "fixOrder",target = "fix_order")
    @Mapping(source = "usefulInformation",target = "useful_information")
    @Mapping(source = "eventTeaserUrl",target = "event_teaser_url")
    @Mapping(source = "codeParcours",target = "code_parcours")
    @Mapping(source = "evtDemo",target = "evt_demo")
    @Mapping(source = "evtFcoFusId",target = "event_fco_fus_id")
    @Mapping(source = "evtFcoDatePropose",target = "event_fco_date_propose",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(source = "evtFcoDateValide",target = "event_fco_date_validation",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(source = "evtFcoId",target = "event_fco_id")
    @Mapping(source = "evtLatitude",target = "event_latitude")
    @Mapping(source = "evtLongitude",target = "event_longitude")
    @Mapping(source = "theme.id",target = "theme_id")
    EventFDLVDTO toDto(Event s);

    @Mapping(target = "zipCode",source = "zip_code")
    @Mapping(target = "cityLogoUrl",source = "city_logo_url")
    @Mapping(target = "labelFinParcours",source = "label_fin_parcours")
    @Mapping(target = "finParcoursPdf",source = "fin_parcours_pdf")
    @Mapping(target = "startAt",source = "start_at")
    @Mapping(target = "endAt",source = "end_at")
    @Mapping(target = "otherEvent",source = "other_event")
    @Mapping(target = "validatedEvent",source = "validated_event")
    @Mapping(target = "fixOrder",source = "fix_order")
    @Mapping(target = "usefulInformation",source = "useful_information")
    @Mapping(target = "eventTeaserUrl",source = "event_teaser_url")
    @Mapping(target = "codeParcours",source = "code_parcours")
    @Mapping(target = "evtDemo",source = "evt_demo")
    @Mapping(target = "evtFcoFusId",source = "event_fco_fus_id")
    @Mapping(target = "evtFcoDatePropose",source = "event_fco_date_propose",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "evtFcoDateValide",source = "event_fco_date_validation",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "evtFcoId",source = "event_fco_id")
    @Mapping(target = "evtLatitude",source = "event_latitude")
    @Mapping(target = "evtLongitude",source = "event_longitude")
    Event toEntity(EventFDLVDTO s);

    @Named("label")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    EventFDLVDTO toDtoLabel(Event event);
}
