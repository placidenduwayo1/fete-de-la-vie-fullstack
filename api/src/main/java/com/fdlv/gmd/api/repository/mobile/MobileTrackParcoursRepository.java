package com.fdlv.gmd.api.repository.mobile;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.mobile.MobileTrackParcours;

@SuppressWarnings("unused")
@Repository
public interface MobileTrackParcoursRepository extends JpaRepository<MobileTrackParcours,Long>{
    @Modifying
    @Query(value = "UPDATE mobile_track_parcours SET mtp_defi_partage = true where mtp_id = :id",nativeQuery = true)
    void share(@Param("id")Long id);

    @Modifying
    @Query(value = "UPDATE mobile_track_parcours SET mtp_challenge_accepte = true where mtp_id = :id",nativeQuery = true)
    void acceptDefi(@Param("id")Long id);

    @Modifying
    @Query(value = "UPDATE mobile_track_parcours SET mtp_quizz_id = :idQuizz where mtp_id = :id",nativeQuery = true)
    void addQuizz(@Param("id")Long id, @Param("idQuizz") Long idQuizz);

}
