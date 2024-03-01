
package com.fdlv.gmd.api.web.rest;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.fdlv.gmd.api.dto.ThemeDTO;
import com.fdlv.gmd.api.dto.fdlv.EventFDLVDTO;
import com.fdlv.gmd.api.dto.fdlv.EventPostFDLVDTO;
import com.fdlv.gmd.api.service.ThemeService;
import com.fdlv.gmd.api.service.fdlv.EventFDLVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.utils.HttpUtils;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.Event}.
 */
@RestController
@RequestMapping("/api/event")
public class EventFDLVResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    private static final String ENTITY_NAME = "eventFDLV";

    private final EventFDLVService eventService;

    private final ThemeService themeService;


    public EventFDLVResource(EventFDLVService eventService, ThemeService themeService) {
        this.eventService = eventService;

        this.themeService = themeService;
    }





    /**
     * {@code POST  /event/add} : Create a new event.
     *
     * @param eventDTO the eventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventDTO, or with status {@code 400 (Bad Request)} if the event has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createEventFDLV(@Valid @RequestBody EventPostFDLVDTO eventDTO) throws URISyntaxException {
        log.debug("REST request to save Event : {}", eventDTO);
        Optional<ThemeDTO> optThemeId = themeService.findOne(eventDTO.getEvent().getTheme_id());
        if(optThemeId.isPresent()){
            ThemeDTO themeDTO = optThemeId.get();
            eventDTO.getEvent().setTheme(themeDTO);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }


       EventFDLVDTO res = eventService.save(eventDTO);

        HashMap<String,String> result = new HashMap<>();
        result.put("message","L'évenement a bien été ajouté !");
        return ResponseEntity.status(201).body(result);
    }


    /**
     * {@code GET  /event} : get all the events.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of events in body.
     */
    @GetMapping()
    public List<EventFDLVDTO> getAllEvents() {
        log.debug("REST request to get all Events");
        return eventService.findAll();
    }

    /**
     * {@code GET  /event/:id} : get the "id" event.
     *
     * @param id the id of the eventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventFDLVDTO> getEvent(@PathVariable Long id) {
        log.debug("REST request to get Event : {}", id);
        Optional<EventFDLVDTO> eventDTO = eventService.findOne(id);
        return HttpUtils.wrapOrNotFound(eventDTO);
    }


}
