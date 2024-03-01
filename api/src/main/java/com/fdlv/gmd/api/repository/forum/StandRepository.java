package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.Stand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandRepository extends JpaRepository<Stand, Long> {
    // Add custom query methods if needed
}