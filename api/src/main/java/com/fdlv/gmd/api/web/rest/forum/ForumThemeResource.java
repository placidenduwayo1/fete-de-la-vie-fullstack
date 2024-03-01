package com.fdlv.gmd.api.web.rest.forum;

import com.fdlv.gmd.api.dto.forum.ForumThemeDTO;
import com.fdlv.gmd.api.service.forum.ForumThemeService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.forum.ForumTheme}.
 */
@RestController
@RequestMapping("/api/forum-theme")
@Validated
public class ForumThemeResource {
    private final Logger log = LoggerFactory.getLogger(ForumThemeResource.class);

    private static final String ENTITY_NAME = "forum-theme";

    private final ForumThemeService ftService;

    public ForumThemeResource(ForumThemeService forumThemeService) {
        this.ftService = forumThemeService;
    }

    /**
     * {@code POST  /forum-theme} : Create a new structure.
     * @param ftDTO the ForumThemeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ForumThemeDTO, or with status {@code 400 (Bad Request)} if the structure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<ForumThemeDTO> createForumTheme(@Valid @RequestBody ForumThemeDTO ftDTO) throws URISyntaxException {
        log.debug("REST request to save New Theme Forum {}", ftDTO);
        if (ftDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        ForumThemeDTO result = ftService.save(ftDTO);
        if (result.getId() != null) {
            result = ftService.save(result);
        }
        return ResponseEntity.created(new URI("/api/structures/" + result.getId())).body(result);
    }

    /**
     * {@code PUT  /structures/:id} : Updates an existing structure.
     *
     * @param id the id of the ForumThemeDTO to save.
     * @param ForumThemeDTO the ForumThemeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ForumThemeDTO,
     * or with status {@code 400 (Bad Request)} if the ForumThemeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ForumThemeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ForumThemeDTO> updateForumTheme(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ForumThemeDTO ForumThemeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Structure : {}, {}", id, ForumThemeDTO);
        if (ForumThemeDTO.getId() == null || !Objects.equals(id, ForumThemeDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!ftService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        ForumThemeDTO result = ftService.save(ForumThemeDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /structures/:id} : Partial updates given fields of an existing forum theme, field will ignore if it is null
     * @param id the id of the ForumThemeDTO to save.
     * @param ForumThemeDTO the ForumThemeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ForumThemeDTO,
     * or with status {@code 400 (Bad Request)} if the ForumThemeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ForumThemeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ForumThemeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ForumThemeDTO> partialUpdateForumTheme(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ForumThemeDTO ForumThemeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update theme forum partially : {}, {}", id, ForumThemeDTO);
        if (ForumThemeDTO.getId() == null || !Objects.equals(id, ForumThemeDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }
        if (!ftService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        Optional<ForumThemeDTO> result = ftService.partialUpdate(ForumThemeDTO);
        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /structures} : get all the theme forum.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of structures in body.
     */
    @GetMapping()
    public List<ForumThemeDTO> getAllForumTheme() {
        log.debug("REST request to get all Structures");
        return ftService.findAll();
    }

    /**
     * {@code GET  /structures/:id} : get the "id" structure.
     * @param id the id of the ForumThemeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ForumThemeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ForumThemeDTO> getForumTheme(@PathVariable Long id) {
        log.debug("REST request to get Structure : {}", id);
        Optional<ForumThemeDTO> ForumThemeDTO = ftService.findOne(id);
        return HttpUtils.wrapOrNotFound(ForumThemeDTO);
    }

    /**
     * {@code DELETE  /structures/:id} : delete the "id" theme forum.
     * @param id the id of the ForumThemeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumTheme(@PathVariable Long id) {
        log.debug("REST request to delete Structure : {}", id);
        ftService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
