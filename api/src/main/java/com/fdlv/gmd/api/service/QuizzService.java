package com.fdlv.gmd.api.service;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.dto.QuizzDTO;
import com.fdlv.gmd.api.dto.QuizzDetailsDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.Quizz}.
 */
public interface QuizzService {
    /**
     * Save a quizz.
     *
     * @param quizzDTO the entity to save.
     * @return the persisted entity.
     */
    QuizzDTO save(QuizzDTO quizzDTO);

    /**
     * Partially updates a quizz.
     *
     * @param quizzDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QuizzDTO> partialUpdate(QuizzDTO quizzDTO);

    /**
     * Get all the quizzes.
     *
     * @return the list of entities.
     */
    List<QuizzDTO> findAll();

    /**
     * Get all the quizz order by label.
     *
     * @return the list of entities.
     */
    List<QuizzDTO> findAllQuizz();

    /**
     * Get the "id" quizz.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuizzDTO> findOne(Long id);

    /**
     * Get the "id" quizz.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Quizz> findOneEntity(Long id);



    /**
     * Delete the "id" quizz.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Save a quizz.
     *
     * @param quizzDTO the entity to save.
     * @return the persisted entity.
     */
    QuizzDTO save(QuizzDetailsDTO quizzDTO);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Get the "id" quizz.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuizzDetailsDTO> findOneDetails(Long id);

    QuizzDTO findByLabel(String label);

    /**
     * find a  list of quizz by associate to a video
     * @param id the id of the video
     * @return A list of quizz
     */
    List<QuizzDTO> findByVideoId(Long id);
}
