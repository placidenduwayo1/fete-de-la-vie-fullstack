package com.fdlv.gmd.api.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.forum.PlanningAction;

@Repository
public interface PlanningActionRepository extends JpaRepository<PlanningAction, Long> {
    
}
