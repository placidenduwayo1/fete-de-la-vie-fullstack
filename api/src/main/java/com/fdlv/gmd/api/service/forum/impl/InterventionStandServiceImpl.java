package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.InterventionStand;
import com.fdlv.gmd.api.dto.forum.InterventionStandDTO;
import com.fdlv.gmd.api.mapper.forum.InterventionStandMapper;
import com.fdlv.gmd.api.repository.forum.InterventionStandRepository;
import com.fdlv.gmd.api.service.forum.InterventionStandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InterventionStandServiceImpl implements InterventionStandService {

    private final Logger log = LoggerFactory.getLogger(InterventionStandServiceImpl.class);

    private final InterventionStandRepository interventionStandRepository;

    private final InterventionStandMapper interventionStandMapper;

    public InterventionStandServiceImpl(InterventionStandRepository interventionStandRepository, InterventionStandMapper interventionStandMapper) {
        this.interventionStandRepository = interventionStandRepository;
        this.interventionStandMapper = interventionStandMapper;
    }

    @Override
    public InterventionStandDTO save(InterventionStandDTO interventionStandDTO) {
        log.debug("Request to save InterventionStand: {}", interventionStandDTO);
        InterventionStand interventionStand = interventionStandMapper.toEntity(interventionStandDTO);
        interventionStand = interventionStandRepository.save(interventionStand);
        return interventionStandMapper.toDto(interventionStand);
    }

    @Override
    public Optional<InterventionStandDTO> partialUpdate(InterventionStandDTO interventionStandDTO) {
        log.debug("Request to partially update InterventionStand: {}", interventionStandDTO);

        return interventionStandRepository
                .findById(interventionStandDTO.getFsiId())
                .map(existingInterventionStand -> {
                    interventionStandMapper.partialUpdate(existingInterventionStand, interventionStandDTO);
                    return existingInterventionStand;
                })
                .map(interventionStandRepository::save)
                .map(interventionStandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionStandDTO> findAll() {
        log.debug("Request to get all InterventionStands");
        return interventionStandRepository.findAll().stream()
                .map(interventionStandMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InterventionStandDTO> findOne(Long id) {
        log.debug("Request to get InterventionStand: {}", id);
        return interventionStandRepository.findById(id)
                .map(interventionStandMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return interventionStandRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InterventionStand: {}", id);
        interventionStandRepository.deleteById(id);
    }
}
