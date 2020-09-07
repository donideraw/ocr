package com.doni.genbe.controller.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doni.genbe.model.dto.PendidikanDto;
import com.doni.genbe.model.dto.StatusDto;
import com.doni.genbe.service.PersonService;
import com.doni.genbe.service.PersonServiceImpl;

@RestController
@RequestMapping("/pendidikan")
public class ApiControllerPendidikan {
	
	@Autowired
	private PersonService personService = new PersonServiceImpl();
	
	@PostMapping("/{idperson}") 
		public StatusDto insertPendidikan(@RequestBody List<PendidikanDto> dtoList, @PathVariable Integer idperson) {
		try {
		personService.insertPendidikan(dtoList, idperson);
		return statusBerhasil();
		} catch (Exception e){
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
	
}
