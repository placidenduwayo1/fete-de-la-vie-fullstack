package com.fdlv.gmd.api.web.rest;

import com.fdlv.gmd.api.dto.mobile.MobileAvisParcoursDTO;
import com.fdlv.gmd.api.service.mobile.MobileAvisParcoursService;
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

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.mobile.MobileAvisParcours}.
 */
@RestController
@RequestMapping("/api/mobile-avis-parcours")
public class MobileAvisParcoursResource {

    private final Logger log = LoggerFactory.getLogger(MobileAvisParcoursResource.class);

    private static final String ENTITY_NAME = "mobileAvisParcours";

    private final MobileAvisParcoursService mobileAvisParcoursService;

    public MobileAvisParcoursResource(MobileAvisParcoursService mobileAvisParcoursService) {
        this.mobileAvisParcoursService = mobileAvisParcoursService;
    }


    /**
     * {@code POST  /mobile-avis-parcours} : Create a new mobileAvisParcours.
     *
     * @param mobileAvisParcoursDTO the mobileAvisParcoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mobileAvisParcoursDTO, or with status {@code 400 (Bad Request)} if the mobileAvisParcours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<MobileAvisParcoursDTO> createMobileAvisParcours(@Valid @RequestBody MobileAvisParcoursDTO mobileAvisParcoursDTO) throws URISyntaxException {
        log.debug("REST request to save MobileAvisParcours : {}", mobileAvisParcoursDTO);
        if (mobileAvisParcoursDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        MobileAvisParcoursDTO result = mobileAvisParcoursService.save(mobileAvisParcoursDTO);
        return ResponseEntity
                .created(new URI("/api/mobile-avis-parcours/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing mobileAvisParcours.
     *
     * @param id the id of the mobileAvisParcoursDTO to save.
     * @param mobileAvisParcoursDTO the mobileAvisParcoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobileAvisParcoursDTO,
     * or with status {@code 400 (Bad Request)} if the mobileAvisParcoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobileAvisParcoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MobileAvisParcoursDTO> updateMobileAvisParcours(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody MobileAvisParcoursDTO mobileAvisParcoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MobileAvisParcours : {}, {}", id, mobileAvisParcoursDTO);
        if (mobileAvisParcoursDTO.getId() == null
                || !Objects.equals(id, mobileAvisParcoursDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!mobileAvisParcoursService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        MobileAvisParcoursDTO result = mobileAvisParcoursService.save(mobileAvisParcoursDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * {@code PATCH  /mobile-avis-parcours/:id} : Partial updates given fields of an existing mobileAvisParcours, field will ignore if it is null
     *
     * @param id the id of the mobileAvisParcoursDTO to save.
     * @param mobileAvisParcoursDTO the mobileAvisParcoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobileAvisParcoursDTO,
     * or with status {@code 400 (Bad Request)} if the mobileAvisParcoursDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mobileAvisParcoursDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mobileAvisParcoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MobileAvisParcoursDTO> partialUpdateMobileAvisParcours(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody MobileAvisParcoursDTO mobileAvisParcoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MobileAvisParcours partially : {}, {}", id, mobileAvisParcoursDTO);
        if (mobileAvisParcoursDTO.getId() == null
                || !Objects.equals(id, mobileAvisParcoursDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!mobileAvisParcoursService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<MobileAvisParcoursDTO> result = mobileAvisParcoursService.partialUpdate(mobileAvisParcoursDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /mobile-avis-parcours} : get all the mobileAvisParcours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mobileAvisParcours in body.
     */
    @GetMapping()
    public List<MobileAvisParcoursDTO> getAllMobileAvisParcours() {
        log.debug("REST request to get all MobileAvisParcours");
        return mobileAvisParcoursService.findAll();
    }

    /**
     * {@code GET  /mobile-avis-parcours/:id} : get the "id" mobileAvisParcours.
     *
     * @param id the id of the mobileAvisParcoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mobileAvisParcoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MobileAvisParcoursDTO> getMobileAvisParcours(@PathVariable Long id) {
        log.debug("REST request to get MobileAvisParcours : {}", id);
        Optional<MobileAvisParcoursDTO> mobileAvisParcoursDTO = mobileAvisParcoursService.findOne(id);
        return HttpUtils.wrapOrNotFound(mobileAvisParcoursDTO);
    }

    /**
     * {@code DELETE  /mobile-avis-parcours/:id} : delete the "id" mobileAvisParcours.
     *
     * @param id the id of the mobileAvisParcoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMobileAvisParcours(@PathVariable Long id) {
        log.debug("REST request to delete MobileAvisParcours : {}", id);
        mobileAvisParcoursService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
