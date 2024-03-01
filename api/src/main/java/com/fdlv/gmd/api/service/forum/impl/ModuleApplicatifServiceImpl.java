package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.ModuleApplicatif;
import com.fdlv.gmd.api.dto.forum.ModuleApplicatifDTO;
import com.fdlv.gmd.api.mapper.forum.ModuleApplicatifMapper;
import com.fdlv.gmd.api.repository.forum.ModuleApplicatifRepository;
import com.fdlv.gmd.api.service.forum.ModuleApplicatifService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModuleApplicatifServiceImpl implements ModuleApplicatifService {

    private final Logger log = LoggerFactory.getLogger(ModuleApplicatifServiceImpl.class);

    private final ModuleApplicatifRepository moduleApplicatifRepository;

    private final ModuleApplicatifMapper moduleApplicatifMapper;

    public ModuleApplicatifServiceImpl(ModuleApplicatifRepository moduleApplicatifRepository, ModuleApplicatifMapper moduleApplicatifMapper) {
        this.moduleApplicatifRepository = moduleApplicatifRepository;
        this.moduleApplicatifMapper = moduleApplicatifMapper;
    }

    @Override
    public ModuleApplicatifDTO save(ModuleApplicatifDTO moduleApplicatifDTO) {
        log.debug("Request to save ModuleApplicatif: {}", moduleApplicatifDTO);
        ModuleApplicatif moduleApplicatif = moduleApplicatifMapper.toEntity(moduleApplicatifDTO);
        moduleApplicatif = moduleApplicatifRepository.save(moduleApplicatif);
        return moduleApplicatifMapper.toDto(moduleApplicatif);
    }

    @Override
    public Optional<ModuleApplicatifDTO> partialUpdate(ModuleApplicatifDTO moduleApplicatifDTO) {
        log.debug("Request to partially update ModuleApplicatif: {}", moduleApplicatifDTO);

        return moduleApplicatifRepository
                .findById(moduleApplicatifDTO.getId())
                .map(existingModuleApplicatif -> {
                    moduleApplicatifMapper.partialUpdate(existingModuleApplicatif, moduleApplicatifDTO);
                    return existingModuleApplicatif;
                })
                .map(moduleApplicatifRepository::save)
                .map(moduleApplicatifMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleApplicatifDTO> findAll() {
        log.debug("Request to get all ModuleApplicatifs");
        return moduleApplicatifRepository.findAll().stream()
                .map(moduleApplicatifMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModuleApplicatifDTO> findOne(Long id) {
        log.debug("Request to get ModuleApplicatif: {}", id);
        return moduleApplicatifRepository.findById(id).map(moduleApplicatifMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return moduleApplicatifRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModuleApplicatif: {}", id);
        moduleApplicatifRepository.deleteById(id);
    }
}
               
