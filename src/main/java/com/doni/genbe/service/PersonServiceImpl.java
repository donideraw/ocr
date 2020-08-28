package com.doni.genbe.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doni.genbe.model.dto.StatusDto;
import com.doni.genbe.model.entity.Biodata;
//import com.doni.genbe.model.entity.Person;
import com.doni.genbe.repository.BiodataRepository;
//import com.doni.genbe.repository.PersonRepository;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
//	@Autowired
//	private PersonRepository personRepository;
	
	@Autowired
	private BiodataRepository biodataRepository;

	@Override
	public StatusDto insertPerson(Biodata biodata) {
		biodataRepository.save(biodata);
//		Person person = new Person();
		
//		if (person.getNik().length() != 16) {
//			return statusGagalNik();
//		}
		return null;
//		return statusBerhasil();
	}
	
//	private StatusDto statusBerhasil() {
//		StatusDto dto = new StatusDto();
//		dto.setStatus("true");
//		dto.setMessage("data berhasil masuk");
//		
//		return dto;
//	}
//	
//	private StatusDto statusGagalNik() {
//		StatusDto dto = new StatusDto();
//		dto.setStatus("false");
//		dto.setMessage("data gagal masuk, jumlah digit nik tidak sama dengan 16");
//		
//		return dto;
//	}
	
//	private StatusDto statusGagalUmur() {
//		StatusDto dto = new StatusDto();
//		dto.setStatus("false");
//		dto.setMessage("data gagal masuk, umur dibawah 30 tahun");
//		
//		return dto;
//	}
//	
	
}
