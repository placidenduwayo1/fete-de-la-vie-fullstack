package com.fdlv.gmd.api.service.fdlv;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fdlv.gmd.api.dto.fdlv.DisplayableEventVideoDTO;
import com.fdlv.gmd.api.mapper.fdlv.DisplayableEventVideoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.fdlv.EventVideo;
import com.fdlv.gmd.api.dto.fdlv.EventVideoDTO;
import com.fdlv.gmd.api.mapper.fdlv.EventVideoMapper;
import com.fdlv.gmd.api.repository.fdlv.EventVideoRepository;

/**
 * Service Implementation for managing {@link EventVideo}.
 */
@Service
@Transactional
public class EventVideoServiceImpl implements EventVideoService {

    private final Logger log = LoggerFactory.getLogger(EventVideoServiceImpl.class);

    private final EventVideoRepository eventVideoRepository;

    private final EventVideoMapper eventVideoMapper;
    private final DisplayableEventVideoMapper displayableEventVideoMapper;

    public EventVideoServiceImpl(EventVideoRepository eventVideoRepository, EventVideoMapper eventVideoMapper, DisplayableEventVideoMapper displayableEventVideoMapper) {
        this.eventVideoRepository = eventVideoRepository;
        this.eventVideoMapper = eventVideoMapper;
        this.displayableEventVideoMapper = displayableEventVideoMapper;
    }

    @Override
    public EventVideoDTO save(EventVideoDTO eventVideoDTO) {
        log.debug("Request to save EventVideo : {}", eventVideoDTO);
        EventVideo eventVideo = eventVideoMapper.toEntity(eventVideoDTO);
        eventVideo = eventVideoRepository.save(eventVideo);
        return eventVideoMapper.toDto(eventVideo);
    }

    @Override
    public Optional<EventVideoDTO> partialUpdate(EventVideoDTO eventVideoDTO) {
        log.debug("Request to partially update eventVideo : {}", eventVideoDTO);

        return eventVideoRepository
            .findById(eventVideoDTO.getId())
            .map(
                existingEventVideo -> {
                    eventVideoMapper.partialUpdate(existingEventVideo, eventVideoDTO);
                    return existingEventVideo;
                }
            )
            .map(eventVideoRepository::save)
            .map(eventVideoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventVideoDTO> findAll() {
        log.debug("Request to get all EventVideos");
        return eventVideoRepository.findAll().stream().map(eventVideoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisplayableEventVideoDTO> findDisplayableVideos() {
        return eventVideoRepository.findDisplayableVideos()
                .stream()
                .map(displayableEventVideoMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventVideoDTO> findOne(Long id) {
        log.debug("Request to get EventVideo : {}", id);
        return eventVideoRepository.findById(id).map(eventVideoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return eventVideoRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventVideo : {}", id);
        eventVideoRepository.deleteById(id);
    }
}
