package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.StandBanniere;
import com.fdlv.gmd.api.dto.forum.StandBanniereDTO;
import com.fdlv.gmd.api.mapper.forum.StandBanniereMapper;
import com.fdlv.gmd.api.repository.forum.StandBanniereRepository;
import com.fdlv.gmd.api.service.forum.StandBanniereService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StandBanniereServiceImpl implements StandBanniereService {

    private final Logger log = LoggerFactory.getLogger(StandBanniereServiceImpl.class);

    private final StandBanniereRepository standBanniereRepository;

    private final StandBanniereMapper standBanniereMapper;

    public StandBanniereServiceImpl(StandBanniereRepository standBanniereRepository, StandBanniereMapper standBanniereMapper) {
        this.standBanniereRepository = standBanniereRepository;
        this.standBanniereMapper = standBanniereMapper;
    }

    @Override
    public StandBanniereDTO save(StandBanniereDTO standBanniereDTO) {
        log.debug("Request to save StandBanniere: {}", standBanniereDTO);
        StandBanniere standBanniere = standBanniereMapper.toEntity(standBanniereDTO);
        standBanniere = standBanniereRepository.save(standBanniere);
        return standBanniereMapper.toDto(standBanniere);
    }

    @Override
    public Optional<StandBanniereDTO> partialUpdate(StandBanniereDTO standBanniereDTO) {
        log.debug("Request to partially update StandBanniere: {}", standBanniereDTO);

        return standBanniereRepository
                .findById(standBanniereDTO.getId())
                .map(existingStandBanniere -> {
                    standBanniereMapper.partialUpdate(existingStandBanniere, standBanniereDTO);
                    return existingStandBanniere;
                })
                .map(standBanniereRepository::save)
                .map(standBanniereMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StandBanniereDTO> findAll() {
        log.debug("Request to get all StandBannieres");
        return standBanniereRepository.findAll().stream()
                .map(standBanniereMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StandBanniereDTO> findOne(Long id) {
        log.debug("Request to get StandBanniere: {}", id);
        return standBanniereRepository.findById(id)
                .map(standBanniereMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return standBanniereRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StandBanniere: {}", id);
        standBanniereRepository.deleteById(id);
    }
}
