package com.doni.genbe.repository;

import com.doni.genbe.model.entity.FolderPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderPathRepository extends JpaRepository<FolderPath, Long> {
}
