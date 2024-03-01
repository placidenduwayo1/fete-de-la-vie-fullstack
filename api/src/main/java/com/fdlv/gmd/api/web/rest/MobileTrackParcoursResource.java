package com.fdlv.gmd.api.web.rest;

import com.fdlv.gmd.api.domain.mobile.MobileTrackParcours;
import com.fdlv.gmd.api.dto.mobile.MobileTrackParcoursDTO;
import com.fdlv.gmd.api.dto.mobile.MobileTrackParcoursFlatDTO;
import com.fdlv.gmd.api.mapper.mobile.MobileTrackParcoursMapper;
import com.fdlv.gmd.api.service.mobile.MobileTrackParcousService;
import io.undertow.util.BadRequestException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mobile-track-parcours")
public class MobileTrackParcoursResource {

    private final Logger log = LoggerFactory.getLogger(MobileTrackParcoursResource.class);
    private final MobileTrackParcousService mobileTrackParcoursService;
    private final MobileTrackParcoursMapper mobileTrackParcoursMapper;
    //    private final MobileStatParcoursService mobileStatParcoursService;
//    private final EventService eventService;
//    private final StageService stageService;
//    private final QuizzService quizzService;
//    private final QuestionService questionService;

    public MobileTrackParcoursResource(MobileTrackParcousService mobileTrackParcoursService,
                                       MobileTrackParcoursMapper mobileTrackParcoursMapper
//                                       MobileStatParcoursService mobileStatParcoursService,
//                                       EventService eventService,
//                                       StageService stageService,
//                                       QuizzService quizzService,
//                                       QuestionService questionService
    ) {
        this.mobileTrackParcoursService = mobileTrackParcoursService;
        this.mobileTrackParcoursMapper = mobileTrackParcoursMapper;
//        this.mobileStatParcoursService = mobileStatParcoursService;
//        this.eventService = eventService;
//        this.stageService = stageService;
//        this.quizzService = quizzService;
//        this.questionService = questionService;
    }


    /*@PostMapping()
    public ResponseEntity<MobileTrackParcoursDTO> createMobileTrackParcours(
            @Valid @RequestBody MobileTrackParcoursDTO mobileTrackParcoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MobileTrackParcours : {}", mobileTrackParcoursDTO);
        if (mobileTrackParcoursDTO.getId() != null)
            throw new CreateIdException(ENTITY_NAME);

        if (!mobileStatParcoursService.existsById(mobileTrackParcoursDTO.getMobileStatParcours().getId()))
            throw new InvalidIdException("mobileStatParcours");

        if (!eventService.existsById(mobileTrackParcoursDTO.getEvent().getId()))
            throw new InvalidIdException("event");

        if (!stageService.existsById(mobileTrackParcoursDTO.getStage().getId()))
            throw new InvalidIdException("stage");

        if (mobileTrackParcoursDTO.getQuizz().getId() != null && !quizzService.existsById(mobileTrackParcoursDTO.getQuizz().getId()))
            throw new InvalidIdException("quizz");

        if (mobileTrackParcoursDTO.getQuestion().getId() != null && !questionService.existsById(mobileTrackParcoursDTO.getQuestion().getId()))
            throw new InvalidIdException("question");

        mobileTrackParcoursDTO.setDateAction(LocalDateTime.now());
        MobileTrackParcoursDTO result = mobileTrackParcoursService.save(mobileTrackParcoursDTO);
        return ResponseEntity.ok().body(result);
    }*/

    @GetMapping(value = "/csv", produces = "text/csv")
    public void getAllMobileTrackParcours(HttpServletResponse servletResponse) throws IOException {
        log.debug("Rest request to get mobile track parcours as csv file");
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"mobiletrackparcours.csv\"");
        mobileTrackParcoursService.findAllCSV(servletResponse.getWriter());
    }

    @GetMapping
    public ResponseEntity<List<MobileTrackParcoursDTO>> getAllMobileTrackParcours() {
        List<MobileTrackParcours> mobileTrackParcoursList = mobileTrackParcoursService.getAllMobileTrackParcours();
        List<MobileTrackParcoursDTO> mobileTrackParcoursDTOList = mobileTrackParcoursMapper.toDtoList(mobileTrackParcoursList);
        return ResponseEntity.ok(mobileTrackParcoursDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobileTrackParcoursDTO> getMobileTrackParcoursById(@PathVariable Long id) {
        MobileTrackParcours mobileTrackParcours = mobileTrackParcoursService.getMobileTrackParcoursById(id);
        if (mobileTrackParcours != null) {
            MobileTrackParcoursDTO mobileTrackParcoursDTO = mobileTrackParcoursMapper.toDto(mobileTrackParcours);
            return ResponseEntity.ok(mobileTrackParcoursDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/full/{id}")
    public ResponseEntity<MobileTrackParcoursDTO> updateMobileTrackParcours(@PathVariable Long id, @RequestBody MobileTrackParcoursDTO mobileTrackParcoursDTO) {
        if (mobileTrackParcoursService.existsMobileTrackParcours(id)) {
            mobileTrackParcoursDTO.setId(id);
            MobileTrackParcours mobileTrackParcours = mobileTrackParcoursMapper.toEntity(mobileTrackParcoursDTO);
            MobileTrackParcours updatedMobileTrackParcours = mobileTrackParcoursService.updateMobileTrackParcours(mobileTrackParcours);
            MobileTrackParcoursDTO updatedMobileTrackParcoursDTO = mobileTrackParcoursMapper.toDto(updatedMobileTrackParcours);
            return ResponseEntity.ok(updatedMobileTrackParcoursDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMobileTrackParcours(@PathVariable Long id,
                                                          @RequestBody MobileTrackParcoursFlatDTO mobileTrackParcoursDTO) throws NotFoundException {
        log.debug("REST request to update MobileTrackParcours : {}", mobileTrackParcoursDTO);
        if (!mobileTrackParcoursService.existsById(id)) {
            throw new NotFoundException("MobileTrackParcours not found");
        }
        mobileTrackParcoursDTO.setId(id);
        Long result = mobileTrackParcoursService.save(mobileTrackParcoursDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMobileTrackParcours(@PathVariable Long id) {
        if (!mobileTrackParcoursService.existsMobileTrackParcours(id)) {
            return ResponseEntity.notFound().build();
        }
        mobileTrackParcoursService.deleteMobileTrackParcours(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("full")
    public ResponseEntity<MobileTrackParcoursDTO> createMobileTrackParcours(@RequestBody MobileTrackParcoursDTO mobileTrackParcoursDTO) {
        MobileTrackParcours mobileTrackParcours = mobileTrackParcoursMapper.toEntity(mobileTrackParcoursDTO);
        MobileTrackParcours createdMobileTrackParcours = mobileTrackParcoursService.createMobileTrackParcours(mobileTrackParcours);
        MobileTrackParcoursDTO createdMobileTrackParcoursDTO = mobileTrackParcoursMapper.toDto(createdMobileTrackParcours);
        return ResponseEntity.ok(createdMobileTrackParcoursDTO);
    }

    @PostMapping
    public ResponseEntity<Long> createMobileTrackParcours(@RequestBody MobileTrackParcoursFlatDTO mobileTrackParcoursDTO) throws URISyntaxException, BadRequestException, NotFoundException {
        log.debug("REST request to create MobileTrackParcours : {}", mobileTrackParcoursDTO);
        if (mobileTrackParcoursDTO.getId() != null) {
            throw new BadRequestException("A new mobileTrackParcours cannot already have an ID");
        }
        Long result = mobileTrackParcoursService.save(mobileTrackParcoursDTO);
        return ResponseEntity.created(new URI("/api/mobile-track-parcours/" + result))
                .body(result);
    }

    /**
     * Fonction permettant d'indiquer que le defi de la MobileTrackParcours {id} a été accepté
     * @param id
     * @return
     * @throws NotFoundException
     */
    @PutMapping("/share/{id}")
    public ResponseEntity<Void> shareMobileTrackParcours(
            @PathVariable Long id) throws NotFoundException {
        log.debug("REST request to update MobileTrackParcours when defi {} is share", id);

        // Perform any necessary validation or checks before proceeding

        mobileTrackParcoursService.share(id);

        return ResponseEntity.noContent().build();
    }


    /**
     * Fonction permettant d'indiquer que le defi de la MobileTrackParcours {id} a été accepté
     * @param id
     * @return
     * @throws NotFoundException
     */
    @PutMapping("/defi/{id}")
    public ResponseEntity<Void> acceptDefiMobileTrackParcours(
            @PathVariable Long id) throws NotFoundException {
        log.debug("REST request to update MobileTrackParcours when defi {} is accept", id);

        // Perform any necessary validation or checks before proceeding

        mobileTrackParcoursService.acceptDefi(id);

        return ResponseEntity.noContent().build();
    }


    /**
     * Fonction permettant d'ajouter le quizz {idQuizz} lié à l'étape de la MobileTrackParcours {id} correspondante
     * @param id
     * @param idQuizz
     * @return
     * @throws NotFoundException
     */
    @PutMapping("/addQuizz/{id}")
    public ResponseEntity<Void> addQuizzMobileTrackParcours(
            @PathVariable Long id,@RequestBody Long idQuizz) throws NotFoundException {
        log.debug("REST request to update MobileTrackParcours {} to add a quizz if it exist", id);

        // Perform any necessary validation or checks before proceeding

        mobileTrackParcoursService.addQuizz(id,idQuizz);

        return ResponseEntity.noContent().build();
    }

}
