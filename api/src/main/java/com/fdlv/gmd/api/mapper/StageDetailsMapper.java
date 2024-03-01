package com.fdlv.gmd.api.mapper;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.Stage;
import com.fdlv.gmd.api.dto.StageDetailsDTO;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Stage} and its DTO {@link StageDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { QuizzDetailsMapper.class })
public interface StageDetailsMapper extends EntityMapper<StageDetailsDTO, Stage> {
    @Mapping(source = "stage_defi_video",target = "challengeString")
    @Mapping(source = "stage_defi_partage",target = "challengeShareString")
    StageDetailsDTO toDto(Stage s);
}
