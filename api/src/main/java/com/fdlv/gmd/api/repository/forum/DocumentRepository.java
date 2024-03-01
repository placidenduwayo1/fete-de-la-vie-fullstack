package com.fdlv.gmd.api.repository.forum;

import com.fdlv.gmd.api.domain.forum.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Add custom query methods if needed
}