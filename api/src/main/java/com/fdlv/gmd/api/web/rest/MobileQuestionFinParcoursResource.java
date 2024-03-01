package com.fdlv.gmd.api.web.rest;

import com.fdlv.gmd.api.dto.mobile.MobileQuestionFinParcoursDTO;
import com.fdlv.gmd.api.service.mobile.MobileQuestionFinParcoursService;
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
 * REST controller for managing {@link com.fdlv.gmd.api.domain.mobile.MobileQuestionFinParcours}.
 */
@RestController
@RequestMapping("/api/mobile-question-fin-parcours")
public class MobileQuestionFinParcoursResource {

    private final Logger log = LoggerFactory.getLogger(MobileQuestionFinParcoursResource.class);

    private static final String ENTITY_NAME = "mobileQuestionFinParcours";

    private final MobileQuestionFinParcoursService mobileQuestionFinParcoursService;

    public MobileQuestionFinParcoursResource(MobileQuestionFinParcoursService mobileQuestionFinParcoursService) {
        this.mobileQuestionFinParcoursService = mobileQuestionFinParcoursService;
    }


    /**
     * {@code POST  /mobile-question-fin-parcours} : Create a new mobileQuestionFinParcours.
     *
     * @param mobileQuestionFinParcoursDTO the mobileQuestionFinParcoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mobileQuestionFinParcoursDTO, or with status {@code 400 (Bad Request)} if the mobileQuestionFinParcours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<MobileQuestionFinParcoursDTO> createMobileQuestionFinParcours(@Valid @RequestBody MobileQuestionFinParcoursDTO mobileQuestionFinParcoursDTO) throws URISyntaxException {
        log.debug("REST request to save MobileQuestionFinParcours : {}", mobileQuestionFinParcoursDTO);
        if (mobileQuestionFinParcoursDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        MobileQuestionFinParcoursDTO result = mobileQuestionFinParcoursService.save(mobileQuestionFinParcoursDTO);
        return ResponseEntity
                .created(new URI("/api/mobile-question-fin-parcours/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing mobileQuestionFinParcours.
     *
     * @param id the id of the mobileQuestionFinParcoursDTO to save.
     * @param mobileQuestionFinParcoursDTO the mobileQuestionFinParcoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobileQuestionFinParcoursDTO,
     * or with status {@code 400 (Bad Request)} if the mobileQuestionFinParcoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobileQuestionFinParcoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MobileQuestionFinParcoursDTO> updateMobileQuestionFinParcours(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody MobileQuestionFinParcoursDTO mobileQuestionFinParcoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MobileQuestionFinParcours : {}, {}", id, mobileQuestionFinParcoursDTO);
        if (mobileQuestionFinParcoursDTO.getId() == null
                || !Objects.equals(id, mobileQuestionFinParcoursDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!mobileQuestionFinParcoursService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        MobileQuestionFinParcoursDTO result = mobileQuestionFinParcoursService.save(mobileQuestionFinParcoursDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * {@code PATCH  /mobile-question-fin-parcours/:id} : Partial updates given fields of an existing mobileQuestionFinParcours, field will ignore if it is null
     *
     * @param id the id of the mobileQuestionFinParcoursDTO to save.
     * @param mobileQuestionFinParcoursDTO the mobileQuestionFinParcoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobileQuestionFinParcoursDTO,
     * or with status {@code 400 (Bad Request)} if the mobileQuestionFinParcoursDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mobileQuestionFinParcoursDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mobileQuestionFinParcoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MobileQuestionFinParcoursDTO> partialUpdateMobileQuestionFinParcours(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody MobileQuestionFinParcoursDTO mobileQuestionFinParcoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MobileQuestionFinParcours partially : {}, {}", id, mobileQuestionFinParcoursDTO);
        if (mobileQuestionFinParcoursDTO.getId() == null
                || !Objects.equals(id, mobileQuestionFinParcoursDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!mobileQuestionFinParcoursService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<MobileQuestionFinParcoursDTO> result = mobileQuestionFinParcoursService.partialUpdate(mobileQuestionFinParcoursDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /mobile-question-fin-parcours} : get all the mobileQuestionsFinParcours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mobileQuestionsFinParcours in body.
     */
    @GetMapping()
    public List<MobileQuestionFinParcoursDTO> getAllMobileQuestionsFinParcours() {
        log.debug("REST request to get all MobileQuestionsFinParcours");
        return mobileQuestionFinParcoursService.findAll();
    }

    /**
     * {@code GET  /mobile-question-fin-parcours/:id} : get the "id" mobileQuestionFinParcours.
     *
     * @param id the id of the mobileQuestionFinParcoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mobileQuestionFinParcoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MobileQuestionFinParcoursDTO> getMobileQuestionFinParcours(@PathVariable Long id) {
        log.debug("REST request to get MobileQuestionFinParcours : {}", id);
        Optional<MobileQuestionFinParcoursDTO> mobileQuestionFinParcoursDTO = mobileQuestionFinParcoursService.findOne(id);
        return HttpUtils.wrapOrNotFound(mobileQuestionFinParcoursDTO);
    }

    /**
     * {@code DELETE  /mobile-question-fin-parcours/:id} : delete the "id" mobileQuestionFinParcours.
     *
     * @param id the id of the mobileQuestionFinParcoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMobileQuestionFinParcours(@PathVariable Long id) {
        log.debug("REST request to delete MobileQuestionFinParcours : {}", id);
        mobileQuestionFinParcoursService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
