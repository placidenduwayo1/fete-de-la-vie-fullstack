package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.InterventionStructure;
import com.fdlv.gmd.api.domain.forum.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionStructureRepository extends JpaRepository<InterventionStructure, Long> {
    //new modif debut
    List<InterventionStructure> findByStructure(Structure structure);
    //new modif fin
}
