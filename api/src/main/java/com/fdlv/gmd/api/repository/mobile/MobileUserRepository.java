package com.fdlv.gmd.api.repository.mobile;

import com.fdlv.gmd.api.domain.mobile.MobileTypeUtilisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.fdlv.Temoignage;
import com.fdlv.gmd.api.domain.mobile.MobileUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data SQL repository for the MobileUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileUserRepository extends JpaRepository<MobileUser, Long> {

      @Query(value = "UPDATE mobile_users set mus_date_cpte_ferme = current_timestamp() where mus_id_user = :id ;",nativeQuery = true)
      @Modifying
      void closeAccount(@Param("id")Long id);

   }
