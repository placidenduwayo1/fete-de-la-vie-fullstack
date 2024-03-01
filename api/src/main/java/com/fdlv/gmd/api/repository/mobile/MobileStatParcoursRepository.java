package com.fdlv.gmd.api.repository.mobile;

import com.fdlv.gmd.api.domain.mobile.EventStat;
import com.fdlv.gmd.api.domain.mobile.MobileStatParcours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MobileStatParcoursRepository extends JpaRepository<MobileStatParcours, Long> {
    @Query("SELECT new com.fdlv.gmd.api.domain.mobile.EventStat(g.event.id, e.label, min(g.dateDebut), max(g.dateFin), count(g.mobileUser.id), SUM(CASE WHEN g.parcoursPartage = true THEN 1 ELSE 0 END)) " +
            "FROM MobileStatParcours g JOIN g.event e " +
            "GROUP BY g.event.id")
    List<EventStat> getEventStatsLists();

    @Modifying
    @Query(value = "UPDATE mobile_stat_parcours SET msp_date_fin = :dateFin where msp_id = :id",nativeQuery = true)
    void end(@Param("id")Long id, @Param("dateFin")LocalDateTime dateFin);

    @Modifying
    @Query(value = "UPDATE mobile_stat_parcours SET msp_parcours_partage = true where msp_id = :id",nativeQuery = true)
    void share(@Param("id")Long id);
}
