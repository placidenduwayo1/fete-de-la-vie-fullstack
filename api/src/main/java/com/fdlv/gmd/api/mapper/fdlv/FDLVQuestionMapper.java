package com.fdlv.gmd.api.mapper.fdlv;

import com.fdlv.gmd.api.domain.Question;
import com.fdlv.gmd.api.dto.fdlv.FDLVQuestionDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link FDLVQuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface FDLVQuestionMapper extends EntityMapper<FDLVQuestionDTO, Question> {

    @Mapping(source = "type.value",target = "type")
    @Mapping(source = "quizz.id",target = "quizz_id")
    @Mapping(source = "order",target = "num_order")
    FDLVQuestionDTO toDto(Question question);


}
