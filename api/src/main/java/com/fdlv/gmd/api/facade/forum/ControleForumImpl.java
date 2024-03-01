package com.fdlv.gmd.api.facade.forum;

import org.springframework.stereotype.Component;

import com.fdlv.gmd.api.service.forum.ForumService;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;

@Component
public class ControleForumImpl implements ControleForumFacade {

	private final ForumService forumService;

	public ControleForumImpl(ForumService forumService) {
		this.forumService = forumService;
	}

	@Override
	public void verifierExistenceEntite(long id) {
		if (!this.forumService.existsById(id)) {
			throw new EntityNotFoundException(ENTITY_NAME);
		}

	}

}
