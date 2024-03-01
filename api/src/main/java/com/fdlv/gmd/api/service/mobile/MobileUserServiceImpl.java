package com.fdlv.gmd.api.service.mobile;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fdlv.gmd.api.dto.mobile.MobileTypeUtilisationDTO;
import com.fdlv.gmd.api.mapper.mobile.MobileTypeUtilisationMapper;
import com.fdlv.gmd.api.repository.mobile.MobileTypeUtilisationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.mobile.MobileUser;
import com.fdlv.gmd.api.dto.mobile.MobileUserDTO;
import com.fdlv.gmd.api.mapper.mobile.MobileUserMapper;
import com.fdlv.gmd.api.repository.mobile.MobileUserRepository;

/**
 * Service Implementation for managing {@link MobileUser}.
 */
@Service
@Transactional
public class MobileUserServiceImpl implements MobileUserService {

    private final Logger log = LoggerFactory.getLogger(MobileUserServiceImpl.class);

    private final MobileUserRepository mobileUserRepository;

    private final MobileUserMapper mobileUserMapper;
    private final MobileTypeUtilisationMapper mobileTypeUtilisationMapper;
    private final MobileTypeUtilisationRepository mobileTypeUtilisationRepository;

    public MobileUserServiceImpl(MobileUserRepository mobileUserRepository, MobileUserMapper mobileUserMapper, MobileTypeUtilisationMapper mobileTypeUtilisationMapper, MobileTypeUtilisationRepository mobileTypeUtilisationRepository) {
        this.mobileUserRepository = mobileUserRepository;
        this.mobileUserMapper = mobileUserMapper;
        this.mobileTypeUtilisationMapper = mobileTypeUtilisationMapper;
        this.mobileTypeUtilisationRepository = mobileTypeUtilisationRepository;
    }

    @Override
    public MobileUserDTO save(MobileUserDTO mobileUserDTO) {
        log.debug("Request to save MobileUser : {}", mobileUserDTO);
        MobileUser mobileUser = mobileUserMapper.toEntity(mobileUserDTO);
        mobileUser = mobileUserRepository.save(mobileUser);
        return mobileUserMapper.toDto(mobileUser);
    }

    @Override
    public Optional<MobileUserDTO> partialUpdate(MobileUserDTO mobileUserDTO) {
        log.debug("Request to partially update mobileUser : {}", mobileUserDTO);

        return mobileUserRepository
            .findById(mobileUserDTO.getId())
            .map(
                existingMobileUser -> {
                    mobileUserMapper.partialUpdate(existingMobileUser, mobileUserDTO);
                    return existingMobileUser;
                }
            )
            .map(mobileUserRepository::save)
            .map(mobileUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MobileUserDTO> findAll() {
        log.debug("Request to get all MobileUsers");
        return mobileUserRepository.findAll().stream().map(mobileUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MobileUserDTO> findOne(Long id) {
        log.debug("Request to get MobileUser : {}", id);
        return mobileUserRepository.findById(id).map(mobileUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return mobileUserRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MobileUser : {}", id);
        mobileUserRepository.deleteById(id);
    }

    @Override
    public MobileTypeUtilisationDTO getTypeUse() {
        return mobileTypeUtilisationMapper.toDto(mobileTypeUtilisationRepository.findAll().get(0));
    }


    @Override
    public void closeAccount(Long id) {
        mobileUserRepository.closeAccount(id);
    }
}
