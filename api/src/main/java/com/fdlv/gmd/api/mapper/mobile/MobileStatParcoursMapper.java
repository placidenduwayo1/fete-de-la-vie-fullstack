package com.fdlv.gmd.api.mapper.mobile;

import com.fdlv.gmd.api.domain.mobile.MobileStatParcours;
import com.fdlv.gmd.api.dto.mobile.MobileStatParcoursDTO;
import com.fdlv.gmd.api.dto.mobile.MobileStatParcoursFlatDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import com.fdlv.gmd.api.mapper.EventMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MobileUserMapper.class, EventMapper.class})
public interface MobileStatParcoursMapper extends EntityMapper<MobileStatParcoursDTO, MobileStatParcours> {

    MobileStatParcoursMapper INSTANCE = Mappers.getMapper(MobileStatParcoursMapper.class);

    MobileStatParcoursDTO toDto(MobileStatParcours entity);

    MobileStatParcours toEntity(MobileStatParcoursDTO dto);

    List<MobileStatParcoursDTO> toDtoList(List<MobileStatParcours> entities);

    @Mappings({
            @Mapping(target = "mobileUser.id", source = "mobileUserId"),
            @Mapping(target = "event.id", source = "eventId"),
            @Mapping(source = "id", target = "id", ignore = true)
    })
    MobileStatParcours toEntityFromFlatDTO(MobileStatParcoursFlatDTO dto);

    @Mappings({
            @Mapping(target = "mobileUser.id", source = "mobileUserId"),
            @Mapping(target = "event.id", source = "eventId"),
            @Mapping(source = "id", target = "id", ignore = true)
    })
    void updateEntityFromFlatDTO(MobileStatParcoursFlatDTO dto, @MappingTarget MobileStatParcours entity);
}
