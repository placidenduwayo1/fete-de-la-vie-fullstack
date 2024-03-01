package com.fdlv.gmd.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.dto.fdlv.DisplayableEventVideoDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.dto.fdlv.EventVideoDTO;
import com.fdlv.gmd.api.service.fdlv.EventVideoService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.fdlv.EventVideo}.
 */
@RestController
@RequestMapping("/api/ytbVideo")
public class EventVideoResource {

    private final Logger log = LoggerFactory.getLogger(EventVideoResource.class);

    private static final String ENTITY_NAME = "eventVideo";

    private final EventVideoService eventVideoService;

    public EventVideoResource(EventVideoService eventVideoService) {
        this.eventVideoService = eventVideoService;
    }

    /**
     * {@code POST  /ytbVideo} : Create a new eventVideo.
     *
     * @param eventVideoDTO the eventVideoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventVideoDTO, or with status {@code 400 (Bad Request)} if the eventVideo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<EventVideoDTO> createEventVideo(@Valid @RequestBody EventVideoDTO eventVideoDTO) throws URISyntaxException {
        log.debug("REST request to save EventVideo : {}", eventVideoDTO);
        if (eventVideoDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        EventVideoDTO result = eventVideoService.save(eventVideoDTO);
        return ResponseEntity
            .created(new URI("/api/ytbVideo/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing eventVideo.
     *
     * @param id the id of the eventVideoDTO to save.
     * @param eventVideoDTO the eventVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventVideoDTO,
     * or with status {@code 400 (Bad Request)} if the eventVideoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventVideoDTO> updateEventVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EventVideoDTO eventVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventVideo : {}, {}", id, eventVideoDTO);
        if (eventVideoDTO.getId() == null
                || !Objects.equals(id, eventVideoDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!eventVideoService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        EventVideoDTO result = eventVideoService.save(eventVideoDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /ytbVideo/:id} : Partial updates given fields of an existing eventVideo, field will ignore if it is null
     *
     * @param id the id of the eventVideoDTO to save.
     * @param eventVideoDTO the eventVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventVideoDTO,
     * or with status {@code 400 (Bad Request)} if the eventVideoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventVideoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EventVideoDTO> partialUpdateEventVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EventVideoDTO eventVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventVideo partially : {}, {}", id, eventVideoDTO);
        if (eventVideoDTO.getId() == null
                || !Objects.equals(id, eventVideoDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!eventVideoService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<EventVideoDTO> result = eventVideoService.partialUpdate(eventVideoDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /ytbVideo} : get all the eventVideos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventVideos in body.
     */
    @GetMapping()
    public List<EventVideoDTO> getAllEventVideos() {
        log.debug("REST request to get all EventVideos");
        return eventVideoService.findAll();
    }

    @GetMapping("/displayable")
    public List<DisplayableEventVideoDTO> getDisplayableEventVideos() {
        log.debug("REST request to get displayable EventVideos");
        return eventVideoService.findDisplayableVideos();
    }

    /**
     * {@code GET  /ytbVideo/:id} : get the "id" eventVideo.
     *
     * @param id the id of the eventVideoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventVideoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventVideoDTO> getEventVideo(@PathVariable Long id) {
        log.debug("REST request to get EventVideo : {}", id);
        Optional<EventVideoDTO> eventVideoDTO = eventVideoService.findOne(id);
        return HttpUtils.wrapOrNotFound(eventVideoDTO);
    }

    /**
     * {@code DELETE  /ytbVideo/:id} : delete the "id" eventVideo.
     *
     * @param id the id of the eventVideoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventVideo(@PathVariable Long id) {
        log.debug("REST request to delete EventVideo : {}", id);
        eventVideoService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
