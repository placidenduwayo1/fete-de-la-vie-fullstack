package com.fdlv.gmd.api.service.forum;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.fdlv.gmd.api.dto.UserDTO;
import com.fdlv.gmd.api.dto.forum.ActeurDTO;

public interface ActeurService {
	/**
	 * Save an acteur.
	 *
	 * @param acteurDTO the DTO to save.
	 * @return the persisted DTO.
	 */
	ActeurDTO save(ActeurDTO acteurDTO);

	/**
	 * Partially updates an acteur.
	 *
	 * @param acteurDTO the DTO to update partially.
	 * @return the persisted DTO.
	 */
	Optional<ActeurDTO> partialUpdate(ActeurDTO acteurDTO);

	/**
	 * Get all the acteurs.
	 *
	 * @return the list of DTOs.
	 */
	List<ActeurDTO> findAll();

	/**
	 * Get the "id" acteur.
	 *
	 * @param id the id of the entity.
	 * @return the DTO.
	 */
	Optional<ActeurDTO> findOne(Long id);

	boolean existsById(Long id);

	/**
	 * Delete the "id" acteur.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	List<ActeurDTO> findResponsables(String responsable);

	/**
	 * Return the last Id number in the Forum table
	 * 
	 * @return
	 */
	Long max();

	UserDTO getUserConnected();

	ActeurDTO activerDesactiverActeur(Long acteurId);

	boolean addPhotoActeur(MultipartFile photo, Long acteurId);
}