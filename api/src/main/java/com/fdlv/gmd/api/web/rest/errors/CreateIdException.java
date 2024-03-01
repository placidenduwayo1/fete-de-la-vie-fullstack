package com.fdlv.gmd.api.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CreateIdException extends ResponseStatusException {

    private static final long serialVersionUID = -1981078017370849633L;

    public CreateIdException(String entityName) {
        super(HttpStatus.BAD_REQUEST,
                "A new ".concat(entityName).concat(" cannot already have an ID"));
    }

}
