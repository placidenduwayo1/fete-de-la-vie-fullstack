package com.fdlv.gmd.api.service.fdlv;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.fdlv.InfoPageWebDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.fdlv.InfoPageWeb}.
 */
public interface InfoPageWebService {
    /**
     * Save an infoPageWeb.
     *
     * @param infoPageWebDTO the entity to save.
     * @return the persisted entity.
     */
    InfoPageWebDTO save(InfoPageWebDTO infoPageWebDTO);

    /**
     * Partially updates an infoPageWeb.
     *
     * @param infoPageWebDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InfoPageWebDTO> partialUpdate(InfoPageWebDTO infoPageWebDTO);

    /**
     * Get all the infoPageWebs.
     *
     * @return the list of entities.
     */
    List<InfoPageWebDTO> findAll();
    
    /**
     * Get all the infoPageWebs of nos actions.
     *
     * @return the list of entities.
     */
    List<InfoPageWebDTO> findAllByNosAction();
    
    /**
     * Get all the infoPageWebs of nos actions displayable.
     *
     * @return the list of entities.
     */
    List<InfoPageWebDTO> findAllByNosActionsDisplayable();

    /**
     * Get the "id" infoPageWeb.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InfoPageWebDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" infoPageWeb.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all the infoPageWebs by pageWeb.
     *
     * @return the list of entities.
     */
    List<InfoPageWebDTO> findAllByPageWeb(String pageWeb);
    /**
     * Get all the ImageSite publish for "Dernier Evenement"
     * @return List of all the ImageSite publish for "Dernier Evenement"
     */
    List<InfoPageWebDTO> findDisplayableLastEvent();

    /**
     * Get all the ImageSite publish for "Devise"
     * @return List of all the ImageSite publish for "Devise"
     */
    List<InfoPageWebDTO> findDisplayableDevise();

    /**
     * Get all the ImageSite publish for "FDLV en Mots"
     * @return List of all the ImageSite publish for "FDLV en Mots"
     */
    List<InfoPageWebDTO> findDisplayableFDLVEnMots();
}
