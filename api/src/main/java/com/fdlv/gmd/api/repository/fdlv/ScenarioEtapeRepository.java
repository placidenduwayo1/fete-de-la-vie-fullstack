package com.fdlv.gmd.api.repository.fdlv;

import com.fdlv.gmd.api.domain.fdlv.ScenarioEtape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScenarioEtapeRepository extends JpaRepository<ScenarioEtape, Long> {

    @Query(value = "SELECT * FROM fdlv_scenario_etape where fse_sps_id = :idOrga ",nativeQuery = true)
    List<ScenarioEtape> findByOrga(@Param("idOrga")Long idOrga);

    @Modifying
    @Query(value = "DELETE FROM fdlv_scenario_etape where fse_sps_id = :idOrga ",nativeQuery = true)
    void deleteByOrga(@Param("idOrga")Long idOrga);
}