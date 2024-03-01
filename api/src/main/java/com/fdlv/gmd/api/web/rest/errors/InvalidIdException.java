package com.fdlv.gmd.api.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidIdException extends ResponseStatusException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidIdException(String entityName) {
        super(HttpStatus.BAD_REQUEST,
                "Invalid id for entity ".concat(entityName));
    }

}
