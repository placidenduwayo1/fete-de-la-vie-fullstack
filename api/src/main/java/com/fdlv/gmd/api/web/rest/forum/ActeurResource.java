package com.fdlv.gmd.api.web.rest.forum;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fdlv.gmd.api.dto.UserDTO;
import com.fdlv.gmd.api.dto.forum.ActeurDTO;
import com.fdlv.gmd.api.service.FtpService;
import com.fdlv.gmd.api.service.forum.ActeurService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

@RestController
@RequestMapping("/api/acteurs")
public class ActeurResource {

	private final Logger log = LoggerFactory.getLogger(ActeurResource.class);

	private static final String ENTITY_NAME = "acteur";

	private static final String ACTEUR_RESPONSABLE = "1";

	private final ActeurService acteurService;

	private final FtpService ftpService;

	public ActeurResource(ActeurService acteurService, FtpService ftpService) {
		this.acteurService = acteurService;
		this.ftpService = ftpService;
	}

	@PostMapping("/photo")
	public boolean addPhotoActeur(@RequestParam("file") MultipartFile file, @RequestParam("acteurId") String acteurId) {
		final Long id = Long.parseLong(acteurId);
		return this.acteurService.addPhotoActeur(file, id);
	}

	@PostMapping()
	public ActeurDTO createActeur(@Valid @RequestBody ActeurDTO acteurDTO)
			throws URISyntaxException {
		this.log.debug("REST request to save Acteur: {}", acteurDTO);
		this.log.debug("User connected :  {} ", this.acteurService.getUserConnected().getLogin());

		if (acteurDTO.getId() != null) {
			throw new CreateIdException(ENTITY_NAME);
		}
		if (acteurDTO.getActif() == null) {
			acteurDTO.setActif("0");
		}
		if (acteurDTO.getPresenceStand() == null) {
			acteurDTO.setPresenceStand("0");
		}
		// A remplacer par le vrai utilisateur qui s'est connecté
		final Optional<ActeurDTO> createdByActor = this.acteurService.findOne(2L);
		acteurDTO.setCreatedByActor(createdByActor.get());

		return this.acteurService.save(acteurDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ActeurDTO> updateActeur(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody ActeurDTO acteurDTO)
			throws URISyntaxException {
		this.log.debug("REST request to update Acteur: {}, {}", id, acteurDTO);
		if (acteurDTO.getId() == null || !Objects.equals(id, acteurDTO.getId())) {
			throw new InvalidIdException(ENTITY_NAME);
		}

		if (!this.acteurService.existsById(id)) {
			throw new EntityNotFoundException(ENTITY_NAME);
		}

		final ActeurDTO result = this.acteurService.save(acteurDTO);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<ActeurDTO> partialUpdateActeur(@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody ActeurDTO acteurDTO) throws URISyntaxException {
		this.log.debug("REST request to partial update Acteur partially: {}, {}", id, acteurDTO);
		if (acteurDTO.getId() == null || !Objects.equals(id, acteurDTO.getId())) {
			throw new InvalidIdException(ENTITY_NAME);
		}

		if (!this.acteurService.existsById(id)) {
			throw new EntityNotFoundException(ENTITY_NAME);
		}

		final Optional<ActeurDTO> result = this.acteurService.partialUpdate(acteurDTO);

		return HttpUtils.wrapOrNotFound(result);
	}

	@GetMapping()
	public List<ActeurDTO> getAllActeurs() {
		this.log.debug("REST request to get all Acteurs");
		return this.acteurService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ActeurDTO> getActeur(@PathVariable Long id) {
		this.log.debug("REST request to get Acteur: {}", id);
		final Optional<ActeurDTO> acteurDTO = this.acteurService.findOne(id);
		return HttpUtils.wrapOrNotFound(acteurDTO);
	}

	@GetMapping("/user-connected")
	public UserDTO getUserConnected() {
		return this.acteurService.getUserConnected();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteActeur(@PathVariable Long id) {
		this.log.debug("REST request to delete Acteur: {}", id);
		final Optional<ActeurDTO> acteurDTO = this.acteurService.findOne(id);
		if (acteurDTO.isPresent()) {
			this.acteurService.delete(id);
			if (acteurDTO.get().getPhotoUrl() != null) {
				this.ftpService.deleteFromServer(acteurDTO.get().getPhotoUrl());
			}
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/acteurs-responsable")
	public List<ActeurDTO> getResponsables() {
		this.log.debug("REST request to get responsable");
		return this.acteurService.findResponsables(ACTEUR_RESPONSABLE);
	}

	@PutMapping("/maj-statut/{id}")
	public ActeurDTO activerDesactiverActeur(@PathVariable("id") Long id) {
		final ActeurDTO activerDesactiverActeur = this.acteurService.activerDesactiverActeur(id);
		if (activerDesactiverActeur == null) {
			throw new EntityNotFoundException(ENTITY_NAME);
		}
		return activerDesactiverActeur;
	}
}
