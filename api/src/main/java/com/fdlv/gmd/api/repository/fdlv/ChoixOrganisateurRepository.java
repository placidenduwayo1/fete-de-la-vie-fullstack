package com.fdlv.gmd.api.repository.fdlv;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur;

/**
 * Spring Data SQL repository for the ChoixOrganisateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChoixOrganisateurRepository extends JpaRepository<ChoixOrganisateur, Long> {
	List<ChoixOrganisateur> findAllByFcoFusId(Long fcoFusId);

	List<ChoixOrganisateur> findAllByNumScenarioLessThan(int valeur);

	List<ChoixOrganisateur> findAllByFcoFusIdAndNumScenarioLessThan(Long id,int valeur);
}