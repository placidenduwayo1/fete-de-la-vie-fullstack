package com.fdlv.gmd.api.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.domain.fdlv.EtatScenarioConverter;
import com.fdlv.gmd.api.domain.fdlv.FdlvUser;
import com.fdlv.gmd.api.service.fdlv.FdlvUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur;
import com.fdlv.gmd.api.dto.ChoixOrganisateurDTO;
import com.fdlv.gmd.api.service.fdlv.ChoixOrganisateurService;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;

/**
 * REST controller for managing
 * {@link com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur}.
 */
@RestController
@RequestMapping("/api/choix-organisateur")
public class ChoixOrganisateurResource {

	private final ChoixOrganisateurService choixOrganisateurService;
	private final FdlvUserService fdlvUserService;
	@Autowired
	private EtatScenarioConverter etatScenarioConverter;

	public ChoixOrganisateurResource(ChoixOrganisateurService choixOrganisateurService, FdlvUserService fdlvUserService) {
		this.choixOrganisateurService = choixOrganisateurService;
		this.fdlvUserService = fdlvUserService;

	}

	/**
	 * {@code GET  /choix-organisateur} : get all the choix-organisateur.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of choix-organisateur by user id in body.
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<Boolean> existsChoixOrganisateurByUser(@PathVariable Long id) {
		return ResponseEntity.ok().body(choixOrganisateurService.existsByFdlvUserId(id));
	}

	/**
	 * {@code GET  /choix-organisateur} : get all the choix-organisateur.
	 *
	 * @return the list of choix-organisateur in body.
	 */
	@GetMapping()
	public List<ChoixOrganisateurDTO> getAllChoixOrganisateurDatas() {
		return choixOrganisateurService.findAll();
	}

	/**
	 * {@code POST  /choix-organisateur} : create a choix-organisateur.
	 *
	 */
	@PostMapping()
	public ResponseEntity<ChoixOrganisateurDTO> createChoixOrganisateur(@RequestBody ChoixOrganisateurDTO choixOrganisateurDTO) throws URISyntaxException {
		if (choixOrganisateurDTO.getId() != null)
			throw new CreateIdException("choix-organisateur");
		choixOrganisateurDTO.setEtatScenario(etatScenarioConverter.convertToEntityAttribute(choixOrganisateurDTO.getEtatScenarioId()));
		Optional <FdlvUser> fdlvUser = fdlvUserService.findOneEntity(choixOrganisateurDTO.getFcoFusId());
		if(fdlvUser.isPresent()){
			choixOrganisateurDTO.setFco(fdlvUser.get());}
		else{
			return ResponseEntity.badRequest().body(choixOrganisateurDTO);
		}
		final ChoixOrganisateurDTO result = choixOrganisateurService.createChoixOrganisateur(choixOrganisateurDTO);
		return ResponseEntity
				.ok()
				.body(result);
	}

	@PutMapping()
	public ResponseEntity<ChoixOrganisateurDTO> updateChoixOrganisation(
			@RequestBody ChoixOrganisateurDTO choixOrganisateur
			) {
		Optional <FdlvUser> fdlvUser = fdlvUserService.findOneEntity(choixOrganisateur.getFcoFusId());
		if(fdlvUser.isPresent()){
			choixOrganisateur.setFco(fdlvUser.get());}
		else{
			return ResponseEntity.badRequest().body(choixOrganisateur);
		}
		choixOrganisateur.setEtatScenario(etatScenarioConverter.convertToEntityAttribute(choixOrganisateur.getEtatScenarioId()));
		final ChoixOrganisateurDTO result = choixOrganisateurService.updateChoixOrganisateur(choixOrganisateur);
		return ResponseEntity
				.ok()
				.body(result);
	}

	/**
	 * {@code GET  /{id}} : find choix organisateur by id.
	 *
	 * @return choix-organisateur by id in body
	 */
	@GetMapping("/{id}")
	public ChoixOrganisateurDTO getChoixOrganisateurById(@PathVariable Long id) {
		return choixOrganisateurService.findById(id);
	}


	/**
	 * {@code GET  /choix-organisateur/fcofusid/} : get all the choix-organisateur through FcoFusID.
	 *
	 * @return the list of choix-organisateur in body.
	 */
	@GetMapping("/fcofusid/{id}")
	public List<ChoixOrganisateur> getChoixOrganisateur(@PathVariable Long id) {
		return choixOrganisateurService.findAllByFcoFusID(id);
	}

	/**
	 * {@code DELETE  /events/:id} : delete the "id" choix-organisateur.
	 *
	 * @param id the id of the ChoixOrganisateurDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteChoixOrganisateur(@PathVariable Long id) {
		choixOrganisateurService.delete(id);
		return ResponseEntity
				.ok()
				.build();
	}
}
