package com.fdlv.gmd.api.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.Theme;

/**
 * Spring Data SQL repository for the Theme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {}
