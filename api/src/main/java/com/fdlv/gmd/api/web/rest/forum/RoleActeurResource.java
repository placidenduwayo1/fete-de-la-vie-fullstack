package com.fdlv.gmd.api.web.rest.forum;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.dto.forum.RoleActeurDTO;
import com.fdlv.gmd.api.facade.forum.ControleRoleActeurFacade;
import com.fdlv.gmd.api.service.forum.RoleActeurService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

@RestController
@RequestMapping("/api/role-acteurs")
public class RoleActeurResource {

	private final Logger log = LoggerFactory.getLogger(RoleActeurResource.class);

	private static final String ENTITY_NAME = "roleActeur";

	private final RoleActeurService roleActeurService;

	private final ControleRoleActeurFacade controleRoleActeurFacade;

	public RoleActeurResource(RoleActeurService roleActeurService, ControleRoleActeurFacade controleRoleActeurFacade) {
		this.roleActeurService = roleActeurService;
		this.controleRoleActeurFacade = controleRoleActeurFacade;
	}

	@PostMapping()
	public ResponseEntity<RoleActeurDTO> createRoleActeur(@Valid @RequestBody RoleActeurDTO roleActeurDTO)
			throws URISyntaxException {
		this.log.debug("REST request to save RoleActeur: {}", roleActeurDTO);
		if (roleActeurDTO.getId() != null) {
			throw new CreateIdException(ENTITY_NAME);
		}
		final RoleActeurDTO result = this.roleActeurService.save(roleActeurDTO);
		return ResponseEntity.created(new URI("/api/role-acteurs/" + result.getId())).body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RoleActeurDTO> updateRoleActeur(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody RoleActeurDTO roleActeurDTO) throws URISyntaxException {
		this.log.debug("REST request to update RoleActeur: {}, {}", id, roleActeurDTO);
		if (roleActeurDTO.getId() == null || !Objects.equals(id, roleActeurDTO.getId())) {
			throw new InvalidIdException(ENTITY_NAME);
		}

		this.controleRoleActeurFacade.verifierExistenceEntite(id);

		final RoleActeurDTO result = this.roleActeurService.save(roleActeurDTO);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<RoleActeurDTO> partialUpdateRoleActeur(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody RoleActeurDTO roleActeurDTO) throws URISyntaxException {
		this.log.debug("REST request to partial update RoleActeur partially: {}, {}", id, roleActeurDTO);
		if (roleActeurDTO.getId() == null || !Objects.equals(id, roleActeurDTO.getId())) {
			throw new InvalidIdException(ENTITY_NAME);
		}

		this.controleRoleActeurFacade.verifierExistenceEntite(id);

		final Optional<RoleActeurDTO> result = this.roleActeurService.partialUpdate(roleActeurDTO);

		return HttpUtils.wrapOrNotFound(result);
	}

	@GetMapping()
	public List<RoleActeurDTO> getAllRoleActeurs() {
		this.log.debug("REST request to get all RoleActeurs");
		return this.roleActeurService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoleActeurDTO> getRoleActeur(@PathVariable Long id) {
		this.log.debug("REST request to get RoleActeur: {}", id);
		this.controleRoleActeurFacade.verifierExistenceEntite(id);
		final Optional<RoleActeurDTO> roleActeurDTO = this.roleActeurService.findOne(id);
		return HttpUtils.wrapOrNotFound(roleActeurDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRoleActeur(@PathVariable Long id) {
		this.log.debug("REST request to delete RoleActeur: {}", id);
		this.controleRoleActeurFacade.verifierExistenceEntite(id);
		this.roleActeurService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
