package com.fdlv.gmd.api.mapper.mobile;

import com.fdlv.gmd.api.domain.mobile.MobileTrackParcours;
import com.fdlv.gmd.api.dto.mobile.MobileTrackParcoursDTO;
import com.fdlv.gmd.api.dto.mobile.MobileTrackParcoursFlatDTO;
import com.fdlv.gmd.api.mapper.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MobileStatParcoursMapper.class, EventMapper.class, StageMapper.class, QuizzMapper.class, QuestionMapper.class})
public interface MobileTrackParcoursMapper extends EntityMapper<MobileTrackParcoursDTO, MobileTrackParcours> {

    MobileTrackParcoursMapper INSTANCE = Mappers.getMapper(MobileTrackParcoursMapper.class);

    MobileTrackParcoursDTO toDto(MobileTrackParcours mobileTrackParcours); // Renamed method

    MobileTrackParcours toEntity(MobileTrackParcoursDTO mobileTrackParcoursDTO);

    List<MobileTrackParcoursDTO> toDtoList(List<MobileTrackParcours> mobileTrackParcoursList);

    @Mappings({
            @Mapping(target = "mobileStatParcours.id", source = "mobileStatParcoursId"),
            @Mapping(target = "event.id", source = "eventId"),
            @Mapping(target = "stage.id", source = "stageId"),
            @Mapping(source = "id", target = "id", ignore = true)
    })
    MobileTrackParcours toEntityFromFlatDTO(MobileTrackParcoursFlatDTO dto);

    @Mappings({
            @Mapping(target = "mobileStatParcours.id", source = "mobileStatParcoursId"),
            @Mapping(target = "event.id", source = "eventId"),
            @Mapping(target = "stage.id", source = "stageId"),
            @Mapping(source = "id", target = "id", ignore = true)
    })
    void updateEntityFromFlatDTO(MobileTrackParcoursFlatDTO dto, @MappingTarget MobileTrackParcours entity);
}