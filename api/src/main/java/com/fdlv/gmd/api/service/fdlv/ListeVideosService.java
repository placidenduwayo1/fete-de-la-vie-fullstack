package com.fdlv.gmd.api.service.fdlv;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.fdlv.FdlvVideoDTO;
import com.fdlv.gmd.api.dto.fdlv.ListeVideosThemeDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.fdlv.ListeVideos}.
 */
public interface ListeVideosService {
    /**
     * Save an listeVideos.
     *
     * @param listeVideosDTO the entity to save.
     * @return the persisted entity.
     */
    ListeVideosThemeDTO save(ListeVideosThemeDTO listeVideosDTO);

    /**
     * Partially updates an listeVideos.
     *
     * @param listeVideosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ListeVideosThemeDTO> partialUpdate(ListeVideosThemeDTO listeVideosDTO);

    /**
     * Get all the listeVideoss.
     *
     * @return the list of entities.
     */
    List<ListeVideosThemeDTO> findAll();

    /**
     * Get the listeVideoss that should be display.
     *
     * @return the list of entities.
     */
    List<FdlvVideoDTO> findDisplayable();

    /**
     * Get the "id" listeVideos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ListeVideosThemeDTO> findOne(Long id);

    /**
     * Get if the entity with given id exists or not
     * 
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Delete the "id" listeVideos.
     *
     * @param id the id of the entity.
     */
    void hardDelete(Long id);

    /**
     * Soft delete the "id" listeVideos.
     *
     * @param id the id of the entity.
     */
    void softDelete(Long id);

//    boolean isVideoUsed(Long id);




    /**
     * Get all the listeVideoss.
     *
     * @return the list of entities.
     */
    ListeVideosThemeDTO updateVideoQuizz(Long videoID, List<Long> quizzVideoIDlist);

    Optional<ListeVideosThemeDTO> findByUrl(String url);

    Long getMaxMediaId();
}
