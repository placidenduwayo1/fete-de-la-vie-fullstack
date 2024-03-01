package com.fdlv.gmd.api.repository.fdlv;

import com.fdlv.gmd.api.domain.fdlv.Partenaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.fdlv.Temoignage;

import java.util.List;

/**
 * Spring Data SQL repository for the Temoignage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemoignageRepository extends JpaRepository<Temoignage, Long> {


    /**
     * This query check if the row is piblish
     * and if the current date is between the start and the end of the testimony
     * @return List<Temoignage> the list of testimony that should be display
     */
    @Query(value = "SELECT * FROM fdlv_temoignage WHERE tge_publie = 1 \n" +
            "and NOW() >= tge_date_debut\n" +
            "and (now() <= tge_date_fin or tge_date_fin is NULL);",
            nativeQuery = true
    )
    List<Temoignage> findDisplayableTestimony();

}
