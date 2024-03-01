package com.fdlv.gmd.api.service.fdlv;

import com.fdlv.gmd.api.dto.EventDTO;
import com.fdlv.gmd.api.dto.fdlv.EventFDLVDTO;
import com.fdlv.gmd.api.dto.fdlv.EventPostFDLVDTO;

import java.util.List;
import java.util.Optional;
/**
 * Service Interface for managing the /event API.
 */

public interface EventFDLVService {
    /**
     * Save a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    EventFDLVDTO save(EventPostFDLVDTO eventDTO);



    /**
     * Get all the events.
     *
     * @return the list of entities.
     */
    List<EventFDLVDTO> findAll();

    /**
     * Get the "id" event.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventFDLVDTO> findOne(Long id);



}
