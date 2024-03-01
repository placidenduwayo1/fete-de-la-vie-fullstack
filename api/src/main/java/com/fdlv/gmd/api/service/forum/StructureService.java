package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.StructureDTO;

import java.util.List;
import java.util.Optional;

public interface StructureService {
    /**
     * Save a Structure.
     *
     * @param structureDTO the DTO to save.
     * @return the persisted DTO.
     */
    StructureDTO save(StructureDTO structureDTO);

    /**
     * Partially updates a Structure.
     *
     * @param structureDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<StructureDTO> partialUpdate(StructureDTO structureDTO);

    /**
     * Get all the Structures.
     *
     * @return the list of DTOs.
     */
    List<StructureDTO> findAll();

    /**
     * Get the "id" Structure.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<StructureDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" Structure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
