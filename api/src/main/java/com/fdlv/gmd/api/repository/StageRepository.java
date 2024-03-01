package com.fdlv.gmd.api.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.Stage;

/**
 * Spring Data SQL repository for the Stage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {}
