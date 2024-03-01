package com.fdlv.gmd.api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.dto.QuizzDTO;

/**
 * Mapper for the entity {@link Quizz} and its DTO {@link QuizzDTO}.
 */
@Mapper(componentModel = "spring", uses = { QuestionMapper.class })
public interface QuizzMapper extends EntityMapper<QuizzDTO, Quizz> {
    @Named("label")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    QuizzDTO toDtoLabel(Quizz quizz);
}
