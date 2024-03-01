package com.fdlv.gmd.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.dto.ChoixOrganisateurDTO;
import com.fdlv.gmd.api.dto.fdlv.FdlvUserDTO;
import com.fdlv.gmd.api.security.SecurityUtils;
import com.fdlv.gmd.api.service.fdlv.FdlvUserService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
import com.fdlv.gmd.api.web.rest.errors.InvalidPasswordException;
import com.fdlv.gmd.api.web.rest.vm.KeyAndPasswordVM;
import com.fdlv.gmd.api.web.rest.vm.ManagedUserVM;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.fdlv.FdlvUser}.
 */
@RestController
@RequestMapping("/api/fdlv-user")
public class FdlvUserResource {

	private final Logger log = LoggerFactory.getLogger(FdlvUserResource.class);

	private static final String ENTITY_NAME = "fdlvUser";

	@Autowired
	private FdlvUserService fdlvUserService;

	/**
	 * {@code POST  /fdlv-user} : Create a new fdlvUser.
	 *
	 * @param fdlvUserDTO the fdlvUserDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fdlvUserDTO, or with status {@code 400 (Bad Request)} if the fdlvUser has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping()
	public ResponseEntity<FdlvUserDTO> createFdlvUser(@Valid @RequestBody FdlvUserDTO fdlvUserDTO) throws URISyntaxException {
		log.debug("REST request to save FdlvUser : {}", fdlvUserDTO);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (fdlvUserDTO.getId() != null || fdlvUserDTO.getLogin()==authentication.getName())
			throw new CreateIdException(ENTITY_NAME);
		final FdlvUserDTO result = fdlvUserService.create(fdlvUserDTO);
		return ResponseEntity
				.created(new URI("/api/fdlv-user/" + result.getId()))
				.body(result);
	}

	/**
	 * {@code PUT  /:id} : Updates an existing fdlvUser.
	 *
	 * @param id the id of the fdlvUserDTO to save.
	 * @param fdlvUserDTO the fdlvUserDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fdlvUserDTO,
	 * or with status {@code 400 (Bad Request)} if the fdlvUserDTO is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the fdlvUserDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<FdlvUserDTO> updateFdlvUser(
			@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody FdlvUserDTO fdlvUserDTO
			) throws URISyntaxException {
		log.debug("REST request to update FdlvUser : {}, {}", id, fdlvUserDTO);
		if (fdlvUserDTO.getId() == null
				|| !Objects.equals(id, fdlvUserDTO.getId()))
			throw new InvalidIdException(ENTITY_NAME);

		final Optional<FdlvUserDTO> optUser = fdlvUserService.findOne(id);
		if (optUser.isEmpty())
			throw new EntityNotFoundException(ENTITY_NAME);

		final FdlvUserDTO result = fdlvUserService.update(fdlvUserDTO);
		return ResponseEntity
				.ok()
				.body(result);
	}

	@PutMapping("/activate/{id}")
	public ResponseEntity<FdlvUserDTO> activateFdlvUser(
			@PathVariable(value = "id", required = true) final Long id,
			@Valid @RequestBody FdlvUserDTO fdlvUserDTO
			) {
		log.debug("REST request to activate FdlvUser : {}", id);
		final Optional<FdlvUserDTO> optUser = fdlvUserService.findOne(id);
		if (optUser.isEmpty())
			throw new EntityNotFoundException(ENTITY_NAME);
		final FdlvUserDTO result = fdlvUserService.updateActivate(optUser.get(), fdlvUserDTO);

		return ResponseEntity
				.ok()
				.body(result);
	}

	@PostMapping("/reset/{id}")
	public ResponseEntity<FdlvUserDTO> resetPwdFdlvUser(
			@PathVariable(value = "id", required = true) final Long id,
			@Valid @RequestBody FdlvUserDTO fdlvUserDTO
			) {
		log.debug("REST request to reset pwd for FdlvUser : {}", id);
		final Optional<FdlvUserDTO> optUser = fdlvUserService.findOne(id);
		if (optUser.isEmpty())
			throw new EntityNotFoundException(ENTITY_NAME);

		final FdlvUserDTO result = fdlvUserService.updateResetKey(fdlvUserDTO);

		return ResponseEntity
				.ok()
				.body(result);
	}

	/**
	 * {@code Post  forgot/:login} : send email for reset password by user login or email.
	 * @param login user login or email
	 */
	@PostMapping("/forgot")
	public void resetPwdFdlvUser(
			@RequestBody String login
			) {
		log.debug("REST request to reset pwd for FdlvUser : {}", login);
		fdlvUserService.sendFdlvEmailResetPassword(login);
	}

	@PostMapping("/resetPassword")
	public void resetPwdFdlvUserFinish(@RequestBody KeyAndPasswordVM keyAndPassword){
		log.debug("REST request to finish reset pwd for FdlvUser : {}", keyAndPassword.getKey());
		if (isPasswordLengthInvalid(keyAndPassword.getNewPassword()))
			throw new InvalidPasswordException();
		fdlvUserService.completePwdReset(keyAndPassword.getKey(),keyAndPassword.getNewPassword());
	}

	/**
	 * {@code GET  /fdlv-user} : get all the fdlvUsers.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fdlvUsers in body.
	 */
	@GetMapping()
	public List<FdlvUserDTO> getAllFdlvUsers() {
		log.debug("REST request to get all FdlvUsers");
		return fdlvUserService.findAll();
	}

	/**
	 * {@code GET  /fdlv-user/:id} : get the "id" fdlvUser.
	 *
	 * @param id the id of the fdlvUserDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fdlvUserDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<FdlvUserDTO> getFdlvUser(@PathVariable Long id) {
		log.debug("REST request to get FdlvUser : {}", id);
		final Optional<FdlvUserDTO> fdlvUserDTO = fdlvUserService.findOne(id);
		return HttpUtils.wrapOrNotFound(fdlvUserDTO);
	}


	/**
	 * {@code DELETE  /fdlv-user/:id} : delete the "id" fdlvUser.
	 *
	 * @param id the id of the fdlvUserDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFdlvUser(@PathVariable Long id) {
		log.debug("REST request to delete FdlvUser : {}", id);
		fdlvUserService.delete(id);
		return ResponseEntity
				.noContent()
				.build();
	}

	@GetMapping("/self")
	public ResponseEntity<FdlvUserDTO> getSelfUser() throws Exception {
		log.debug("REST request to get user FdlvUser");

		final String login = SecurityUtils.getCurrentUserLogin().orElse(null);

		if(login == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		return ResponseEntity.ok(fdlvUserService.findByLogin(login));
	}

	private static boolean isPasswordLengthInvalid(String password) {
		return (
				StringUtils.isEmpty(password) ||
				password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
				password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH
				);
	}

	/**
	 * {@code GET  /fdlv-user/all-parcours} : get all parcours with users
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ChoixOrganisateurDTO with , or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/all-parcours")
	public List<ChoixOrganisateurDTO> getAllUsersWithParcours() {
		log.debug("REST request to get  /fdlv-user/all-parcours");
		return fdlvUserService.getAllUsersWithParcours();
	}

	/**
	 * {@code GET  /fdlv-user/all-parcours} : get all parcours with id users
	 *
	 * @param id of user fdlv
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ChoixOrganisateurDTO with , or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/parcours/{id}")
	public List<ChoixOrganisateurDTO> getParcoursByFusId(@PathVariable Long id) {
		log.debug("REST request to get  /fdlv-user/parcours/" + id);
		return fdlvUserService.getParcoursByFusId(id);
	}

}
