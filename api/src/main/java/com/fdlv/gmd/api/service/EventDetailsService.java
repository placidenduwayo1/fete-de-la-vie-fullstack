package com.fdlv.gmd.api.service;

import java.util.List;

import com.fdlv.gmd.api.dto.EventDetailsDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.Event}.
 */
public interface EventDetailsService {
    /**
     * Get all the events details.
     *
     * @return the list of entities.
     */
    List<EventDetailsDTO> findAll();
}
