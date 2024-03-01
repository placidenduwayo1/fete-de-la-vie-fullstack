package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.BanniereDTO;

import java.util.List;
import java.util.Optional;

public interface BanniereService {
	/**
	 * Save a forum.
	 *
	 * @param banniereDTO the entity to save.
	 * @return the persisted entity.
	 */
	BanniereDTO save(BanniereDTO banniereDTO);

	/**
	 * Partially updates a bannière
	 * @param banniereDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<BanniereDTO> partialUpdate(BanniereDTO banniereDTO);

	/**
	 * Get all bannière.
	 * @return the list of entities.
	 */
	List<BanniereDTO> findAll();
	boolean existsById(Long id);
	Optional<BanniereDTO> findOne(Long id);

	/**
	 * Delete the "id" bannière
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
