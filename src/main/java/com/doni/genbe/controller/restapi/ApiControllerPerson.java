package com.doni.genbe.controller.restapi;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doni.genbe.model.dto.GetDto;
import com.doni.genbe.model.dto.PersonDto;
import com.doni.genbe.model.dto.ResponseDto;
import com.doni.genbe.model.dto.StatusDto;
import com.doni.genbe.model.entity.Person;
import com.doni.genbe.repository.BiodataRepository;
import com.doni.genbe.repository.PendidikanRepository;
import com.doni.genbe.repository.PersonRepository;
import com.doni.genbe.service.PersonService;
import com.doni.genbe.service.PersonServiceImpl;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/person")
public class ApiControllerPerson {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private BiodataRepository biodataRepository;

	@Autowired
	private PendidikanRepository pendidikanRepository;
	
	@Autowired
	private PersonService personService = new PersonServiceImpl();

	@GetMapping("/{nik}")
	public MappingJacksonValue get(@PathVariable String nik) {
		ResponseDto dt = new ResponseDto();
		GetDto dto = new GetDto();
		if (nik.length() != 16) {
			dt.setStatus("false");
			dt.setMessage("data gagal masuk, jumlah digit nik tidak sama dengan 16");
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("status","message");
			FilterProvider filters = new SimpleFilterProvider().addFilter("ResponseDtoFilter", filter);
			MappingJacksonValue mapping = new MappingJacksonValue(dt);
			mapping.setFilters(filters);
			return mapping;
		} else if (personRepository.getNamaByNik(nik) == null) {
			dt.setStatus("false");
			dt.setMessage("data dengan nik " + nik + " tidak ditemukan");
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("status","message");
			FilterProvider filters = new SimpleFilterProvider().addFilter("ResponseDtoFilter", filter);
			MappingJacksonValue mapping = new MappingJacksonValue(dt);
			mapping.setFilters(filters);
			return mapping;
		} else {
			dt.setStatus("true");
			dt.setMessage("success");
			dto.setAddress(personRepository.getAlamatByNik(nik));
			dto.setName(personRepository.getNamaByNik(nik));
			dto.setNik(personRepository.getNikByNik(nik));
			dto.setHp(biodataRepository.getNoHpByNik(nik));
			dto.setTgl(biodataRepository.getTanggalLahirByNik(nik));
			dto.setTempatLahir(biodataRepository.getTempatLahirByNik(nik));

			Date birth = biodataRepository.getTanggalLahirByNik(nik);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(birth);
			Integer age = 2020 - calendar.get(Calendar.YEAR);

			dto.setUmur(String.valueOf(age));
			dto.setPendidikan_terakhir(pendidikanRepository.getPendidikanByNik(nik));
			dt.setData(dto);
		}
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("status","message","data");
		FilterProvider filters = new SimpleFilterProvider().addFilter("ResponseDtoFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(dt);
		mapping.setFilters(filters);
		return mapping;
	}

	@PostMapping("/insert")
	public StatusDto insert(@RequestBody PersonDto dto) {
		Date birth = dto.getTgl();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(birth);

		int tahun = calendar.get(Calendar.YEAR);
		int bulan = calendar.get(Calendar.MONTH) + 1;
		int hari = calendar.get(Calendar.DAY_OF_MONTH);

		if (dto.getNik().length() != 16) {
			return statusGagalNik();
		} else if (2020 - tahun < 30 || (2020 - tahun == 30 && bulan > 8)
				|| (2020 - tahun == 30 && bulan == 8 && hari > 29)) {
			return statusGagalUmur();
		} else {
			Person person = convertToEntityPerson(dto);
			personService.insertPerson(person, dto);
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

	private Person convertToEntityPerson(PersonDto dto) {
		Person person = new Person();
		person.setNik(dto.getNik());
		person.setNama(dto.getName());
		person.setAlamat(dto.getAddress());
		return person;
	}

}
