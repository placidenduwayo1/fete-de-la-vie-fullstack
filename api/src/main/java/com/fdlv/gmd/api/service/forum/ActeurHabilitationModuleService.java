package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.ActeurHabilitationModuleDTO;

import java.util.List;
import java.util.Optional;

public interface ActeurHabilitationModuleService {
    /**
     * Save an ActeurHabilitationModule.
     *
     * @param acteurHabilitationModuleDTO the DTO to save.
     * @return the persisted DTO.
     */
    ActeurHabilitationModuleDTO save(ActeurHabilitationModuleDTO acteurHabilitationModuleDTO);

    /**
     * Partially updates an ActeurHabilitationModule.
     *
     * @param acteurHabilitationModuleDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<ActeurHabilitationModuleDTO> partialUpdate(ActeurHabilitationModuleDTO acteurHabilitationModuleDTO);

    /**
     * Get all the ActeurHabilitationModules.
     *
     * @return the list of DTOs.
     */
    List<ActeurHabilitationModuleDTO> findAll();

    /**
     * Get the "id" ActeurHabilitationModule.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<ActeurHabilitationModuleDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" ActeurHabilitationModule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
