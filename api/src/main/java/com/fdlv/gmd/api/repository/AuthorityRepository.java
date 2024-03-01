package com.fdlv.gmd.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdlv.gmd.api.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
