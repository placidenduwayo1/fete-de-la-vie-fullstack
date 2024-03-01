package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.RoleActeur;
import com.fdlv.gmd.api.dto.forum.RoleActeurDTO;
import com.fdlv.gmd.api.mapper.forum.RoleActeurMapper;
import com.fdlv.gmd.api.repository.forum.RoleActeurRepository;
import com.fdlv.gmd.api.service.forum.RoleActeurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleActeurServiceImpl implements RoleActeurService {

    private final Logger log = LoggerFactory.getLogger(RoleActeurServiceImpl.class);

    private final RoleActeurRepository roleActeurRepository;

    private final RoleActeurMapper roleActeurMapper;

    public RoleActeurServiceImpl(RoleActeurRepository roleActeurRepository, RoleActeurMapper roleActeurMapper) {
        this.roleActeurRepository = roleActeurRepository;
        this.roleActeurMapper = roleActeurMapper;
    }

    @Override
    public RoleActeurDTO save(RoleActeurDTO roleActeurDTO) {
        log.debug("Request to save RoleActeur: {}", roleActeurDTO);
        RoleActeur roleActeur = roleActeurMapper.toEntity(roleActeurDTO);
        roleActeur = roleActeurRepository.save(roleActeur);
        return roleActeurMapper.toDto(roleActeur);
    }

    @Override
    public Optional<RoleActeurDTO> partialUpdate(RoleActeurDTO roleActeurDTO) {
        log.debug("Request to partially update RoleActeur: {}", roleActeurDTO);

        return roleActeurRepository
                .findById(roleActeurDTO.getId())
                .map(existingRoleActeur -> {
                    roleActeurMapper.partialUpdate(existingRoleActeur, roleActeurDTO);
                    return existingRoleActeur;
                })
                .map(roleActeurRepository::save)
                .map(roleActeurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleActeurDTO> findAll() {
        log.debug("Request to get all RoleActeurs");
        return roleActeurRepository.findAll().stream()
                .map(roleActeurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleActeurDTO> findOne(Long id) {
        log.debug("Request to get RoleActeur: {}", id);
        return roleActeurRepository.findById(id)
                .map(roleActeurMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return roleActeurRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleActeur: {}", id);
        roleActeurRepository.deleteById(id);
    }
}
