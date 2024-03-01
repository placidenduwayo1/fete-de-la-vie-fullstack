package com.fdlv.gmd.api.service.fdlv;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.domain.fdlv.Partenaire;
import com.fdlv.gmd.api.dto.fdlv.PartenaireDTO;
import com.fdlv.gmd.api.dto.fdlv.PartenaireModuleDTO;

/**
 * Service Interface for managing {@link Partenaire}.
 */
public interface PartenaireService {

    /**
     * Save a partenaire.
     *
     * @param partenaireDTO the entity to save.
     * @return the persisted entity.
     */
    PartenaireDTO save(PartenaireDTO partenaireDTO);

    /**
     * Partially updates a partenaire.
     *
     * @param partenaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PartenaireDTO> partialUpdate(PartenaireDTO partenaireDTO);

    /**
     * Get all the partenaires.
     *
     * @return the list of entities.
     */
    List<PartenaireDTO> findAll();

    /**
     * Get the "id" partenaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartenaireDTO> findOne(Long id);

    /**
     * Delete the "id" partenaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    boolean existsById(Long id);


    /**
     * Get all the partenaires for display on the website.
     *
     * @return the list of entities.
     */
    List<PartenaireModuleDTO> findWebsitePartenaires();

    /**
     * Get all the partenaires for display on the mobile app.
     *
     * @return the list of entities.
     */
    List<PartenaireModuleDTO> findMobilePartenaires();





}
