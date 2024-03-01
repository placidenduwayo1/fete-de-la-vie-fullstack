package com.fdlv.gmd.api.mapper;

import org.mapstruct.*;

import com.fdlv.gmd.api.domain.*;
import com.fdlv.gmd.api.dto.QuestionDTO;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = { QuizzMapper.class })
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {
    @Mapping(target = "quizz", source = "quizz", qualifiedByName = "label")
    QuestionDTO toDto(Question s);

    @Named("label")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    QuestionDTO toDtoLabel(Question question);
}
