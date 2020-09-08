package com.doni.genbe.controller.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doni.genbe.model.dto.StatusDto;
import com.doni.genbe.model.dto.WidgetDto;
import com.doni.genbe.repository.PendidikanRepository;
import com.doni.genbe.repository.PersonRepository;

@RestController
@RequestMapping
public class ApiControllerHiasan {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PendidikanRepository pendidikanRepository;
	
	@GetMapping("/widgets")
	public WidgetDto get() {
		WidgetDto dto = new WidgetDto();
		dto.setJumlahPerson(personRepository.getJumlahPerson());
		dto.setJumlahSarjana(pendidikanRepository.getJumlahSarjana());
		dto.setJumlahMagister(pendidikanRepository.getJumlahMagister());
		dto.setNamaTerakhir(personRepository.getNamaTerakhir());
		return dto;
	}
	
	@GetMapping("/login/{username}")
	public StatusDto login(@PathVariable String username) {
		StatusDto dto = new StatusDto();
		if(personRepository.findUsername(username) == null) {
			dto.setStatus("false");
			dto.setMessage("nama tidak ada");
		} else {
			dto.setStatus("true");
			dto.setMessage("berhasil masuk");
		}
		return dto;
		
	}
}
