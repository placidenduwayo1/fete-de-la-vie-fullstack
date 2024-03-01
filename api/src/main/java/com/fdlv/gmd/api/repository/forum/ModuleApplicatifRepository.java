package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.ModuleApplicatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleApplicatifRepository extends JpaRepository<ModuleApplicatif, Long> {
    // Add custom query methods if needed
}