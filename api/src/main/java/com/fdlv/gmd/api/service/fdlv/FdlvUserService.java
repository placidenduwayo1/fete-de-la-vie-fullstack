package com.fdlv.gmd.api.service.fdlv;

import java.util.List;
import java.util.Optional;

import com.fdlv.gmd.api.domain.User;
import com.fdlv.gmd.api.domain.fdlv.FdlvUser;
import com.fdlv.gmd.api.dto.ChoixOrganisateurDTO;
import com.fdlv.gmd.api.dto.fdlv.FdlvUserDTO;

/**
 * Service Interface for managing {@link com.fdlv.gmd.api.domain.fdlv.FdlvUser}.
 */
public interface FdlvUserService {
    /**
     * Save an fdlvUser.
     *
     * @param fdlvUserDTO the entity to save.
     * @return the persisted entity.
     */
    FdlvUserDTO create(FdlvUserDTO fdlvUserDTO);
    FdlvUserDTO update(FdlvUserDTO fdlvUserDTO);
    FdlvUserDTO updateResetKey(FdlvUserDTO fdlvUserDTO);
    void sendFdlvEmailResetPassword(String login);
    FdlvUserDTO updateActivate(FdlvUserDTO oldUser, FdlvUserDTO newUser);

	/**
	 * Get all the fdlvUsers.
	 *
	 * @return the list of entities.
	 */
	List<FdlvUserDTO> findAll();

	/**
	 * Get the "id" fdlvUser.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<FdlvUserDTO> findOne(Long id);

	/**
	 * Get the "id" fdlvUserEntity.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<FdlvUser> findOneEntity(Long id);


	/**
	 * Get if the entity with given id exists or not
	 *
	 * @param id the id of the entity
	 * @return true if the entity exists, false otherwise
	 */
	boolean existsById(Long id);

    /**
     * Delete the "id" fdlvUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    FdlvUserDTO findByLogin(String login);

    FdlvUserDTO findByEmail(String login);

    void completePwdReset(String key, String newPassword);

	/**
	 * Get all parcours with all users
	 *
	 * @return list of parcours with users
	 */
	List<ChoixOrganisateurDTO> getAllUsersWithParcours();

	/**
	 * Get all parcours with id of fdlv users
	 *
	 * @return list of parcours with users
	 */
	List<ChoixOrganisateurDTO> getParcoursByFusId(Long id);
}
