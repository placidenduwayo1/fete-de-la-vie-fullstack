package com.fdlv.gmd.api.service.forum;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.forum.ForumDTO;

public interface ForumService {
	/**
	 * Save a forum.
	 *
	 * @param forumDTO the entity to save.
	 * @return the persisted entity.
	 */
	ForumDTO save(ForumDTO forumDTO);

	/**
	 * Partially updates a forum.
	 *
	 * @param forumDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<ForumDTO> partialUpdate(ForumDTO forumDTO);

	/**
	 * Get all the forums.
	 *
	 * @return the list of entities.
	 */
	List<ForumDTO> findAll();

	List<ForumDTO>findAllWithCorrespondantFDLV();
	/**
	 * Get the "id" forum.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<ForumDTO> findOne(Long id);

	/**
	 * Get if the entity with given id exists or not
	 * 
	 * @param id the id of the entity
	 * @return true if the entity exists, false otherwise
	 */
	boolean existsById(Long id);

	/**
	 * Delete the "id" forum.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Return the last Id number in the Forum table
	 * 
	 * @return
	 */
	Long max();

}
