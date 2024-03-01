package com.fdlv.gmd.api.service;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.EventDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.Event}.
 */
public interface EventService {
    /**
     * Save a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    EventDTO save(EventDTO eventDTO);

    /**
     * Partially updates a event.
     *
     * @param eventDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventDTO> partialUpdate(EventDTO eventDTO);

    /**
     * Get all the events.
     *
     * @return the list of entities.
     */
    List<EventDTO> findAll();

    /**
     * Get the "id" event.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventDTO> findOne(Long id);


    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" event.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
