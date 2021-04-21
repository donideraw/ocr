package com.doni.genbe.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
	@GetMapping("/tabelbiodata")
	public String getData() {
		return "table";
	}

	@GetMapping("/editpath")
	public String insertPendidikan() {
		return "pendidikan";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
}
