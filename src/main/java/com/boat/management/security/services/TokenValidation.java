package com.boat.management.security.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class TokenValidation {
	 @Value("${usermanagement.url}")
	 private String userManagementUrl;
	 private final RestTemplate restTemplate;
	 
	 public TokenValidation( RestTemplate restTemplate) {
		    
	      this.restTemplate = restTemplate;
	  }
	  
	 public List<String> authenToken(String token) {
		  String url = userManagementUrl + "/api/auth/validatetoken";
		  HttpHeaders headers = new HttpHeaders();

		  if (!token.startsWith("Bearer ")) {
		        token = "Bearer " + token;
		  }

		  headers.set("Authorization", token);
		  HttpEntity<String> entity = new HttpEntity<>(headers);
		    
		  try {
		        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		     // Parse JSON response to extract user roles
		        ObjectMapper objectMapper = new ObjectMapper();
		        List<String> userRoles = objectMapper.readValue(response.getBody(), new TypeReference<List<String>>() {});
		        return userRoles;
		  }
		  catch (Exception e) {
		    	 String errorMessage = "Failed to validate Token: " + e.getMessage();
		         System.err.println(errorMessage); // Replace with proper logging
		         return new ArrayList<>();
		  }
	 }
}
