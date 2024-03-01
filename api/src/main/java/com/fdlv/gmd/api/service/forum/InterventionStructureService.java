package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.domain.forum.Structure;
import com.fdlv.gmd.api.dto.forum.InterventionStructureDTO;

import java.util.List;
import java.util.Optional;

public interface InterventionStructureService {
    /**
     * Save an InterventionStructure.
     *
     * @param interventionStructureDTO the DTO to save.
     * @return the persisted DTO.
     */
    InterventionStructureDTO save(InterventionStructureDTO interventionStructureDTO);

    /**
     * Partially updates an InterventionStructure.
     *
     * @param interventionStructureDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<InterventionStructureDTO> partialUpdate(InterventionStructureDTO interventionStructureDTO);

    /**
     * Get all the InterventionStructures.
     *
     * @return the list of DTOs.
     */
    List<InterventionStructureDTO> findAll();

    /**
     * Get the "id" InterventionStructure.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<InterventionStructureDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" InterventionStructure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    //new modif debut
    List<InterventionStructureDTO> getInterventionForStructure(Structure structure);
    //new modif fin
}
