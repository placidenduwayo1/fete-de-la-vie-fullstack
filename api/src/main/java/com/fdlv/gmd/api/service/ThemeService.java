package com.fdlv.gmd.api.service;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.ThemeDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.Theme}.
 */
public interface ThemeService {
    /**
     * Save a theme.
     *
     * @param themeDTO the entity to save.
     * @return the persisted entity.
     */
    ThemeDTO save(ThemeDTO themeDTO);

    /**
     * Partially updates a theme.
     *
     * @param themeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThemeDTO> partialUpdate(ThemeDTO themeDTO);

    /**
     * Get all the themes.
     *
     * @return the list of entities.
     */
    List<ThemeDTO> findAll();

    /**
     * Get the "id" theme.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThemeDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" theme.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
