package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.Acteur;
import com.fdlv.gmd.api.domain.forum.Structure;
import com.fdlv.gmd.api.dto.forum.ActeurDTO;
import com.fdlv.gmd.api.dto.forum.StructureDTO;
import com.fdlv.gmd.api.mapper.forum.ActeurMapper;
import com.fdlv.gmd.api.mapper.forum.StructureMapper;
import com.fdlv.gmd.api.repository.forum.StructureRepository;
import com.fdlv.gmd.api.service.forum.ActeurService;
import com.fdlv.gmd.api.service.forum.StructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StructureServiceImpl implements StructureService {

    private final Logger log = LoggerFactory.getLogger(StructureServiceImpl.class);

    private final StructureRepository structureRepository;

    private final StructureMapper structureMapper;

    public StructureServiceImpl(StructureRepository structureRepository, StructureMapper structureMapper) {
        this.structureRepository = structureRepository;
        this.structureMapper = structureMapper;
    }

    @Override
    public StructureDTO save(StructureDTO structureDTO) {
        log.debug("Request to save Structure: {}", structureDTO);
        Structure structure = structureMapper.toEntity(structureDTO);
        structure = structureRepository.save(structure);
        return structureMapper.toDto(structure);
    }

    @Override
    public Optional<StructureDTO> partialUpdate(StructureDTO structureDTO) {
        log.debug("Request to partially update Structure: {}", structureDTO);

        return structureRepository
                .findById(structureDTO.getId())
                .map(existingStructure -> {
                    structureMapper.partialUpdate(existingStructure, structureDTO);
                    return existingStructure;
                })
                .map(structureRepository::save)
                .map(structureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StructureDTO> findAll() {
        log.debug("Request to get all Structures");
        return structureRepository.findAll().stream()
                .map(structureMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StructureDTO> findOne(Long id) {
        log.debug("Request to get Structure: {}", id);
        return structureRepository.findById(id)
                .map(structureMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return structureRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Structure: {}", id);
        structureRepository.deleteById(id);
    }
}
