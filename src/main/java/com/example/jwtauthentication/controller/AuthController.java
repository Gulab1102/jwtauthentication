package com.example.jwtauthentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtauthentication.helper.JwtHelper;
import com.example.jwtauthentication.model.JwtRequest;
import com.example.jwtauthentication.model.JwtResponse;
import com.example.jwtauthentication.model.User;
import com.example.jwtauthentication.repo.UserRepo;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	@Autowired
	UserRepo userRepo;
	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;
    
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    


    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
//    	System.out.println(request);
//    	

         
        this.doAuthenticate(request.getEmail(), request.getPassword());
        

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = new JwtResponse(token,userDetails.getUsername());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    
    
    @GetMapping("/test")
	public String test() {
		//this.logger.warn("This is working message");
		return "{\"name\":\"Gulab\"}";
	}
    
    
    @PostMapping("/register")
    public ResponseEntity<String> hello(@RequestBody User user) {
    	
    	
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	//user.setEmail(request.getEmail());
    	user.setRole("USER");
        User temp=	userRepo.save(user);

        if(temp!=null)
        	return ResponseEntity.ok("Registered successfully !!");
        return ResponseEntity.ok("Registered successfully not !!");
    	
    	
//    	System.out.println(user);
//        if(user==null)return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No User");
//       // this.doAuthenticate(request.getEmail(), request.getPassword());
//    //	User user=new User();
//    	if(user.getEmail()==null||"".equals(user.getEmail())||user.getPassword()==null||"".equals(user.getPassword()))
//    		  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid Username Password");
//    	
//    	 UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
//    	  
//    	 if(userDetails.getUsername()!=null)
//    		 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Username Alraedy There try another");
//    	 
//    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//    	//user.setEmail(request.getEmail());
//    	user.setRole("USER");
//        User temp=	userRepo.save(user);
//        // System.out.println(temp);
//
//        if(temp!=null)
//        	return ResponseEntity.ok("Registered successfully !!");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("nternal error");
    }

}
