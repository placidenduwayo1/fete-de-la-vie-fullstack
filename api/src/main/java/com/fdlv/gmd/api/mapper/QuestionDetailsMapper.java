package com.fdlv.gmd.api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.fdlv.gmd.api.domain.Question;
import com.fdlv.gmd.api.dto.QuestionDetailsDTO;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { AnswerDetailsMapper.class })
public interface QuestionDetailsMapper extends EntityMapper<QuestionDetailsDTO, Question> {
    QuestionDetailsDTO toDto(Question s);

    @Named("label")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    QuestionDetailsDTO toDtoLabel(Question question);
}
