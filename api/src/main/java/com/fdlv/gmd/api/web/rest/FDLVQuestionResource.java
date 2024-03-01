package com.fdlv.gmd.api.web.rest;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.dto.fdlv.FDLVQuestionDTO;
import com.fdlv.gmd.api.service.fdlv.FDLVQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.utils.HttpUtils;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.Question}.
 */
@RestController
@RequestMapping("/api/question")
public class FDLVQuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionResource.class);

    private static final String ENTITY_NAME = "question";

    private final FDLVQuestionService questionService;


    public FDLVQuestionResource(FDLVQuestionService questionService) {
        this.questionService = questionService;

    }



    /**
     * {@code GET  /questions} : get all the questions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questions in body.
     */
    @GetMapping()
    public List<FDLVQuestionDTO> getAllQuestions() {
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
    public ResponseEntity<FDLVQuestionDTO> getQuestion(@PathVariable Long id) {
        log.debug("REST request to get Question : {}", id);
        Optional<FDLVQuestionDTO> questionDTO = questionService.findOne(id);
        return HttpUtils.wrapOrNotFound(questionDTO);
    }

    /**
     * GET /quizz/{id} : Get all questions for a given quizz ID.
     *
     * @param id the ID of the quizz for which to retrieve questions
     * @return the list of questions for the specified quizz
     */
    @GetMapping("/quizz/{id}")
    public List<FDLVQuestionDTO> getQuestionsByQuizzId(@PathVariable Long id){
        log.debug("REST request to get Questions by Quizz id : {}", id);
        return questionService.getQuestionsByQuizzId(id);
    }
}
