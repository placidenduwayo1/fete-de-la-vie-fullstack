package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.RoleStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleStructureRepository extends JpaRepository<RoleStructure, Long> {
}