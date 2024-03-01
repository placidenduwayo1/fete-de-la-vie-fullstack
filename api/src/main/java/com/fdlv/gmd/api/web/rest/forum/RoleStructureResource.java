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

import com.fdlv.gmd.api.dto.forum.RoleStructureDTO;
import com.fdlv.gmd.api.service.forum.RoleStructureService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.RoleStructure}.
 */
@RestController
@RequestMapping("/api/roleStructures")
public class RoleStructureResource {
    private final Logger log = LoggerFactory.getLogger(RoleStructureResource.class);

    private static final String ENTITY_NAME = "roleStructure";

    private final RoleStructureService roleStructureService;

    public RoleStructureResource(RoleStructureService roleStructureService ) {
        this.roleStructureService = roleStructureService;
    }

    /**
     * {@code POST  /roleStructures} : Create a new roleStructure.
     *
     * @param roleStructureDTO the roleStructureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleStructureDTO, or with status {@code 400 (Bad Request)} if the roleStructure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<RoleStructureDTO> createRoleStructure(@Valid @RequestBody RoleStructureDTO roleStructureDTO) throws URISyntaxException {
        log.debug("REST request to save RoleStructure : {}", roleStructureDTO);
        if (roleStructureDTO.getFroId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        RoleStructureDTO result = roleStructureService.save(roleStructureDTO);
        return ResponseEntity
            .created(new URI("/api/roleStructures/" + result.getFroId()))
            .body(result);
    }

    /**
     * {@code PUT  /roleStructures/:id} : Updates an existing roleStructure.
     *
     * @param id the id of the roleStructureDTO to save.
     * @param roleStructureDTO the roleStructureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleStructureDTO,
     * or with status {@code 400 (Bad Request)} if the roleStructureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleStructureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoleStructureDTO> updateRoleStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoleStructureDTO roleStructureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoleStructure : {}, {}", id, roleStructureDTO);
        if (roleStructureDTO.getFroId() == null
                || !Objects.equals(id, roleStructureDTO.getFroId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!roleStructureService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        RoleStructureDTO result = roleStructureService.save(roleStructureDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /roleStructures/:id} : Partial updates given fields of an existing roleStructure, field will ignore if it is null
     *
     * @param id the id of the roleStructureDTO to save.
     * @param roleStructureDTO the roleStructureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleStructureDTO,
     * or with status {@code 400 (Bad Request)} if the roleStructureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roleStructureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleStructureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RoleStructureDTO> partialUpdateRoleStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoleStructureDTO roleStructureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleStructure partially : {}, {}", id, roleStructureDTO);
        if (roleStructureDTO.getFroId() == null
                || !Objects.equals(id, roleStructureDTO.getFroId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!roleStructureService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<RoleStructureDTO> result = roleStructureService.partialUpdate(roleStructureDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /roleStructures} : get all the roleStructures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleStructures in body.
     */
    @GetMapping()
    public List<RoleStructureDTO> getAllRoleStructures() {
        log.debug("REST request to get all RoleStructures");
        return roleStructureService.findAll();
    }

    /**
     * {@code GET  /roleStructures/:id} : get the "id" roleStructure.
     *
     * @param id the id of the roleStructureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleStructureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleStructureDTO> getRoleStructure(@PathVariable Long id) {
        log.debug("REST request to get RoleStructure : {}", id);
        Optional<RoleStructureDTO> roleStructureDTO = roleStructureService.findOne(id);
        return HttpUtils.wrapOrNotFound(roleStructureDTO);
    }

    /**
     * {@code DELETE  /roleStructures/:id} : delete the "id" roleStructure.
     *
     * @param id the id of the roleStructureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleStructure(@PathVariable Long id) {
        log.debug("REST request to delete RoleStructure : {}", id);
        roleStructureService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
    
}
