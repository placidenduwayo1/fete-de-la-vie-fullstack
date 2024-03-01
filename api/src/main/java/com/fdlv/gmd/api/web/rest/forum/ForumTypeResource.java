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

import com.fdlv.gmd.api.domain.forum.ForumType;
import com.fdlv.gmd.api.dto.forum.ForumTypeDTO;
import com.fdlv.gmd.api.service.forum.ForumTypeService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link ForumType}.
 */
@RestController
@RequestMapping("/api/forumTypes")
public class ForumTypeResource {
    private final Logger log = LoggerFactory.getLogger(ForumTypeResource.class);

    private static final String ENTITY_NAME = "forumType";

    private final ForumTypeService forumTypeService;

    public ForumTypeResource(ForumTypeService forumTypeService ) {
        this.forumTypeService = forumTypeService;
    }

    /**
     * {@code POST  /forumTypes} : Create a new forumType.
     *
     * @param forumTypeDTO the forumTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new forumTypeDTO, or with status {@code 400 (Bad Request)} if the forumType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<ForumTypeDTO> createForumType(@Valid @RequestBody ForumTypeDTO forumTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ForumType : {}", forumTypeDTO);
        if (forumTypeDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        ForumTypeDTO result = forumTypeService.save(forumTypeDTO);
        return ResponseEntity
            .created(new URI("/api/forumTypes/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /forumTypes/:id} : Updates an existing forumType.
     *
     * @param id the id of the forumTypeDTO to save.
     * @param forumTypeDTO the forumTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated forumTypeDTO,
     * or with status {@code 400 (Bad Request)} if the forumTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the forumTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ForumTypeDTO> updateForumType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ForumTypeDTO forumTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ForumType : {}, {}", id, forumTypeDTO);
        if (forumTypeDTO.getId() == null
                || !Objects.equals(id, forumTypeDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!forumTypeService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        
        ForumTypeDTO result = forumTypeService.save(forumTypeDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /forumTypes/:id} : Partial updates given fields of an existing forumType, field will ignore if it is null
     *
     * @param id the id of the forumTypeDTO to save.
     * @param forumTypeDTO the forumTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated forumTypeDTO,
     * or with status {@code 400 (Bad Request)} if the forumTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the forumTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the forumTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ForumTypeDTO> partialUpdateForumType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ForumTypeDTO forumTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ForumType partially : {}, {}", id, forumTypeDTO);
        if (forumTypeDTO.getId() == null
                || !Objects.equals(id, forumTypeDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!forumTypeService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<ForumTypeDTO> result = forumTypeService.partialUpdate(forumTypeDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /forumTypes/all} : get all the forumTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of forumTypes in body.
     */
    @GetMapping("/all")
    public List<ForumTypeDTO> getAllForumTypes() {
        log.debug("REST request to get all ForumTypes");
        return forumTypeService.findAll();
    }

    /**
     * {@code GET  /forumTypes/:id} : get the "id" forumType.
     *
     * @param id the id of the forumTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the forumTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ForumTypeDTO> getForumType(@PathVariable Long id) {
        log.debug("REST request to get ForumType : {}", id);
        Optional<ForumTypeDTO> forumTypeDTO = forumTypeService.findOne(id);
        return HttpUtils.wrapOrNotFound(forumTypeDTO);
    }

    /**
     * {@code DELETE  /forumTypes/:id} : delete the "id" forumType.
     *
     * @param id the id of the forumTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumType(@PathVariable Long id) {
        log.debug("REST request to delete ForumType : {}", id);
        forumTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
    
}
