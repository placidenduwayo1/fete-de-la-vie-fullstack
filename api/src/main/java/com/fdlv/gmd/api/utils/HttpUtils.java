package com.fdlv.gmd.api.utils;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public class HttpUtils {

    /**
     * Génère une réponse OK à partir de l'Optional en entrée, si existant
     * @param <X> Le type de l'objet en entrée, un DTO de l'application
     * @param maybeResponse un Optional de l'objet en entrée
     * @return une réponse OK buildée sur l'objet en entrée si trouvé,
     *         une exception NOT FOUND sinon
     */
    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) throws ResponseStatusException {
        return maybeResponse.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
