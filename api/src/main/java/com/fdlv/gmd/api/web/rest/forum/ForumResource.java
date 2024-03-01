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

import com.fdlv.gmd.api.domain.forum.Forum;
import com.fdlv.gmd.api.dto.forum.ForumDTO;
import com.fdlv.gmd.api.service.forum.ForumService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link Forum}.
 */
@RestController
@RequestMapping("/api/forums")
public class ForumResource {
    private final Logger log = LoggerFactory.getLogger(ForumResource.class);

    private static final String ENTITY_NAME = "forum";

    private final ForumService forumService;
    

    public ForumResource(ForumService forumService) {
        this.forumService = forumService;
        
    }

    /**
     * {@code POST  /forums} : Create a new forum.
     *
     * @param forumDTO the forumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new forumDTO, or with status {@code 400 (Bad Request)} if the forum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<ForumDTO> createForum(@Valid @RequestBody ForumDTO forumDTO) throws URISyntaxException {
        log.debug("REST request to save Forum : {}", forumDTO);
        if (forumDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        ForumDTO result = forumService.save(forumDTO);
        return ResponseEntity
            .created(new URI("/api/forums/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /forums/:id} : Updates an existing forum.
     *
     * @param id the id of the forumDTO to save.
     * @param forumDTO the forumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated forumDTO,
     * or with status {@code 400 (Bad Request)} if the forumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the forumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ForumDTO> updateForum(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ForumDTO forumDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Forum : {}, {}", id, forumDTO);
        if (forumDTO.getId() == null || !Objects.equals(id, forumDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }
        if (!forumService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        Optional<ForumDTO> result = forumService.partialUpdate(forumDTO);

        return ResponseEntity
            .ok()
            .body(result.get());
    }

    /**
     * {@code PATCH  /forums/:id} : Partial updates given fields of an existing forum, field will ignore if it is null
     *
     * @param id the id of the forumDTO to save.
     * @param forumDTO the forumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated forumDTO,
     * or with status {@code 400 (Bad Request)} if the forumDTO is not valid,
     * or with status {@code 404 (Not Found)} if the forumDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the forumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ForumDTO> partialUpdateForum(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ForumDTO forumDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Forum partially : {}, {}", id, forumDTO);
        if (forumDTO.getId() == null
                || !Objects.equals(id, forumDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!forumService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<ForumDTO> result = forumService.partialUpdate(forumDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /forums} : get all the forums.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of forums in body.
     */
    @GetMapping("/all")
    public List<ForumDTO> getAllForums() {
        log.debug("REST request to get all Forums");
        return forumService.findAll();
    }

    /**
     * {@code GET  /forums/:id} : get the "id" forum.
     *
     * @param id the id of the forumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the forumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ForumDTO> getForum(@PathVariable Long id) {
        log.debug("REST request to get Forum : {}", id);
        Optional<ForumDTO> forumDTO = forumService.findOne(id);
        return HttpUtils.wrapOrNotFound(forumDTO);
    }

    /**
     * {@code DELETE  /forums/:id} : delete the "id" forum.
     *
     * @param id the id of the forumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForum(@PathVariable Long id) {
        log.debug("REST request to delete Forum : {}", id);
        forumService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
    
}
