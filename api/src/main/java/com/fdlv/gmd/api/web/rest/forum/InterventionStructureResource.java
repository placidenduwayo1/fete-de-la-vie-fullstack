package com.fdlv.gmd.api.web.rest.forum;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.domain.forum.Structure;
import com.fdlv.gmd.api.dto.forum.StructureDTO;
import com.fdlv.gmd.api.mapper.forum.StructureMapper;
import com.fdlv.gmd.api.service.forum.StructureService;
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

import com.fdlv.gmd.api.domain.forum.InterventionStructure;
import com.fdlv.gmd.api.dto.forum.InterventionStructureDTO;
import com.fdlv.gmd.api.service.forum.InterventionStructureService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link InterventionStructure}.
 */
@RestController
@RequestMapping("/api/interventionStructures")
public class InterventionStructureResource {
    private final Logger log = LoggerFactory.getLogger(InterventionStructureResource.class);

    private static final String ENTITY_NAME = "interventionStructure";

    private final InterventionStructureService interventionStructureService;
    private final StructureService structureService;
    private final StructureMapper structureMapper;

    public InterventionStructureResource(InterventionStructureService interventionStructureService, StructureService structureService, StructureMapper structureMapper) {
        this.interventionStructureService = interventionStructureService;
        this.structureService = structureService;
        this.structureMapper = structureMapper;
    }

    /**
     * {@code POST  /interventionStructures} : Create a new interventionStructure.
     *
     * @param interventionStructureDTO the interventionStructureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interventionStructureDTO, or with status {@code 400 (Bad Request)} if the interventionStructure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<InterventionStructureDTO> createInterventionStructure(@Valid @RequestBody InterventionStructureDTO interventionStructureDTO) throws URISyntaxException {
        log.debug("REST request to save InterventionStructure : {}", interventionStructureDTO);
        if (interventionStructureDTO.getFsnId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        InterventionStructureDTO result = interventionStructureService.save(interventionStructureDTO);
        return ResponseEntity
            .created(new URI("/api/interventionStructures/" + result.getFsnId()))
            .body(result);
    }

    /**
     * {@code PUT  /interventionStructures/:id} : Updates an existing interventionStructure.
     *
     * @param id the id of the interventionStructureDTO to save.
     * @param interventionStructureDTO the interventionStructureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interventionStructureDTO,
     * or with status {@code 400 (Bad Request)} if the interventionStructureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interventionStructureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InterventionStructureDTO> updateInterventionStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InterventionStructureDTO interventionStructureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InterventionStructure : {}, {}", id, interventionStructureDTO);
        if (interventionStructureDTO.getFsnId() == null
                || !Objects.equals(id, interventionStructureDTO.getFsnId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!interventionStructureService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        InterventionStructureDTO result = interventionStructureService.save(interventionStructureDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /interventionStructures/:id} : Partial updates given fields of an existing interventionStructure, field will ignore if it is null
     *
     * @param id the id of the interventionStructureDTO to save.
     * @param interventionStructureDTO the interventionStructureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interventionStructureDTO,
     * or with status {@code 400 (Bad Request)} if the interventionStructureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the interventionStructureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the interventionStructureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<InterventionStructureDTO> partialUpdateInterventionStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InterventionStructureDTO interventionStructureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InterventionStructure partially : {}, {}", id, interventionStructureDTO);
        if (interventionStructureDTO.getFsnId() == null
                || !Objects.equals(id, interventionStructureDTO.getFsnId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!interventionStructureService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<InterventionStructureDTO> result = interventionStructureService.partialUpdate(interventionStructureDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /interventionStructures} : get all the interventionStructures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interventionStructures in body.
     */
    @GetMapping()
    public List<InterventionStructureDTO> getAllInterventionStructures() {
        log.debug("REST request to get all InterventionStructures");
        return interventionStructureService.findAll();
    }

    /**
     * {@code GET  /interventionStructures/:id} : get the "id" interventionStructure.
     *
     * @param id the id of the interventionStructureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interventionStructureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InterventionStructureDTO> getInterventionStructure(@PathVariable Long id) {
        log.debug("REST request to get InterventionStructure : {}", id);
        Optional<InterventionStructureDTO> interventionStructureDTO = interventionStructureService.findOne(id);
        return HttpUtils.wrapOrNotFound(interventionStructureDTO);
    }

    /**
     * {@code DELETE  /interventionStructures/:id} : delete the "id" interventionStructure.
     *
     * @param id the id of the interventionStructureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterventionStructure(@PathVariable Long id) {
        log.debug("REST request to delete InterventionStructure : {}", id);
        interventionStructureService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    //new modif debut
    @GetMapping("/intervention-structure/{structureId}")
    public List<InterventionStructureDTO> getInterventionForStructure(@PathVariable(name = "structureId") Long structureId){
       if(structureService.existsById(structureId)){
           Optional<StructureDTO> structureDTO = structureService.findOne(structureId);
           Structure structure  = structureMapper.toEntity(structureDTO.get());
           return interventionStructureService.getInterventionForStructure(structure);
       }
       else {
           return Collections.emptyList();
       }
    }
    //new modif fin
}
