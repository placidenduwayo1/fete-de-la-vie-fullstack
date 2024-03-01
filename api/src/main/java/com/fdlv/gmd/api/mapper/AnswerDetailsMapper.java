package com.fdlv.gmd.api.mapper;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.Answer;
import com.fdlv.gmd.api.dto.AnswerDTO;
import com.fdlv.gmd.api.dto.AnswerDetailsDTO;

/**
 * Mapper for the entity {@link Answer} and its DTO {@link AnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnswerDetailsMapper extends EntityMapper<AnswerDetailsDTO, Answer> {
    AnswerDetailsDTO toDto(Answer s);
}
