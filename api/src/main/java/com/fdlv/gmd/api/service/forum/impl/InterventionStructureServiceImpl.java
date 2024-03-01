package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.InterventionStructure;
import com.fdlv.gmd.api.domain.forum.Structure;
import com.fdlv.gmd.api.dto.forum.InterventionStructureDTO;
import com.fdlv.gmd.api.mapper.forum.InterventionStructureMapper;
import com.fdlv.gmd.api.repository.forum.InterventionStructureRepository;
import com.fdlv.gmd.api.service.forum.InterventionStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InterventionStructureServiceImpl implements InterventionStructureService {

    private final Logger log = LoggerFactory.getLogger(InterventionStructureServiceImpl.class);

    private final InterventionStructureRepository interventionStructureRepository;

    private final InterventionStructureMapper interventionStructureMapper;

    public InterventionStructureServiceImpl(InterventionStructureRepository interventionStructureRepository, InterventionStructureMapper interventionStructureMapper) {
        this.interventionStructureRepository = interventionStructureRepository;
        this.interventionStructureMapper = interventionStructureMapper;
    }

    @Override
    public InterventionStructureDTO save(InterventionStructureDTO interventionStructureDTO) {
        log.debug("Request to save InterventionStructure: {}", interventionStructureDTO);
        InterventionStructure interventionStructure = interventionStructureMapper.toEntity(interventionStructureDTO);
        interventionStructure = interventionStructureRepository.save(interventionStructure);
        return interventionStructureMapper.toDto(interventionStructure);
    }

    @Override
    public Optional<InterventionStructureDTO> partialUpdate(InterventionStructureDTO interventionStructureDTO) {
        log.debug("Request to partially update InterventionStructure: {}", interventionStructureDTO);

        return interventionStructureRepository
                .findById(interventionStructureDTO.getFsnId())
                .map(existingInterventionStructure -> {
                    interventionStructureMapper.partialUpdate(existingInterventionStructure, interventionStructureDTO);
                    return existingInterventionStructure;
                })
                .map(interventionStructureRepository::save)
                .map(interventionStructureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionStructureDTO> findAll() {
        log.debug("Request to get all InterventionStructures");
        return interventionStructureRepository.findAll().stream()
                .map(interventionStructureMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InterventionStructureDTO> findOne(Long id) {
        log.debug("Request to get InterventionStructure: {}", id);
        return interventionStructureRepository.findById(id)
                .map(interventionStructureMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return interventionStructureRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InterventionStructure: {}", id);
        interventionStructureRepository.deleteById(id);
    }

    // new modif debut
    @Override
    public List<InterventionStructureDTO> getInterventionForStructure(Structure structure) {
        List<InterventionStructure>  interventionStructures = interventionStructureRepository.findByStructure(structure);
        return interventionStructures.stream().map(interventionStructureMapper::toDto)
                .collect(Collectors.toList());
    }
    // new modif fin
}