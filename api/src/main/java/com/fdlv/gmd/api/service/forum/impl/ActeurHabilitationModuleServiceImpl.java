package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.ActeurHabilitationModule;
import com.fdlv.gmd.api.dto.forum.ActeurHabilitationModuleDTO;
import com.fdlv.gmd.api.mapper.forum.ActeurHabilitationModuleMapper;
import com.fdlv.gmd.api.repository.forum.ActeurHabilitationModuleRepository;
import com.fdlv.gmd.api.service.forum.ActeurHabilitationModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActeurHabilitationModuleServiceImpl implements ActeurHabilitationModuleService {

    private final Logger log = LoggerFactory.getLogger(ActeurHabilitationModuleServiceImpl.class);

    private final ActeurHabilitationModuleRepository acteurHabilitationModuleRepository;

    private final ActeurHabilitationModuleMapper acteurHabilitationModuleMapper;

    public ActeurHabilitationModuleServiceImpl(ActeurHabilitationModuleRepository acteurHabilitationModuleRepository, ActeurHabilitationModuleMapper acteurHabilitationModuleMapper) {
        this.acteurHabilitationModuleRepository = acteurHabilitationModuleRepository;
        this.acteurHabilitationModuleMapper = acteurHabilitationModuleMapper;
    }

    @Override
    public ActeurHabilitationModuleDTO save(ActeurHabilitationModuleDTO acteurHabilitationModuleDTO) {
        log.debug("Request to save ActeurHabilitationModule: {}", acteurHabilitationModuleDTO);
        ActeurHabilitationModule acteurHabilitationModule = acteurHabilitationModuleMapper.toEntity(acteurHabilitationModuleDTO);
        acteurHabilitationModule = acteurHabilitationModuleRepository.save(acteurHabilitationModule);
        return acteurHabilitationModuleMapper.toDto(acteurHabilitationModule);
    }

    @Override
    public Optional<ActeurHabilitationModuleDTO> partialUpdate(ActeurHabilitationModuleDTO acteurHabilitationModuleDTO) {
        log.debug("Request to partially update ActeurHabilitationModule: {}", acteurHabilitationModuleDTO);

        return acteurHabilitationModuleRepository
                .findById(acteurHabilitationModuleDTO.getId())
                .map(existingActeurHabilitationModule -> {
                    acteurHabilitationModuleMapper.partialUpdate(existingActeurHabilitationModule, acteurHabilitationModuleDTO);
                    return existingActeurHabilitationModule;
                })
                .map(acteurHabilitationModuleRepository::save)
                .map(acteurHabilitationModuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActeurHabilitationModuleDTO> findAll() {
        log.debug("Request to get all ActeurHabilitationModules");
        return acteurHabilitationModuleRepository.findAll().stream()
                .map(acteurHabilitationModuleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActeurHabilitationModuleDTO> findOne(Long id) {
        log.debug("Request to get ActeurHabilitationModule: {}", id);
        return acteurHabilitationModuleRepository.findById(id)
                .map(acteurHabilitationModuleMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return acteurHabilitationModuleRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActeurHabilitationModule: {}", id);
        acteurHabilitationModuleRepository.deleteById(id);
    }
}
