package com.doni.genbe.service;

import java.util.List;

import com.doni.genbe.model.dto.PendidikanDto;
import com.doni.genbe.model.dto.PersonDto;
import com.doni.genbe.model.entity.Biodata;
import com.doni.genbe.model.entity.Person;

public interface PersonService {
	public void insertPendidikan (List<PendidikanDto> dtoList, Integer idperson);
	public void insertPerson (Person person, PersonDto dto);
	public void insertBiodata (Biodata biodata);
}
