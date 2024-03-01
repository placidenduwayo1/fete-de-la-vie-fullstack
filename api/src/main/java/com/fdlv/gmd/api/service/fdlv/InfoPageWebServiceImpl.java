package com.fdlv.gmd.api.service.fdlv;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.fdlv.InfoPageWeb;
import com.fdlv.gmd.api.dto.fdlv.InfoPageWebDTO;
import com.fdlv.gmd.api.mapper.fdlv.InfoPageWebMapper;
import com.fdlv.gmd.api.repository.fdlv.InfoPageWebRepository;

/**
 * Service Implementation for managing {@link InfoPageWeb}.
 */
@Service
@Transactional
public class InfoPageWebServiceImpl implements InfoPageWebService {

	private static String ACTION  = "1 = NOS ACTIONS";
	private static String VALEURS  = "2 = Valeurs/Points forts";
	private static String EXPLO_PROTO  = "3 = Explo-Proto";

	private final Logger log = LoggerFactory.getLogger(InfoPageWebServiceImpl.class);

	private final InfoPageWebRepository infoPageWebRepository;

	private final InfoPageWebMapper infoPageWebMapper;

	public InfoPageWebServiceImpl(InfoPageWebRepository infoPageWebRepository, InfoPageWebMapper infoPageWebMapper) {
		this.infoPageWebRepository = infoPageWebRepository;
		this.infoPageWebMapper = infoPageWebMapper;
	}

	@Override
	public InfoPageWebDTO save(InfoPageWebDTO infoPageWebDTO) {
		log.debug("Request to save InfoPageWeb : {}", infoPageWebDTO);
		InfoPageWeb infoPageWeb = infoPageWebMapper.toEntity(infoPageWebDTO);
		infoPageWeb = infoPageWebRepository.save(infoPageWeb);
		return infoPageWebMapper.toDto(infoPageWeb);
	}

	@Override
	public Optional<InfoPageWebDTO> partialUpdate(InfoPageWebDTO infoPageWebDTO) {
		log.debug("Request to partially update infoPageWeb : {}", infoPageWebDTO);

		return infoPageWebRepository
				.findById(infoPageWebDTO.getId())
				.map(
						existingInfoPageWeb -> {
							infoPageWebMapper.partialUpdate(existingInfoPageWeb, infoPageWebDTO);
							return existingInfoPageWeb;
						}
						)
				.map(infoPageWebRepository::save)
				.map(infoPageWebMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InfoPageWebDTO> findAll() {
		log.debug("Request to get all InfoPageWebs");
		return infoPageWebRepository.findAll().stream().map(infoPageWebMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	@Transactional(readOnly = true)
	public List<InfoPageWebDTO> findAllByNosAction() {
		log.debug("Request to get all InfoPageWebs of nos actions");
		return infoPageWebMapper.toDto(infoPageWebRepository.findAllByPageWeb(ACTION));
	}

	@Override
	@Transactional(readOnly = true)
	public List<InfoPageWebDTO> findAllByNosActionsDisplayable() {
		log.debug("Request to get all InfoPageWebs of nos actions displayable");
		return infoPageWebMapper.toDto(infoPageWebRepository.findAllByPageWebAndPublieIsTrue(ACTION));
	}


	@Override
	@Transactional(readOnly = true)
	public Optional<InfoPageWebDTO> findOne(Long id) {
		log.debug("Request to get InfoPageWeb : {}", id);
		return infoPageWebRepository.findById(id).map(infoPageWebMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(Long id) {
		return infoPageWebRepository.existsById(id);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete InfoPageWeb : {}", id);
		infoPageWebRepository.deleteById(id);
	}

	@Override
	public List<InfoPageWebDTO> findAllByPageWeb(String pageWeb) {
		log.debug("Request to get all InfoPageWebs of nos actions");
		String pageWebDbValue = mapPageWeb().get(pageWeb);
		return infoPageWebMapper.toDto(infoPageWebRepository.findAllByPageWebAndPublieIsTrue(pageWebDbValue));
	}

	private Map<String, String> mapPageWeb() {
		Map<String, String> map = new HashMap<>();
		map.put("nos-actions", ACTION);
		map.put("nos-valeurs", VALEURS);
		map.put("explo-Proto", EXPLO_PROTO);
		return map;
	}
	public List<InfoPageWebDTO> findDisplayableLastEvent() {
		return infoPageWebRepository.findAllByRubriquePageWebAndPublieIsTrue("Dernier Evènement").stream().map(infoPageWebMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public List<InfoPageWebDTO> findDisplayableDevise() {
		return infoPageWebRepository.findAllByRubriquePageWebAndPublieIsTrue("Devise").stream().map(infoPageWebMapper::toDto).collect(Collectors.toList());

	}

	@Override
	public List<InfoPageWebDTO> findDisplayableFDLVEnMots() {
		return infoPageWebRepository.findAllByRubriquePageWebAndPublieIsTrue("FDLV en Mots").stream().map(infoPageWebMapper::toDto).collect(Collectors.toList());
	}
}
