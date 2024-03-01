package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.NiveauResponsabilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NiveauResponsabiliteRepository extends JpaRepository<NiveauResponsabilite,Long> {
       List<NiveauResponsabilite> findByResponsibilityLevel( char responsibilityLevel);
}
