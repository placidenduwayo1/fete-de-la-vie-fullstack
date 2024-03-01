package com.fdlv.gmd.api.web.rest.forum;

import com.fdlv.gmd.api.dto.forum.ActeurHabilitationDocumentDTO;
import com.fdlv.gmd.api.service.forum.ActeurHabilitationDocumentService;
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
@RequestMapping("/api/acteur-habilitation-documents")
public class ActeurHabilitationDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ActeurHabilitationDocumentResource.class);

    private static final String ENTITY_NAME = "acteurHabilitationDocument";

    private final ActeurHabilitationDocumentService acteurHabilitationDocumentService;

    public ActeurHabilitationDocumentResource(ActeurHabilitationDocumentService acteurHabilitationDocumentService) {
        this.acteurHabilitationDocumentService = acteurHabilitationDocumentService;
    }

    @PostMapping()
    public ResponseEntity<ActeurHabilitationDocumentDTO> createActeurHabilitationDocument(
            @Valid @RequestBody ActeurHabilitationDocumentDTO acteurHabilitationDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ActeurHabilitationDocument: {}", acteurHabilitationDocumentDTO);
        if (acteurHabilitationDocumentDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        ActeurHabilitationDocumentDTO result = acteurHabilitationDocumentService.save(acteurHabilitationDocumentDTO);
        return ResponseEntity
                .created(new URI("/api/acteur-habilitation-documents/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActeurHabilitationDocumentDTO> updateActeurHabilitationDocument(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody ActeurHabilitationDocumentDTO acteurHabilitationDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ActeurHabilitationDocument: {}, {}", id, acteurHabilitationDocumentDTO);
        if (acteurHabilitationDocumentDTO.getId() == null || !Objects.equals(id, acteurHabilitationDocumentDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!acteurHabilitationDocumentService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        ActeurHabilitationDocumentDTO result = acteurHabilitationDocumentService.save(acteurHabilitationDocumentDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ActeurHabilitationDocumentDTO> partialUpdateActeurHabilitationDocument(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody ActeurHabilitationDocumentDTO acteurHabilitationDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActeurHabilitationDocument partially: {}, {}", id, acteurHabilitationDocumentDTO);
        if (acteurHabilitationDocumentDTO.getId() == null || !Objects.equals(id, acteurHabilitationDocumentDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!acteurHabilitationDocumentService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<ActeurHabilitationDocumentDTO> result = acteurHabilitationDocumentService.partialUpdate(acteurHabilitationDocumentDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    @GetMapping()
    public List<ActeurHabilitationDocumentDTO> getAllActeurHabilitationDocuments() {
        log.debug("REST request to get all ActeurHabilitationDocuments");
        return acteurHabilitationDocumentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActeurHabilitationDocumentDTO> getActeurHabilitationDocument(@PathVariable Long id) {
        log.debug("REST request to get ActeurHabilitationDocument: {}", id);
        Optional<ActeurHabilitationDocumentDTO> acteurHabilitationDocumentDTO = acteurHabilitationDocumentService.findOne(id);
        return HttpUtils.wrapOrNotFound(acteurHabilitationDocumentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActeurHabilitationDocument(@PathVariable Long id) {
        log.debug("REST request to delete ActeurHabilitationDocument: {}", id);
        acteurHabilitationDocumentService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
