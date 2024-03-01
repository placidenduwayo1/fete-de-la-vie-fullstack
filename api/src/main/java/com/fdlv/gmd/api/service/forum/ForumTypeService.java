package com.fdlv.gmd.api.service.forum;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.forum.ForumTypeDTO;

public interface ForumTypeService {
     /**
     * Save a ForumType.
     *
     * @param forumTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ForumTypeDTO save(ForumTypeDTO forumTypeDTO);

    /**
     * Partially updates a forumTypeDTO.
     *
     * @param forumTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ForumTypeDTO> partialUpdate(ForumTypeDTO forumTypeDTO);

    /**
     * Get all the forumTypeDTO.
     *
     * @return the list of entities.
     */
    List<ForumTypeDTO> findAll();

    /**
     * Get the "id" forumType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ForumTypeDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" forumType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
}
