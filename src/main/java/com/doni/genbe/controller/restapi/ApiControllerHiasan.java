package com.doni.genbe.controller.restapi;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doni.genbe.model.dto.AllDto;
import com.doni.genbe.model.dto.StatusDto;
import com.doni.genbe.model.dto.WidgetDto;
import com.doni.genbe.model.entity.Biodata;
import com.doni.genbe.model.entity.Person;
import com.doni.genbe.repository.BiodataRepository;
import com.doni.genbe.repository.PendidikanRepository;
import com.doni.genbe.repository.PersonRepository;
import com.doni.genbe.service.PersonService;
import com.doni.genbe.service.PersonServiceImpl;

@RestController
@RequestMapping
public class ApiControllerHiasan {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PendidikanRepository pendidikanRepository;
	
	@Autowired
	private BiodataRepository biodataRepository;
	
	@Autowired
	private PersonService personService = new PersonServiceImpl();
	
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
	
	@GetMapping("/all")
	public List<AllDto> getAll() {
		List<Biodata> biodataList = biodataRepository.findAll();
		List<AllDto> dtoList = biodataList.stream().map(this::convertToDto).collect(Collectors.toList());
		return dtoList;
		
	}
	
	@PostMapping("/edit")
	public StatusDto insert(@RequestBody AllDto dto) {
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
			personService.insertBiodata(person, dto);
		}
		return statusBerhasil();
	}

	private StatusDto statusBerhasil() {
		StatusDto dto = new StatusDto();
		dto.setStatus("true");
		dto.setMessage("data berhasil diganti");
		return dto;
	}

	private StatusDto statusGagalNik() {
		StatusDto dto = new StatusDto();
		dto.setStatus("false");
		dto.setMessage("data gagal diganti, jumlah digit nik tidak sama dengan 16");
		return dto;
	}

	private StatusDto statusGagalUmur() {
		StatusDto dto = new StatusDto();
		dto.setStatus("false");
		dto.setMessage("data gagal diganti, umur dibawah 30 tahun");
		return dto;
	}
	
	
	private AllDto convertToDto (Biodata biodata) {
		AllDto dto = new AllDto();
		dto.setNik(biodata.getPerson().getNik());
		dto.setKodePerson(biodata.getPerson().getKodePerson());
		dto.setName(biodata.getPerson().getNama());
		dto.setAddress(biodata.getPerson().getAlamat());
		dto.setHp(biodata.getNomorHandphone());
		dto.setKodeBiodata(biodata.getKodeBiodata());
		dto.setTgl(biodata.getTanggalLahir());
		dto.setTempatLahir(biodata.getTempatLahir());
		
		return dto;
	}
	
	private Person convertToEntityPerson(AllDto dto) {
		Person person = new Person();
		person.setNik(dto.getNik());
		person.setNama(dto.getName());
		person.setAlamat(dto.getAddress());
		person.setKodePerson(dto.getKodePerson());
		return person;
	}
}
