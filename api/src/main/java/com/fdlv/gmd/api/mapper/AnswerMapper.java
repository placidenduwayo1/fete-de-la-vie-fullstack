package com.fdlv.gmd.api.mapper;

import org.mapstruct.*;

import com.fdlv.gmd.api.domain.*;
import com.fdlv.gmd.api.dto.AnswerDTO;

/**
 * Mapper for the entity {@link Answer} and its DTO {@link AnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = { QuestionMapper.class })
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {
    @Mapping(target = "question", source = "question", qualifiedByName = "label")
    AnswerDTO toDto(Answer s);
}
