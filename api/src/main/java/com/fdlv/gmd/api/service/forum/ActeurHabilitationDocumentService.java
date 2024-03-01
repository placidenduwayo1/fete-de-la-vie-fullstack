package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.ActeurHabilitationDocumentDTO;

import java.util.List;
import java.util.Optional;

public interface ActeurHabilitationDocumentService {
    /**
     * Save an ActeurHabilitationDocument.
     *
     * @param acteurHabilitationDocumentDTO the DTO to save.
     * @return the persisted DTO.
     */
    ActeurHabilitationDocumentDTO save(ActeurHabilitationDocumentDTO acteurHabilitationDocumentDTO);

    /**
     * Partially updates an ActeurHabilitationDocument.
     *
     * @param acteurHabilitationDocumentDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<ActeurHabilitationDocumentDTO> partialUpdate(ActeurHabilitationDocumentDTO acteurHabilitationDocumentDTO);

    /**
     * Get all the ActeurHabilitationDocuments.
     *
     * @return the list of DTOs.
     */
    List<ActeurHabilitationDocumentDTO> findAll();

    /**
     * Get the "id" ActeurHabilitationDocument.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<ActeurHabilitationDocumentDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" ActeurHabilitationDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}