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

import com.fdlv.gmd.api.dto.StageDTO;
import com.fdlv.gmd.api.service.StageService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.Stage}.
 */
@RestController
@RequestMapping("/api/stages")
public class StageResource {

    private final Logger log = LoggerFactory.getLogger(StageResource.class);

    private static final String ENTITY_NAME = "stage";

    private final StageService stageService;

    public StageResource(StageService stageService) {
        this.stageService = stageService;
    }

    /**
     * {@code POST  /stages} : Create a new stage.
     *
     * @param stageDTO the stageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stageDTO, or with status {@code 400 (Bad Request)} if the stage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<StageDTO> createStage(@Valid @RequestBody StageDTO stageDTO) throws URISyntaxException {
        log.debug("REST request to save Stage : {}", stageDTO);
        if (stageDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        
        if(!stageDTO.getVideoUrl().equals(stageDTO.getVideo().getUrlVideo()))
            stageDTO.setVideoUrl(stageDTO.getVideo().getUrlVideo());
        if(stageDTO.getVideoDescription()==null)    
            stageDTO.setVideoDescription(stageDTO.getVideo().getDescription());
        if(stageDTO.getVideoImageUrl()==null)
            stageDTO.setVideoImageUrl(stageDTO.getVideo().getUrlImage());

        StageDTO result = stageService.save(stageDTO);
        return ResponseEntity
            .created(new URI("/api/stages/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /stages/:id} : Updates an existing stage.
     *
     * @param id the id of the stageDTO to save.
     * @param stageDTO the stageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stageDTO,
     * or with status {@code 400 (Bad Request)} if the stageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StageDTO> updateStage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StageDTO stageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Stage : {}, {}", id, stageDTO);
        if (stageDTO.getId() == null
                || !Objects.equals(id, stageDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!stageService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        if(!stageDTO.getVideoUrl().equals(stageDTO.getVideo().getUrlVideo()))
            stageDTO.setVideoUrl(stageDTO.getVideo().getUrlVideo());
        if(stageDTO.getVideoDescription()==null)    
            stageDTO.setVideoDescription(stageDTO.getVideo().getDescription());
        if(stageDTO.getVideoImageUrl()==null)
            stageDTO.setVideoImageUrl(stageDTO.getVideo().getUrlImage());
            
        StageDTO result = stageService.save(stageDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /stages/:id} : Partial updates given fields of an existing stage, field will ignore if it is null
     *
     * @param id the id of the stageDTO to save.
     * @param stageDTO the stageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stageDTO,
     * or with status {@code 400 (Bad Request)} if the stageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StageDTO> partialUpdateStage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StageDTO stageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stage partially : {}, {}", id, stageDTO);
        if (stageDTO.getId() == null
                || !Objects.equals(id, stageDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!stageService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<StageDTO> result = stageService.partialUpdate(stageDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /stages} : get all the stages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stages in body.
     */
    @GetMapping()
    public List<StageDTO> getAllStages() {
        log.debug("REST request to get all Stages");
        return stageService.findAll();
    }

    /**
     * {@code GET  /stages/:id} : get the "id" stage.
     *
     * @param id the id of the stageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StageDTO> getStage(@PathVariable Long id) {
        log.debug("REST request to get Stage : {}", id);
        Optional<StageDTO> stageDTO = stageService.findOne(id);
        return HttpUtils.wrapOrNotFound(stageDTO);
    }

    /**
     * {@code DELETE  /stages/:id} : delete the "id" stage.
     *
     * @param id the id of the stageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        log.debug("REST request to delete Stage : {}", id);
        stageService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
