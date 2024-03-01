package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.ActeurHabilitationDocument;
import com.fdlv.gmd.api.dto.forum.ActeurHabilitationDocumentDTO;
import com.fdlv.gmd.api.mapper.forum.ActeurHabilitationDocumentMapper;
import com.fdlv.gmd.api.repository.forum.ActeurHabilitationDocumentRepository;
import com.fdlv.gmd.api.service.forum.ActeurHabilitationDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActeurHabilitationDocumentServiceImpl implements ActeurHabilitationDocumentService {

    private final Logger log = LoggerFactory.getLogger(ActeurHabilitationDocumentServiceImpl.class);

    private final ActeurHabilitationDocumentRepository acteurHabilitationDocumentRepository;

    private final ActeurHabilitationDocumentMapper acteurHabilitationDocumentMapper;

    public ActeurHabilitationDocumentServiceImpl(ActeurHabilitationDocumentRepository acteurHabilitationDocumentRepository, ActeurHabilitationDocumentMapper acteurHabilitationDocumentMapper) {
        this.acteurHabilitationDocumentRepository = acteurHabilitationDocumentRepository;
        this.acteurHabilitationDocumentMapper = acteurHabilitationDocumentMapper;
    }

    @Override
    public ActeurHabilitationDocumentDTO save(ActeurHabilitationDocumentDTO acteurHabilitationDocumentDTO) {
        log.debug("Request to save ActeurHabilitationDocument: {}", acteurHabilitationDocumentDTO);
        ActeurHabilitationDocument acteurHabilitationDocument = acteurHabilitationDocumentMapper.toEntity(acteurHabilitationDocumentDTO);
        acteurHabilitationDocument = acteurHabilitationDocumentRepository.save(acteurHabilitationDocument);
        return acteurHabilitationDocumentMapper.toDto(acteurHabilitationDocument);
    }

    @Override
    public Optional<ActeurHabilitationDocumentDTO> partialUpdate(ActeurHabilitationDocumentDTO acteurHabilitationDocumentDTO) {
        log.debug("Request to partially update ActeurHabilitationDocument: {}", acteurHabilitationDocumentDTO);

        return acteurHabilitationDocumentRepository
                .findById(acteurHabilitationDocumentDTO.getId())
                .map(existingActeurHabilitationDocument -> {
                    acteurHabilitationDocumentMapper.partialUpdate(existingActeurHabilitationDocument, acteurHabilitationDocumentDTO);
                    return existingActeurHabilitationDocument;
                })
                .map(acteurHabilitationDocumentRepository::save)
                .map(acteurHabilitationDocumentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActeurHabilitationDocumentDTO> findAll() {
        log.debug("Request to get all ActeurHabilitationDocuments");
        return acteurHabilitationDocumentRepository.findAll().stream()
                .map(acteurHabilitationDocumentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActeurHabilitationDocumentDTO> findOne(Long id) {
        log.debug("Request to get ActeurHabilitationDocument: {}", id);
        return acteurHabilitationDocumentRepository.findById(id)
                .map(acteurHabilitationDocumentMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return acteurHabilitationDocumentRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActeurHabilitationDocument: {}", id);
        acteurHabilitationDocumentRepository.deleteById(id);
    }
}