package com.fdlv.gmd.api.service.fdlv;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.fdlv.FdlvUser;
import com.fdlv.gmd.api.dto.ChoixOrganisateurDTO;
import com.fdlv.gmd.api.dto.fdlv.FdlvUserDTO;
import com.fdlv.gmd.api.mapper.ChoixOrganisateurMapper;
import com.fdlv.gmd.api.mapper.fdlv.FdlvUserMapper;
import com.fdlv.gmd.api.repository.fdlv.ChoixOrganisateurRepository;
import com.fdlv.gmd.api.repository.fdlv.FdlvUserRepository;
import com.fdlv.gmd.api.service.MailService;

/**
 * Service Implementation for managing {@link FdlvUser}.
 */
@Service
@Transactional
public class FdlvUserServiceImpl implements FdlvUserService {


	private static class AccountResourceException extends RuntimeException {

		private AccountResourceException(String message) {
			super(message);
		}
	}
	private static final String ENTITY_NAME = "fdlvUser";
	private final FdlvUserRepository fdlvUserRepository;

	private final FdlvUserMapper fdlvUserMapper;

	private final MailService mailService;

	private final Logger log = LoggerFactory.getLogger(FdlvUserServiceImpl.class);

	private static int ACCEPTPARCOURS = 9;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ChoixOrganisateurRepository choixOrganisateurRepository;

	@Autowired
	private ChoixOrganisateurMapper choixOrganisateurMapper;

	public FdlvUserServiceImpl(FdlvUserRepository fdlvUserRepository,
			FdlvUserMapper fdlvUserMapper,
			MailService mailService) {
		this.fdlvUserRepository = fdlvUserRepository;
		this.fdlvUserMapper = fdlvUserMapper;
		this.mailService = mailService;
	}

	@Override
	public FdlvUserDTO create(FdlvUserDTO fdlvUserDTO) {
		setHashedPwd(fdlvUserDTO);
		if (Boolean.TRUE.equals(fdlvUserDTO.getFromFDLV())) {
			fdlvUserDTO.setPermanent(false);
			fdlvUserDTO.setActif(false);
			fdlvUserDTO.setDateDebut(LocalDate.now());
			fdlvUserDTO.setDateFin(LocalDate.now());
			fdlvUserDTO.setCreePar("web_orga");
		}
		fdlvUserDTO.setDateCreation(LocalDate.now());
		if (fdlvUserDTO.getActif() == null) {
			fdlvUserDTO.setActif(Boolean.FALSE);
		}

		FdlvUser fdlvUser = fdlvUserMapper.toEntity(fdlvUserDTO);
		fdlvUser = fdlvUserRepository.save(fdlvUser);

		// L'utilisateur est créé activé :
		// on envoie le mail d'activation
		final FdlvUserDTO result = fdlvUserMapper.toDto(fdlvUser);
		if (Boolean.TRUE.equals(result.getActif())) {
			mailService.sendFDLVActivatedMail(result);
		}
		return result;
	}

	@Override
	public FdlvUserDTO update(FdlvUserDTO fdlvUserDTO) {
		final Optional<FdlvUser> oldUser = fdlvUserRepository.findById(fdlvUserDTO.getId());
		final boolean wasActif = oldUser.isPresent() &&
				Boolean.TRUE.equals(oldUser.get().getActif());

		// Le mdp a changé, on le re-hash
		if (oldUser.isPresent()
				&& !StringUtils.equals(oldUser.get().getMdpHash(), fdlvUserDTO.getMdpHash())) {
			setHashedPwd(fdlvUserDTO);
		}
		fdlvUserDTO.setDateModif(LocalDate.now());

		FdlvUser fdlvUser = fdlvUserMapper.toEntity(fdlvUserDTO);
		fdlvUser = fdlvUserRepository.save(fdlvUser);
		final FdlvUserDTO result =  fdlvUserMapper.toDto(fdlvUser);

		// La modification de l'utilisateur inclus l'activation du compte :
		// on envoie le mail d'activation
		if (!wasActif && Boolean.TRUE.equals(result.getActif())) {
			mailService.sendFDLVActivatedMail(result);
		}

		return result;
	}


	@Override
	public FdlvUserDTO updateResetKey(FdlvUserDTO fdlvUserDTO) {
		FdlvUserDTO result;
		fdlvUserDTO.setResetKey(new String(Hex.encode(RandomUtils.nextBytes(16))));
		if (Boolean.TRUE.equals(fdlvUserDTO.getFromFDLV())) {
			fdlvUserDTO.setDateDebut(LocalDate.now());
			fdlvUserDTO.setDateFin(LocalDate.now());
			result = update(fdlvUserDTO);
			mailService.sendFDLVResetMail(fdlvUserDTO);
		} else {
			fdlvUserDTO.setDateReset(Instant.now());
			result = update(fdlvUserDTO);
			mailService.sendFDLVReinitAdminMail(fdlvUserDTO);
		}
		return result;
	}

	@Override
	public void sendFdlvEmailResetPassword(String login){
		Optional<FdlvUser> fdlvUserOpt = fdlvUserRepository.findByLogin(login);
		if (!fdlvUserOpt.isPresent()) {
			fdlvUserOpt = fdlvUserRepository.findOneByEmailIgnoreCase(login);
			if(!fdlvUserOpt.isPresent())
				throw new EntityNotFoundException(ENTITY_NAME);
		}
		final FdlvUser fdlvUser = fdlvUserOpt.get();
		fdlvUser.setResetKey(new String(Hex.encode(RandomUtils.nextBytes(16))));
		fdlvUser.setDateReset(Instant.now());
		fdlvUserRepository.save(fdlvUser);
		mailService.sendFDLVResetMail(fdlvUserMapper.toDto(fdlvUser));
	}

	@Override
	public void completePwdReset(String key, String newPassword) {
		log.debug("Request to complete reset password: {}", key);
		final Optional<FdlvUser> fdlvUserOpt = fdlvUserRepository.findOneByResetKey(key).filter(user -> user.getDateReset().isAfter(Instant.now().minusSeconds(86400)));
		if(!fdlvUserOpt.isPresent())
			throw new AccountResourceException("No user was found for this reset key");
		final FdlvUser fdlvUser = fdlvUserOpt.get();
		fdlvUser.setMdpHash(passwordEncoder.encode(newPassword));
		fdlvUser.setResetKey(null);
		fdlvUser.setDateReset(null);
		mailService.sendFDLVValidPassWord(fdlvUserMapper.toDto(fdlvUser));
	}

	@Override
	public FdlvUserDTO updateActivate(FdlvUserDTO oldUser, FdlvUserDTO newUser) {
		FdlvUserDTO result;
		// On a activé le compte
		final boolean wasActivated = Boolean.FALSE.equals(oldUser.getActif())
				&& Boolean.TRUE.equals(newUser.getActif());
		oldUser.setDateModif(LocalDate.now());
		oldUser.setActif(newUser.getActif());

		FdlvUser fdlvUser = fdlvUserMapper.toEntity(oldUser);
		fdlvUser = fdlvUserRepository.save(fdlvUser);
		result = fdlvUserMapper.toDto(fdlvUser);
		if (wasActivated) {
			mailService.sendFDLVActivatedMail(result);
		}
		return result;
	}

	@Override
	public FdlvUserDTO findByLogin(String login) {
		log.debug("Request to get FdlvUser by login: {}", login);
		return fdlvUserRepository.findByLogin(login).map(fdlvUserMapper::toDto).orElse(null);
	}

	@Override
	public FdlvUserDTO findByEmail(String email) {
		log.debug("Request to get FdlvUser by email: {}", email);
		return fdlvUserRepository.findOneByEmailIgnoreCase(email).map(fdlvUserMapper::toDto).orElse(null);
	}

	private void setHashedPwd(FdlvUserDTO fdlvUserDTO) {
		final String hashedPwd = BCrypt.hashpw(fdlvUserDTO.getMdpHash(), BCrypt.gensalt(10));
		fdlvUserDTO.setMdpHash(hashedPwd);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FdlvUserDTO> findAll() {
		log.debug("Request to get all FdlvUsers");
		return fdlvUserRepository.findAll().stream().map(fdlvUserMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<FdlvUserDTO> findOne(Long id) {
		log.debug("Request to get FdlvUser : {}", id);
		return fdlvUserRepository.findById(id).map(fdlvUserMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<FdlvUser> findOneEntity(Long id) {
		log.debug("Request to get FdlvUser : {}", id);
		return fdlvUserRepository.findById(id);
	}


	@Override
	@Transactional(readOnly = true)
	public boolean existsById(Long id) {
		return fdlvUserRepository.existsById(id);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete FdlvUser : {}", id);
		fdlvUserRepository.deleteById(id);
	}


	@Override
	public List<ChoixOrganisateurDTO> getAllUsersWithParcours() {
		final List<ChoixOrganisateurDTO> choixOrganisateurs = choixOrganisateurRepository.findAllByNumScenarioLessThan(ACCEPTPARCOURS).stream()
				.map(choixOrganisateurMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
		return choixOrganisateurs;
	}

	@Override
	public List<ChoixOrganisateurDTO> getParcoursByFusId(Long id) {
		final List<ChoixOrganisateurDTO> choixOrganisateurs = choixOrganisateurRepository.findAllByFcoFusIdAndNumScenarioLessThan(id,ACCEPTPARCOURS).stream()
				.map(choixOrganisateurMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
		return choixOrganisateurs;
	}

}
