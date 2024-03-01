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

import com.fdlv.gmd.api.dto.forum.ModuleApplicatifDTO;
import com.fdlv.gmd.api.service.forum.ModuleApplicatifService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.ModuleApplicatif}.
 */
@RestController
@RequestMapping("/api/moduleApplicatifs")
public class ModuleApplicatifResource {
    
    private final Logger log = LoggerFactory.getLogger(ModuleApplicatifResource.class);

    private static final String ENTITY_NAME = "moduleApplicatif";

    private final ModuleApplicatifService moduleApplicatifService;

    public ModuleApplicatifResource(ModuleApplicatifService moduleApplicatifService ) {
        this.moduleApplicatifService = moduleApplicatifService;
    }

    /**
     * {@code POST  /moduleApplicatifs} : Create a new moduleApplicatif.
     *
     * @param moduleApplicatifDTO the moduleApplicatifDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moduleApplicatifDTO, or with status {@code 400 (Bad Request)} if the moduleApplicatif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<ModuleApplicatifDTO> createModuleApplicatif(@Valid @RequestBody ModuleApplicatifDTO moduleApplicatifDTO) throws URISyntaxException {
        log.debug("REST request to save ModuleApplicatif : {}", moduleApplicatifDTO);
        if (moduleApplicatifDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        ModuleApplicatifDTO result = moduleApplicatifService.save(moduleApplicatifDTO);
        return ResponseEntity
            .created(new URI("/api/moduleApplicatifs/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /moduleApplicatifs/:id} : Updates an existing moduleApplicatif.
     *
     * @param id the id of the moduleApplicatifDTO to save.
     * @param moduleApplicatifDTO the moduleApplicatifDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleApplicatifDTO,
     * or with status {@code 400 (Bad Request)} if the moduleApplicatifDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moduleApplicatifDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ModuleApplicatifDTO> updateModuleApplicatif(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ModuleApplicatifDTO moduleApplicatifDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ModuleApplicatif : {}, {}", id, moduleApplicatifDTO);
        if (moduleApplicatifDTO.getId() == null
                || !Objects.equals(id, moduleApplicatifDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!moduleApplicatifService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        ModuleApplicatifDTO result = moduleApplicatifService.save(moduleApplicatifDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /moduleApplicatifs/:id} : Partial updates given fields of an existing moduleApplicatif, field will ignore if it is null
     *
     * @param id the id of the moduleApplicatifDTO to save.
     * @param moduleApplicatifDTO the moduleApplicatifDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleApplicatifDTO,
     * or with status {@code 400 (Bad Request)} if the moduleApplicatifDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moduleApplicatifDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moduleApplicatifDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ModuleApplicatifDTO> partialUpdateModuleApplicatif(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ModuleApplicatifDTO moduleApplicatifDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModuleApplicatif partially : {}, {}", id, moduleApplicatifDTO);
        if (moduleApplicatifDTO.getId() == null
                || !Objects.equals(id, moduleApplicatifDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!moduleApplicatifService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<ModuleApplicatifDTO> result = moduleApplicatifService.partialUpdate(moduleApplicatifDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /moduleApplicatifs} : get all the moduleApplicatifs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moduleApplicatifs in body.
     */
    @GetMapping()
    public List<ModuleApplicatifDTO> getAllModuleApplicatifs() {
        log.debug("REST request to get all ModuleApplicatifs");
        return moduleApplicatifService.findAll();
    }

    /**
     * {@code GET  /moduleApplicatifs/:id} : get the "id" moduleApplicatif.
     *
     * @param id the id of the moduleApplicatifDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moduleApplicatifDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModuleApplicatifDTO> getModuleApplicatif(@PathVariable Long id) {
        log.debug("REST request to get ModuleApplicatif : {}", id);
        Optional<ModuleApplicatifDTO> moduleApplicatifDTO = moduleApplicatifService.findOne(id);
        return HttpUtils.wrapOrNotFound(moduleApplicatifDTO);
    }

    /**
     * {@code DELETE  /moduleApplicatifs/:id} : delete the "id" moduleApplicatif.
     *
     * @param id the id of the moduleApplicatifDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModuleApplicatif(@PathVariable Long id) {
        log.debug("REST request to delete ModuleApplicatif : {}", id);
        moduleApplicatifService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
