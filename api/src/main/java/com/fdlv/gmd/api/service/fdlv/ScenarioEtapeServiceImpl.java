package com.fdlv.gmd.api.service.fdlv;

import com.fdlv.gmd.api.domain.fdlv.ScenarioEtape;
import com.fdlv.gmd.api.dto.fdlv.ScenarioEtapeDTO;
import com.fdlv.gmd.api.mapper.fdlv.ScenarioEtapeMapper;
import com.fdlv.gmd.api.mapper.fdlv.ScenarioEtapeMapperImpl;
import com.fdlv.gmd.api.repository.fdlv.ScenarioEtapeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScenarioEtapeServiceImpl implements ScenarioEtapeService {

    private final Logger log = LoggerFactory.getLogger(ScenarioEtapeServiceImpl.class);

    ScenarioEtapeRepository scenarioEtapeRepository;

    @Autowired
    ScenarioEtapeMapperImpl scenarioEtapeMapper;

    public ScenarioEtapeServiceImpl(ScenarioEtapeRepository scenarioEtapeRepository) {
        this.scenarioEtapeRepository = scenarioEtapeRepository;

    }

    @Override
    public ScenarioEtapeDTO save(ScenarioEtapeDTO scenarioEtapeDTO) {
        log.debug("Request to save EventVideo : {}", scenarioEtapeDTO);
        ScenarioEtape scenarioEtape = scenarioEtapeMapper.toEntity(scenarioEtapeDTO);
        System.out.println("\n\n\n\n");
        System.out.println(scenarioEtape+"\n\n\n\n\n");
        System.out.println((scenarioEtape.getQuizz()==null)+"\n\n\n");
        scenarioEtape = scenarioEtapeRepository.save(scenarioEtape);
        return scenarioEtapeMapper.toDto(scenarioEtape);
    }

    @Override
    public Optional<ScenarioEtapeDTO> updateScenarioEtape(Long id, ScenarioEtapeDTO scenarioEtapeDTO) {
        log.debug("Request to partially update eventVideo : {}", scenarioEtapeDTO);

        return scenarioEtapeRepository
                // TODO : RAF FDLVGMD-259 : lombok DTO
                .findById(scenarioEtapeDTO.getFse_id())
                .map(
                        existingScenarioEtapeDTO -> {
                            scenarioEtapeMapper.partialUpdate(existingScenarioEtapeDTO, scenarioEtapeDTO);
                            return existingScenarioEtapeDTO;
                        }
                )
                .map(scenarioEtapeRepository::save)
                .map(scenarioEtapeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScenarioEtapeDTO> findById(Long id) {
        log.debug("Request to get ScenarioEtape : {}", id);
        return scenarioEtapeRepository.findById(id).map(scenarioEtapeMapper::toDto);
    }

    @Override
    public List<ScenarioEtapeDTO> findAll() {
        log.debug("Request to get all ScenariosEtapes");
        return scenarioEtapeRepository.findAll().stream().map(scenarioEtapeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void deleteScenarioEtape(Long id) {
        log.debug("Request to delete ScenarioEtape : {}", id);
        scenarioEtapeRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return scenarioEtapeRepository.existsById(id);
    }

    @Override
    public List<ScenarioEtapeDTO> findByIdOrga(Long idOrga) {
        log.debug("Request to get ScenariosEtapes by organisateur");
        return scenarioEtapeRepository.findByOrga(idOrga).stream().map(scenarioEtapeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

    }

    @Override
    @Transactional
    public void deleteByOrga(Long idOrga) {
        log.debug("Request to delete ScenariosEtapes by organisateur");
        scenarioEtapeRepository.deleteByOrga(idOrga);
    }
}
