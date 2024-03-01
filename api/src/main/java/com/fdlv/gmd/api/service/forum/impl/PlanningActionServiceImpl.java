package com.fdlv.gmd.api.service.forum.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.fdlv.gmd.api.service.forum.PlanningActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.forum.PlanningAction;
import com.fdlv.gmd.api.dto.forum.PlanningActionDTO;
import com.fdlv.gmd.api.mapper.forum.PlanningActionMapper;
import com.fdlv.gmd.api.repository.forum.PlanningActionRepository;

/**
 * Service Implementation for managing {@link PlanningAction }.
 */
@Service
@Transactional
public class PlanningActionServiceImpl implements PlanningActionService {
    private final Logger log = LoggerFactory.getLogger(PlanningActionServiceImpl.class);

    private final PlanningActionRepository planingActionRepository;

    private final PlanningActionMapper planingActionMapper;

    public PlanningActionServiceImpl(PlanningActionRepository planingActionRepository, PlanningActionMapper planingActionMapper) {
        this.planingActionRepository = planingActionRepository;
        this.planingActionMapper = planingActionMapper;
    }

    @Override
    public PlanningActionDTO save(PlanningActionDTO planingActionDTO) {
        log.debug("Request to save PlaningAction : {}", planingActionDTO);
        PlanningAction planingAction = planingActionMapper.toEntity(planingActionDTO);
        planingAction = planingActionRepository.save(planingAction);
        return planingActionMapper.toDto(planingAction);
    }

    @Override
    public Optional<PlanningActionDTO> partialUpdate(PlanningActionDTO planingActionDTO) {
        log.debug("Request to partially update PlaningAction : {}", planingActionDTO);

        return planingActionRepository
                .findById(planingActionDTO.getId())
                .map(
                        existingPlaningAction -> {
                            planingActionMapper.partialUpdate(existingPlaningAction, planingActionDTO);
                            return existingPlaningAction;
                        }
                )
                .map(planingActionRepository::save)
                .map(planingActionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanningActionDTO> findAll() {
        log.debug("Request to get all PlaningActions");
        return planingActionRepository.findAll().stream().map(planingActionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanningActionDTO> findOne(Long id) {
        log.debug("Request to get PlaningAction : {}", id);
        return planingActionRepository.findById(id).map(planingActionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return planingActionRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlaningAction : {}", id);
        planingActionRepository.deleteById(id);
    }

}