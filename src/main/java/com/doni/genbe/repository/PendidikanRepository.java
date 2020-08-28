package com.doni.genbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doni.genbe.model.entity.Pendidikan;

@Repository
public interface PendidikanRepository extends JpaRepository<Pendidikan, Integer>{
}
