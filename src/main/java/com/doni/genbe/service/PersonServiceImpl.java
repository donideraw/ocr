package com.doni.genbe.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doni.genbe.model.entity.Pendidikan;
import com.doni.genbe.repository.PendidikanRepository;
//import com.doni.genbe.repository.PersonRepository;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
//	@Autowired
//	private PersonRepository personRepository;
	
	@Autowired
	private PendidikanRepository pendidikanRepo;

	@Override
	public void insertPendidikan(Pendidikan didik) {
		pendidikanRepo.save(didik);
	}
	
	
	
}
