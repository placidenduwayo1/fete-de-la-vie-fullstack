package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.RoleActeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleActeurRepository extends JpaRepository<RoleActeur, Long> {
}