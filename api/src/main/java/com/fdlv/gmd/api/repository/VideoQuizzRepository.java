package com.fdlv.gmd.api.repository;

import com.fdlv.gmd.api.domain.fdlv.VideoQuizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the VideoQuizz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoQuizzRepository extends JpaRepository<VideoQuizz, Long> {
    List<VideoQuizz> findByIdQuizz(Long idQuizz);
}
