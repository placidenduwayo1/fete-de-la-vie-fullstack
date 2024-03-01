package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.Banniere;
import com.fdlv.gmd.api.dto.forum.BanniereDTO;
import com.fdlv.gmd.api.mapper.forum.BanniereMapper;
import com.fdlv.gmd.api.repository.forum.BanniereRepository;
import com.fdlv.gmd.api.service.forum.BanniereService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Banniere}.
 */
@Service
@Transactional
public class BanniereServiceImpl implements BanniereService {
	public BanniereServiceImpl(BanniereRepository banniereRepository, BanniereMapper banniereMapper) {
		this.banniereRepository = banniereRepository;
		this.banniereMapper = banniereMapper;
	}

	private final Logger log = LoggerFactory.getLogger(BanniereServiceImpl.class);

	private final BanniereRepository banniereRepository;

	private final BanniereMapper banniereMapper;

    @Override
    public BanniereDTO save(BanniereDTO banniereDTO) {
        log.debug("Request to save Forum : {}", banniereDTO);
        Banniere banniere = banniereMapper.toEntity(banniereDTO);
		banniere = banniereRepository.save(banniere);
        return banniereMapper.toDto(banniere);
    }

	@Override
	public Optional<BanniereDTO> partialUpdate(BanniereDTO banniereDTO) {
		return this.banniereRepository.findById(banniereDTO.getId()).map(existingBanniere -> {
			this.banniereMapper.partialUpdate(existingBanniere, banniereDTO);
			return existingBanniere;
		}).map(this.banniereRepository::save).map(this.banniereMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BanniereDTO> findAll() {
		this.log.debug("Request to get all Forums Theme");
		List<Banniere> ltf = this.banniereRepository.findAll();
		return banniereMapper.toDto(ltf);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BanniereDTO> findOne(Long id) {
		this.log.debug("Request to get Forum : {}", id);
		return this.banniereRepository.findById(id).map(this.banniereMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(Long id) {
		return this.banniereRepository.existsById(id);
	}

	@Override
	public void delete(Long id) {
		this.log.debug("Request to delete Forum : {}", id);
		this.banniereRepository.deleteById(id);
	}
}