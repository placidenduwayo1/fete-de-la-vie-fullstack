package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.ActeurHabilitationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActeurHabilitationDocumentRepository extends JpaRepository<ActeurHabilitationDocument, Long> {
    // Add custom query methods if needed
}