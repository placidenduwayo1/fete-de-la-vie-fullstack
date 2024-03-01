package com.fdlv.gmd.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.fdlv.gmd.api.dto.fdlv.InfoPageWebDTO;
import com.fdlv.gmd.api.service.FtpService;
import com.fdlv.gmd.api.service.fdlv.InfoPageWebService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.fdlv.InfoPageWeb}.
 */
@RestController
@RequestMapping("/api/info-page-web")
public class InfoPageWebResource {

    private final Logger log = LoggerFactory.getLogger(InfoPageWebResource.class);

    private static final String ENTITY_NAME = "infoPageWeb";

    private final InfoPageWebService infoPageWebService;
    
    private final FtpService ftpService;

    public InfoPageWebResource(InfoPageWebService infoPageWebService, FtpService ftpService) {
        this.infoPageWebService = infoPageWebService;
        this.ftpService = ftpService;
    }

    @PostMapping("/image")
    public ResponseEntity<StringWrapper> postImageOnServer(@RequestParam(value = "image") MultipartFile image, @RequestParam(value = "name") String name) {
        String extention = FileNameUtils.getExtension(image.getOriginalFilename());
        StringWrapper result = new StringWrapper();
        result.setValue(ftpService.putImageFile(name, extention, image, false));
        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * {@code POST  /info-page-web} : Create a new infoPageWeb.
     *
     * @param infoPageWebDTO the infoPageWebDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoPageWebDTO, or with status {@code 400 (Bad Request)} if the infoPageWeb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<InfoPageWebDTO> createInfoPageWeb(@Valid @RequestBody InfoPageWebDTO infoPageWebDTO) throws URISyntaxException {
        log.debug("REST request to save InfoPageWeb : {}", infoPageWebDTO);
        if (infoPageWebDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        InfoPageWebDTO result = infoPageWebService.save(infoPageWebDTO);
        return ResponseEntity
            .created(new URI("/api/info-page-web/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing infoPageWeb.
     *
     * @param id the id of the infoPageWebDTO to save.
     * @param infoPageWebDTO the infoPageWebDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoPageWebDTO,
     * or with status {@code 400 (Bad Request)} if the infoPageWebDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoPageWebDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InfoPageWebDTO> updateInfoPageWeb(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InfoPageWebDTO infoPageWebDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InfoPageWeb : {}, {}", id, infoPageWebDTO);
        if (infoPageWebDTO.getId() == null
                || !Objects.equals(id, infoPageWebDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!infoPageWebService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        InfoPageWebDTO result = infoPageWebService.save(infoPageWebDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /info-page-web/:id} : Partial updates given fields of an existing infoPageWeb, field will ignore if it is null
     *
     * @param id the id of the infoPageWebDTO to save.
     * @param infoPageWebDTO the infoPageWebDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoPageWebDTO,
     * or with status {@code 400 (Bad Request)} if the infoPageWebDTO is not valid,
     * or with status {@code 404 (Not Found)} if the infoPageWebDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the infoPageWebDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<InfoPageWebDTO> partialUpdateInfoPageWeb(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InfoPageWebDTO infoPageWebDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InfoPageWeb partially : {}, {}", id, infoPageWebDTO);
        if (infoPageWebDTO.getId() == null
                || !Objects.equals(id, infoPageWebDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!infoPageWebService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<InfoPageWebDTO> result = infoPageWebService.partialUpdate(infoPageWebDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /info-page-web} : get all the infoPageWebs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoPageWebs in body.
     */
    @GetMapping()
    public List<InfoPageWebDTO> getAllInfoPageWebs() {
        log.debug("REST request to get all InfoPageWebs");
        return infoPageWebService.findAll();
    }
    
    /**
     * {@code GET  /info-page-web/nos-actions} : get all the infoPageWebs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoPageWebs in body.
     */
    @GetMapping("/nos-actions")
    public List<InfoPageWebDTO> getAllInfoOfNosActions() {
        log.debug("REST request to get all InfoPageWebs of nos actions");
        return infoPageWebService.findAllByNosAction();
    }
    
    /**
     * {@code GET  /info-page-web/nos-actions} : get all the infoPageWebs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoPageWebs in body.
     */
    @GetMapping("/nos-actions/displayable")
    public List<InfoPageWebDTO> getAllInfoOfNosActionsDisplayable() {
        log.debug("REST request to get all InfoPageWebs of nos actions");
        return infoPageWebService.findAllByNosActionsDisplayable();
    }

    /**
     * {@code GET  /info-page-web/:id} : get the "id" infoPageWeb.
     *
     * @param id the id of the infoPageWebDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoPageWebDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InfoPageWebDTO> getInfoPageWeb(@PathVariable Long id) {
        log.debug("REST request to get InfoPageWeb : {}", id);
        Optional<InfoPageWebDTO> infoPageWebDTO = infoPageWebService.findOne(id);
        return HttpUtils.wrapOrNotFound(infoPageWebDTO);
    }

    /**
     * {@code DELETE  /info-page-web/:id} : delete the "id" infoPageWeb.
     *
     * @param id the id of the infoPageWebDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInfoPageWeb(@PathVariable Long id) {
        log.debug("REST request to delete InfoPageWeb : {}", id);
        Optional<InfoPageWebDTO> opt = infoPageWebService.findOne(id);
        if (opt.isPresent()) {
            InfoPageWebDTO infoPageWeb = opt.get();
            if (infoPageWeb.getUrlMedia() != null) {
                this.ftpService.deleteFromServer(infoPageWeb.getUrlMedia());
            }
        }
        infoPageWebService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    /**
     * {@code GET  /info-page-web/pageWeb?pageWeb=2 = Valeurs} : get all the infoPageWebs by pageWeb .
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoPageWebs in body.
     */
    @GetMapping("/pageWeb")
    public List<InfoPageWebDTO> getAllInfoByPageWeb(@RequestParam(value = "pageWeb") String pageWeb) {
        log.debug("REST request to get all InfoPageWebs by " + pageWeb);
        return infoPageWebService.findAllByPageWeb(pageWeb);
    }

    /**
     * Get all images publish of "Dernier Evènement"
     * @return List of all ImageSite  publish of "Dernier Evènement"
     */
    @GetMapping("/displayable/lastEvent")
    public List<InfoPageWebDTO> getAllImagesPublishLastEvent(){
        log.debug("Request to get all images");
        return infoPageWebService.findDisplayableLastEvent();
    }

    /**
     * Get all images publish of "FDLV en Mots"
     * @return List of all ImageSite  publish of "FDLV en Mots"
     */
    @GetMapping("/displayable/fdlvEnMots")
    public List<InfoPageWebDTO> getAllImagesPublishFDLVEnMots(){
        log.debug("Request to get all images");
        return infoPageWebService.findDisplayableFDLVEnMots();
    }

    /**
     * Get all images publish of "Devise"
     * @return List of all ImageSite  publish of "Devise"
     */
    @GetMapping("/displayable/devise")
    public List<InfoPageWebDTO> getAllImagesPublishDevise(){
        log.debug("Request to get all images");
        return infoPageWebService.findDisplayableDevise();
    }
}
