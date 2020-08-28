package com.doni.genbe.service;


//import com.doni.genbe.model.dto.PersonDto;
//
//import java.util.List;

//import com.doni.genbe.model.dto.PendidikanDto;
//import com.doni.genbe.model.dto.StatusDto;
import com.doni.genbe.model.entity.Biodata;
import com.doni.genbe.model.entity.Person;
import com.doni.genbe.model.entity.Pendidikan;

public interface PersonService {
	public void insertPendidikan (Pendidikan didik);
	public void insertPerson (Person person);
	public void insertBiodata (Biodata biodata);
}
