package com.fdlv.gmd.api.facade.forum;

import org.springframework.stereotype.Component;

import com.fdlv.gmd.api.service.forum.RoleActeurService;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;

@Component
public class ControleRoleActeurImpl implements ControleRoleActeurFacade {

	private final RoleActeurService roleActeurService;

	public ControleRoleActeurImpl(RoleActeurService roleActeurService) {
		this.roleActeurService = roleActeurService;
	}

	@Override
	public void verifierExistenceEntite(long id) {
		if (!this.roleActeurService.existsById(id)) {
			throw new EntityNotFoundException(ENTITY_NAME);
		}
	}

}
