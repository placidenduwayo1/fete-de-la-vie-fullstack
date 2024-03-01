package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.StandBanniereDTO;

import java.util.List;
import java.util.Optional;

public interface StandBanniereService {
    /**
     * Save a StandBanniere.
     *
     * @param standBanniereDTO the DTO to save.
     * @return the persisted DTO.
     */
    StandBanniereDTO save(StandBanniereDTO standBanniereDTO);

    /**
     * Partially updates a StandBanniere.
     *
     * @param standBanniereDTO the DTO to update partially.
     * @return the persisted DTO.
     */
    Optional<StandBanniereDTO> partialUpdate(StandBanniereDTO standBanniereDTO);

    /**
     * Get all the StandBannieres.
     *
     * @return the list of DTOs.
     */
    List<StandBanniereDTO> findAll();

    /**
     * Get the "id" StandBanniere.
     *
     * @param id the id of the entity.
     * @return the DTO.
     */
    Optional<StandBanniereDTO> findOne(Long id);

    boolean existsById(Long id);

    /**
     * Delete the "id" StandBanniere.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
