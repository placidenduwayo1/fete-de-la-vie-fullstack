package com.fdlv.gmd.api.service.mobile;

import com.fdlv.gmd.api.dto.mobile.MobileAvisParcoursDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.mobile.MobileAvisParcours}.
 */
public interface MobileAvisParcoursService {
    /**
     * Save an mobileAvisParcours.
     *
     * @param mobileAvisParcoursDTO the entity to save.
     * @return the persisted entity.
     */
    MobileAvisParcoursDTO save(MobileAvisParcoursDTO mobileAvisParcoursDTO);

    /**
     * Partially updates an mobileAvisParcours.
     *
     * @param mobileAvisParcoursDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MobileAvisParcoursDTO> partialUpdate(MobileAvisParcoursDTO mobileAvisParcoursDTO);

    /**
     * Get all the mobileQuestionsFinParcours.
     *
     * @return the list of entities.
     */
    List<MobileAvisParcoursDTO> findAll();

    /**
     * Get the "id" mobileAvisParcours.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MobileAvisParcoursDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     *
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" mobileAvisParcours.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
