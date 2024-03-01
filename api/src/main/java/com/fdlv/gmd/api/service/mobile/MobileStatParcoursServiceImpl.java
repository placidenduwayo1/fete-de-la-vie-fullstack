package com.fdlv.gmd.api.service.mobile;

import com.fdlv.gmd.api.domain.Event;
import com.fdlv.gmd.api.domain.mobile.EventStat;
import com.fdlv.gmd.api.domain.mobile.MobileStatParcours;
import com.fdlv.gmd.api.domain.mobile.MobileUser;
import com.fdlv.gmd.api.dto.mobile.MobileStatParcoursDTO;
import com.fdlv.gmd.api.dto.mobile.MobileStatParcoursFlatDTO;
import com.fdlv.gmd.api.mapper.mobile.MobileStatParcoursMapper;
import com.fdlv.gmd.api.repository.EventRepository;
import com.fdlv.gmd.api.repository.mobile.MobileStatParcoursRepository;
import com.fdlv.gmd.api.repository.mobile.MobileUserRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MobileStatParcoursServiceImpl implements MobileStatParcoursService {

    private final Logger log = LoggerFactory.getLogger(MobileStatParcoursServiceImpl.class);

    private final MobileStatParcoursMapper mobileStatParcoursMapper;
    private final MobileStatParcoursRepository mobileStatParcoursRepository;
    private final MobileUserRepository mobileUserRepository;
    private final EventRepository eventRepository;

    public MobileStatParcoursServiceImpl(MobileStatParcoursMapper mobileStatParcoursMapper,
                                         MobileStatParcoursRepository mobileStatParcoursRepository,
                                         MobileUserRepository mobileUserRepository,
                                         EventRepository eventRepository
    ) {
        this.mobileStatParcoursMapper = mobileStatParcoursMapper;
        this.mobileStatParcoursRepository = mobileStatParcoursRepository;
        this.mobileUserRepository = mobileUserRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Long save(Long idUser, Long idEvent) {
        log.debug("Request to save a MobileStatParcours with id");
        MobileStatParcours mobileStatParcours = new MobileStatParcours();
        MobileUser mobileUser = mobileUserRepository.findById(idUser).orElse(null); // Replace null with your desired default value
        mobileStatParcours.setMobileUser(mobileUser);
        Event event = eventRepository.findById(idEvent).orElse(null); // Replace null with your desired default value
        mobileStatParcours.setEvent(event);
        mobileStatParcours.setDateDebut(LocalDateTime.now());
        mobileStatParcours = mobileStatParcoursRepository.save(mobileStatParcours);
        return mobileStatParcours.getId();
    }

    @Override
    public Optional<MobileStatParcoursDTO> update(Long id, boolean isShared) {
        log.debug("Request to update a MobileStatParcours with id: {}", id);
        return mobileStatParcoursRepository
                .findById(id)
                .map(
                        existingMobileStatParcours -> {
                            existingMobileStatParcours.setParcoursPartage(isShared);
                            existingMobileStatParcours.setDateFin(LocalDateTime.now());
                            return existingMobileStatParcours;
                        }
                )
                .map(mobileStatParcoursRepository::save)
                .map(mobileStatParcoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return mobileStatParcoursRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventStat> findEventStatsList() {
        log.debug("Request to get all partenaires");
        return new LinkedList<>(mobileStatParcoursRepository
                .getEventStatsLists());
    }

    public List<MobileStatParcours> getAllMobileStatParcours() {
        return mobileStatParcoursRepository.findAll();
    }

    public MobileStatParcours getMobileStatParcoursById(Long id) {
        return mobileStatParcoursRepository.findById(id).orElse(null);
    }

    public MobileStatParcours createMobileStatParcours(MobileStatParcours mobileStatParcours) {
        return mobileStatParcoursRepository.save(mobileStatParcours);
    }

    @Override
    public Long save(MobileStatParcoursFlatDTO dto) {
        log.debug("Request to save a MobileStatParcours with flat DTO");
        MobileStatParcours mobileStatParcours = mobileStatParcoursMapper.toEntityFromFlatDTO(dto);
        mobileStatParcours = mobileStatParcoursRepository.save(mobileStatParcours);
        return mobileStatParcours.getId();
    }

    public MobileStatParcours updateMobileStatParcours(MobileStatParcours mobileStatParcours) {
        return mobileStatParcoursRepository.save(mobileStatParcours);
    }

    @Override
    public void update(Long id, MobileStatParcoursFlatDTO dto) throws NotFoundException {
        log.debug("Request to update MobileStatParcours with flat DTO : {}", id);
        MobileStatParcours existingMobileStatParcours = mobileStatParcoursRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MobileStatParcours not found with id: " + id));

        mobileStatParcoursMapper.updateEntityFromFlatDTO(dto, existingMobileStatParcours);
        mobileStatParcoursRepository.save(existingMobileStatParcours);
    }

    public void deleteMobileStatParcours(Long id) {
        mobileStatParcoursRepository.deleteById(id);
    }

    public boolean existsMobileStatParcours(Long id) {
        return mobileStatParcoursRepository.existsById(id);
    }

    @Override
    public void end(Long id,LocalDateTime dateFin) throws NotFoundException {
        mobileStatParcoursRepository.end(id,dateFin);
    }

    @Override
    public void share(Long id) throws NotFoundException {
        mobileStatParcoursRepository.share(id);
    }
}
