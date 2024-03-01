package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.RoleStructure;
import com.fdlv.gmd.api.dto.forum.RoleStructureDTO;
import com.fdlv.gmd.api.mapper.forum.RoleStructureMapper;
import com.fdlv.gmd.api.repository.forum.RoleStructureRepository;
import com.fdlv.gmd.api.service.forum.RoleStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleStructureServiceImpl implements RoleStructureService {

    private final Logger log = LoggerFactory.getLogger(RoleStructureServiceImpl.class);

    private final RoleStructureRepository roleStructureRepository;

    private final RoleStructureMapper roleStructureMapper;

    public RoleStructureServiceImpl(RoleStructureRepository roleStructureRepository, RoleStructureMapper roleStructureMapper) {
        this.roleStructureRepository = roleStructureRepository;
        this.roleStructureMapper = roleStructureMapper;
    }

    @Override
    public RoleStructureDTO save(RoleStructureDTO roleStructureDTO) {
        log.debug("Request to save RoleStructure: {}", roleStructureDTO);
        RoleStructure roleStructure = roleStructureMapper.toEntity(roleStructureDTO);
        roleStructure = roleStructureRepository.save(roleStructure);
        return roleStructureMapper.toDto(roleStructure);
    }

    @Override
    public Optional<RoleStructureDTO> partialUpdate(RoleStructureDTO roleStructureDTO) {
        log.debug("Request to partially update RoleStructure: {}", roleStructureDTO);

        return roleStructureRepository
                .findById(roleStructureDTO.getFroId())
                .map(existingRoleStructure -> {
                    roleStructureMapper.partialUpdate(existingRoleStructure, roleStructureDTO);
                    return existingRoleStructure;
                })
                .map(roleStructureRepository::save)
                .map(roleStructureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleStructureDTO> findAll() {
        log.debug("Request to get all RoleStructures");
        return roleStructureRepository.findAll().stream()
                .map(roleStructureMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleStructureDTO> findOne(Long id) {
        log.debug("Request to get RoleStructure: {}", id);
        return roleStructureRepository.findById(id)
                .map(roleStructureMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return roleStructureRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleStructure: {}", id);
        roleStructureRepository.deleteById(id);
    }
}
