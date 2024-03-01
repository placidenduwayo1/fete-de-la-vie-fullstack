package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.ForumThemeDTO;

import java.util.List;
import java.util.Optional;

public interface ForumThemeService {
	/**
	 * Save a forum.
	 *
	 * @param forumThemeDTO the entity to save.
	 * @return the persisted entity.
	 */
	ForumThemeDTO save(ForumThemeDTO forumThemeDTO);

	/**
	 * Partially updates a forum theme
	 * @param forumThemeDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<ForumThemeDTO> partialUpdate(ForumThemeDTO forumThemeDTO);

	/**
	 * Get all forum theme.
	 * @return the list of entities.
	 */
	List<ForumThemeDTO> findAll();
	boolean existsById(Long id);
	Optional<ForumThemeDTO> findOne(Long id);

	/**
	 * Delete the "id" forum theme
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
