package com.fdlv.gmd.api.mapper;

import org.mapstruct.*;

import com.fdlv.gmd.api.domain.*;
import com.fdlv.gmd.api.dto.StageDTO;

/**
 * Mapper for the entity {@link Stage} and its DTO {@link StageDTO}.
 */
@Mapper(componentModel = "spring", uses = { EventMapper.class, QuizzMapper.class })
public interface StageMapper extends EntityMapper<StageDTO, Stage> {
    @Mapping(target = "event", source = "event", qualifiedByName = "label")
    @Mapping(target = "quizz", source = "quizz", qualifiedByName = "label")
    StageDTO toDto(Stage s);
}
