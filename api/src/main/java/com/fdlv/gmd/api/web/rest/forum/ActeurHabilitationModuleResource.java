package com.fdlv.gmd.api.web.rest.forum;

import com.fdlv.gmd.api.dto.forum.ActeurHabilitationModuleDTO;
import com.fdlv.gmd.api.service.forum.ActeurHabilitationModuleService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/acteur-habilitation-modules")
public class ActeurHabilitationModuleResource {

    private final Logger log = LoggerFactory.getLogger(ActeurHabilitationModuleResource.class);

    private static final String ENTITY_NAME = "acteurHabilitationModule";

    private final ActeurHabilitationModuleService acteurHabilitationModuleService;

    public ActeurHabilitationModuleResource(ActeurHabilitationModuleService acteurHabilitationModuleService) {
        this.acteurHabilitationModuleService = acteurHabilitationModuleService;
    }

    @PostMapping()
    public ResponseEntity<ActeurHabilitationModuleDTO> createActeurHabilitationModule(
            @Valid @RequestBody ActeurHabilitationModuleDTO acteurHabilitationModuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ActeurHabilitationModule: {}", acteurHabilitationModuleDTO);
        if (acteurHabilitationModuleDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        ActeurHabilitationModuleDTO result = acteurHabilitationModuleService.save(acteurHabilitationModuleDTO);
        return ResponseEntity
                .created(new URI("/api/acteur-habilitation-modules/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActeurHabilitationModuleDTO> updateActeurHabilitationModule(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody ActeurHabilitationModuleDTO acteurHabilitationModuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ActeurHabilitationModule: {}, {}", id, acteurHabilitationModuleDTO);
        if (acteurHabilitationModuleDTO.getId() == null || !Objects.equals(id, acteurHabilitationModuleDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!acteurHabilitationModuleService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        ActeurHabilitationModuleDTO result = acteurHabilitationModuleService.save(acteurHabilitationModuleDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ActeurHabilitationModuleDTO> partialUpdateActeurHabilitationModule(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody ActeurHabilitationModuleDTO acteurHabilitationModuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActeurHabilitationModule partially: {}, {}", id, acteurHabilitationModuleDTO);
        if (acteurHabilitationModuleDTO.getId() == null || !Objects.equals(id, acteurHabilitationModuleDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!acteurHabilitationModuleService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<ActeurHabilitationModuleDTO> result = acteurHabilitationModuleService.partialUpdate(acteurHabilitationModuleDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    @GetMapping()
    public List<ActeurHabilitationModuleDTO> getAllActeurHabilitationModules() {
        log.debug("REST request to get all ActeurHabilitationModules");
        return acteurHabilitationModuleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActeurHabilitationModuleDTO> getActeurHabilitationModule(@PathVariable Long id) {
        log.debug("REST request to get ActeurHabilitationModule: {}", id);
        Optional<ActeurHabilitationModuleDTO> acteurHabilitationModuleDTO = acteurHabilitationModuleService.findOne(id);
        return HttpUtils.wrapOrNotFound(acteurHabilitationModuleDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActeurHabilitationModule(@PathVariable Long id) {
        log.debug("REST request to delete ActeurHabilitationModule: {}", id);
        acteurHabilitationModuleService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
