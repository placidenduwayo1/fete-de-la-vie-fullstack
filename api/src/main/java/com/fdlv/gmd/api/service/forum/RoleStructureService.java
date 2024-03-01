package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.RoleStructureDTO;

import java.util.List;
import java.util.Optional;

public interface RoleStructureService {
    /**
     * Save a RoleStructure.
     *
     * @param roleStructureDTO the DTO to save.
     * @return the persisted DTO.
     */
    RoleStructureDTO save(RoleStructureDTO roleStructureDTO);

    /**
     * Partially updates a RoleStructure.
     *
     * @param roleStructureDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<RoleStructureDTO> partialUpdate(RoleStructureDTO roleStructureDTO);

    /**
     * Get all the RoleStructures.
     *
     * @return the list of DTOs.
     */
    List<RoleStructureDTO> findAll();

    /**
     * Get the "id" RoleStructure.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<RoleStructureDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" RoleStructure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
