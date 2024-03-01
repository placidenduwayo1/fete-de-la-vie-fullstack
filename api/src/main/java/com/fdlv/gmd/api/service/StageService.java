package com.fdlv.gmd.api.service;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.StageDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.Stage}.
 */
public interface StageService {
    /**
     * Save a stage.
     *
     * @param stageDTO the entity to save.
     * @return the persisted entity.
     */
    StageDTO save(StageDTO stageDTO);

    /**
     * Partially updates a stage.
     *
     * @param stageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StageDTO> partialUpdate(StageDTO stageDTO);

    /**
     * Get all the stages.
     *
     * @return the list of entities.
     */
    List<StageDTO> findAll();

    /**
     * Get the "id" stage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StageDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" stage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
