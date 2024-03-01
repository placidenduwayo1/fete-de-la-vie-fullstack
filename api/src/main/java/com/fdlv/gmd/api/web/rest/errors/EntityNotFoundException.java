package com.fdlv.gmd.api.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {

    private static final long serialVersionUID = 5491341495341338650L;

    public EntityNotFoundException(String entityName) {
        super(HttpStatus.BAD_REQUEST,
                "Entity ".concat(entityName).concat(" not found"));
    }
}
