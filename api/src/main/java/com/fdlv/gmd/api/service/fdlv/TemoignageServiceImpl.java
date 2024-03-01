package com.fdlv.gmd.api.service.fdlv;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fdlv.gmd.api.dto.fdlv.TemoignageApiDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.fdlv.Temoignage;
import com.fdlv.gmd.api.dto.fdlv.TemoignageDTO;
import com.fdlv.gmd.api.mapper.fdlv.TemoignageMapper;
import com.fdlv.gmd.api.repository.fdlv.TemoignageRepository;

/**
 * Service Implementation for managing {@link Temoignage}.
 */
@Service
@Transactional
public class TemoignageServiceImpl implements TemoignageService {

    private final Logger log = LoggerFactory.getLogger(TemoignageServiceImpl.class);

    private final TemoignageRepository temoignageRepository;

    private final TemoignageMapper temoignageMapper;

    public TemoignageServiceImpl(TemoignageRepository temoignageRepository, TemoignageMapper temoignageMapper) {
        this.temoignageRepository = temoignageRepository;
        this.temoignageMapper = temoignageMapper;
    }

    @Override
    public TemoignageDTO save(TemoignageDTO temoignageDTO) {
        log.debug("Request to save Temoignage : {}", temoignageDTO);
        Temoignage temoignage = temoignageMapper.toEntity(temoignageDTO);
        temoignage = temoignageRepository.save(temoignage);
        return temoignageMapper.toDto(temoignage);
    }

    @Override
    public Optional<TemoignageDTO> partialUpdate(TemoignageDTO temoignageDTO) {
        log.debug("Request to partially update temoignage : {}", temoignageDTO);

        return temoignageRepository
            .findById(temoignageDTO.getId())
            .map(
                existingTemoignage -> {
                    temoignageMapper.partialUpdate(existingTemoignage, temoignageDTO);
                    return existingTemoignage;
                }
            )
            .map(temoignageRepository::save)
            .map(temoignageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemoignageDTO> findAll() {
        log.debug("Request to get all Temoignages");
        return temoignageRepository.findAll().stream().map(temoignageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemoignageApiDTO> findDisplayable() {
        log.debug("Request to get displayable Temoignages");
        return temoignageRepository.findDisplayableTestimony().stream().map(temoignageMapper::toDto).map(TemoignageApiDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TemoignageDTO> findOne(Long id) {
        log.debug("Request to get Temoignage : {}", id);
        return temoignageRepository.findById(id).map(temoignageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return temoignageRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Temoignage : {}", id);
        temoignageRepository.deleteById(id);
    }
}
