/**
 * For CRUD of Boat Management
 * 
 * @author Leakhena SUON
 * @version 1.0
 * @since 2024-05-01
 */
package com.boat.management.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boat.management.security.services.TokenValidation;
import com.boat.management.model.Boat;
import com.boat.management.repository.BoatRepository;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BoatController {

	@Autowired
	BoatRepository boatRepository;
	
	@Autowired
	private TokenValidation tokenValidation;

	@GetMapping("/boats")
	public ResponseEntity<List<Boat>> getAllBoats(HttpServletRequest request,@RequestParam(required = false) String name) {
		String token = request.getHeader("Authorization");
		
		try {
				List<String> userRoles = tokenValidation.authenToken(token);
				if (userRoles != null && userRoles.contains("ROLE_ADMIN")) 
					try {
						
						List<Boat> boats = new ArrayList<Boat>();

						if (name == null)
							boatRepository.findAll().forEach(boats::add);
						else
							boatRepository.findByNameContaining(name).forEach(boats::add);

						if (boats.isEmpty()) {
							return new ResponseEntity<>(HttpStatus.NO_CONTENT);
						}

						return new ResponseEntity<>(boats, HttpStatus.OK);
					} catch (Exception e) {
						return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
					}
				
				else  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			
		}
		catch (Exception e) {
			  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		     } 
	}

	@GetMapping("/boat/{id}")
	public ResponseEntity<Boat> getTutorialById(HttpServletRequest request,@PathVariable("id") long id) {
		String token = request.getHeader("Authorization");
		try {
			List<String> userRoles = tokenValidation.authenToken(token);
			if (userRoles != null && userRoles.contains("ROLE_ADMIN")) 
				try {
					
					Optional<Boat> BoatData = boatRepository.findById(id);

					if (BoatData.isPresent()) {
						return new ResponseEntity<>(BoatData.get(), HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					}
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			
			else  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		}catch (Exception e) {
		  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	     } 
	}

	@PostMapping("/boat")
	public ResponseEntity<Boat> createBoat(HttpServletRequest request,@RequestBody Boat boat) {
		String token = request.getHeader("Authorization");
		try {
			List<String> userRoles = tokenValidation.authenToken(token);
			if (userRoles != null && userRoles.contains("ROLE_ADMIN")) 
				try {
					Boat _boat = boatRepository
							.save(new Boat(boat.getName(), boat.getDescription()));
					return new ResponseEntity<>(_boat, HttpStatus.CREATED);
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}

			else  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		}catch (Exception e) {
		  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	     } 

	}

	@PutMapping("/boat/{id}")
	public ResponseEntity<Boat> updateTutorial(HttpServletRequest request,@PathVariable("id") long id, @RequestBody Boat boat) {
		String token = request.getHeader("Authorization");
		try {
			List<String> userRoles = tokenValidation.authenToken(token);
			if (userRoles != null && userRoles.contains("ROLE_ADMIN")) {
				Optional<Boat> boatData = boatRepository.findById(id);

				if (boatData.isPresent()) {
					Boat _boat = boatData.get();
					_boat.setName(boat.getName());
					_boat.setDescription(boat.getDescription());
					return new ResponseEntity<>(boatRepository.save(_boat), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
			else  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		}catch (Exception e) {
		  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	     } 
	}

	@DeleteMapping("/boat/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			boatRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/boats")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			boatRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
