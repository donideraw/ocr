package com.doni.genbe.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doni.genbe.model.dto.PendidikanDto;
import com.doni.genbe.model.dto.PersonDto;
import com.doni.genbe.model.entity.Biodata;
import com.doni.genbe.model.entity.Pendidikan;
import com.doni.genbe.model.entity.Person;
import com.doni.genbe.repository.BiodataRepository;
import com.doni.genbe.repository.PendidikanRepository;
import com.doni.genbe.repository.PersonRepository;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PendidikanRepository pendidikanRepo;
	
	@Autowired
	private BiodataRepository biodataRepo;

	@Override
	public void insertPendidikan(List<PendidikanDto> dtoList, Integer idperson) {
		for (int i = 0; i<dtoList.size(); i++) {
			Pendidikan didik = convertToEntity(dtoList.get(i));
			if (didik.getJenjang() == "" || didik.getInstitusi() == "" || didik.getTahunMasuk() == "" || didik.getTahunLulus() == "") {
				Integer.parseInt("saya");
				}
			didik.setPerson(personRepository.findById(idperson).get());
			pendidikanRepo.save(didik);
		}
	}
	
	private Pendidikan convertToEntity (PendidikanDto dto) {
		Pendidikan didik = new Pendidikan();
		didik.setJenjang(dto.getJenjang());
		didik.setInstitusi(dto.getInstitusi());
		didik.setTahunMasuk(dto.getMasuk());
		didik.setTahunLulus(dto.getLulus());
		return didik;
	}
	
	@Override
	public void insertPerson(Person person, PersonDto dto) {
		personRepository.save(person);
		dto.setKodePerson(person.getKodePerson());
		person = personRepository.findById(dto.getKodePerson()).get();
		Biodata biodata = new Biodata();
		biodata.setNomorHandphone(dto.getHp());
		biodata.setTanggalLahir(dto.getTgl());
		biodata.setTempatLahir(dto.getTempatLahir());
		biodata.setPerson(person);
		biodataRepo.save(biodata);
	}
	
	@Override
	public void insertBiodata(Biodata biodata) {
		biodataRepo.save(biodata);
	}
}
