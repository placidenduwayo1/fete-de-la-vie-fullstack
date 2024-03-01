package com.fdlv.gmd.api.service.fdlv;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.fdlv.DisplayableEventVideoDTO;
import com.fdlv.gmd.api.dto.fdlv.EventVideoDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.fdlv.EventVideo}.
 */
public interface EventVideoService {
    /**
     * Save an eventVideo.
     *
     * @param eventVideoDTO the entity to save.
     * @return the persisted entity.
     */
    EventVideoDTO save(EventVideoDTO eventVideoDTO);

    /**
     * Partially updates an eventVideo.
     *
     * @param eventVideoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventVideoDTO> partialUpdate(EventVideoDTO eventVideoDTO);

    /**
     * Get all the eventVideos.
     *
     * @return the list of entities.
     */
    List<EventVideoDTO> findAll();

    /**
     * Get displayable eventVideos.
     *
     * @return the list of entities.
     */
    List<DisplayableEventVideoDTO> findDisplayableVideos();

    /**
     * Get the "id" eventVideo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventVideoDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" eventVideo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
