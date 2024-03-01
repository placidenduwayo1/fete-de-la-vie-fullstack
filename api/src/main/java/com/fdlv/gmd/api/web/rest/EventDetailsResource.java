package com.fdlv.gmd.api.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.dto.EventDetailsDTO;
import com.fdlv.gmd.api.service.EventDetailsService;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.Event}.
 */
@RestController
@RequestMapping("/api/event-details")
public class EventDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EventDetailsResource.class);

    private final EventDetailsService eventDetailsService;

    public EventDetailsResource(EventDetailsService eventDetailsService) {
        this.eventDetailsService = eventDetailsService;
    }

    /**
     * {@code GET  /events/:id} : get the "id" event.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping()
    public List<EventDetailsDTO> getAllEventsDetails() {
        log.debug("REST request to get all Events details");
        return eventDetailsService.findAll();
    }
}
