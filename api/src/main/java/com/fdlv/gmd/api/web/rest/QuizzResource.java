package com.fdlv.gmd.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.domain.fdlv.VideoQuizz;
import com.fdlv.gmd.api.service.VideoQuizzService;
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
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.dto.QuizzDTO;
import com.fdlv.gmd.api.dto.QuizzDetailsDTO;
import com.fdlv.gmd.api.service.QuizzService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.Quizz}.
 */
@RestController
@RequestMapping("/api")
public class QuizzResource {

    private final Logger log = LoggerFactory.getLogger(QuizzResource.class);

    private static final String ENTITY_NAME = "quizz";

    private final QuizzService quizzService;

    private final VideoQuizzService videoQuizzService;

    public QuizzResource(QuizzService quizzService,VideoQuizzService videoQuizzService) {
        this.quizzService = quizzService;
        this.videoQuizzService = videoQuizzService;
    }

    /**
     * {@code POST  /quizzes} : Create a new quizz.
     *
     * @param quizzDTO the quizzDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quizzDTO, or with status {@code 400 (Bad Request)} if the quizz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quizzes")
    public ResponseEntity<QuizzDTO> createQuizz(@Valid @RequestBody QuizzDTO quizzDTO) throws URISyntaxException {
        log.debug("REST request to save Quizz : {}", quizzDTO);
        if (quizzDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        QuizzDTO result = quizzService.save(quizzDTO);
        return ResponseEntity
            .created(new URI("/api/quizzes/" + result.getId()))
            .body(result);
    }

    @PostMapping("/quizz")
    public ResponseEntity<QuizzDTO> createQuizzDetails(@Valid @RequestBody QuizzDetailsDTO quizzDTO) throws URISyntaxException {
        log.debug("REST request to save Quizz : {}", quizzDTO);
        if (quizzDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        QuizzDTO result = quizzService.save(quizzDTO);
        return ResponseEntity
            .created(new URI("/api/quizz/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /quizzes/:id} : Updates an existing quizz.
     *
     * @param id the id of the quizzDTO to save.
     * @param quizzDTO the quizzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quizzDTO,
     * or with status {@code 400 (Bad Request)} if the quizzDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quizzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quizzes/{id}")
    public ResponseEntity<QuizzDTO> updateQuizz(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QuizzDTO quizzDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Quizz : {}, {}", id, quizzDTO);
        if (quizzDTO.getId() == null
                || !Objects.equals(id, quizzDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!quizzService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        QuizzDTO result = quizzService.save(quizzDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @PutMapping("/quizz/{id}")
    public ResponseEntity<QuizzDTO> updateQuizzDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QuizzDetailsDTO quizzDTO
    ) {
        log.debug("REST request to update Quizz : {}, {}", id, quizzDTO);
        if (quizzDTO.getId() == null
                || !Objects.equals(id, quizzDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!quizzService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        QuizzDTO result = quizzService.save(quizzDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /quizzes/:id} : Partial updates given fields of an existing quizz, field will ignore if it is null
     *
     * @param id the id of the quizzDTO to save.
     * @param quizzDTO the quizzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quizzDTO,
     * or with status {@code 400 (Bad Request)} if the quizzDTO is not valid,
     * or with status {@code 404 (Not Found)} if the quizzDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the quizzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quizzes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QuizzDTO> partialUpdateQuizz(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody QuizzDTO quizzDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Quizz partially : {}, {}", id, quizzDTO);
        if (quizzDTO.getId() == null
                || !Objects.equals(id, quizzDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!quizzService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<QuizzDTO> result = quizzService.partialUpdate(quizzDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /quizzes} : get all the quizzes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quizzes in body.
     */
    @GetMapping("/quizzes")
    public List<QuizzDTO> getAllQuizzes() {
        log.debug("REST request to get all Quizzes");
        return quizzService.findAll();
    }

    /**
     * {@code GET  /quizzes} : get all the quizzes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quizzes in body.
     */
    @GetMapping("/quizz")
    public List<QuizzDTO> getAllQuizz() {
        log.debug("REST request to get all Quizz");
        return quizzService.findAllQuizz();
    }


   /**
     * {@code GET  /quizzes/:id} : get the "id" quizz.
     *
     * @param id the id of the quizzDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quizzDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quizzes/{id}")
    public ResponseEntity<QuizzDTO> getQuizz(@PathVariable Long id) {
        log.debug("REST request to get Quizz : {}", id);
        Optional<QuizzDTO> quizzDTO = quizzService.findOne(id);
        return HttpUtils.wrapOrNotFound(quizzDTO);
    }





    @GetMapping("/quizzes/label/{label}")
    public QuizzDTO getQuizzByLabel(@PathVariable String label) {
        log.debug("REST request to get Quizz by Label : {}", label);
        QuizzDTO quizzDTO = quizzService.findByLabel(label);
        return quizzDTO;
    }


    /**
     * {@code DELETE  /quizzes/:id} : delete the "id" quizz.
     *
     * @param id the id of the quizzDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<Void> deleteQuizz(@PathVariable Long id) {
        log.debug("REST request to delete Quizz : {}", id);
        quizzService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    /**
     * {@code GET  /quizzes/:id} : get the "id" quizz.
     *
     * @param id the id of the quizzDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quizzDTO, or with status {@code 404 (Not Found)}.
     **/
    @GetMapping("/quizz/{id}")
    public ResponseEntity<QuizzDetailsDTO> getQuizzDetails(@PathVariable Long id) {
        log.debug("REST request to get Quizz : {}", id);
        Optional<QuizzDetailsDTO> quizzDTO = quizzService.findOneDetails(id);
        return HttpUtils.wrapOrNotFound(quizzDTO);
    }

    @GetMapping("/videoQuizz/{quizzId}")
    public List<VideoQuizz> getAllQuizzes(@PathVariable Long quizzId) {
        log.debug("REST request to get all Quizzes");
        return videoQuizzService.getByIdQuizz(quizzId);
    }
    /**
     * Return a List of quizz associate to a video
     * @param id the id of the video
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body a list of quizzDTO
     */
    @GetMapping("/quizz/video/{id}")
    public ResponseEntity<List<QuizzDTO>> findByVideoId(@PathVariable Long id){
        return ResponseEntity.ok(quizzService.findByVideoId(id));
    }
}
