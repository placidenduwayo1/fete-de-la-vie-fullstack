package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.ActeurHabilitationModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActeurHabilitationModuleRepository extends JpaRepository<ActeurHabilitationModule, Long> {
    // Add custom query methods if needed
}