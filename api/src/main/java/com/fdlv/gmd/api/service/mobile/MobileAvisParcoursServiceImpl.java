package com.fdlv.gmd.api.service.mobile;

import com.fdlv.gmd.api.domain.mobile.MobileAvisParcours;
import com.fdlv.gmd.api.dto.mobile.MobileAvisParcoursDTO;
import com.fdlv.gmd.api.mapper.mobile.MobileAvisParcoursMapper;
import com.fdlv.gmd.api.repository.mobile.MobileAvisParcoursRepository;
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
public class MobileAvisParcoursServiceImpl implements MobileAvisParcoursService{

    private final Logger log = LoggerFactory.getLogger(MobileAvisParcoursServiceImpl.class);

    private final MobileAvisParcoursRepository mobileAvisParcoursRepository;

    private final MobileAvisParcoursMapper mobileAvisParcoursMapper;

    public MobileAvisParcoursServiceImpl(MobileAvisParcoursRepository mobileAvisParcoursRepository, MobileAvisParcoursMapper mobileAvisParcoursMapper) {
        this.mobileAvisParcoursRepository = mobileAvisParcoursRepository;
        this.mobileAvisParcoursMapper = mobileAvisParcoursMapper;
    }

    @Override
    public MobileAvisParcoursDTO save(MobileAvisParcoursDTO mobileAvisParcoursDTO) {
        log.debug("Request to save MobileAvisParcours : {}", mobileAvisParcoursDTO);
        MobileAvisParcours mobileAvisParcours = mobileAvisParcoursMapper.toEntity(mobileAvisParcoursDTO);
        mobileAvisParcours = mobileAvisParcoursRepository.save(mobileAvisParcours);
        return mobileAvisParcoursMapper.toDto(mobileAvisParcours);
    }

    @Override
    public Optional<MobileAvisParcoursDTO> partialUpdate(MobileAvisParcoursDTO mobileAvisParcoursDTO) {
        log.debug("Request to partially update mobileAvisParcours : {}", mobileAvisParcoursDTO);

        return mobileAvisParcoursRepository
                .findById(mobileAvisParcoursDTO.getId())
                .map(
                        existingMobileAvisParcours -> {
                            mobileAvisParcoursMapper.partialUpdate(existingMobileAvisParcours, mobileAvisParcoursDTO);
                            return existingMobileAvisParcours;
                        }
                )
                .map(mobileAvisParcoursRepository::save)
                .map(mobileAvisParcoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MobileAvisParcoursDTO> findAll() {
        log.debug("Request to get all MobileAvisParcours");
        return mobileAvisParcoursRepository.findAll().stream().map(mobileAvisParcoursMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MobileAvisParcoursDTO> findOne(Long id) {
        log.debug("Request to get MobileAvisParcours : {}", id);
        return mobileAvisParcoursRepository.findById(id).map(mobileAvisParcoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return mobileAvisParcoursRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MobileAvisParcours : {}", id);
        mobileAvisParcoursRepository.deleteById(id);
    }
}
