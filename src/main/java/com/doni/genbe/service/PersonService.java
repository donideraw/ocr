package com.doni.genbe.service;

import com.doni.genbe.model.dto.StatusDto;
import com.doni.genbe.model.entity.Biodata;
//import com.doni.genbe.model.entity.Person;

public interface PersonService {
	public StatusDto insertPerson (Biodata biodata);
}
