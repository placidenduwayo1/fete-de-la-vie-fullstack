package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.InterventionStand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterventionStandRepository extends JpaRepository<InterventionStand, Long> {

}
