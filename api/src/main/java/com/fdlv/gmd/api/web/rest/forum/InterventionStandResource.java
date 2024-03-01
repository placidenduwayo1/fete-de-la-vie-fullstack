package com.fdlv.gmd.api.web.rest.forum;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

import com.fdlv.gmd.api.dto.forum.InterventionStandDTO;
import com.fdlv.gmd.api.service.forum.InterventionStandService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.InterventionStand}.
 */
@RestController
@RequestMapping("/api/interventionStands")
public class InterventionStandResource {
    private final Logger log = LoggerFactory.getLogger(InterventionStandResource.class);

    private static final String ENTITY_NAME = "interventionStand";

    private final InterventionStandService interventionStandService;

    public InterventionStandResource(InterventionStandService interventionStandService) {
        this.interventionStandService = interventionStandService;
    }

    /**
     * {@code POST  /interventionStands} : Create a new interventionStand.
     *
     * @param interventionStandDTO the interventionStandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interventionStandDTO, or with status {@code 400 (Bad Request)} if the interventionStand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<InterventionStandDTO> createInterventionStand(@Valid @RequestBody InterventionStandDTO interventionStandDTO) throws URISyntaxException {
        log.debug("REST request to save InterventionStand : {}", interventionStandDTO);
        if (interventionStandDTO.getFsiId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        InterventionStandDTO result = interventionStandService.save(interventionStandDTO);
        return ResponseEntity
            .created(new URI("/api/interventionStands/" + result.getFsiId()))
            .body(result);
    }

    /**
     * {@code PUT  /interventionStands/:id} : Updates an existing interventionStand.
     *
     * @param id the id of the interventionStandDTO to save.
     * @param interventionStandDTO the interventionStandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interventionStandDTO,
     * or with status {@code 400 (Bad Request)} if the interventionStandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interventionStandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InterventionStandDTO> updateInterventionStand(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InterventionStandDTO interventionStandDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InterventionStand : {}, {}", id, interventionStandDTO);
        if (interventionStandDTO.getFsiId() == null
                || !Objects.equals(id, interventionStandDTO.getFsiId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!interventionStandService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        InterventionStandDTO result = interventionStandService.save(interventionStandDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /interventionStands/:id} : Partial updates given fields of an existing interventionStand, field will ignore if it is null
     *
     * @param id the id of the interventionStandDTO to save.
     * @param interventionStandDTO the interventionStandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interventionStandDTO,
     * or with status {@code 400 (Bad Request)} if the interventionStandDTO is not valid,
     * or with status {@code 404 (Not Found)} if the interventionStandDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the interventionStandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<InterventionStandDTO> partialUpdateInterventionStand(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InterventionStandDTO interventionStandDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InterventionStand partially : {}, {}", id, interventionStandDTO);
        if (interventionStandDTO.getFsiId() == null
                || !Objects.equals(id, interventionStandDTO.getFsiId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!interventionStandService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<InterventionStandDTO> result = interventionStandService.partialUpdate(interventionStandDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /interventionStands} : get all the interventionStands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interventionStands in body.
     */
    @GetMapping()
    public List<InterventionStandDTO> getAllInterventionStands() {
        log.debug("REST request to get all InterventionStands");
        return interventionStandService.findAll();
    }

    /**
     * {@code GET  /interventionStands/:id} : get the "id" interventionStand.
     *
     * @param id the id of the interventionStandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interventionStandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InterventionStandDTO> getInterventionStand(@PathVariable Long id) {
        log.debug("REST request to get InterventionStand : {}", id);
        Optional<InterventionStandDTO> interventionStandDTO = interventionStandService.findOne(id);
        return HttpUtils.wrapOrNotFound(interventionStandDTO);
    }

    /**
     * {@code DELETE  /interventionStands/:id} : delete the "id" interventionStand.
     *
     * @param id the id of the interventionStandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterventionStand(@PathVariable Long id) {
        log.debug("REST request to delete InterventionStand : {}", id);
        interventionStandService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
    
}
