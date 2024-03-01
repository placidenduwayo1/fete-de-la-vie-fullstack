package com.fdlv.gmd.api.service.mobile;

import com.fdlv.gmd.api.domain.mobile.EventStat;
import com.fdlv.gmd.api.domain.mobile.MobileStatParcours;
import com.fdlv.gmd.api.dto.mobile.MobileStatParcoursDTO;
import com.fdlv.gmd.api.dto.mobile.MobileStatParcoursFlatDTO;
import javassist.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MobileStatParcoursService {

    /**
     * Create a new MobilteStatParcours entry
     *
     * @param idUser  id of the mobile user.
     * @param idEvent id of the event the user is currently doing.
     * @return id of the persisted entity.
     */

    Long save(Long idUser, Long idEvent);

    /**
     * Update MobileStatParcours with finish time and if the event was shared.
     *
     * @param id       id of the mobilestatparcours.
     * @param isShared boolean representing if the event was shared or not.
     * @return id of the persisted entity.
     */

    Optional<MobileStatParcoursDTO> update(Long id, boolean isShared);

    /**
     * Get if the entity with given id exists or not
     *
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    List<EventStat> findEventStatsList();

    List<MobileStatParcours> getAllMobileStatParcours();

    MobileStatParcours getMobileStatParcoursById(Long id);

    MobileStatParcours createMobileStatParcours(MobileStatParcours mobileStatParcours);

    MobileStatParcours updateMobileStatParcours(MobileStatParcours mobileStatParcours);

    void update(Long id, MobileStatParcoursFlatDTO dto) throws NotFoundException;

    /**
     * Fonction permettant d'enregistrer l'heure a laquelle l'utilisateur a terminé le parcours
     * @param id
     * @param dateFin
     * @throws NotFoundException
     */
    void end(Long id, LocalDateTime dateFin) throws NotFoundException;

    /**
     * Fonction permettant d'indiquer que l'utilisateur a partagé le parcours
     * @param id
     * @throws NotFoundException
     */
    void share(Long id) throws NotFoundException;
    void deleteMobileStatParcours(Long id);

    boolean existsMobileStatParcours(Long id);

    Long save(MobileStatParcoursFlatDTO dto);
}
