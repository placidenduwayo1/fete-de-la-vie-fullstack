package com.fdlv.gmd.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import com.fdlv.gmd.api.dto.fdlv.PartenaireModuleDTO;
import com.fdlv.gmd.api.dto.fdlv.PartenaireModulePostDTO;
import org.apache.commons.compress.utils.FileNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fdlv.gmd.api.domain.fdlv.Partenaire;
import com.fdlv.gmd.api.dto.StringWrapper;
import com.fdlv.gmd.api.dto.fdlv.PartenaireDTO;
import com.fdlv.gmd.api.service.FtpService;
import com.fdlv.gmd.api.service.fdlv.PartenaireService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link Partenaire}.
 */
@RestController
@RequestMapping("/api/partenaires")
public class PartenaireResource {

    private final Logger log = LoggerFactory.getLogger(PartenaireResource.class);

    private static final String ENTITY_NAME ="partenaire";

    private final PartenaireService partenaireService;
    private final FtpService ftpService;

    public PartenaireResource(PartenaireService partenaireService, FtpService ftpService) {
        this.partenaireService = partenaireService;
        this.ftpService = ftpService;
    }

    @PostMapping("/image")
    public ResponseEntity<StringWrapper> postImageOnServer(@RequestParam(value = "image") MultipartFile image) {
        String extention = FileNameUtils.getExtension(image.getOriginalFilename());
        StringWrapper result = new StringWrapper();
        result.setValue(ftpService.putLogoPartenaire( extention, image));
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /partenaires} : Create a new partenaire.
     *
     * @param partenaireDTO the partenaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partenaireDTO, or with status {@code 400 (Bad Request)} if the partenaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<PartenaireDTO> createParenaire(@Valid @RequestBody PartenaireDTO partenaireDTO) throws URISyntaxException {
        log.debug("REST request to save partenaire : {}", partenaireDTO);
        if (partenaireDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        PartenaireDTO result = partenaireService.save(partenaireDTO);
        return ResponseEntity
            .created(new URI("/api/partenaires/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /partenaires/:id} : Updates an existing partenaire.
     *
     * @param id the id of the partenaireDTO to save.
     * @param partenaireDTO the partenaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partenaireDTO,
     * or with status {@code 400 (Bad Request)} if the partenaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partenaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */


    @PutMapping("/{id}")
    public ResponseEntity<PartenaireDTO> updatePartenaire(
        @PathVariable(value = "id",required = false) final Long id,
        @Valid @RequestBody PartenaireDTO partenaireDTO
        ) throws URISyntaxException{
        log.debug("REST request to update Partenaire : {} , {}",id , partenaireDTO);
        
        if(partenaireDTO.getId()==null || !Objects.equals(id, partenaireDTO.getId())){
            throw new InvalidIdException(ENTITY_NAME);
        }

        if(!partenaireService.existsById(id)){
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        PartenaireDTO result = partenaireService.save(partenaireDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code GET  /partenaires} : get all the partenaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partenaires in body.
     */
    @GetMapping()
    public List<PartenaireDTO> getAllPartenaires() {
        log.debug("REST request to get all Partenaires");
        return partenaireService.findAll();
    }

    /**
     * {@code GET  /partenaires/:id} : get the "id" partenaire.
     *
     * @param id the id of the partenaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partenaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartenaireDTO> getPartenaire(@PathVariable Long id) {
        log.debug("REST request to get Partenaire : {}", id);
        Optional<PartenaireDTO> partenaireDTO = partenaireService.findOne(id);
        return HttpUtils.wrapOrNotFound(partenaireDTO);
    }


     /**
     * {@code DELETE  /partenaires:id} : delete the "id" partenaire.
     *
     * @param id the id of the partenaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartenaire(@PathVariable Long id) {
        log.debug("REST request to delete Partenaire : {}", id);
        partenaireService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    /**{@code POST  /partenaires/module/} : Check the post body then return the parameters for mobile or web.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partenaires in body.
     */
    @PostMapping(value = "/module", consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PartenaireModuleDTO>> getPartenairesModule(@RequestBody PartenaireModulePostDTO partenaireModulePostDTO){

        // check if the post contains the mobile value or the website value
        if(partenaireModulePostDTO.getPartner_web() == null){
            log.debug("post make by the flutter app");
            return ResponseEntity.ok(partenaireService.findMobilePartenaires());
        } else if (partenaireModulePostDTO.getPartner_mobile() == null) {
            System.out.println("post make by the website");
            return ResponseEntity.ok(partenaireService.findWebsitePartenaires());

        }
        return ResponseEntity.badRequest().build();
    }






}
