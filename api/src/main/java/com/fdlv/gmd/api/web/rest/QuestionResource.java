package com.fdlv.gmd.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

import com.fdlv.gmd.api.dto.QuestionDTO;
import com.fdlv.gmd.api.dto.QuestionDetailsDTO;
import com.fdlv.gmd.api.service.AnswerService;
import com.fdlv.gmd.api.service.QuestionService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 *  REST controller for managing  .
 *
 */ /**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.Question}.
 */
@RestController
@RequestMapping("/api/questions")
public class QuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionResource.class);

    private static final String ENTITY_NAME = "question";

    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionResource(QuestionService questionService,AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    /**
     * {@code POST  /questions} : Create a new question.
     *
     * @param questionDTO the questionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionDTO, or with status {@code 400 (Bad Request)} if the question has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<QuestionDetailsDTO> createQuestion(@Valid @RequestBody QuestionDetailsDTO questionDTO) throws URISyntaxException {
        log.debug("REST request to save Question : {}", questionDTO);
        if (questionDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        QuestionDetailsDTO result = questionService.save(questionDTO);
        return ResponseEntity
            .created(new URI("/api/questions/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /questions/:id} : Updates an existing question.
     *
     * @param id the id of the questionDTO to save.
     * @param questionDTO the questionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionDTO,
     * or with status {@code 400 (Bad Request)} if the questionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<QuestionDetailsDTO> updateQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QuestionDetailsDTO questionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Question : {}, {}", id, questionDTO);
        if (questionDTO.getId() == null
                || !Objects.equals(id, questionDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!questionService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        answerService.cleanQuestionAnswers(answerService.findQuestionAnswers(questionDTO), questionDTO);
        QuestionDetailsDTO result = questionService.save(questionDTO);

        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /questions/:id} : Partial updates given fields of an existing question, field will ignore if it is null
     *
     * @param id the id of the questionDTO to save.
     * @param questionDTO the questionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionDTO,
     * or with status {@code 400 (Bad Request)} if the questionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the questionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the questionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QuestionDTO> partialUpdateQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody QuestionDTO questionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Question partially : {}, {}", id, questionDTO);
        if (questionDTO.getId() == null
                || !Objects.equals(id, questionDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!questionService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<QuestionDTO> result = questionService.partialUpdate(questionDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /questions} : get all the questions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questions in body.
     */
    @GetMapping()
    public List<QuestionDTO> getAllQuestions() {
        log.debug("REST request to get all Questions");
        return questionService.findAll();
    }

    /**
     * {@code GET  /questions/:id} : get the "id" question.
     *
     * @param id the id of the questionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable Long id) {
        log.debug("REST request to get Question : {}", id);
        Optional<QuestionDTO> questionDTO = questionService.findOne(id);
        return HttpUtils.wrapOrNotFound(questionDTO);
    }

    /**
     * {@code DELETE  /questions/:id} : delete the "id" question.
     *
     * @param id the id of the questionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        log.debug("REST request to delete Question : {}", id);
        questionService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }


}
