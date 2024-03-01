package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {

}