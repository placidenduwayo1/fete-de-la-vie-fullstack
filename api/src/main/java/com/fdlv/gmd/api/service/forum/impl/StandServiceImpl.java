package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.Stand;
import com.fdlv.gmd.api.dto.forum.StandDTO;
import com.fdlv.gmd.api.mapper.forum.StandMapper;
import com.fdlv.gmd.api.repository.forum.StandRepository;
import com.fdlv.gmd.api.service.forum.StandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StandServiceImpl implements StandService {
    private final Logger log = LoggerFactory.getLogger(StandServiceImpl.class);

    private final StandRepository standRepository;
    private final StandMapper standMapper;

    public StandServiceImpl(StandRepository standRepository, StandMapper standMapper) {
        this.standRepository = standRepository;
        this.standMapper = standMapper;
    }

    @Override
    public StandDTO save(StandDTO standDTO) {
        log.debug("Request to save Stand: {}", standDTO);
        Stand stand = standMapper.toEntity(standDTO);
        stand = standRepository.save(stand);
        return standMapper.toDto(stand);
    }

    @Override
    public Optional<StandDTO> partialUpdate(StandDTO standDTO) {
        log.debug("Request to partially update Stand: {}", standDTO);

        return standRepository.findById(standDTO.getId())
                .map(existingStand -> {
                    standMapper.updateStandFromDto(existingStand, standDTO);
                    return existingStand;
                })
                .map(standRepository::save)
                .map(standMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StandDTO> findAll() {
        log.debug("Request to get all Stands");
        return standRepository.findAll().stream()
                .map(standMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StandDTO> findOne(Long id) {
        log.debug("Request to get Stand: {}", id);
        return standRepository.findById(id)
                .map(standMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return standRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stand: {}", id);
        standRepository.deleteById(id);
    }
}
