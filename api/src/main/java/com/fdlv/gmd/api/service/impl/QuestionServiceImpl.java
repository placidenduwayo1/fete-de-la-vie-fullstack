package com.fdlv.gmd.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fdlv.gmd.api.dto.fdlv.FDLVQuestionDTO;
import com.fdlv.gmd.api.mapper.fdlv.FDLVQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.Question;
import com.fdlv.gmd.api.dto.QuestionDTO;
import com.fdlv.gmd.api.dto.QuestionDetailsDTO;
import com.fdlv.gmd.api.mapper.QuestionDetailsMapper;
import com.fdlv.gmd.api.mapper.QuestionMapper;
import com.fdlv.gmd.api.repository.QuestionRepository;
import com.fdlv.gmd.api.service.QuestionService;

/**
 * Service Implementation for managing {@link Question}.
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;
    private final QuestionDetailsMapper questionDetailsMapper;

    private  final FDLVQuestionMapper questionByQuizzMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper,QuestionDetailsMapper questionDetailsMapper, FDLVQuestionMapper questionByQuizzMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.questionDetailsMapper=questionDetailsMapper;
        this.questionByQuizzMapper = questionByQuizzMapper;

    }

    @Override
    public QuestionDetailsDTO save(QuestionDetailsDTO questionDTO) {
        log.debug("Request to save Question : {}", questionDTO);
        Question question = questionDetailsMapper.toEntity(questionDTO);
        question = questionRepository.save(question);
        return questionDetailsMapper.toDto(question);
    }

    @Override
    public Optional<QuestionDTO> partialUpdate(QuestionDTO questionDTO) {
        log.debug("Request to partially update Question : {}", questionDTO);

        return questionRepository
            .findById(questionDTO.getId())
            .map(
                existingQuestion -> {
                    questionMapper.partialUpdate(existingQuestion, questionDTO);
                    return existingQuestion;
                }
            )
            .map(questionRepository::save)
            .map(questionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDTO> findAll() {
        log.debug("Request to get all Questions");
        return questionRepository.findAll().stream().map(questionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionDTO> findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findById(id).map(questionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return questionRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.deleteById(id);
    }


}
