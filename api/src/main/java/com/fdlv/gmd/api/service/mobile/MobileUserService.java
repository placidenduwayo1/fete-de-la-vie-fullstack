package com.fdlv.gmd.api.service.mobile;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.mobile.MobileTypeUtilisationDTO;
import com.fdlv.gmd.api.dto.mobile.MobileUserDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.mobile.MobileUser}.
 */
public interface MobileUserService {
    /**
     * Save an mobileUser.
     *
     * @param mobileUserDTO the entity to save.
     * @return the persisted entity.
     */
    MobileUserDTO save(MobileUserDTO mobileUserDTO);

    /**
     * Partially updates an mobileUser.
     *
     * @param mobileUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MobileUserDTO> partialUpdate(MobileUserDTO mobileUserDTO);

    /**
     * Get all the mobileUsers.
     *
     * @return the list of entities.
     */
    List<MobileUserDTO> findAll();

    /**
     * Get the "id" mobileUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MobileUserDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" mobileUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the different type of utilisation in bd
     *
     * @return the list of MobileTypeUtilisation
     */
    MobileTypeUtilisationDTO getTypeUse();

    /**
     * Close the account of the user id
     * @param id the id of the user
     */
    void closeAccount(Long id);

}
