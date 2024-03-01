package com.fdlv.gmd.api.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.Quizz;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Quizz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuizzRepository extends JpaRepository<Quizz, Long> {
    Quizz findByLabel(String label);

    @Query("select q FROM Quizz q order by q.label")
    List<Quizz> findAllQuizz();

    @Query(value = "SELECT  quizz.id,  quizz.label ,stage_id\n" +
            "            FROM fdlv_liste_videos\n" +
            "            INNER JOIN  video_quizz ON video_quizz.vqu_flv_id= fdlv_liste_videos.flv_id\n" +
            "            INNER JOIN  quizz ON quizz.id=video_quizz.vqu_quizz_id\n" +
            "             WHERE fdlv_liste_videos.flv_id = :id ",nativeQuery = true)
    List<Quizz> findQuizzByVideoId(@Param("id")Long id);
}
