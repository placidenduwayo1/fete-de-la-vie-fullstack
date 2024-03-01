package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.StandSecteur;
import com.fdlv.gmd.api.dto.forum.StandSecteurDTO;
import com.fdlv.gmd.api.mapper.forum.StandSecteurMapper;
import com.fdlv.gmd.api.repository.forum.StandSecteurRepository;
import com.fdlv.gmd.api.service.forum.StandSecteurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StandSecteurServiceImpl implements StandSecteurService {
    private final Logger log = LoggerFactory.getLogger(StandSecteurServiceImpl.class);

    private final StandSecteurRepository standSecteurRepository;
    private final StandSecteurMapper standSecteurMapper;

    public StandSecteurServiceImpl(StandSecteurRepository standSecteurRepository, StandSecteurMapper standSecteurMapper) {
        this.standSecteurRepository = standSecteurRepository;
        this.standSecteurMapper = standSecteurMapper;
    }

    @Override
    public StandSecteurDTO save(StandSecteurDTO standSecteurDTO) {
        log.debug("Request to save StandSecteur: {}", standSecteurDTO);
        StandSecteur standSecteur = standSecteurMapper.toEntity(standSecteurDTO);
        standSecteur = standSecteurRepository.save(standSecteur);
        return standSecteurMapper.toDto(standSecteur);
    }

    @Override
    public Optional<StandSecteurDTO> partialUpdate(StandSecteurDTO standSecteurDTO) {
        log.debug("Request to partially update StandSecteur: {}", standSecteurDTO);

        return standSecteurRepository.findById(standSecteurDTO.getId())
                .map(existingStandSecteur -> {
                    // Perform partial update
                    if (standSecteurDTO.getCodeSecteur() != null) {
                        existingStandSecteur.setCodeSecteur(standSecteurDTO.getCodeSecteur());
                    }
                    if (standSecteurDTO.getLibelle() != null) {
                        existingStandSecteur.setLibelle(standSecteurDTO.getLibelle());
                    }
                    return existingStandSecteur;
                })
                .map(standSecteurRepository::save)
                .map(standSecteurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StandSecteurDTO> findAll() {
        log.debug("Request to get all StandSecteurs");
        List<StandSecteur> standSecteurs = standSecteurRepository.findAll();
        return standSecteurMapper.toDtoList(standSecteurs);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StandSecteurDTO> findOne(Long id) {
        log.debug("Request to get StandSecteur : {}", id);
        return standSecteurRepository.findById(id).map(standSecteurMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return standSecteurRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StandSecteur : {}", id);
        standSecteurRepository.deleteById(id);
    }

}
