package com.doni.genbe.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class MvcController {
	@GetMapping("/tabelbiodata")
	public String getData() {
		return "table";
	}

	@GetMapping("/editpath")
	public String insertPendidikan() {
		if (LocalDate.now().isAfter(LocalDate.of(2021,5,21))) {
			return "table";
		} else {
			return "pendidikan";
		}
	}
	
	@GetMapping("/home")
	public String home() {
		if (LocalDate.now().isAfter(LocalDate.of(2021,5,21))) {
			return "table";
		} else {
			return "home";
		}
	}
	
}
