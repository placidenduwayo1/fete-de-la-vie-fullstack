package com.fdlv.gmd.api.service.forum;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.forum.PlanningActionDTO;

/**
 * Service Interface for managing {@link PlaningAction }.
 */
public interface PlanningActionService {
     /**
     * Save a planingAction.
     *
     * @param planingActionDTO the entity to save.
     * @return the persisted entity.
     */
    PlanningActionDTO save(PlanningActionDTO planingActionDTO);

    /**
     * Partially updates a planingAction.
     *
     * @param planingActionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanningActionDTO> partialUpdate(PlanningActionDTO planingActionDTO);

    /**
     * Get all the planingActions.
     *
     * @return the list of entities.
     */
    List<PlanningActionDTO> findAll();

    /**
     * Get the "id" planingAction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanningActionDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" planingAction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
