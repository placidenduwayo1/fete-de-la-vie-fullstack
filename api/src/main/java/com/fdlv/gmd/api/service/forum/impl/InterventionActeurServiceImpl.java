package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.InterventionActeur;
import com.fdlv.gmd.api.dto.forum.InterventionActeurDTO;
import com.fdlv.gmd.api.mapper.forum.InterventionActeurMapper;
import com.fdlv.gmd.api.repository.forum.InterventionActeurRepository;
import com.fdlv.gmd.api.service.forum.InterventionActeurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InterventionActeurServiceImpl implements InterventionActeurService {

    private final Logger log = LoggerFactory.getLogger(InterventionActeurServiceImpl.class);

    private final InterventionActeurRepository interventionActeurRepository;

    private final InterventionActeurMapper interventionActeurMapper;

    public InterventionActeurServiceImpl(InterventionActeurRepository interventionActeurRepository, InterventionActeurMapper interventionActeurMapper) {
        this.interventionActeurRepository = interventionActeurRepository;
        this.interventionActeurMapper = interventionActeurMapper;
    }

    @Override
    public InterventionActeurDTO save(InterventionActeurDTO interventionActeurDTO) {
        log.debug("Request to save InterventionActeur: {}", interventionActeurDTO);
        InterventionActeur interventionActeur = interventionActeurMapper.toEntity(interventionActeurDTO);
        interventionActeur = interventionActeurRepository.save(interventionActeur);
        return interventionActeurMapper.toDto(interventionActeur);
    }

    @Override
    public Optional<InterventionActeurDTO> partialUpdate(InterventionActeurDTO interventionActeurDTO) {
        log.debug("Request to partially update InterventionActeur: {}", interventionActeurDTO);

        return interventionActeurRepository
                .findById(interventionActeurDTO.getFinId())
                .map(existingInterventionActeur -> {
                    interventionActeurMapper.partialUpdate(existingInterventionActeur, interventionActeurDTO);
                    return existingInterventionActeur;
                })
                .map(interventionActeurRepository::save)
                .map(interventionActeurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionActeurDTO> findAll() {
        log.debug("Request to get all InterventionActeurs");
        return interventionActeurRepository.findAll().stream()
                .map(interventionActeurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InterventionActeurDTO> findOne(Long id) {
        log.debug("Request to get InterventionActeur: {}", id);
        return interventionActeurRepository.findById(id)
                .map(interventionActeurMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return interventionActeurRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InterventionActeur: {}", id);
        interventionActeurRepository.deleteById(id);
    }
}
       
