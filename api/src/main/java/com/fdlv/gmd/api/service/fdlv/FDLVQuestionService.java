package com.fdlv.gmd.api.service.fdlv;

import com.fdlv.gmd.api.dto.QuestionDTO;
import com.fdlv.gmd.api.dto.fdlv.FDLVQuestionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.Question} in the FDLV Website.
 */
public interface FDLVQuestionService {

    /**
     * Get all the questions.
     *
     * @return the list of entities.
     */
    List<FDLVQuestionDTO> findAll();

    /**
     * Get the "id" question.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FDLVQuestionDTO> findOne(Long id);

    /**
     * Retrieves the list of questions associated with a quiz.
     *
     * @param quizzId The identifier of the quiz.
     * @return The list of questions associated with the quiz in DTO format.
     */
    List<FDLVQuestionDTO> getQuestionsByQuizzId(Long quizzId);
}
