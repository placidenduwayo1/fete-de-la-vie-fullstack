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

import com.fdlv.gmd.api.domain.forum.DocumentType;
import com.fdlv.gmd.api.dto.forum.DocumentTypeDTO;
import com.fdlv.gmd.api.service.forum.DocumentTypeService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link DocumentType}.
 */
@RestController
@RequestMapping("/api/documentTypes")
public class DocumentTypeResource {
    private final Logger log = LoggerFactory.getLogger(DocumentTypeResource.class);

    private static final String ENTITY_NAME = "documentType";

    private final DocumentTypeService documentTypeService;
   

    public DocumentTypeResource(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    /**
     * {@code POST  /documentTypes} : Create a new documentType.
     *
     * @param documentTypeDTO the documentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentTypeDTO, or with status {@code 400 (Bad Request)} if the documentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<DocumentTypeDTO> createDocumentType(@Valid @RequestBody DocumentTypeDTO documentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentType : {}", documentTypeDTO);
        if (documentTypeDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        DocumentTypeDTO result = documentTypeService.save(documentTypeDTO);
        return ResponseEntity
            .created(new URI("/api/documentTypes/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /documentTypes/:id} : Updates an existing documentType.
     *
     * @param id the id of the documentTypeDTO to save.
     * @param documentTypeDTO the documentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the documentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeDTO> updateDocumentType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumentTypeDTO documentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentType : {}, {}", id, documentTypeDTO);
        if (documentTypeDTO.getId() == null
                || !Objects.equals(id, documentTypeDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!documentTypeService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        
        DocumentTypeDTO result = documentTypeService.save(documentTypeDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /documentTypes/:id} : Partial updates given fields of an existing documentType, field will ignore if it is null
     *
     * @param id the id of the documentTypeDTO to save.
     * @param documentTypeDTO the documentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the documentTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DocumentTypeDTO> partialUpdateDocumentType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumentTypeDTO documentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentType partially : {}, {}", id, documentTypeDTO);
        if (documentTypeDTO.getId() == null
                || !Objects.equals(id, documentTypeDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!documentTypeService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<DocumentTypeDTO> result = documentTypeService.partialUpdate(documentTypeDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /documentTypes} : get all the documentTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentTypes in body.
     */
    @GetMapping()
    public List<DocumentTypeDTO> getAllDocumentTypes() {
        log.debug("REST request to get all DocumentTypes");
        return documentTypeService.findAll();
    }

    /**
     * {@code GET  /documentTypes/:id} : get the "id" documentType.
     *
     * @param id the id of the documentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeDTO> getDocumentType(@PathVariable Long id) {
        log.debug("REST request to get DocumentType : {}", id);
        Optional<DocumentTypeDTO> documentTypeDTO = documentTypeService.findOne(id);
        return HttpUtils.wrapOrNotFound(documentTypeDTO);
    }

    /**
     * {@code DELETE  /documentTypes/:id} : delete the "id" documentType.
     *
     * @param id the id of the documentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable Long id) {
        log.debug("REST request to delete DocumentType : {}", id);
        documentTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
