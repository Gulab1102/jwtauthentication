package com.example.jwtauthentication.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {

	org.slf4j.Logger logger = LoggerFactory.getLogger(HomeController.class);

	@PostMapping("/test")
	public String test() {
		this.logger.warn("This is working message");
		return "{\"name\":\"Gulab\"}";
	}

}
