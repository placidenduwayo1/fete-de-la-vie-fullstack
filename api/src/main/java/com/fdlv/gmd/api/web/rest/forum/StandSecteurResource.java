package com.fdlv.gmd.api.web.rest.forum;

import com.fdlv.gmd.api.dto.forum.StandSecteurDTO;
import com.fdlv.gmd.api.service.forum.StandSecteurService;
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
@RequestMapping("/api/stand-secteurs")
public class StandSecteurResource {

    private final Logger log = LoggerFactory.getLogger(StandSecteurResource.class);

    private static final String ENTITY_NAME = "standSecteur";

    private final StandSecteurService standSecteurService;

    public StandSecteurResource(StandSecteurService standSecteurService) {
        this.standSecteurService = standSecteurService;
    }

    @PostMapping()
    public ResponseEntity<StandSecteurDTO> createStandSecteur(@Valid @RequestBody StandSecteurDTO standSecteurDTO) throws URISyntaxException {
        log.debug("REST request to save StandSecteur: {}", standSecteurDTO);
        if (standSecteurDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        StandSecteurDTO result = standSecteurService.save(standSecteurDTO);
        return ResponseEntity
            .created(new URI("/api/stand-secteurs/" + result.getId()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandSecteurDTO> updateStandSecteur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StandSecteurDTO standSecteurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StandSecteur: {}, {}", id, standSecteurDTO);
        if (standSecteurDTO.getId() == null || !Objects.equals(id, standSecteurDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!standSecteurService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        StandSecteurDTO result = standSecteurService.save(standSecteurDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StandSecteurDTO> partialUpdateStandSecteur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StandSecteurDTO standSecteurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StandSecteur partially: {}, {}", id, standSecteurDTO);
        if (standSecteurDTO.getId() == null || !Objects.equals(id, standSecteurDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!standSecteurService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<StandSecteurDTO> result = standSecteurService.partialUpdate(standSecteurDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    @GetMapping()
    public List<StandSecteurDTO> getAllStandSecteurs() {
        log.debug("REST request to get all StandSecteurs");
        return standSecteurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandSecteurDTO> getStandSecteur(@PathVariable Long id) {
        log.debug("REST request to get StandSecteur: {}", id);
        Optional<StandSecteurDTO> standSecteurDTO = standSecteurService.findOne(id);
        return HttpUtils.wrapOrNotFound(standSecteurDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandSecteur(@PathVariable Long id) {
        log.debug("REST request to delete StandSecteur: {}", id);
        standSecteurService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
