package com.fdlv.gmd.api.service.forum.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fdlv.gmd.api.service.forum.ForumTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.forum.ForumType;
import com.fdlv.gmd.api.dto.forum.ForumTypeDTO;
import com.fdlv.gmd.api.mapper.forum.ForumTypeMapper;
import com.fdlv.gmd.api.repository.forum.ForumTypeRepository;

@Service
@Transactional
public class ForumTypeServiceImpl implements ForumTypeService {

    private final Logger log = LoggerFactory.getLogger(ForumTypeServiceImpl.class);

    private final ForumTypeRepository forumTypeRepository;
    private final ForumTypeMapper forumTypeMapper;

    public ForumTypeServiceImpl(ForumTypeRepository forumTypeRepository, ForumTypeMapper forumTypeMapper) {
        this.forumTypeRepository = forumTypeRepository;
        this.forumTypeMapper = forumTypeMapper;
    }

    @Override
    public ForumTypeDTO save(ForumTypeDTO forumTypeDTO) {
        log.debug("Request to save ForumType : {}", forumTypeDTO);
        ForumType forumType = forumTypeMapper.toEntity(forumTypeDTO);
        forumType = forumTypeRepository.save(forumType);
        return forumTypeMapper.toDto(forumType);
    }

    @Override
    public Optional<ForumTypeDTO> partialUpdate(ForumTypeDTO forumTypeDTO) {
        log.debug("Request to partially update ForumType : {}", forumTypeDTO);

        return forumTypeRepository
            .findById(forumTypeDTO.getId())
            .map(
                existingForumTYpe -> {
                    forumTypeMapper.partialUpdate(existingForumTYpe, forumTypeDTO);
                    return existingForumTYpe;
                }
            )
            .map(forumTypeRepository::save)
            .map(forumTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ForumTypeDTO> findAll() {
        log.debug("Request to get all Forum Types");
        return forumTypeRepository.findAll().stream().map(forumTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ForumTypeDTO> findOne(Long id) {
        log.debug("Request to get ForumType : {}", id);
        return forumTypeRepository.findById(id).map(forumTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return forumTypeRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ForumType : {}", id);
        forumTypeRepository.deleteById(id);
    }

}
