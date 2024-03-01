package com.fdlv.gmd.api.repository.fdlv;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.fdlv.FdlvUser;

/**
 * Spring Data SQL repository for the FdlvUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FdlvUserRepository extends JpaRepository<FdlvUser, Long> {


    Optional<FdlvUser> findByLogin(String login);
    
    Optional<FdlvUser> findOneWithAuthoritiesByEmailIgnoreCase(String login);

	Optional<FdlvUser> findOneWithAuthoritiesByLogin(String lowercaseLogin);

    Optional<FdlvUser> findOneByEmailIgnoreCase(String mail);

    Optional<FdlvUser> findOneByResetKey(String key);
}


