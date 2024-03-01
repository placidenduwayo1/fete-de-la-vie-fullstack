package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    // Add custom query methods if needed
}