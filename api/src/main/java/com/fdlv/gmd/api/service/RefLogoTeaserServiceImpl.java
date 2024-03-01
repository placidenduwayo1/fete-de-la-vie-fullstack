package com.fdlv.gmd.api.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.RefLogoTeaser;
import com.fdlv.gmd.api.dto.RefLogoTeaserDTO;
import com.fdlv.gmd.api.mapper.RefLogoTeaserMapper;
import com.fdlv.gmd.api.repository.RefLogoTeaserRepository;

/**
 * Service Implementation for managing {@link RefLogoTeaser}.
 */
@Service
@Transactional
public class RefLogoTeaserServiceImpl implements RefLogoTeaserService {

    private final Logger log = LoggerFactory.getLogger(RefLogoTeaserServiceImpl.class);

    private final RefLogoTeaserRepository refLogoTeaserRepository;

    private final RefLogoTeaserMapper refLogoTeaserMapper;

    public RefLogoTeaserServiceImpl(RefLogoTeaserRepository refLogoTeaserRepository, RefLogoTeaserMapper refLogoTeaserMapper) {
        this.refLogoTeaserRepository = refLogoTeaserRepository;
        this.refLogoTeaserMapper = refLogoTeaserMapper;
    }

    @Override
    public RefLogoTeaserDTO save(RefLogoTeaserDTO refLogoTeaserDTO) {
        log.debug("Request to save RefLogoTeaser : {}", refLogoTeaserDTO);
        RefLogoTeaser refLogoTeaser = refLogoTeaserMapper.toEntity(refLogoTeaserDTO);
        refLogoTeaser = refLogoTeaserRepository.save(refLogoTeaser);
        return refLogoTeaserMapper.toDto(refLogoTeaser);
    }

    @Override
    public Optional<RefLogoTeaserDTO> partialUpdate(RefLogoTeaserDTO refLogoTeaserDTO) {
        log.debug("Request to partially update refLogoTeaser : {}", refLogoTeaserDTO);

        return refLogoTeaserRepository
            .findById(refLogoTeaserDTO.getId())
            .map(
                existingRefLogoTeaser -> {
                    refLogoTeaserMapper.partialUpdate(existingRefLogoTeaser, refLogoTeaserDTO);
                    return existingRefLogoTeaser;
                }
            )
            .map(refLogoTeaserRepository::save)
            .map(refLogoTeaserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RefLogoTeaserDTO> findAll() {
        log.debug("Request to get all RefLogoTeasers");
        return refLogoTeaserRepository.findAll().stream().map(refLogoTeaserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefLogoTeaserDTO> findOne(Long id) {
        log.debug("Request to get RefLogoTeaser : {}", id);
        return refLogoTeaserRepository.findById(id).map(refLogoTeaserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return refLogoTeaserRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefLogoTeaser : {}", id);
        refLogoTeaserRepository.deleteById(id);
    }

    @Override
    public Long getMaxRltId() {
        log.debug("Request to get max ID in RefLogoTeaser");
        Long maxRltId = refLogoTeaserRepository.getMaxRltId();
        log.debug("Request to get max ID in RefLogoTeaser returned: {}", maxRltId);
        return maxRltId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RefLogoTeaserDTO> findAllLogos() {
        log.debug("Request to get all RefLogos");
        return refLogoTeaserRepository.findByTypeMedia("Logo").stream().map(refLogoTeaserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RefLogoTeaserDTO> findAllTeasers() {
        log.debug("Request to get all RefTeasers");
        return refLogoTeaserRepository.findByTypeMedia("Teaser").stream().map(refLogoTeaserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
