package com.fdlv.gmd.api.service.fdlv;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fdlv.gmd.api.dto.fdlv.PartenaireModuleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.fdlv.Partenaire;
import com.fdlv.gmd.api.dto.fdlv.PartenaireDTO;
import com.fdlv.gmd.api.mapper.fdlv.PartenaireMapper;
import com.fdlv.gmd.api.repository.fdlv.PartenaireRepository;

/**
 * Service Implementation for managing {@link Partenaire}.
 */
@Service
@Transactional
public class PartenaireServicImpl implements PartenaireService {

    private final Logger log =LoggerFactory.getLogger(PartenaireServicImpl.class);
    private final PartenaireRepository partenaireRepository;
    private final PartenaireMapper partenaireMapper;
    

    public PartenaireServicImpl(PartenaireRepository partenaireRepository, PartenaireMapper partenaireMapper) {
        this.partenaireRepository = partenaireRepository;
        this.partenaireMapper = partenaireMapper;
    }

    @Override
    public PartenaireDTO save(PartenaireDTO partenaireDTO) {
        log.debug("Request to save Partenaire : {}",partenaireDTO);
        Partenaire partenaire= partenaireMapper.toEntity(partenaireDTO);
        partenaire=partenaireRepository.save(partenaire);
        return partenaireMapper.toDto(partenaire);
    }

    @Override
    public Optional<PartenaireDTO> partialUpdate(PartenaireDTO partenaireDTO) {
       log.debug("Request to partially update Partenaire : {}",partenaireDTO);
       return partenaireRepository
                .findById(partenaireDTO.getId())
                .map(
                    existingPartenaire -> {
                    partenaireMapper.partialUpdate(existingPartenaire, partenaireDTO);
                    return existingPartenaire;
                }
                )
                .map(partenaireRepository::save)
                .map(partenaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartenaireDTO> findAll() {
        log.debug("Request to get all partenaires");
        return partenaireRepository
                .findAll()
                .stream()
                .map(partenaireMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PartenaireDTO> findOne(Long id) {
       log.debug("Request to get a partenaire : {}",id);
       return partenaireRepository
                .findById(id)
                .map(partenaireMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete partenaire : {}",id);
        partenaireRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        log.debug("Request to check if the partenaire with id exists : {}",id);
        return partenaireRepository.existsById(id)   ;
    }

    @Override
    public List<PartenaireModuleDTO> findWebsitePartenaires() {
        log.debug("get the partenaires for the website");
        List<PartenaireDTO> listP = partenaireMapper.toDto(partenaireRepository.findPartenaireWebsite());
        // the return if you want to get PartenaireDTO (for future code re-factorisation)
        //return partenaireMapper.toDto(partenaireRepository.findPartenaireWebsite());
        return  listP.stream().map(partenaireDTO ->  new PartenaireModuleDTO(
                partenaireDTO.getId(),
                partenaireDTO.getNum_ordre(),
                partenaireDTO.getImage(),
                partenaireDTO.getPriorite().getDbValue(),
                partenaireDTO.getModule().getDbValue())).collect(Collectors.toList());
    }

    @Override
    public List<PartenaireModuleDTO> findMobilePartenaires() {
        log.debug("get the partenaires for the website");
        // the return if you want to get PartenaireDTO (for future code re-factorisation)
        //return partenaireMapper.toDto(partenaireRepository.findPartenaireMobile());
        List<PartenaireDTO> listP = partenaireMapper.toDto(partenaireRepository.findPartenaireMobile());


        //convert the PartenaireDTO into PartenraireModuleDTO for matching props name with flutter and website
        return  listP.stream().map(partenaireDTO ->  new PartenaireModuleDTO(
                partenaireDTO.getId(),
                partenaireDTO.getNum_ordre(),
                partenaireDTO.getImage(),
                partenaireDTO.getPriorite().getDbValue(),
                partenaireDTO.getModule().getDbValue())).collect(Collectors.toList());
    }


}
