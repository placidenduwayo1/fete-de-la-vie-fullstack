package com.fdlv.gmd.api.web.rest;

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

import com.fdlv.gmd.api.dto.ThemeDTO;
import com.fdlv.gmd.api.service.ThemeService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.Theme}.
 */
@RestController
@RequestMapping("/api/themes")
public class ThemeResource {

    private final Logger log = LoggerFactory.getLogger(ThemeResource.class);

    private static final String ENTITY_NAME = "theme";

    private final ThemeService themeService;

    public ThemeResource(ThemeService themeService) {
        this.themeService = themeService;
    }

    /**
     * {@code POST  /themes} : Create a new theme.
     *
     * @param themeDTO the themeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new themeDTO, or with status {@code 400 (Bad Request)} if the theme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<ThemeDTO> createTheme(@Valid @RequestBody ThemeDTO themeDTO) throws URISyntaxException {
        log.debug("REST request to save Theme : {}", themeDTO);
        if (themeDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        ThemeDTO result = themeService.save(themeDTO);
        return ResponseEntity
            .created(new URI("/api/themes/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /themes/:id} : Updates an existing theme.
     *
     * @param id the id of the themeDTO to save.
     * @param themeDTO the themeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themeDTO,
     * or with status {@code 400 (Bad Request)} if the themeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the themeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ThemeDTO> updateTheme(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ThemeDTO themeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Theme : {}, {}", id, themeDTO);
        if (themeDTO.getId() == null
                || !Objects.equals(id, themeDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!themeService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        ThemeDTO result = themeService.save(themeDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /themes/:id} : Partial updates given fields of an existing theme, field will ignore if it is null
     *
     * @param id the id of the themeDTO to save.
     * @param themeDTO the themeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themeDTO,
     * or with status {@code 400 (Bad Request)} if the themeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the themeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the themeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ThemeDTO> partialUpdateTheme(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ThemeDTO themeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Theme partially : {}, {}", id, themeDTO);
        if (themeDTO.getId() == null
                || !Objects.equals(id, themeDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!themeService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<ThemeDTO> result = themeService.partialUpdate(themeDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /themes} : get all the themes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themes in body.
     */
    @GetMapping()
    public ResponseEntity<Object> getAllThemes() {
        log.debug("REST request to get all Themes");
        try{
            List<ThemeDTO> themes = themeService.findAll();
            return ResponseEntity.ok(themes);
        } catch (Exception ex){
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }

    /**
     * {@code GET  /themes/:id} : get the "id" theme.
     *
     * @param id the id of the themeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the themeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ThemeDTO> getTheme(@PathVariable Long id) {
        log.debug("REST request to get Theme : {}", id);
        Optional<ThemeDTO> themeDTO = themeService.findOne(id);
        return HttpUtils.wrapOrNotFound(themeDTO);
    }

    /**
     * {@code DELETE  /themes/:id} : delete the "id" theme.
     *
     * @param id the id of the themeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        log.debug("REST request to delete Theme : {}", id);
        themeService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
