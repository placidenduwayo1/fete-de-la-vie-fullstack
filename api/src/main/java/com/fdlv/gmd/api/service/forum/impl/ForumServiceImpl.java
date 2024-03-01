package com.fdlv.gmd.api.service.forum.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fdlv.gmd.api.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.forum.Forum;
import com.fdlv.gmd.api.dto.forum.ForumDTO;
import com.fdlv.gmd.api.mapper.forum.ForumMapper;
import com.fdlv.gmd.api.repository.forum.ForumRepository;
import com.fdlv.gmd.api.service.forum.ForumService;

/**
 * Service Implementation for managing {@link Forum}.
 */
@Service
@Transactional
public class ForumServiceImpl implements ForumService {

	public ForumServiceImpl(ForumRepository forumRepository, ForumMapper forumMapper) {
		this.forumRepository = forumRepository;
		this.forumMapper = forumMapper;
	}

	private final Logger log = LoggerFactory.getLogger(ForumServiceImpl.class);

	private final ForumRepository forumRepository;

	private final ForumMapper forumMapper;

    @Override
    public ForumDTO save(ForumDTO forumDTO) {
        log.debug("Request to save Forum : {}", forumDTO);
        //Generate Reference Auto:
        StringBuilder ref = new StringBuilder();
        String hp = "-";
        ref.append(forumDTO.getNumForum()).append(hp).append(forumDTO.getCodePostal()).append(hp);
        String label = forumDTO.getLibelle().toUpperCase();
        ref.append(label.substring(0,3).concat(label.substring(label.length() - 2)));
        Forum forum = forumMapper.toEntity(forumDTO);
        String tempRef = ref.toString();
        forum.setReference(tempRef);
        forum = forumRepository.save(forum);
        Long id = forum.getId();
        String finalRef = id.toString().concat(hp).concat(tempRef);
        forum.setReference(finalRef);
        forum = forumRepository.save(forum);
        return forumMapper.toDto(forum);
    }

	@Override
	public Optional<ForumDTO> partialUpdate(ForumDTO forumDTO) {
		Optional<ForumDTO> result = this.forumRepository.findById(forumDTO.getId()).map(existingForum -> {
			this.forumMapper.partialUpdate(existingForum, forumDTO);
			return existingForum;
		}).map(this.forumRepository::save).map(this.forumMapper::toDto);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ForumDTO> findAll() {
		this.log.debug("Request to get all Forums");
		return this.forumRepository.findAll().stream().map(this.forumMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}
	@Override
	@Transactional(readOnly = true)
	public List<ForumDTO> findAllWithCorrespondantFDLV() {
		this.log.debug("Request to get all Forums with Correspondant FDLV");
		return this.forumRepository.findAllWithCorrespondantFDLV().stream()
				.map(this.forumMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}
	@Override
	@Transactional(readOnly = true)
	public Optional<ForumDTO> findOne(Long id) {
		this.log.debug("Request to get Forum : {}", id);
		return this.forumRepository.findById(id).map(this.forumMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(Long id) {
		return this.forumRepository.existsById(id);

	}

	@Override
	public void delete(Long id) {
		this.log.debug("Request to delete Forum : {}", id);
		this.forumRepository.deleteById(id);
	}

	@Override
	public Long max() {
		this.log.debug("Request to get maximum id of Forum ");
		return this.forumRepository.max();
	}

	private void initReference(ForumDTO forumDTO) {
		Long id = 0L;

		if (forumDTO.getId() != null && forumDTO.getId() > 0) {
			id = forumDTO.getId();
		} else {
			id = this.max() + 1;
		}

		final String libelle = forumDTO.getLibelle();

		final StringBuilder reference = new StringBuilder();
		reference.append(id.toString()).append(forumDTO.getNumForum().toString()).append(libelle.substring(0, 3))
				.append(libelle.substring(libelle.length() - 2));
		forumDTO.setReference(reference.toString());
	}

}
