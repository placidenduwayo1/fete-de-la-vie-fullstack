package com.fdlv.gmd.api.service.fdlv;

import com.fdlv.gmd.api.dto.fdlv.FDLVQuestionDTO;
import com.fdlv.gmd.api.mapper.fdlv.FDLVQuestionMapper;
import com.fdlv.gmd.api.repository.QuestionRepository;
import com.fdlv.gmd.api.service.impl.QuestionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class FDLVQuestionServiceImpl implements FDLVQuestionService{

    private final QuestionRepository questionRepository;

    private final FDLVQuestionMapper questionMapper;
    private final Logger log = LoggerFactory.getLogger(FDLVQuestionServiceImpl.class);

    public FDLVQuestionServiceImpl(QuestionRepository questionRepository, FDLVQuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FDLVQuestionDTO> findAll() {
        log.debug("Request to get all Questions");
        return questionRepository.findAll().stream().map(questionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FDLVQuestionDTO> findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findById(id).map(questionMapper::toDto);
    }


    @Override
    public List<FDLVQuestionDTO> getQuestionsByQuizzId(Long quizzId) {
        return questionRepository.findByQuizzId(quizzId).stream().map(questionMapper::toDto).collect(Collectors.toList());
    }
}
