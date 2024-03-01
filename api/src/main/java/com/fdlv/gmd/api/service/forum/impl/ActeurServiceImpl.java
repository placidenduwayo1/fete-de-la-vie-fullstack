package com.fdlv.gmd.api.service.forum.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fdlv.gmd.api.domain.forum.Acteur;
import com.fdlv.gmd.api.domain.forum.Forum;
import com.fdlv.gmd.api.dto.UserDTO;
import com.fdlv.gmd.api.dto.forum.ActeurDTO;
import com.fdlv.gmd.api.mapper.forum.ActeurMapper;
import com.fdlv.gmd.api.mapper.forum.EntityMapper;
import com.fdlv.gmd.api.repository.forum.ActeurRepository;
import com.fdlv.gmd.api.repository.forum.ForumRepository;
import com.fdlv.gmd.api.service.FtpService;
import com.fdlv.gmd.api.service.forum.ActeurService;

@Service
@Transactional
public class ActeurServiceImpl implements ActeurService {

	private final Logger log = LoggerFactory.getLogger(ActeurServiceImpl.class);

	private final ActeurRepository acteurRepository;

	private final ForumRepository forumRepository;

	private final ActeurMapper acteurMapper;

	private final static Set<String> acteursLogin = new HashSet<>();

	private final FtpService ftpService;

	@Qualifier("acteurMappeurHandler")
	private final EntityMapper<ActeurDTO, Acteur> acteurMappeurHandler;

	public ActeurServiceImpl(ActeurRepository acteurRepository, ActeurMapper acteurMapper,
			EntityMapper<ActeurDTO, Acteur> acteurMappeurHandler, ForumRepository forumRepository,
			FtpService ftpService) {
		this.acteurRepository = acteurRepository;
		this.acteurMapper = acteurMapper;
		this.acteurMappeurHandler = acteurMappeurHandler;
		this.forumRepository = forumRepository;
		this.ftpService = ftpService;
	}

	@Override
	public ActeurDTO save(ActeurDTO acteurDTO) {
		this.log.debug("Request to save Acteur: {}", acteurDTO);
		this.initLogin(acteurDTO);
		this.setHashedPwd(acteurDTO);
		this.initReferenceActeur(acteurDTO);
		Acteur acteur = this.acteurMapper.toEntity(acteurDTO);
		acteur = this.acteurRepository.save(acteur);
		return this.acteurMapper.toDto(acteur);
	}

	@Override
	public Optional<ActeurDTO> partialUpdate(ActeurDTO acteurDTO) {
		this.log.debug("Request to partially update Acteur: {}", acteurDTO);
		this.initReferenceActeur(acteurDTO);
		return this.acteurRepository.findById(acteurDTO.getId()).map(existingActeur -> {
			this.acteurMapper.partialUpdate(existingActeur, acteurDTO);
			return existingActeur;
		}).map(this.acteurRepository::save).map(this.acteurMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActeurDTO> findAll() {
		this.log.debug("Request to get all Acteurs");
		return this.acteurRepository.findAll().stream().map(this.acteurMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ActeurDTO> findOne(Long id) {
		this.log.debug("Request to get Acteur: {}", id);
		return this.acteurRepository.findById(id).map(this.acteurMapper::toDto);
	}

	@Override
	public boolean existsById(Long id) {
		return this.acteurRepository.existsById(id);
	}

	@Override
	public void delete(Long id) {
		this.log.debug("Request to delete Acteur: {}", id);
		this.acteurRepository.deleteById(id);
	}

	@Override
	public List<ActeurDTO> findResponsables(String responsable) {
		this.log.debug("Request to get All responsables");
		return this.acteurRepository.findByResponsable(responsable).stream().map(this.acteurMappeurHandler::entityToDto)
				.collect(Collectors.toList());
	}

	private void initLogin(ActeurDTO acteurDTO) {
		StringBuilder login = new StringBuilder();
		login.append(acteurDTO.getName().substring(0, 1))
				.append(acteurDTO.getLastName());
		final boolean addedActeurLogin = ActeurServiceImpl.acteursLogin.add(login.toString().toLowerCase());
		// Si le login existe ajouté l'id de l'acteur à la fin
		if (!addedActeurLogin) {
			final Long id = this.getActeurId(acteurDTO);
			login = login.append(String.valueOf(id));
			ActeurServiceImpl.acteursLogin.add(login.toString().toLowerCase());
		}
		acteurDTO.setLogin(login.toString().toLowerCase());
	}

	private String initFileName(ActeurDTO acteurDTO) {
		final StringBuilder fileName = new StringBuilder();
		fileName.append(this.getActeurId(acteurDTO)).append("_").append(acteurDTO.getLogin());
		return fileName.toString();
	}

	private Long getActeurId(ActeurDTO acteurDTO) {
		Long id = null;
		if (acteurDTO.getId() != null && acteurDTO.getId() > 0) {
			id = acteurDTO.getId();
		} else {
			id = this.max() + 1;
		}
		return id;
	}

	private void initReferenceActeur(ActeurDTO acteurDTO) {
		if (acteurDTO.getStructureId() != null) {
			String structureId = acteurDTO.getStructureId().getId().toString();
			final StringBuilder reference = new StringBuilder();
			reference.append(this.getActeurId(acteurDTO)).append("-")
					.append(structureId).append("-")
					.append(acteurDTO.getLastName().substring(0, 3))
					.append(acteurDTO.getName().substring(0, 2));
			acteurDTO.setReference(reference.toString().toLowerCase());
		} else {
			String reference = "000";
			acteurDTO.setReference(reference);
		}
	}

	private void setHashedPwd(ActeurDTO acteurDTO) {
		if (acteurDTO.getPasswordHash() != null) {
			final String hashedPwd = BCrypt.hashpw(acteurDTO.getPasswordHash(), BCrypt.gensalt(10));
			acteurDTO.setPasswordHash(hashedPwd);
		}
	}

	@Override
	public Long max() {
		this.log.debug("Request to get maximum id of Acteur ");
		if (this.acteurRepository.max() == null)
			return 0L;
		return this.acteurRepository.max();
	}

	@Override
	public UserDTO getUserConnected() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String userLogin = authentication.getName();
		final UserDTO userDTO = new UserDTO();
		userDTO.setLogin(userLogin);
		return userDTO;
	}

	@Override
	@Transactional
	public ActeurDTO activerDesactiverActeur(Long acteurId) {
		final Optional<ActeurDTO> acteurDto = this.acteurRepository.findById(acteurId)
				.map(this.acteurMappeurHandler::entityToDto);
		if (acteurDto.isPresent()) {
			final ActeurDTO acteurDTO = acteurDto.get();
			acteurDTO.setActif("0".equals(acteurDTO.getActif()) ? "1" : "0");
			this.acteurRepository.save(this.acteurMapper.toEntity(acteurDTO));
			return acteurDTO;
		}
		return null;
	}

	@Override
	@Transactional
	public boolean addPhotoActeur(MultipartFile photo, Long acteurId) {
		final Optional<ActeurDTO> acteurDto = this.acteurRepository.findById(acteurId)
				.map(this.acteurMappeurHandler::entityToDto);

		if (acteurDto.isPresent()) {

			final ActeurDTO realActeurDato = acteurDto.get();
			final String photoUrl = this.ftpService.putActeurPhoto(photo, this.initFileName(realActeurDato));
			realActeurDato.setPhotoUrl(photoUrl);
			this.acteurRepository.save(this.acteurMapper.toEntity(realActeurDato));
			return true;
		}
		return false;
	}

}
