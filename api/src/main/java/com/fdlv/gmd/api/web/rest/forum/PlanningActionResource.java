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

import com.fdlv.gmd.api.domain.forum.PlanningAction;
import com.fdlv.gmd.api.dto.forum.PlanningActionDTO;
import com.fdlv.gmd.api.service.forum.PlanningActionService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link PlanningAction}.
 */
@RestController
@RequestMapping("/api/planningActions")
public class PlanningActionResource {
    private final Logger log = LoggerFactory.getLogger(PlanningActionResource.class);

    private static final String ENTITY_NAME = "planningAction";

    private final PlanningActionService planningActionService;

    public PlanningActionResource(PlanningActionService planningActionService) {
        this.planningActionService = planningActionService;
    }

    /**
     * {@code POST  /planningActions} : Create a new planningAction.
     *
     * @param planningActionDTO the planningActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planningActionDTO, or with status {@code 400 (Bad Request)} if the planningAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<PlanningActionDTO> createPlanningAction(@Valid @RequestBody PlanningActionDTO planningActionDTO) throws URISyntaxException {
        log.debug("REST request to save PlanningAction : {}", planningActionDTO);
        if (planningActionDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        PlanningActionDTO result = planningActionService.save(planningActionDTO);
        return ResponseEntity
            .created(new URI("/api/planningActions/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /planningActions/:id} : Updates an existing planningAction.
     *
     * @param id the id of the planningActionDTO to save.
     * @param planningActionDTO the planningActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planningActionDTO,
     * or with status {@code 400 (Bad Request)} if the planningActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planningActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlanningActionDTO> updatePlanningAction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanningActionDTO planningActionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlanningAction : {}, {}", id, planningActionDTO);
        if (planningActionDTO.getId() == null
                || !Objects.equals(id, planningActionDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!planningActionService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        
        PlanningActionDTO result = planningActionService.save(planningActionDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /planningActions/:id} : Partial updates given fields of an existing planningAction, field will ignore if it is null
     *
     * @param id the id of the planningActionDTO to save.
     * @param planningActionDTO the planningActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planningActionDTO,
     * or with status {@code 400 (Bad Request)} if the planningActionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the planningActionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the planningActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PlanningActionDTO> partialUpdatePlanningAction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanningActionDTO planningActionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanningAction partially : {}, {}", id, planningActionDTO);
        if (planningActionDTO.getId() == null
                || !Objects.equals(id, planningActionDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!planningActionService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<PlanningActionDTO> result = planningActionService.partialUpdate(planningActionDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /planningActions} : get all the planningActions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planningActions in body.
     */
    @GetMapping()
    public List<PlanningActionDTO> getAllPlanningActions() {
        log.debug("REST request to get all PlanningActions");
        return planningActionService.findAll();
    }

    /**
     * {@code GET  /planningActions/:id} : get the "id" planningAction.
     *
     * @param id the id of the planningActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planningActionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanningActionDTO> getPlanningAction(@PathVariable Long id) {
        log.debug("REST request to get PlanningAction : {}", id);
        Optional<PlanningActionDTO> planningActionDTO = planningActionService.findOne(id);
        return HttpUtils.wrapOrNotFound(planningActionDTO);
    }

    /**
     * {@code DELETE  /planningActions/:id} : delete the "id" planningAction.
     *
     * @param id the id of the planningActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanningAction(@PathVariable Long id) {
        log.debug("REST request to delete PlanningAction : {}", id);
        planningActionService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
