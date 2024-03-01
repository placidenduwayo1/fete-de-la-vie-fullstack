package com.fdlv.gmd.api.web.rest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.dto.StringWrapper;
import com.fdlv.gmd.api.service.FtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fdlv.gmd.api.dto.RefLogoTeaserDTO;
import com.fdlv.gmd.api.service.RefLogoTeaserService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.RefLogoTeaser}.
 */
@RestController
@RequestMapping("/api/logo-teaser")
public class RefLogoTeaserResource {

    private final Logger log = LoggerFactory.getLogger(RefLogoTeaserResource.class);

    private static final String ENTITY_NAME = "refLogoTeaser";

    private final RefLogoTeaserService refLogoTeaserService;
    private final FtpService ftpService;

    public RefLogoTeaserResource(RefLogoTeaserService refLogoTeaserService, FtpService ftpService) {
        this.refLogoTeaserService = refLogoTeaserService;
        this.ftpService = ftpService;
    }

    /**
     * {@code POST  /logo-teaser} : Create a new refLogoTeaser.
     *
     * @param refLogoTeaserDTO the refLogoTeaserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refLogoTeaserDTO, or with status {@code 400 (Bad Request)} if the refLogoTeaser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<RefLogoTeaserDTO> createRefLogoTeaser(@Valid @RequestBody RefLogoTeaserDTO refLogoTeaserDTO) throws URISyntaxException, MalformedURLException {
        log.debug("REST request to save RefLogoTeaser : {}", refLogoTeaserDTO);
        if (refLogoTeaserDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        RefLogoTeaserDTO inter = refLogoTeaserService.save(refLogoTeaserDTO);

        String originalUrl = inter.getUrl();
        String newUrl = modifyUrl(originalUrl, inter.getId().toString());
        inter.setUrl(newUrl);

        RefLogoTeaserDTO result = refLogoTeaserService.partialUpdate(inter).orElseThrow(RuntimeException::new);

        modifyUrlAndRenameFile(originalUrl, newUrl);

        return ResponseEntity
                .created(new URI("/api/logo-teaser/" + result.getId()))
                .body(result);
    }

    private void modifyUrlAndRenameFile(String originalUrl, String newUrl) throws MalformedURLException {
        String fromPath = new URL(originalUrl).getPath();
        String toPath = new URL(newUrl).getPath();

        ftpService.renameFile(fromPath, toPath);
    }

    public String modifyUrl(String url, String valueToAdd) {
        int index = url.lastIndexOf("/");
        return url.substring(0, index + 1) + valueToAdd + "_" + url.substring(index + 1);
    }

    /**
     * {@code PUT  /:id} : Updates an existing refLogoTeaser.
     *
     * @param id the id of the refLogoTeaserDTO to save.
     * @param refLogoTeaserDTO the refLogoTeaserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refLogoTeaserDTO,
     * or with status {@code 400 (Bad Request)} if the refLogoTeaserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refLogoTeaserDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RefLogoTeaserDTO> updateRefLogoTeaser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RefLogoTeaserDTO refLogoTeaserDTO
    ) {
        log.debug("REST request to update RefLogoTeaser : {}, {}", id, refLogoTeaserDTO);
        if (refLogoTeaserDTO.getId() == null
                || !Objects.equals(id, refLogoTeaserDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!refLogoTeaserService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        RefLogoTeaserDTO result = refLogoTeaserService.save(refLogoTeaserDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /logo-teaser/:id} : Partial updates given fields of an existing refLogoTeaser, field will ignore if it is null
     *
     * @param id the id of the refLogoTeaserDTO to save.
     * @param refLogoTeaserDTO the refLogoTeaserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refLogoTeaserDTO,
     * or with status {@code 400 (Bad Request)} if the refLogoTeaserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the refLogoTeaserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the refLogoTeaserDTO couldn't be updated.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RefLogoTeaserDTO> partialUpdateRefLogoTeaser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RefLogoTeaserDTO refLogoTeaserDTO
    ) {
        log.debug("REST request to partial update RefLogoTeaser partially : {}, {}", id, refLogoTeaserDTO);
        if (refLogoTeaserDTO.getId() == null
                || !Objects.equals(id, refLogoTeaserDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!refLogoTeaserService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<RefLogoTeaserDTO> result = refLogoTeaserService.partialUpdate(refLogoTeaserDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /logo-teaser} : get all the refLogoTeasers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refLogoTeasers in body.
     */
    @GetMapping()
    public List<RefLogoTeaserDTO> getAllRefLogoTeasers() {
        log.debug("REST request to get all RefLogoTeasers");
        return refLogoTeaserService.findAll();
    }

    /**
     * {@code GET  /logo-teaser/logos} : get all the refLogos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refLogos in body.
     */
    @GetMapping("/logos")
    public List<RefLogoTeaserDTO> getAllRefLogos() {
        log.debug("REST request to get all RefLogos");
        return refLogoTeaserService.findAllLogos();
    }

    /**
     * {@code GET  /logo-teaser/teasers} : get all the refTeasers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refTeasers in body.
     */
    @GetMapping("/teasers")
    public List<RefLogoTeaserDTO> getAllRefTeasers() {
        log.debug("REST request to get all RefLogos");
        return refLogoTeaserService.findAllTeasers();
    }

    /**
     * {@code GET  /logo-teaser/:id} : get the "id" refLogoTeaser.
     *
     * @param id the id of the refLogoTeaserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refLogoTeaserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RefLogoTeaserDTO> getRefLogoTeaser(@PathVariable Long id) {
        log.debug("REST request to get RefLogoTeaser : {}", id);
        Optional<RefLogoTeaserDTO> refLogoTeaserDTO = refLogoTeaserService.findOne(id);
        return HttpUtils.wrapOrNotFound(refLogoTeaserDTO);
    }

    /**
     * {@code DELETE  /logo-teaser/:id} : delete the "id" refLogoTeaser.
     *
     * @param id the id of the refLogoTeaserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRefLogoTeaser(@PathVariable Long id) {
        log.debug("REST request to delete RefLogoTeaser : {}", id);
        refLogoTeaserService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PostMapping("/media/{typeMedia}")
    public ResponseEntity<StringWrapper> postImageOnServer(@RequestParam(value = "media") MultipartFile media, @PathVariable String typeMedia) {
        log.debug("REST request to upload : {}", typeMedia);
        StringWrapper result = new StringWrapper();
        result.setValue(ftpService.putLogoTeaser(media, typeMedia));
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/max-rlt-id")
    public ResponseEntity<Long> getMaxRltId() {
        Long maxRltId = refLogoTeaserService.getMaxRltId();
        if (maxRltId == null) {
            maxRltId = 0L;
        }
        return ResponseEntity.ok(maxRltId);
    }
}
