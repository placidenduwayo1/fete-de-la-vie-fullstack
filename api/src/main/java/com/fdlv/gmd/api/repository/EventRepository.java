package com.fdlv.gmd.api.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.Event;

/**
 * Spring Data SQL repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {}
