package com.fdlv.gmd.api.repository.mobile;

import com.fdlv.gmd.api.domain.mobile.MobileQuestionFinParcours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data SQL repository for the MobileUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileQuestionFinParcoursRepository extends JpaRepository<MobileQuestionFinParcours, Long> {}
