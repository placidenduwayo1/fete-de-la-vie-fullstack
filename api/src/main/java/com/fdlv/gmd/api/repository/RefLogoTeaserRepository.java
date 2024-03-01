package com.fdlv.gmd.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdlv.gmd.api.domain.RefLogoTeaser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RefLogoTeaserRepository extends JpaRepository<RefLogoTeaser, Long> {
    @Query("SELECT COALESCE(MAX(r.id), 0) FROM RefLogoTeaser r")
    Long getMaxRltId();

    List<RefLogoTeaser> findByTypeMedia(String typeMedia);
}
