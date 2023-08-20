package com.example.jwtauthentication.controller;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	  org.slf4j.Logger logger = LoggerFactory.getLogger(HomeController.class);

	    @RequestMapping("/test")
	    public String test() {
	        this.logger.warn("This is working message");
	        return "Testing message";
	    }


}
