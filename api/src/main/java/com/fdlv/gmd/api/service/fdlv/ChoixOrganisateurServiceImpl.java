package com.fdlv.gmd.api.service.fdlv;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.Event;
import com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur;
import com.fdlv.gmd.api.dto.ChoixOrganisateurDTO;
import com.fdlv.gmd.api.dto.EventDTO;
import com.fdlv.gmd.api.mapper.ChoixOrganisateurMapper;
import com.fdlv.gmd.api.mapper.EventMapper;
import com.fdlv.gmd.api.repository.EventRepository;
import com.fdlv.gmd.api.repository.fdlv.ChoixOrganisateurRepository;

/**
 * Service Implementation for managing {@link ChoixOrganisateur}.
 */
@Service
@Transactional
public class ChoixOrganisateurServiceImpl implements ChoixOrganisateurService {

	private final ChoixOrganisateurRepository choixOrganisateurRepository;
	private final ChoixOrganisateurMapper choixOrganisateurMapper;
	private final EventRepository eventRepository;
	private final EventMapper eventMapper;
	private final Logger log = LoggerFactory.getLogger(InfoPageWebServiceImpl.class);

	public ChoixOrganisateurServiceImpl(ChoixOrganisateurRepository choixOrganisateurRepository,
			ChoixOrganisateurMapper choixOrganisateurMapper, EventRepository eventRepository, EventMapper eventMapper) {
		this.choixOrganisateurRepository = choixOrganisateurRepository;
		this.choixOrganisateurMapper = choixOrganisateurMapper;
		this.eventRepository = eventRepository;
		this.eventMapper = eventMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByFdlvUserId(Long userId) {
		return choixOrganisateurRepository.existsById(userId);
	}

	public EventDTO partialUpdateChoixOrganisateur(EventDTO eventDTO) {
		final Event event = eventRepository
				.findById(eventDTO.getId())
				.map(existingEvent -> {
					eventMapper.partialUpdate(existingEvent, eventDTO);
					return existingEvent;
				})
				.map(eventRepository::save).orElseThrow(RuntimeException::new);

		if (event.isValidatedEvent() && (event.getEvtFcoId() != null)) {
			choixOrganisateurRepository.findById(event.getEvtFcoId()).map(choixOrganisateur -> {
				choixOrganisateur.setEventArchStatus(true);
				choixOrganisateur.setDateArchEvent(LocalDate.now());
				choixOrganisateurRepository.save(choixOrganisateur);
				return null;
			});
		}
		return eventMapper.toDto(event);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ChoixOrganisateurDTO> findAll() {
		return choixOrganisateurRepository.findAll().stream().map(choixOrganisateurMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ChoixOrganisateur> findAllByFcoFusID(Long id) {
		return choixOrganisateurRepository.findAllByFcoFusId(id);
	}

	@Override
	public void delete(Long id) {
		choixOrganisateurRepository.deleteById(id);
	}

	@Override
	public ChoixOrganisateurDTO createChoixOrganisateur(ChoixOrganisateurDTO choixOrganisateurDTO) {
		return choixOrganisateurMapper.toDto(choixOrganisateurRepository.save(choixOrganisateurMapper.toEntity(choixOrganisateurDTO)));
	}

	@Override
	@Transactional(readOnly = true)
	public ChoixOrganisateurDTO findById(Long id) {
		return choixOrganisateurMapper.toDto(choixOrganisateurRepository.findById(id).orElseThrow(RuntimeException::new));
	}

	@Override
	public ChoixOrganisateurDTO updateChoixOrganisateur(ChoixOrganisateurDTO choixOrganisateur) {
		return choixOrganisateurMapper.toDto(choixOrganisateurRepository.save(choixOrganisateurMapper.toEntity(choixOrganisateur)));
	}
}
