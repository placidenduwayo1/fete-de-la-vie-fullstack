package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.InterventionActeurDTO;

import java.util.List;
import java.util.Optional;

public interface InterventionActeurService {
    /**
     * Save an InterventionActeur.
     *
     * @param interventionActeurDTO the DTO to save.
     * @return the persisted DTO.
     */
    InterventionActeurDTO save(InterventionActeurDTO interventionActeurDTO);

    /**
     * Partially updates an InterventionActeur.
     *
     * @param interventionActeurDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<InterventionActeurDTO> partialUpdate(InterventionActeurDTO interventionActeurDTO);

    /**
     * Get all the InterventionActeurs.
     *
     * @return the list of DTOs.
     */
    List<InterventionActeurDTO> findAll();

    /**
     * Get the "id" InterventionActeur.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<InterventionActeurDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" InterventionActeur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
