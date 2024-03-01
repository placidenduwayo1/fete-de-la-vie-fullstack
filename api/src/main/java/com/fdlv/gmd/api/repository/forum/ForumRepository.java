package com.fdlv.gmd.api.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.forum.Forum;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
	@Query("SELECT MAX(F.id) FROM Forum F")
	Long max();

	@Query("select distinct f from Forum f " +
			"join f.interventionActeurs fin " +
			"join fin.acteur fac " +
			"join fin.roleActeur fra " +
			"where fra.roleActeur = 'Responsable' " +
			"and fac.acteurFDLV = '1'")
	List<Forum> findAllWithCorrespondantFDLV();
}