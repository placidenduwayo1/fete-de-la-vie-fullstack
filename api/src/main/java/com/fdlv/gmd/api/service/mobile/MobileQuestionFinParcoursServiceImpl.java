package com.fdlv.gmd.api.service.mobile;

import com.fdlv.gmd.api.domain.mobile.MobileQuestionFinParcours;
import com.fdlv.gmd.api.dto.mobile.MobileQuestionFinParcoursDTO;
import com.fdlv.gmd.api.mapper.mobile.MobileQuestionFinParcoursMapper;
import com.fdlv.gmd.api.repository.mobile.MobileQuestionFinParcoursRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MobileQuestionFinParcoursServiceImpl implements MobileQuestionFinParcoursService {

    private final Logger log = LoggerFactory.getLogger(MobileQuestionFinParcoursServiceImpl.class);

    private final MobileQuestionFinParcoursRepository mobileQuestionFinParcoursRepository;

    private final MobileQuestionFinParcoursMapper mobileQuestionFinParcoursMapper;

    public MobileQuestionFinParcoursServiceImpl(MobileQuestionFinParcoursRepository mobileQuestionFinParcoursRepository, MobileQuestionFinParcoursMapper mobileQuestionFinParcoursMapper) {
        this.mobileQuestionFinParcoursRepository = mobileQuestionFinParcoursRepository;
        this.mobileQuestionFinParcoursMapper = mobileQuestionFinParcoursMapper;
    }

    @Override
    public MobileQuestionFinParcoursDTO save(MobileQuestionFinParcoursDTO mobileQuestionFinParcoursDTO) {
        log.debug("Request to save MobileQuestionFinParcours : {}", mobileQuestionFinParcoursDTO);
        MobileQuestionFinParcours mobileQuestionFinParcours = mobileQuestionFinParcoursMapper.toEntity(mobileQuestionFinParcoursDTO);
        mobileQuestionFinParcours = mobileQuestionFinParcoursRepository.save(mobileQuestionFinParcours);
        return mobileQuestionFinParcoursMapper.toDto(mobileQuestionFinParcours);
    }

    @Override
    public Optional<MobileQuestionFinParcoursDTO> partialUpdate(MobileQuestionFinParcoursDTO mobileQuestionFinParcoursDTO) {
        log.debug("Request to partially update mobileQuestionFinParcours : {}", mobileQuestionFinParcoursDTO);

        return mobileQuestionFinParcoursRepository
                .findById(mobileQuestionFinParcoursDTO.getId())
                .map(
                        existingMobileQuestionFinParcours -> {
                            mobileQuestionFinParcoursMapper.partialUpdate(existingMobileQuestionFinParcours, mobileQuestionFinParcoursDTO);
                            return existingMobileQuestionFinParcours;
                        }
                )
                .map(mobileQuestionFinParcoursRepository::save)
                .map(mobileQuestionFinParcoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MobileQuestionFinParcoursDTO> findAll() {
        log.debug("Request to get all MobileQuestionsFinParcours");
        return mobileQuestionFinParcoursRepository.findAll().stream().map(mobileQuestionFinParcoursMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MobileQuestionFinParcoursDTO> findOne(Long id) {
        log.debug("Request to get MobileQuestionFinParcours : {}", id);
        return mobileQuestionFinParcoursRepository.findById(id).map(mobileQuestionFinParcoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return mobileQuestionFinParcoursRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MobileQuestionFinParcours : {}", id);
        mobileQuestionFinParcoursRepository.deleteById(id);
    }
}
