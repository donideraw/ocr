package com.doni.genbe.repository;

import com.doni.genbe.model.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query(value = "select * from t_document where is_scanned = true order by docid desc", nativeQuery = true)
    List<Document> findAllScannedIs();

    @Query(value = "select * from t_document where is_scanned = false order by docid desc", nativeQuery = true)
    List<Document> findAllScannedFalse();

    Document findByDocName(String docName);
}
