package com.fdlv.gmd.api.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.Answer;
import com.fdlv.gmd.api.dto.AnswerDTO;
import com.fdlv.gmd.api.dto.AnswerDetailsDTO;
import com.fdlv.gmd.api.dto.QuestionDTO;
import com.fdlv.gmd.api.dto.QuestionDetailsDTO;
import com.fdlv.gmd.api.mapper.AnswerMapper;
import com.fdlv.gmd.api.repository.AnswerRepository;
import com.fdlv.gmd.api.service.AnswerService;

/**
 * Service Implementation for managing {@link Answer}.
 */
@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);

    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    @Override
    public AnswerDTO save(AnswerDTO answerDTO) {
        log.debug("Request to save Answer : {}", answerDTO);
        Answer answer = answerMapper.toEntity(answerDTO);
        answer = answerRepository.save(answer);
        return answerMapper.toDto(answer);
    }

    @Override
    public Optional<AnswerDTO> partialUpdate(AnswerDTO answerDTO) {
        log.debug("Request to partially update Answer : {}", answerDTO);

        return answerRepository
            .findById(answerDTO.getId())
            .map(
                existingAnswer -> {
                    answerMapper.partialUpdate(existingAnswer, answerDTO);
                    return existingAnswer;
                }
            )
            .map(answerRepository::save)
            .map(answerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerDTO> findAll() {
        log.debug("Request to get all Answers");
        return answerRepository.findAll().stream().map(answerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerDTO> findOne(Long id) {
        log.debug("Request to get Answer : {}", id);
        return answerRepository.findById(id).map(answerMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return answerRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Answer : {}", id);
        answerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerDTO> findQuestionAnswers(QuestionDetailsDTO questionDTO) {
        // TODO Auto-generated method stub
        log.debug("Request to get list of answers of a");
        return answerRepository.findAll().stream().map(answerMapper::toDto).filter(x->x.getQuestion().getId()==questionDTO.getId()).collect(Collectors.toCollection(LinkedList::new));
    }


    /*
     * Method to clean up answers discared when updating a question
     * answers: list of answers fetched from repo
     * question: new updated question object 
     */

    @Override
    public List<AnswerDTO> cleanQuestionAnswers(List<AnswerDTO> answers,QuestionDetailsDTO question) {
        // TODO Auto-generated method stub
        List<AnswerDTO> answerstobedeleted=new ArrayList<>(answers);
        for (AnswerDetailsDTO  answer : question.getAnswers()) {
            answers.forEach(x->{
                if(x.getId()==answer.getId())
                answerstobedeleted.remove(x);
            });
            
        }
        for (AnswerDTO answerDTO : answerstobedeleted) {
            delete(answerDTO.getId());
        }
        return answerstobedeleted;

    }
}
