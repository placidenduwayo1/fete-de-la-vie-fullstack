package com.fdlv.gmd.api.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.Question;

import java.util.List;

/**
 * Spring Data SQL repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuizzId(Long quizzID);
}
