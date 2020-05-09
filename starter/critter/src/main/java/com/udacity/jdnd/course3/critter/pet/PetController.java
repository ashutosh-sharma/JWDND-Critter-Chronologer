package com.udacity.jdnd.course3.critter.pet;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFound;
import com.udacity.jdnd.course3.critter.exceptions.ExceptionConstants;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFound;

import javassist.NotFoundException;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
//TODO: Remove all stack traces prints
@RestController
@RequestMapping("/pet")
public class PetController {

	private static Logger log = Logger.getLogger(PetController.class);

	@Autowired
	PetService petService;

	// Modified API request for POST: http://localhost:8082/pet/
	// And ownerId should be passed in the request body of petDTO

	// Sample post request body for savePet
	/******
	 * { "type": "CAT", "name": "Kilo", "birthDate": "2019-12-16T04:43:57.995Z",
	 * "notes": "HI KILO", "ownerId": 1 }
	 * 
	 * @throws NotFound
	 * @throws NotFoundException
	 ***/
	@PostMapping
	public PetDTO savePet(@RequestBody PetDTO petDTO) {

		try {
			petDTO = petService.savePet(petDTO);
		} catch (CustomerNotFound e) {
			// return empty petDTO if associated owner not found
			petDTO = null;
			log.error(ExceptionConstants.CUSTOMER_NOT_FOUND);
		}
		return petDTO;
	}

	@GetMapping("/{petId}")
	public PetDTO getPet(@PathVariable long petId) {
		PetDTO petDTO = null;
		try {
			petDTO = petService.getPet(petId);
		} catch (PetNotFound e) {
			// return empty petDTO if associated owner not found
			e.printStackTrace();
			log.error(ExceptionConstants.PET_NOT_FOUND);
		}
		return petDTO;
	}

	@GetMapping
	public List<PetDTO> getPets() {
		return petService.getAllPets();
	}

	@GetMapping("/owner/{ownerId}")
	public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
		return petService.getPetsByOwner(ownerId);
	}
}
