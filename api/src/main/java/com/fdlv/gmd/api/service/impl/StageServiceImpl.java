package com.fdlv.gmd.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fdlv.gmd.api.domain.fdlv.ListeVideos;
import com.fdlv.gmd.api.repository.fdlv.ListeVideosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.Stage;
import com.fdlv.gmd.api.dto.StageDTO;
import com.fdlv.gmd.api.mapper.StageMapper;
import com.fdlv.gmd.api.repository.StageRepository;
import com.fdlv.gmd.api.service.StageService;

/**
 * Service Implementation for managing {@link Stage}.
 */
@Service
@Transactional
public class StageServiceImpl implements StageService {

    private final Logger log = LoggerFactory.getLogger(StageServiceImpl.class);

    private final StageRepository stageRepository;

    private final  ListeVideosRepository  listeVideosRepository;

    private final StageMapper stageMapper;
    private static final  String shareDefaultString= "Je t'invite à visionner cette video « %s » et à y relever le defi santé" ;

    public StageServiceImpl(StageRepository stageRepository, ListeVideosRepository listeVideosRepository, StageMapper stageMapper) {
        this.stageRepository = stageRepository;
        this.listeVideosRepository = listeVideosRepository;
        this.stageMapper = stageMapper;
    }

    @Override
    public StageDTO save(StageDTO stageDTO) {
        log.debug("Request to save Stage : {}", stageDTO);
        Stage stage = stageMapper.toEntity(stageDTO);
        
        
        stage = stageRepository.save(stage);
        return stageMapper.toDto(stage);
    }

    @Override
    public Optional<StageDTO> partialUpdate(StageDTO stageDTO) {
        log.debug("Request to partially update Stage : {}", stageDTO);

        return stageRepository
            .findById(stageDTO.getId())
            .map(
                existingStage -> {
                    stageMapper.partialUpdate(existingStage, stageDTO);
                    return existingStage;
                }
            )
            .map(stageRepository::save)
            .map(stageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StageDTO> findAll() {
        log.debug("Request to get all Stages");
        log.info("Request to get all Stages");
        return stageRepository.findAll(Sort.by(Sort.Direction.ASC,"label")).stream().map(stageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StageDTO> findOne(Long id) {
        log.debug("Request to get Stage : {}", id);
        return stageRepository.findById(id).map(stageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return stageRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stage : {}", id);
        stageRepository.deleteById(id);
    }



}
