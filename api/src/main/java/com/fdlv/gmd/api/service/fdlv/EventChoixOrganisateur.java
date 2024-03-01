package com.fdlv.gmd.api.service.fdlv;

import com.fdlv.gmd.api.domain.Event;
import com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur;
import com.fdlv.gmd.api.dto.EventDTO;
import com.fdlv.gmd.api.mapper.EventMapper;
import com.fdlv.gmd.api.repository.EventRepository;
import com.fdlv.gmd.api.repository.fdlv.ChoixOrganisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class EventChoixOrganisateur {
    private final Logger log = LoggerFactory.getLogger(EventChoixOrganisateur.class);
    private final EventRepository eventRepository;
    private final ChoixOrganisateurRepository choixOrganisateurRepository;
    private final EventMapper eventMapper;

    public EventChoixOrganisateur(EventRepository eventRepository,
            ChoixOrganisateurRepository choixOrganisateurRepository, EventMapper eventMapper) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.choixOrganisateurRepository = choixOrganisateurRepository;
    }

    public EventDTO partialUpdate(EventDTO eventDTO) {
        Event event = eventRepository
                .findById(eventDTO.getId())
                .map(existingEvent -> {
                    eventMapper.partialUpdate(existingEvent, eventDTO);
                    return existingEvent;
                })
                .map(eventRepository::save).orElseThrow(RuntimeException::new);
        if (event.isValidatedEvent()) {
               ChoixOrganisateur choixOrganisateur = choixOrganisateurRepository
                    .findById(event.getEvtFcoFusId()).orElseThrow(RuntimeException::new);

            choixOrganisateur.setEventArchStatus(true);
            choixOrganisateur.setDateArchEvent(LocalDate.now());
            choixOrganisateurRepository.save(choixOrganisateur);
        }
        return eventMapper.toDto(event);

    }
}
