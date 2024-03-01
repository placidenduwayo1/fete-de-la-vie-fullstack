package com.fdlv.gmd.api.web.rest;

import com.fdlv.gmd.api.domain.fdlv.ScenarioEtape;
import com.fdlv.gmd.api.dto.fdlv.EventVideoDTO;
import com.fdlv.gmd.api.dto.fdlv.ScenarioEtapeDTO;
import com.fdlv.gmd.api.service.fdlv.ScenarioEtapeService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/scenario-etape")
public class ScenarioEtapeResource {

    private final Logger log = LoggerFactory.getLogger(ScenarioEtapeResource.class);

    private static final String ENTITY_NAME = "scenarioEtape";

    private final ScenarioEtapeService scenarioEtapeService;

    public ScenarioEtapeResource(ScenarioEtapeService scenarioEtapeService) {
        this.scenarioEtapeService = scenarioEtapeService;
    }

    @GetMapping
    public ResponseEntity<List<ScenarioEtapeDTO>> getAllScenarioEtapes() {
        log.debug("REST request to get all ScenarioEtape");
        return ResponseEntity.ok(scenarioEtapeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScenarioEtapeDTO> getScenarioEtapeById(@PathVariable Long id) {
        log.debug("REST request to get ScenarioEtape : {}", id);
        Optional<ScenarioEtapeDTO> scenarioEtapeDTO = scenarioEtapeService.findById(id);
        return HttpUtils.wrapOrNotFound(scenarioEtapeDTO);
    }

    @PostMapping
    public ResponseEntity<?> createScenarioEtape(@Valid @RequestBody ScenarioEtapeDTO scenarioEtapeDTO) throws URISyntaxException {
        log.debug("REST request to save ScenarioEtape : {}", scenarioEtapeDTO);
        if (scenarioEtapeDTO.getFse_id() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        System.out.println(scenarioEtapeDTO+"\n\n\n\n");

        ScenarioEtapeDTO result = scenarioEtapeService.save(scenarioEtapeDTO);
        HashMap<String,String> res = new HashMap<>();
        res.put("message","Etape scénario ajouté à la liste");
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScenarioEtapeDTO> updateScenarioEtape(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody ScenarioEtapeDTO scenarioEtapeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventVideo : {}, {}", id, scenarioEtapeDTO);
        // TODO : RAF FDLVGMD-259 : lombok DTO
        if (scenarioEtapeDTO.getFse_id() == null
                || !Objects.equals(id, scenarioEtapeDTO.getFse_id())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!scenarioEtapeService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        ScenarioEtapeDTO result = scenarioEtapeService.save(scenarioEtapeDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/choix-organisateur/{id}")
    public ResponseEntity<?> getChoixOrganisateur(@PathVariable Long id) {
        log.debug("REST request to get scenarioEtape by organisateur");
        return ResponseEntity.ok(scenarioEtapeService.findByIdOrga(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScenarioEtapeById(@PathVariable Long id) {
        // TODO : RAF FDLVGMD-259
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/choix-organisateur/{id}")
    public ResponseEntity<?> deleteScenarioEtapeByChoixOrgaId(@PathVariable Long id) {

        scenarioEtapeService.deleteByOrga(id);
        HashMap<String,String> res = new HashMap<>();
        res.put("message","Etape scénario supprimé!");
        return ResponseEntity.ok(res);
    }
}
