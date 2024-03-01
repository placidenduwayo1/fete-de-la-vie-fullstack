package com.fdlv.gmd.api.web.rest.forum;

import com.fdlv.gmd.api.dto.forum.BanniereDTO;
import com.fdlv.gmd.api.service.forum.BanniereService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.forum.Banniere}.
 */
@RestController
@RequestMapping("/api/banniere")
@Validated
public class BanniereResource {
    private final Logger log = LoggerFactory.getLogger(BanniereResource.class);

    private static final String ENTITY_NAME = "forum-theme";

    private final BanniereService banniereService;

    public BanniereResource(BanniereService banniereService) {
        this.banniereService = banniereService;
    }

    /**
     * {@code POST  /forum-theme} : Create a new structure.
     * @param banniereDTO the BanniereDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new BanniereDTO, or with status {@code 400 (Bad Request)} if the structure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<BanniereDTO> createBanniere(@Valid @RequestBody BanniereDTO banniereDTO) throws URISyntaxException {
        log.debug("REST request to save New Bannière");
        if (banniereDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        BanniereDTO result = banniereService.save(banniereDTO);
        if (result.getId() != null) {
            result = banniereService.save(result);
        }
        return ResponseEntity.created(new URI("/api/banniere/" + result.getId())).body(result);
    }

    /**
     * {@code PUT  /structures/:id} : Updates an existing structure.
     *
     * @param id the id of the BanniereDTO to save.
     * @param banniere the BanniereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated BanniereDTO,
     * or with status {@code 400 (Bad Request)} if the BanniereDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the BanniereDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BanniereDTO> updateBanniere(
            @PathVariable(value = "id") final Long id, @Valid @RequestBody BanniereDTO banniere
    ) {
        log.debug("REST request to update Banniere : {}, {}", id, banniere);
        if (banniere.getId() == null || !Objects.equals(id, banniere.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }
        if (!banniereService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        BanniereDTO result = banniereService.save(banniere);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /structures/:id} : Partial updates given fields of an existing forum theme, field will ignore if it is null
     * @param id the id of the BanniereDTO to save.
     * @param BanniereDTO the BanniereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated BanniereDTO,
     * or with status {@code 400 (Bad Request)} if the BanniereDTO is not valid,
     * or with status {@code 404 (Not Found)} if the BanniereDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the BanniereDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BanniereDTO> partialUpdateBanniere(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody BanniereDTO BanniereDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update theme forum partially : {}, {}", id, BanniereDTO);
        if (BanniereDTO.getId() == null || !Objects.equals(id, BanniereDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }
        if (!banniereService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        Optional<BanniereDTO> result = banniereService.partialUpdate(BanniereDTO);
        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /structures} : get all the theme forum.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of structures in body.
     */
    @GetMapping()
    public List<BanniereDTO> getAllBanniere() {
        log.debug("REST request to get all Bannieres");
        return banniereService.findAll();
    }

    /**
     * {@code GET  /structures/:id} : get the "id" banniere.
     * @param id the id of the BanniereDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the BanniereDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BanniereDTO> getBanniere(@PathVariable Long id) {
        log.debug("REST request to get Banniere : {}", id);
        Optional<BanniereDTO> BanniereDTO = banniereService.findOne(id);
        return HttpUtils.wrapOrNotFound(BanniereDTO);
    }

    /**
     * {@code DELETE  /structures/:id} : delete the "id" banniere
     * @param id the id of the BanniereDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanniere(@PathVariable Long id) {
        log.debug("REST request to delete Banniere : {}", id);
        banniereService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
