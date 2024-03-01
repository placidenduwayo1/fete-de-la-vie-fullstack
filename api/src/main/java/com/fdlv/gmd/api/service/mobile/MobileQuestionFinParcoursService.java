package com.fdlv.gmd.api.service.mobile;

import com.fdlv.gmd.api.dto.mobile.MobileQuestionFinParcoursDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.mobile.MobileQuestionFinParcours}.
 */
public interface MobileQuestionFinParcoursService {
    /**
     * Save an mobileQuestionFinParcours.
     *
     * @param mobileQuestionFinParcoursDTO the entity to save.
     * @return the persisted entity.
     */
    MobileQuestionFinParcoursDTO save(MobileQuestionFinParcoursDTO mobileQuestionFinParcoursDTO);

    /**
     * Partially updates an mobileQuestionFinParcours.
     *
     * @param mobileQuestionFinParcoursDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MobileQuestionFinParcoursDTO> partialUpdate(MobileQuestionFinParcoursDTO mobileQuestionFinParcoursDTO);

    /**
     * Get all the mobileQuestionsFinParcours.
     *
     * @return the list of entities.
     */
    List<MobileQuestionFinParcoursDTO> findAll();

    /**
     * Get the "id" mobileQuestionFinParcours.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MobileQuestionFinParcoursDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     *
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" mobileQuestionFinParcours.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
