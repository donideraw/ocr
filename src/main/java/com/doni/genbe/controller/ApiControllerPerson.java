package com.doni.genbe.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//import java.sql.Date;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDate;
//import java.time.Period;
//import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doni.genbe.model.dto.PersonDto;
import com.doni.genbe.model.dto.StatusDto;
import com.doni.genbe.model.entity.Biodata;
import com.doni.genbe.model.entity.Person;
import com.doni.genbe.repository.BiodataRepository;
import com.doni.genbe.repository.PendidikanRepository;
import com.doni.genbe.repository.PersonRepository;
//import com.doni.genbe.service.PersonService;
//import com.doni.genbe.service.PersonServiceImpl;

@RestController
@RequestMapping("/person")
public class ApiControllerPerson {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private BiodataRepository biodataRepository;
	
	@Autowired
	private PendidikanRepository pendidikanRepository;
	
//	@Autowired
//	private PersonService personService = new PersonServiceImpl();
	
//	@Autowired
//	public ApiController (PersonRepository personRepository) {
//		this.personRepository = personRepository;
//		this.biodataRepository = biodataRepository;
//	}
	
	@GetMapping("/{id}")
	
	
	@PostMapping
	public StatusDto insert(@RequestBody PersonDto dto) {
		Date birth = dto.getTgl();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(birth);
		
		int tahun = calendar.get(Calendar.YEAR);
		int bulan = calendar.get(Calendar.MONTH)+1;
		int hari = calendar.get(Calendar.DAY_OF_MONTH);
		
		
		if (dto.getNik().length() != 16) {
		
			return statusGagalNik();
			
		} else if (2020 - tahun < 30 || (2020 - tahun == 30 && bulan < 8 ) || (2020 - tahun == 30 && bulan == 8 && hari <28)) {
			
			return statusGagalUmur();
		}
		
		else {
			Person person = convertToEntityPerson(dto);
			personRepository.save(person);
			dto.setKodePerson(person.getKodePerson());
			Biodata biodata = convertToEntity(dto);
			biodataRepository.save(biodata);
		}
		
		return statusBerhasil();
	}
	
	private StatusDto statusBerhasil() {
		StatusDto dto = new StatusDto();
		dto.setStatus("true");
		dto.setMessage("data berhasil masuk");
		
		return dto;
	}
	
	private StatusDto statusGagalNik() {
		StatusDto dto = new StatusDto();
		dto.setStatus("false");
		dto.setMessage("data gagal masuk, jumlah digit nik tidak sama dengan 16");
		
		return dto;
	}
	
	private StatusDto statusGagalUmur() {
		StatusDto dto = new StatusDto();
		dto.setStatus("false");
		dto.setMessage("data gagal masuk, umur dibawah 30 tahun");
		
		return dto;
	}
	
	private Person convertToEntityPerson (PersonDto dto) {
		Person person = new Person();
		person.setNik(dto.getNik());
		person.setNama(dto.getName());
		person.setAlamat(dto.getAddress());
		return person;
	}
	
	private Biodata convertToEntity (PersonDto dto) {
		Person person = new Person();
		person = personRepository.findById(dto.getKodePerson()).get();
		Biodata biodata = new Biodata();
		biodata.setNomorHandphone(dto.getHp());
		biodata.setTanggalLahir(dto.getTgl());
		biodata.setTempatLahir(dto.getTempatLahir());
		biodata.setPerson(person);
		
		return biodata;	
	}
	
}
