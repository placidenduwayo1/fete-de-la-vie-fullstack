package com.fdlv.gmd.api.service.fdlv;


import com.fdlv.gmd.api.dto.fdlv.ScenarioEtapeDTO;

import java.util.List;
import java.util.Optional;

public interface ScenarioEtapeService {

    ScenarioEtapeDTO save(ScenarioEtapeDTO scenarioEtapeDTO);

    Optional<ScenarioEtapeDTO> updateScenarioEtape(Long id, ScenarioEtapeDTO scenarioEtapeDTO);

    Optional<ScenarioEtapeDTO> findById(Long id);

    List<ScenarioEtapeDTO> findAll();

    void deleteScenarioEtape(Long id);

    boolean existsById(Long id);

    /**
     * get all the scenario etape from an organisation
     * @param idOrga id of the orga
     * @return all the scenarioEtape of the organisation
     */

    List<ScenarioEtapeDTO> findByIdOrga(Long idOrga);

    /**
     * delete all the scenario etape of an organisation
     * @param idOrga th id of the orga
     */
    void deleteByOrga(Long idOrga);

}
