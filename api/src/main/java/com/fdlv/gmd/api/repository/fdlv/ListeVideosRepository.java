package com.fdlv.gmd.api.repository.fdlv;

import com.fdlv.gmd.api.domain.fdlv.ListeVideos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the EventVideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListeVideosRepository extends JpaRepository<ListeVideos, Long> {
    Optional<ListeVideos> findByUrlVideo (String urlVideo);

    @Query("SELECT COALESCE(MAX(lv.id), 0) FROM ListeVideos lv")
    Long findMaxId();

    @Query("SELECT lv FROM ListeVideos lv WHERE lv.active = TRUE")
    List<ListeVideos> findDisplayableDTO();

}
