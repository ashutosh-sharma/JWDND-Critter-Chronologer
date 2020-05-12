package com.udacity.jdnd.course3.critter.pet;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.entities.PetEntity;
import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFound;
import com.udacity.jdnd.course3.critter.exceptions.ExceptionConstants;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFound;
import javassist.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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
			PetEntity pet = new PetEntity(petDTO.getType(), petDTO.getName(), petDTO.getBirthDate(), petDTO.getNotes());
			petDTO = entityToDTO(petService.savePet(pet, petDTO.getOwnerId()));
		} catch (CustomerNotFound e) {
			petDTO = null;
			log.error(ExceptionConstants.CUSTOMER_NOT_FOUND);
		}
		return petDTO;
	}

	@GetMapping("/{petId}")
	public PetDTO getPet(@PathVariable long petId) {
		PetDTO petDTO = null;
		try {
			return entityToDTO(petService.getPet(petId));
		} catch (PetNotFound e) {
			e.printStackTrace();
			log.error(ExceptionConstants.PET_NOT_FOUND);
		}
		return petDTO;
	}

	@GetMapping
	public List<PetDTO> getPets() {
		return petService.getAllPets().stream().map(petEntity -> entityToDTO(petEntity)).collect(Collectors.toList());
	}

	@GetMapping("/owner/{ownerId}")
	public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
		return petService.getPetsByOwner(ownerId).stream().map(petEntity -> entityToDTO(petEntity))
				.collect(Collectors.toList());
	}

	public static PetDTO entityToDTO(PetEntity petEntity) {
		return new PetDTO(petEntity.getId(), petEntity.getType(), petEntity.getName(), petEntity.getCustomer().getId(),
				petEntity.getBirthDate(), petEntity.getPetNotes());
	}
}
