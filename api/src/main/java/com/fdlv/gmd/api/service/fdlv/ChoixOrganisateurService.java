package com.fdlv.gmd.api.service.fdlv;

import java.util.List;

import com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur;
import com.fdlv.gmd.api.dto.ChoixOrganisateurDTO;


/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur}.
 */
public interface ChoixOrganisateurService {

	/**
	 * Get if the entity with given user id exists or not
	 *
	 * @param id the id of the user linked
	 * @return true if the entity exists, false otherwise
	 */
	boolean existsByFdlvUserId(Long userId);

	/**
	 * Get all the choix-organisateur.
	 *
	 * @return the list of choix-organisateur.
	 */
	List<ChoixOrganisateurDTO> findAll();

	/**
	 * Get if the entity with given user FcoFusID exists or not
	 *
	 * @param id the id of the user linked
	 * @return an list of the entity if exists
	 */
	List<ChoixOrganisateur> findAllByFcoFusID(Long id);

	/**
	 * Delete the "id" choix-organisateur.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * create choix-organisateur.
	 *
	 * @param choix to create.
	 */
	ChoixOrganisateurDTO createChoixOrganisateur(ChoixOrganisateurDTO choixOrganisateurDTO);

	/**
	 * get choix-organisateur by id.
	 *
	 * @param id of choix organisateur.
	 */
	ChoixOrganisateurDTO findById(Long id);

	/**
	 * create choix-organisateur.
	 *
	 * @param infomartion to update
	 */
	ChoixOrganisateurDTO updateChoixOrganisateur(ChoixOrganisateurDTO choixOrganisateur);
}
