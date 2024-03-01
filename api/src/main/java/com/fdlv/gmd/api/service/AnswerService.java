package com.fdlv.gmd.api.service;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.AnswerDTO;
import com.fdlv.gmd.api.dto.QuestionDTO;
import com.fdlv.gmd.api.dto.QuestionDetailsDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.Answer}.
 */
public interface AnswerService {
    /**
     * Save a answer.
     *
     * @param answerDTO the entity to save.
     * @return the persisted entity.
     */
    AnswerDTO save(AnswerDTO answerDTO);

    /**
     * Partially updates a answer.
     *
     * @param answerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnswerDTO> partialUpdate(AnswerDTO answerDTO);

    /**
     * Get all the answers.
     *
     * @return the list of entities.
     */
    List<AnswerDTO> findAll();

    /**
     * Get all the answers related to one question.
     *
     * @return the list of entities.
     */
    List<AnswerDTO> findQuestionAnswers(QuestionDetailsDTO questionDTO);

    /**
     * Clean up removed answers.
     *
     * @return the list of entities.
     */
    List<AnswerDTO> cleanQuestionAnswers(List<AnswerDTO> answers,QuestionDetailsDTO question);

    /**
     * Get the "id" answer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" answer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
