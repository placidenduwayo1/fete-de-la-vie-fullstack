package com.fdlv.gmd.api.repository.fdlv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.fdlv.Partenaire;

import java.util.List;

/**
 * Spring Data SQL repository for the Partenaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire,Long>{



    @Query(value = "select * from fdlv_partenaire where fpe_module = 1 or fpe_module  = 3;",
            nativeQuery = true
    )
    List<Partenaire> findPartenaireWebsite();

    @Query(value = "select * from fdlv_partenaire where fpe_module = 2 or fpe_module  = 3;",
            nativeQuery = true
    )
    List<Partenaire> findPartenaireMobile();
    
}
