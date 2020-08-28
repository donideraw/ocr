package com.doni.genbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doni.genbe.model.entity.Pendidikan;

@Repository
public interface PendidikanRepository extends JpaRepository<Pendidikan, Integer>{
	@Query(value = "select jenjang from t_pendidikan a inner join t_person b on a.idperson = b.id_person where nik = ? Order by tahunlulus desc limit 1", nativeQuery = true)
	String getPendidikanByNik (String nik);
}
