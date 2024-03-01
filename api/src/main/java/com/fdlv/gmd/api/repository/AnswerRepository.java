package com.fdlv.gmd.api.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.Answer;

/**
 * Spring Data SQL repository for the Answer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {}
