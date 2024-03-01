package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.InterventionStandDTO;

import java.util.List;
import java.util.Optional;

public interface InterventionStandService {
    /**
     * Save an InterventionStand.
     *
     * @param interventionStandDTO the DTO to save.
     * @return the persisted DTO.
     */
    InterventionStandDTO save(InterventionStandDTO interventionStandDTO);

    /**
     * Partially updates an InterventionStand.
     *
     * @param interventionStandDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<InterventionStandDTO> partialUpdate(InterventionStandDTO interventionStandDTO);

    /**
     * Get all the InterventionStands.
     *
     * @return the list of DTOs.
     */
    List<InterventionStandDTO> findAll();

    /**
     * Get the "id" InterventionStand.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<InterventionStandDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" InterventionStand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
