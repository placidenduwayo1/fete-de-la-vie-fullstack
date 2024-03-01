package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.StandBanniere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandBanniereRepository extends JpaRepository<StandBanniere, Long> {
    // Add custom query methods if needed
}