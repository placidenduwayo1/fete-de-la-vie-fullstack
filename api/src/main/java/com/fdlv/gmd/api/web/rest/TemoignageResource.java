package com.fdlv.gmd.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.dto.fdlv.TemoignageApiDTO;
import org.apache.commons.compress.utils.FileNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fdlv.gmd.api.dto.StringWrapper;
import com.fdlv.gmd.api.dto.fdlv.TemoignageDTO;
import com.fdlv.gmd.api.service.FtpService;
import com.fdlv.gmd.api.service.fdlv.TemoignageService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.fdlv.Temoignage}.
 */
@RestController
@RequestMapping("/api/testimony")
public class TemoignageResource {

    private final Logger log = LoggerFactory.getLogger(TemoignageResource.class);

    private static final String ENTITY_NAME = "temoignage";

    private final TemoignageService temoignageService;

    private final FtpService ftpService;

    public TemoignageResource(TemoignageService temoignageService, FtpService ftpService) {
        this.temoignageService = temoignageService;
        this.ftpService = ftpService;
    }

    @PostMapping("/image")
    public ResponseEntity<StringWrapper> postImageOnServer(@RequestParam(value = "image") MultipartFile image,
            @RequestParam(value = "name") String name) {
        String extention = FileNameUtils.getExtension(image.getOriginalFilename());
        StringWrapper result = new StringWrapper();
        result.setValue(ftpService.putImageFile(name, extention, image, true));
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /testimony} : Create a new temoignage.
     *
     * @param temoignageDTO the temoignageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new temoignageDTO, or with status {@code 400 (Bad Request)} if the temoignage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<TemoignageDTO> createTemoignage(@Valid @RequestBody TemoignageDTO temoignageDTO) throws URISyntaxException {
        log.debug("REST request to save Temoignage : {}", temoignageDTO);
        if (temoignageDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        TemoignageDTO result = temoignageService.save(temoignageDTO);
        return ResponseEntity
            .created(new URI("/api/testimony/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing temoignage.
     *
     * @param id the id of the temoignageDTO to save.
     * @param temoignageDTO the temoignageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated temoignageDTO,
     * or with status {@code 400 (Bad Request)} if the temoignageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the temoignageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TemoignageDTO> updateTemoignage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TemoignageDTO temoignageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Temoignage : {}, {}", id, temoignageDTO);
        if (temoignageDTO.getId() == null
                || !Objects.equals(id, temoignageDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!temoignageService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        TemoignageDTO result = temoignageService.save(temoignageDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /testimony/:id} : Partial updates given fields of an existing temoignage, field will ignore if it is null
     *
     * @param id the id of the temoignageDTO to save.
     * @param temoignageDTO the temoignageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated temoignageDTO,
     * or with status {@code 400 (Bad Request)} if the temoignageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the temoignageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the temoignageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TemoignageDTO> partialUpdateTemoignage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TemoignageDTO temoignageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Temoignage partially : {}, {}", id, temoignageDTO);
        if (temoignageDTO.getId() == null
                || !Objects.equals(id, temoignageDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!temoignageService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<TemoignageDTO> result = temoignageService.partialUpdate(temoignageDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /testimony} : get all the temoignages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of temoignages in body.
     */
    @GetMapping()
    public List<TemoignageDTO> getAllTemoignages() {
        log.debug("REST request to get all Temoignages");
        return temoignageService.findAll();
    }

    /**
     * {@code GET  /testimony/:id} : get the "id" temoignage.
     *
     * @param id the id of the temoignageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the temoignageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TemoignageDTO> getTemoignage(@PathVariable Long id) {
        log.debug("REST request to get Temoignage : {}", id);
        Optional<TemoignageDTO> temoignageDTO = temoignageService.findOne(id);
        return HttpUtils.wrapOrNotFound(temoignageDTO);
    }

    /**
     * {@code DELETE  /testimony/:id} : delete the "id" temoignage.
     *
     * @param id the id of the temoignageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemoignage(@PathVariable Long id) {
        log.debug("REST request to delete Temoignage : {}", id);
        Optional<TemoignageDTO> opt = temoignageService.findOne(id);
        if (opt.isPresent()) {
            TemoignageDTO temoignage = opt.get();
            if (temoignage.getLienImageAuteur() != null) {
                this.ftpService.deleteFromServer(temoignage.getLienImageAuteur());
            }
        }
        temoignageService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    /**
     * {@code GET  /testimony/displayable} : get the displayables temoignages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of temoignages in body.
     */
    @GetMapping("/displayable")
    public ResponseEntity<List<TemoignageApiDTO>> getDisplayableTemoignages() {
        log.debug("REST request to get displayable Temoignages");
        return ResponseEntity.ok(temoignageService.findDisplayable());
    }
}
