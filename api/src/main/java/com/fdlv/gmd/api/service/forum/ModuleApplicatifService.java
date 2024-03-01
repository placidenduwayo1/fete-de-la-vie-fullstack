package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.ModuleApplicatifDTO;

import java.util.List;
import java.util.Optional;

public interface ModuleApplicatifService {
    /**
     * Save a ModuleApplicatif.
     *
     * @param moduleApplicatifDTO the DTO to save.
     * @return the persisted DTO.
     */
    ModuleApplicatifDTO save(ModuleApplicatifDTO moduleApplicatifDTO);

    /**
     * Partially updates a ModuleApplicatif.
     *
     * @param moduleApplicatifDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<ModuleApplicatifDTO> partialUpdate(ModuleApplicatifDTO moduleApplicatifDTO);

    /**
     * Get all the ModuleApplicatifs.
     *
     * @return the list of DTOs.
     */
    List<ModuleApplicatifDTO> findAll();

    /**
     * Get the "id" ModuleApplicatif.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<ModuleApplicatifDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" ModuleApplicatif.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
