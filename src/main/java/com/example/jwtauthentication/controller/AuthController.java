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
import com.example.jwtauthentication.model.MyResponse;
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
        String jwtToken = this.helper.generateToken(userDetails);

        JwtResponse response = new JwtResponse();
        response.setToken(jwtToken);
        response.setUsername(userDetails.getUsername());
        
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
    
    
    @PostMapping("/test")
	public String test() {
		//this.logger.warn("This is working message");
		return "{\"name\":\"Gulab\"}";
	}
    
    
    @PostMapping("/register")
    public ResponseEntity<MyResponse> hello(@RequestBody User user) {
    	MyResponse response=new MyResponse();
    	if((user.getPassword()==null)||(user.getEmail()==null)||(user.getEmail()=="")||user.getPassword()=="") {
			//throw new Error("Alraedy There");
			response.setMyresponse("username or password can not be empty");
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
    	
    	if(userRepo.findByEmail(user.getEmail())!=null) {
			//throw new Error("Alraedy There");
			response.setMyresponse("Try another username");
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
    	
    	
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	//user.setEmail(request.getEmail());
    	user.setRole("USER");
    	
    	
    	
    	try {
    		
    		userRepo.save(user);
    		response.setMyresponse("Registered successfully !!");
    		return new ResponseEntity<>(response,HttpStatus.OK);
    		
		} catch (Exception e) {
			
			response.setMyresponse(e.getMessage().toString());
			
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    
		    
			// TODO: handle exception
		}
      
      
        	
        

    }

}
