package com.doni.genbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doni.genbe.model.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{
	
		@Query(value = "select alamat from t_person where nik = ?", nativeQuery = true)
		String getAlamatByNik (String nik);
		
		@Query(value = "select nama from t_person where nik = ?", nativeQuery = true)
		String getNamaByNik (String nik);
		
		@Query(value = "select nik from t_person where nik = ?", nativeQuery = true)
		String getNikByNik (String nik);
		
		@Query(value = "select count(id_person) from t_person", nativeQuery = true)
		String getJumlahPerson ();
		
		@Query(value = "select nama from t_person order by id_person desc limit 1", nativeQuery = true)
		String getNamaTerakhir ();
		
		@Query(value = "select nama from t_person where nama = ?", nativeQuery = true)
		String findUsername(String username);
}
