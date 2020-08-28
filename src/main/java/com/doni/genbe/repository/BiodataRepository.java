package com.doni.genbe.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doni.genbe.model.entity.Biodata;

@Repository
public interface BiodataRepository extends JpaRepository<Biodata, Integer>{
	@Query(value = "select nohp from t_biodata b inner join t_person p on b.idperson = p.id_person where nik = ?", nativeQuery = true)
	String getNoHpByNik (String nik);
	
	@Query(value = "select tempat_lahir from t_biodata b inner join t_person p on b.idperson = p.id_person where nik = ?", nativeQuery = true)
	String getTempatLahirByNik (String nik);
	
	@Query(value = "select tanggal_lahir from t_biodata b inner join t_person p on b.idperson = p.id_person where nik = ?", nativeQuery = true)
	Date getTanggalLahirByNik (String nik);
}
