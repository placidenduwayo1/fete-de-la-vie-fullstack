package com.fdlv.gmd.api.service.mobile;

import com.fdlv.gmd.api.domain.mobile.MobileTrackParcours;
import com.fdlv.gmd.api.dto.mobile.MobileTrackParcoursDTO;
import com.fdlv.gmd.api.dto.mobile.MobileTrackParcoursFlatDTO;
import javassist.NotFoundException;

import java.io.Writer;
import java.util.List;

public interface MobileTrackParcousService {

    /**
     * Save a mobiletrackparcours
     *
     * @param mobileTrackParcoursDTO the mobile track parcours to save
     * @return the persisted entity
     */
    MobileTrackParcoursDTO save(MobileTrackParcoursDTO mobileTrackParcoursDTO);

    /**
     * Get if the entity with given id exists or not
     *
     * @param id the id of the entity
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(Long id);

    void findAllCSV(Writer writer);

    MobileTrackParcours getMobileTrackParcoursById(Long id);

    MobileTrackParcours createMobileTrackParcours(MobileTrackParcours mobileTrackParcours);

    Long save(MobileTrackParcoursFlatDTO mobileTrackParcoursDTO) throws NotFoundException;

    MobileTrackParcours updateMobileTrackParcours(MobileTrackParcours mobileTrackParcours);

    void deleteMobileTrackParcours(Long id);

    boolean existsMobileTrackParcours(Long id);

    List<MobileTrackParcours> getAllMobileTrackParcours();

    /**
     * Fonction permettant d'indiquer que l'utilisateur a partagé le defi.
     * @param id
     */
    void share(Long id);

    /**
     * Fonction permettant d'indiquer que l'utilisateur a accepté le defi
     * @param id
     */

    void acceptDefi(Long id);


    /***
     * Fonction permettant d'ajouter l'id du quizz lié a l étape
     * @param id
     * @param idQuizz
     */
    void addQuizz(Long id, Long idQuizz);
}
