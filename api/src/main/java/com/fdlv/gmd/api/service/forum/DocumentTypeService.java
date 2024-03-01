package com.fdlv.gmd.api.service.forum;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.domain.forum.DocumentType;
import com.fdlv.gmd.api.dto.forum.DocumentTypeDTO;

/**
 * Service Interface for managing {@link DocumentType}.
 */
public interface DocumentTypeService {
    /**
     * Save a documentType.
     *
     * @param documentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentTypeDTO save(DocumentTypeDTO documentTypeDTO);

    /**
     * Partially updates a documentType.
     *
     * @param documentTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocumentTypeDTO> partialUpdate(DocumentTypeDTO documentTypeDTO);

    /**
     * Get all the documentTypes.
     *
     * @return the list of entities.
     */
    List<DocumentTypeDTO> findAll();

    /**
     * Get the "id" documentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentTypeDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" documentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
