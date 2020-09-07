package com.doni.genbe.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
	@GetMapping("/tabelbiodata")
	public String getData() {
		return "table";
	}
	
	@GetMapping("/formbiodata")
	public String insertBiodata() {
		return "biodata";
	}
	
	@GetMapping("/formpendidikan")
	public String insertPendidikan() {
		return "pendidikan";
	}
	
	@GetMapping("/cobacoba")
	public String latihan() {
		return "test";
	}
	
	
}