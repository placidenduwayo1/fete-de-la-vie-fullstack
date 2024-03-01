package com.fdlv.gmd.api.web.rest.forum;

import com.fdlv.gmd.api.dto.forum.StandBanniereDTO;
import com.fdlv.gmd.api.service.forum.StandBanniereService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/stand-bannieres")
public class StandBanniereResource {

    private final Logger log = LoggerFactory.getLogger(StandBanniereResource.class);

    private static final String ENTITY_NAME = "standBanniere";

    private final StandBanniereService standBanniereService;

    public StandBanniereResource(StandBanniereService standBanniereService) {
        this.standBanniereService = standBanniereService;
    }

    @PostMapping()
    public ResponseEntity<StandBanniereDTO> createStandBanniere(@Valid @RequestBody StandBanniereDTO standBanniereDTO) throws URISyntaxException {
        log.debug("REST request to save StandBanniere: {}", standBanniereDTO);
        if (standBanniereDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        StandBanniereDTO result = standBanniereService.save(standBanniereDTO);
        return ResponseEntity
            .created(new URI("/api/stand-bannieres/" + result.getId()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandBanniereDTO> updateStandBanniere(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StandBanniereDTO standBanniereDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StandBanniere: {}, {}", id, standBanniereDTO);
        if (standBanniereDTO.getId() == null || !Objects.equals(id, standBanniereDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!standBanniereService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        StandBanniereDTO result = standBanniereService.save(standBanniereDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StandBanniereDTO> partialUpdateStandBanniere(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StandBanniereDTO standBanniereDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StandBanniere partially: {}, {}", id, standBanniereDTO);
        if (standBanniereDTO.getId() == null || !Objects.equals(id, standBanniereDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!standBanniereService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<StandBanniereDTO> result = standBanniereService.partialUpdate(standBanniereDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    @GetMapping()
    public List<StandBanniereDTO> getAllStandBannieres() {
        log.debug("REST request to get all StandBannieres");
        return standBanniereService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandBanniereDTO> getStandBanniere(@PathVariable Long id) {
        log.debug("REST request to get StandBanniere: {}", id);
        Optional<StandBanniereDTO> standBanniereDTO = standBanniereService.findOne(id);
        return HttpUtils.wrapOrNotFound(standBanniereDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandBanniere(@PathVariable Long id) {
        log.debug("REST request to delete StandBanniere: {}", id);
        standBanniereService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
