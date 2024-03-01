package com.fdlv.gmd.api.service;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.RefLogoTeaserDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.RefLogoTeaser}.
 */
public interface RefLogoTeaserService {
    /**
     * Save an refLogoTeaser.
     *
     * @param refLogoTeaserDTO the entity to save.
     * @return the persisted entity.
     */
    RefLogoTeaserDTO save(RefLogoTeaserDTO refLogoTeaserDTO);

    /**
     * Partially updates an refLogoTeaser.
     *
     * @param refLogoTeaserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RefLogoTeaserDTO> partialUpdate(RefLogoTeaserDTO refLogoTeaserDTO);

    /**
     * Get all the refLogoTeasers.
     *
     * @return the list of entities.
     */
    List<RefLogoTeaserDTO> findAll();

    /**
     * Get the "id" refLogoTeaser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RefLogoTeaserDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" refLogoTeaser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Long getMaxRltId();

    List<RefLogoTeaserDTO> findAllLogos();

    List<RefLogoTeaserDTO> findAllTeasers();
}
