package com.fdlv.gmd.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.dto.QuizzDTO;
import com.fdlv.gmd.api.dto.QuizzDetailsDTO;
import com.fdlv.gmd.api.mapper.QuizzDetailsMapper;
import com.fdlv.gmd.api.mapper.QuizzMapper;
import com.fdlv.gmd.api.repository.QuizzRepository;
import com.fdlv.gmd.api.service.QuizzService;

/**
 * Service Implementation for managing {@link Quizz}.
 */
@Service
@Transactional
public class QuizzServiceImpl implements QuizzService {

    private final Logger log = LoggerFactory.getLogger(QuizzServiceImpl.class);

    private final QuizzRepository quizzRepository;

    private final QuizzMapper quizzMapper;

    private final QuizzDetailsMapper quizzDetailsMapper;

    public QuizzServiceImpl(QuizzRepository quizzRepository, QuizzMapper quizzMapper, QuizzDetailsMapper quizzDetailsMapper) {
        this.quizzRepository = quizzRepository;
        this.quizzMapper = quizzMapper;
        this.quizzDetailsMapper = quizzDetailsMapper;
    }

    @Override
    public QuizzDTO save(QuizzDTO quizzDTO) {
        log.debug("Request to save Quizz : {}", quizzDTO);
        Quizz quizz = quizzMapper.toEntity(quizzDTO);
        quizz = quizzRepository.save(quizz);
        return quizzMapper.toDto(quizz);
    }

    @Override
    public Optional<QuizzDTO> partialUpdate(QuizzDTO quizzDTO) {
        log.debug("Request to partially update Quizz : {}", quizzDTO);

        return quizzRepository
            .findById(quizzDTO.getId())
            .map(
                existingQuizz -> {
                    quizzMapper.partialUpdate(existingQuizz, quizzDTO);
                    return existingQuizz;
                }
            )
            .map(quizzRepository::save)
            .map(quizzMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizzDTO> findAll() {
        log.debug("Request to get all Quizzes");
        return quizzRepository.findAll().stream().map(quizzMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizzDTO> findAllQuizz() {
        log.debug("Request to get all Quizz");
        return quizzRepository.findAllQuizz().stream().map(quizzMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuizzDTO> findOne(Long id) {
        log.debug("Request to get Quizz : {}", id);
        return quizzRepository.findById(id).map(quizzMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Quizz> findOneEntity(Long id) {
        log.debug("Request to get Quizz : {}", id);
        return quizzRepository.findById(id);
    }



    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quizz : {}", id);
        quizzRepository.deleteById(id);
    }

    @Override
    public QuizzDTO save(QuizzDetailsDTO quizzDTO) {
        log.debug("Request to save Quizz : {}", quizzDTO);
        Quizz quizz = quizzDetailsMapper.toEntity(quizzDTO);
        quizz = quizzRepository.save(quizz);
        return quizzMapper.toDto(quizz);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return quizzRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuizzDetailsDTO> findOneDetails(Long id) {
        log.debug("Request to get Quizz : {}", id);
        return quizzRepository.findById(id).map(quizzDetailsMapper::toDto);
    }
    @Override
    public QuizzDTO findByLabel(String label) {
        return quizzMapper.toDto(quizzRepository.findByLabel(label));
    }

    @Override
    public List<QuizzDTO> findByVideoId(Long id) {
        return quizzRepository.findQuizzByVideoId(id).stream().map(quizzMapper::toDto).collect(Collectors.toList());
    }
}
