package com.fdlv.gmd.api.repository.forum;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdlv.gmd.api.domain.forum.Acteur;

@Repository
public interface ActeurRepository extends JpaRepository<Acteur, Long> {

	Optional<Acteur> findByLogin(String login);

	List<Acteur> findByResponsable(String responsable);

	List<Acteur> findAllByOrderByIdDesc();

	@Query("SELECT MAX(A.id) FROM Acteur A")
	Long max();

}