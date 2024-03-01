package com.fdlv.gmd.api.repository.fdlv;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.fdlv.InfoPageWeb;

/**
 * Spring Data SQL repository for the InfoPageWeb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoPageWebRepository extends JpaRepository<InfoPageWeb, Long> {

	List<InfoPageWeb> findAllByPageWeb(String pageWeb);

	List<InfoPageWeb> findAllByPageWebAndPublieIsTrue(String pageWeb);

	List<InfoPageWeb> findAllByRubriquePageWebAndPublieIsTrue(String rubriquePageWeb);
}
