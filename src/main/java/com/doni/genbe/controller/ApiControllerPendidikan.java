package com.doni.genbe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doni.genbe.model.dto.PendidikanDto;
import com.doni.genbe.model.dto.StatusDto;
import com.doni.genbe.model.entity.Pendidikan;
import com.doni.genbe.repository.PersonRepository;
import com.doni.genbe.service.PersonService;
import com.doni.genbe.service.PersonServiceImpl;

@RestController
@RequestMapping("/pendidikan")
public class ApiControllerPendidikan {
	
	@Autowired
	private PersonRepository personRepo;
	
	@Autowired
	private PersonService personService = new PersonServiceImpl();
	
	@PostMapping("/{idperson}") 
		public StatusDto insertPendidikan(@RequestBody List<PendidikanDto> dtoList, @PathVariable Integer idperson) {
		int count = 0;
		for (int i = 0; i<dtoList.size(); i++) {
			Pendidikan didik = convertToEntity(dtoList.get(i));
			didik.setPerson(personRepo.findById(idperson).get());
			personService.insertPendidikan(didik);
			count++;
		}
		if (count == dtoList.size()) {
			return statusBerhasil();
		} else {
			return statusGagal();
		}
	}
	
	private StatusDto statusBerhasil() {
		StatusDto dto = new StatusDto();
		dto.setStatus("true");
		dto.setMessage("data berhasil masuk");
		return dto;
	}
	
	private StatusDto statusGagal() {
		StatusDto dto = new StatusDto();
		dto.setStatus("false");
		dto.setMessage("data gagal masuk");
		
		return dto;
	}
	
	private Pendidikan convertToEntity (PendidikanDto dto) {
		Pendidikan didik = new Pendidikan();
		didik.setJenjang(dto.getJenjang());
		didik.setInstitusi(dto.getInstitusi());
		didik.setTahunMasuk(dto.getMasuk());
		didik.setTahunLulus(dto.getLulus());
		return didik;
	}
}
