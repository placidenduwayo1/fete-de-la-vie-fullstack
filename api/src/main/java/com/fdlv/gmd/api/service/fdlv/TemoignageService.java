package com.fdlv.gmd.api.service.fdlv;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.fdlv.TemoignageApiDTO;
import com.fdlv.gmd.api.dto.fdlv.TemoignageDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.fdlv.Temoignage}.
 */
public interface TemoignageService {
    /**
     * Save an temoignage.
     *
     * @param temoignageDTO the entity to save.
     * @return the persisted entity.
     */
    TemoignageDTO save(TemoignageDTO temoignageDTO);

    /**
     * Partially updates an temoignage.
     *
     * @param temoignageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TemoignageDTO> partialUpdate(TemoignageDTO temoignageDTO);

    /**
     * Get all the temoignages.
     *
     * @return the list of entities.
     */
    List<TemoignageDTO> findAll();

    /**
     * Get the temoignages that should be display.
     *
     * @return the list of entities.
     */
    List<TemoignageApiDTO> findDisplayable();

    /**
     * Get the "id" temoignage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TemoignageDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" temoignage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
