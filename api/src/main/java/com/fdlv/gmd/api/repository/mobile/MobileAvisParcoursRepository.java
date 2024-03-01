package com.fdlv.gmd.api.repository.mobile;

import com.fdlv.gmd.api.domain.mobile.MobileAvisParcours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MobileUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileAvisParcoursRepository extends JpaRepository<MobileAvisParcours, Long> {
}
