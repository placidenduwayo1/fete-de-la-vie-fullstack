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
import org.springframework.web.bind.annotation.*;

import com.fdlv.gmd.api.domain.forum.InterventionActeur;
import com.fdlv.gmd.api.dto.forum.InterventionActeurDTO;
import com.fdlv.gmd.api.service.forum.InterventionActeurService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link InterventionActeur}.
 */
@RestController
@RequestMapping("/api/interventionActeurs")
public class InterventionActeurResource {
	private final Logger log = LoggerFactory.getLogger(InterventionActeurResource.class);

	private static final String ENTITY_NAME = "interventionActeur";

	private final InterventionActeurService interventionActeurService;

	public InterventionActeurResource(InterventionActeurService interventionActeurService) {
		this.interventionActeurService = interventionActeurService;
	}

	/**
	 * {@code POST  /interventionActeurs} : Create a new interventionActeur.
	 *
	 * @param interventionActeurDTO the interventionActeurDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new interventionActeurDTO, or with status
	 *         {@code 400 (Bad Request)} if the interventionActeur has already an
	 *         ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping
	public ResponseEntity<InterventionActeurDTO> createInterventionActeur(
			@Valid @RequestBody InterventionActeurDTO interventionActeurDTO) throws URISyntaxException {
		this.log.debug("REST request to save InterventionActeur : {}", interventionActeurDTO);
		if (interventionActeurDTO.getFinId() != null) {
			throw new CreateIdException(ENTITY_NAME);
		}
		final InterventionActeurDTO result = this.interventionActeurService.save(interventionActeurDTO);
		return ResponseEntity.created(new URI("/api/interventionActeurs/" + result.getFinId())).body(result);
	}

	/**
	 * {@code PUT  /interventionActeurs/:id} : Updates an existing
	 * interventionActeur.
	 *
	 * @param id                    the id of the interventionActeurDTO to save.
	 * @param interventionActeurDTO the interventionActeurDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated interventionActeurDTO, or with status
	 *         {@code 400 (Bad Request)} if the interventionActeurDTO is not valid,
	 *         or with status {@code 500 (Internal Server Error)} if the
	 *         interventionActeurDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<InterventionActeurDTO> updateInterventionActeur(
			@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody InterventionActeurDTO interventionActeurDTO) throws URISyntaxException {
		this.log.debug("REST request to update InterventionActeur : {}, {}", id, interventionActeurDTO);
		if (interventionActeurDTO.getFinId() == null || !Objects.equals(id, interventionActeurDTO.getFinId())) {
			throw new InvalidIdException(ENTITY_NAME);
		}

		if (!this.interventionActeurService.existsById(id)) {
			throw new EntityNotFoundException(ENTITY_NAME);
		}
		final InterventionActeurDTO result = this.interventionActeurService.save(interventionActeurDTO);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * {@code PATCH  /interventionActeurs/:id} : Partial updates given fields of an
	 * existing interventionActeur, field will ignore if it is null
	 *
	 * @param id                    the id of the interventionActeurDTO to save.
	 * @param interventionActeurDTO the interventionActeurDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated interventionActeurDTO, or with status
	 *         {@code 400 (Bad Request)} if the interventionActeurDTO is not valid,
	 *         or with status {@code 404 (Not Found)} if the interventionActeurDTO
	 *         is not found, or with status {@code 500 (Internal Server Error)} if
	 *         the interventionActeurDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<InterventionActeurDTO> partialUpdateInterventionActeur(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody InterventionActeurDTO interventionActeurDTO) throws URISyntaxException {
		this.log.debug("REST request to partial update InterventionActeur partially : {}, {}", id,
				interventionActeurDTO);
		if (interventionActeurDTO.getFinId() == null || !Objects.equals(id, interventionActeurDTO.getFinId())) {
			throw new InvalidIdException(ENTITY_NAME);
		}

		if (!this.interventionActeurService.existsById(id)) {
			throw new EntityNotFoundException(ENTITY_NAME);
		}

		final Optional<InterventionActeurDTO> result = this.interventionActeurService
				.partialUpdate(interventionActeurDTO);

		return HttpUtils.wrapOrNotFound(result);
	}

	/**
	 * {@code GET  /interventionActeurs} : get all the interventionActeurs.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of interventionActeurs in body.
	 */
	@GetMapping()
	public List<InterventionActeurDTO> getAllInterventionActeurs() {
		this.log.debug("REST request to get all InterventionActeurs");
		return this.interventionActeurService.findAll();
	}

	/**
	 * {@code GET  /interventionActeurs/:id} : get the "id" interventionActeur.
	 *
	 * @param id the id of the interventionActeurDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the interventionActeurDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<InterventionActeurDTO> getInterventionActeur(@PathVariable Long id) {
		this.log.debug("REST request to get InterventionActeur : {}", id);
		final Optional<InterventionActeurDTO> interventionActeurDTO = this.interventionActeurService.findOne(id);
		return HttpUtils.wrapOrNotFound(interventionActeurDTO);
	}

	/**
	 * {@code DELETE  /interventionActeurs/:id} : delete the "id"
	 * interventionActeur.
	 *
	 * @param id the id of the interventionActeurDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteInterventionActeur(@PathVariable Long id) {
		this.log.debug("REST request to delete InterventionActeur : {}", id);
		this.interventionActeurService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
