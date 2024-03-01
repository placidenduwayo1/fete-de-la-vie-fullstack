package com.fdlv.gmd.api.web.rest.forum;

import com.fdlv.gmd.api.dto.forum.StandDTO;
import com.fdlv.gmd.api.service.forum.StandService;
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
@RequestMapping("/api/stands")
public class StandResource {

    private final Logger log = LoggerFactory.getLogger(StandResource.class);

    private static final String ENTITY_NAME = "stand";

    private final StandService standService;

    public StandResource(StandService standService) {
        this.standService = standService;
    }

    @PostMapping()
    public ResponseEntity<StandDTO> createStand(@Valid @RequestBody StandDTO standDTO) throws URISyntaxException {
        log.debug("REST request to save Stand: {}", standDTO);
        if (standDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        StandDTO result = standService.save(standDTO);
        return ResponseEntity
            .created(new URI("/api/stands/" + result.getId()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandDTO> updateStand(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StandDTO standDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Stand: {}, {}", id, standDTO);
        if (standDTO.getId() == null || !Objects.equals(id, standDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!standService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        StandDTO result = standService.save(standDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StandDTO> partialUpdateStand(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StandDTO standDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stand partially: {}, {}", id, standDTO);
        if (standDTO.getId() == null || !Objects.equals(id, standDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!standService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<StandDTO> result = standService.partialUpdate(standDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    @GetMapping()
    public List<StandDTO> getAllStands() {
        log.debug("REST request to get all Stands");
        return standService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandDTO> getStand(@PathVariable Long id) {
        log.debug("REST request to get Stand: {}", id);
        Optional<StandDTO> standDTO = standService.findOne(id);
        return HttpUtils.wrapOrNotFound(standDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStand(@PathVariable Long id) {
        log.debug("REST request to delete Stand: {}", id);
        standService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
