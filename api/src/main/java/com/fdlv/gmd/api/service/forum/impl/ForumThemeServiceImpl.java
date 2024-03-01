package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.ForumTheme;
import com.fdlv.gmd.api.dto.forum.ForumThemeDTO;
import com.fdlv.gmd.api.mapper.forum.ForumThemeMapper;
import com.fdlv.gmd.api.repository.forum.ForumThemeRepository;
import com.fdlv.gmd.api.service.forum.ForumThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ForumTheme}.
 */
@Service
@Transactional
public class ForumThemeServiceImpl implements ForumThemeService {
	public ForumThemeServiceImpl(ForumThemeRepository forumThemeRepository, ForumThemeMapper ftMapper) {
		this.forumThemeRepository = forumThemeRepository;
		this.ftMapper = ftMapper;
	}

	private final Logger log = LoggerFactory.getLogger(ForumThemeServiceImpl.class);

	private final ForumThemeRepository forumThemeRepository;

	private final ForumThemeMapper ftMapper;

    @Override
    public ForumThemeDTO save(ForumThemeDTO ftDTO) {
        log.debug("Request to save Forum : {}", ftDTO);
        ForumTheme ft = ftMapper.toEntity(ftDTO);
		ft = forumThemeRepository.save(ft);
        return ftMapper.toDto(ft);
    }

	@Override
	public Optional<ForumThemeDTO> partialUpdate(ForumThemeDTO ftDTO) {
		return this.forumThemeRepository.findById(ftDTO.getId()).map(existingForum -> {
			this.ftMapper.partialUpdate(existingForum, ftDTO);
			return existingForum;
		}).map(this.forumThemeRepository::save).map(this.ftMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ForumThemeDTO> findAll() {
		this.log.debug("Request to get all Forums Theme");
		List<ForumTheme> ltf = this.forumThemeRepository.findAll();
		return ftMapper.toDto(ltf);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ForumThemeDTO> findOne(Long id) {
		this.log.debug("Request to get Forum : {}", id);
		return this.forumThemeRepository.findById(id).map(this.ftMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(Long id) {
		return this.forumThemeRepository.existsById(id);

	}

	@Override
	public void delete(Long id) {
		this.log.debug("Request to delete Forum : {}", id);
		this.forumThemeRepository.deleteById(id);
	}
}
