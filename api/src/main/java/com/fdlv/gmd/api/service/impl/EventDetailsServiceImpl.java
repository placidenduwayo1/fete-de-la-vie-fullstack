package com.fdlv.gmd.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.Event;
import com.fdlv.gmd.api.dto.EventDetailsDTO;
import com.fdlv.gmd.api.mapper.EventDetailsMapper;
import com.fdlv.gmd.api.repository.EventRepository;
import com.fdlv.gmd.api.service.EventDetailsService;

/**
 * Service Implementation for managing {@link Event}.
 */
@Service
@Transactional
public class EventDetailsServiceImpl implements EventDetailsService {

    private final Logger log = LoggerFactory.getLogger(EventDetailsServiceImpl.class);

    private final EventRepository eventRepository;

    private final EventDetailsMapper eventDetailsMapper;

    public EventDetailsServiceImpl(EventRepository eventRepository, EventDetailsMapper eventDetailsMapper) {
        this.eventRepository = eventRepository;
        this.eventDetailsMapper = eventDetailsMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDetailsDTO> findAll() {
        log.debug("Request to get all Events");
        return eventRepository.findAll().stream().map(eventDetailsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
