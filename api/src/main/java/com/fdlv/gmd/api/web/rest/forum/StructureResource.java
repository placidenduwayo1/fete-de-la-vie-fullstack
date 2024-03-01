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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.dto.forum.StructureDTO;
import com.fdlv.gmd.api.service.forum.StructureService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.Structure}.
 */
@RestController
@RequestMapping("/api/structures")
@Validated
public class StructureResource {
    private final Logger log = LoggerFactory.getLogger(StructureResource.class);

    private static final String ENTITY_NAME = "structure";

    private final StructureService structureService;

    public StructureResource(StructureService structureService) {
        this.structureService = structureService;
    }

    /**
     * {@code POST  /structures} : Create a new structure.
     *
     * @param structureDTO the structureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new structureDTO, or with status {@code 400 (Bad Request)} if the structure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<StructureDTO> createStructure(@Valid @RequestBody StructureDTO structureDTO) throws URISyntaxException {
        log.debug("REST request to save Structure : {}", structureDTO);
        if (structureDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        structureDTO.setReference("-"+structureDTO.getCode()+"_"+structureDTO.getCp()+"_"+structureDTO.getCommune()+"-"+structureDTO.getLibelle().substring(0,4));
        StructureDTO result = structureService.save(structureDTO);
        if (result.getId() != null) {
            result.setReference(result.getId()+result.getReference());
            result = structureService.save(result);
        }
        return ResponseEntity
            .created(new URI("/api/structures/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /structures/:id} : Updates an existing structure.
     *
     * @param id the id of the structureDTO to save.
     * @param structureDTO the structureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated structureDTO,
     * or with status {@code 400 (Bad Request)} if the structureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the structureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StructureDTO> updateStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StructureDTO structureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Structure : {}, {}", id, structureDTO);
        if (structureDTO.getId() == null
                || !Objects.equals(id, structureDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!structureService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        structureDTO.setReference(structureDTO.getId()+"-"+structureDTO.getCode()+"_"+structureDTO.getCp()+"_"+structureDTO.getCommune()+"-"+structureDTO.getLibelle().substring(0,4));
        StructureDTO result = structureService.save(structureDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /structures/:id} : Partial updates given fields of an existing structure, field will ignore if it is null
     *
     * @param id the id of the structureDTO to save.
     * @param structureDTO the structureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated structureDTO,
     * or with status {@code 400 (Bad Request)} if the structureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the structureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the structureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StructureDTO> partialUpdateStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StructureDTO structureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Structure partially : {}, {}", id, structureDTO);
        if (structureDTO.getId() == null
                || !Objects.equals(id, structureDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!structureService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<StructureDTO> result = structureService.partialUpdate(structureDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /structures} : get all the structures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of structures in body.
     */
    @GetMapping()
    public List<StructureDTO> getAllStructures() {
        log.debug("REST request to get all Structures");
        return structureService.findAll();
    }

    /**
     * {@code GET  /structures/:id} : get the "id" structure.
     *
     * @param id the id of the structureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the structureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StructureDTO> getStructure(@PathVariable Long id) {
        log.debug("REST request to get Structure : {}", id);
        Optional<StructureDTO> structureDTO = structureService.findOne(id);
        return HttpUtils.wrapOrNotFound(structureDTO);
    }

    /**
     * {@code DELETE  /structures/:id} : delete the "id" structure.
     *
     * @param id the id of the structureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStructure(@PathVariable Long id) {
        log.debug("REST request to delete Structure : {}", id);
        structureService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
