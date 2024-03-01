package com.fdlv.gmd.api.repository.fdlv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.fdlv.EventVideo;

import java.util.List;

/**
 * Spring Data SQL repository for the EventVideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventVideoRepository extends JpaRepository<EventVideo, Long> {

    @Query(value = "SELECT e FROM EventVideo e WHERE e.publie = true"
            + " AND  current_date  >= e.dateDebut"
            + " AND (current_date <= dateFin OR dateFin IS NULL)")
    List<EventVideo> findDisplayableVideos();
}
