package com.fdlv.gmd.api.mapper;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.dto.QuizzDTO;
import com.fdlv.gmd.api.dto.QuizzDetailsDTO;

/**
 * Mapper for the entity {@link Quizz} and its DTO {@link QuizzDTO}.
 */
@Mapper(componentModel = "spring", uses = { AnswerDetailsMapper.class })
public interface QuizzDetailsMapper extends EntityMapper<QuizzDetailsDTO, Quizz> {
    QuizzDetailsDTO toDto(Quizz s);
}
