package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.RoleActeurDTO;

import java.util.List;
import java.util.Optional;

public interface RoleActeurService {
    /**
     * Save a RoleActeur.
     *
     * @param roleActeurDTO the DTO to save.
     * @return the persisted DTO.
     */
    RoleActeurDTO save(RoleActeurDTO roleActeurDTO);

    /**
     * Partially updates a RoleActeur.
     *
     * @param roleActeurDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<RoleActeurDTO> partialUpdate(RoleActeurDTO roleActeurDTO);

    /**
     * Get all the RoleActeurs.
     *
     * @return the list of DTOs.
     */
    List<RoleActeurDTO> findAll();

    /**
     * Get the "id" RoleActeur.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<RoleActeurDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" RoleActeur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
