package com.fdlv.gmd.api.web.rest;

import com.fdlv.gmd.api.domain.mobile.EventStat;
import com.fdlv.gmd.api.domain.mobile.MobileStatParcours;
import com.fdlv.gmd.api.dto.mobile.MobileStatParcoursDTO;
import com.fdlv.gmd.api.dto.mobile.MobileStatParcoursFlatDTO;
import com.fdlv.gmd.api.mapper.mobile.MobileStatParcoursMapper;
import com.fdlv.gmd.api.service.EventService;
import com.fdlv.gmd.api.service.mobile.MobileStatParcoursService;
import com.fdlv.gmd.api.service.mobile.MobileUserService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mobile-stat-parcours")
public class MobileStatParcoursResource {

    private static final String ENTITY_NAME = "mobileStatParcours";
    private final Logger log = LoggerFactory.getLogger(MobileStatParcoursResource.class);
    private final MobileStatParcoursMapper mobileStatParcoursMapper;
    private final MobileStatParcoursService mobileStatParcoursService;
    private final MobileUserService mobileUserService;
    private final EventService eventService;


    public MobileStatParcoursResource(MobileStatParcoursService mobileStatParcoursService,
                                      MobileStatParcoursMapper mobileStatParcoursMapper,
                                      MobileUserService mobileUserService,
                                      EventService eventService) {
        this.mobileStatParcoursService = mobileStatParcoursService;
        this.mobileStatParcoursMapper = mobileStatParcoursMapper;
        this.mobileUserService = mobileUserService;
        this.eventService = eventService;
    }

    /**
     * {@code POST /mobile-stat-parcours } : Create a new mobilestatparcours entry.
     *
     * @param idUser  the id of the mobile user
     * @param idEvent the id of the event
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the id of the newly created mobilestatparcours,
     * or with status {@code 400 (Bad Request)} if the userID or eventID is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobilestatparcours couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/{idUser}/{idEvent}")
    public ResponseEntity<Long> createMobileStatParcours(
            @PathVariable(value = "idUser") final Long idUser,
            @PathVariable(value = "idEvent") final Long idEvent
    ) throws URISyntaxException {
        log.debug("REST request to create an new mobilestatparcours entry with userID and eventID : {} , {}", idUser, idEvent);
        if (!mobileUserService.existsById(idUser)) {
            throw new InvalidIdException("mobileUser");
        }
        if (!eventService.existsById(idEvent)) {
            throw new InvalidIdException("event");
        }
        Long result = mobileStatParcoursService.save(idUser, idEvent);

        return ResponseEntity
                .created(new URI("api/mobile-stat-parcours/" + result))
                .body(result);
    }


    /**
     * {@code PUT /mobile-stat-parcours/:id/:isShared} : Updates an existing mobilestatparcours
     *
     * @param id       the id of mobilestatparcours to update
     * @param isShared : boolean indicating if the event was shared or not
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobilestatparcoursDTO,
     * or with status {@code 400 (Bad Request)} if the mobilestatparcoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobilestatparcoursDTO couldn't be updated.
     */
    @PutMapping("/{id}/{isShared}")
    public ResponseEntity<MobileStatParcoursDTO> updateMobileStatParcours(
            @PathVariable(value = "id") final Long id,
            @PathVariable(value = "isShared") final boolean isShared
    ) {
        log.debug("REST request to update MobileStatParctous with id {}", id);
        if (!mobileStatParcoursService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }
        Optional<MobileStatParcoursDTO> result = mobileStatParcoursService.update(id, isShared);
        return HttpUtils.wrapOrNotFound(result);
    }

    @GetMapping("/event-stats")
    public List<EventStat> getEventStatsList() {
        return mobileStatParcoursService.findEventStatsList();

    }

    @GetMapping
    public ResponseEntity<List<MobileStatParcoursDTO>> getAllMobileStatParcours() {
        List<MobileStatParcours> mobileStatParcoursList = mobileStatParcoursService.getAllMobileStatParcours();
        List<MobileStatParcoursDTO> mobileStatParcoursDTOList = mobileStatParcoursMapper.toDtoList(mobileStatParcoursList);
        return ResponseEntity.ok(mobileStatParcoursDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobileStatParcoursDTO> getMobileStatParcoursById(@PathVariable Long id) {
        MobileStatParcours mobileStatParcours = mobileStatParcoursService.getMobileStatParcoursById(id);
        if (mobileStatParcours != null) {
            MobileStatParcoursDTO mobileStatParcoursDTO = mobileStatParcoursMapper.toDto(mobileStatParcours);
            return ResponseEntity.ok(mobileStatParcoursDTO);
        }
        return ResponseEntity.notFound().build();
    }

    // full create and not flat create
    @PostMapping("/full")
    public ResponseEntity<MobileStatParcoursDTO> createMobileStatParcours(
            @RequestBody MobileStatParcoursDTO mobileStatParcoursDTO) {
        MobileStatParcours mobileStatParcours = mobileStatParcoursMapper.toEntity(mobileStatParcoursDTO);
        MobileStatParcours createdMobileStatParcours = mobileStatParcoursService.createMobileStatParcours(mobileStatParcours);
        MobileStatParcoursDTO createdMobileStatParcoursDTO = mobileStatParcoursMapper.toDto(createdMobileStatParcours);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMobileStatParcoursDTO);
    }

    @PostMapping
    public ResponseEntity<Long> createMobileStatParcours(@RequestBody MobileStatParcoursFlatDTO dto)
            throws URISyntaxException {
        log.debug("REST request to create a new mobilestatparcours entry with flat DTO");

        // Perform any necessary validation or checks before proceeding
        System.out.println("\n\n\n\n"+dto.toString()+"\n\n\n\n");
        Long result = mobileStatParcoursService.save(dto);

        return ResponseEntity
                .created(new URI("api/mobile-stat-parcours/" + result))
                .body(result);
    }

    @PutMapping("/full/{id}")
    public ResponseEntity<MobileStatParcoursDTO> updateMobileStatParcours(
            @PathVariable Long id,
            @RequestBody MobileStatParcoursDTO mobileStatParcoursDTO) {
        if (mobileStatParcoursService.existsMobileStatParcours(id)) {
            MobileStatParcours mobileStatParcours = mobileStatParcoursMapper.toEntity(mobileStatParcoursDTO);
            mobileStatParcours.setId(id);
            MobileStatParcours updatedMobileStatParcours = mobileStatParcoursService.updateMobileStatParcours(mobileStatParcours);
            MobileStatParcoursDTO updatedMobileStatParcoursDTO = mobileStatParcoursMapper.toDto(updatedMobileStatParcours);
            return ResponseEntity.ok(updatedMobileStatParcoursDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMobileStatParcours(
            @PathVariable Long id, @RequestBody MobileStatParcoursFlatDTO dto) throws NotFoundException {
        log.debug("REST request to update MobileStatParcours with flat DTO: {}", id);

        // Perform any necessary validation or checks before proceeding

        mobileStatParcoursService.update(id, dto);

        return ResponseEntity.noContent().build();
    }

    /**
     * Fonction permettant d'indiquer que l'utilisateur a fini le parcours associé à la
     * MobileStatParcours{id} à la date {dateFin}
     * @param id
     * @param dateFin
     * @return
     * @throws NotFoundException
     */
    @PutMapping("/end/{id}")
    public ResponseEntity<Void> endMobileStatParcours(
            @PathVariable Long id,@RequestBody LocalDateTime dateFin) throws NotFoundException {
        log.debug("REST request to update MobileStatParcours when parcours {} is end", id);

        // Perform any necessary validation or checks before proceeding

        mobileStatParcoursService.end(id,dateFin);

        return ResponseEntity.noContent().build();
    }

    /**
     * Fonction permettant d'indiquer que le parcours lié à la MobileStatParcours{id}
     * a été partagé
     * @param id
     * @return
     * @throws NotFoundException
     */
    @PutMapping("/share/{id}")
    public ResponseEntity<Void> shareMobileStatParcours(
            @PathVariable Long id) throws NotFoundException {
        log.debug("REST request to update MobileStatParcours when parcours {} is share", id);

        // Perform any necessary validation or checks before proceeding

        mobileStatParcoursService.share(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMobileStatParcours(@PathVariable Long id) {
        if (mobileStatParcoursService.existsMobileStatParcours(id)) {
            mobileStatParcoursService.deleteMobileStatParcours(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
