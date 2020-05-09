package com.udacity.jdnd.course3.critter.pet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entities.CustomerEntity;
import com.udacity.jdnd.course3.critter.entities.PetEntity;
import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFound;
import com.udacity.jdnd.course3.critter.exceptions.ExceptionConstants;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFound;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerService;

@Service
@Transactional
public class PetService {
	@Autowired
	PetRepository petRepository;
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerService customerService;

	private static Logger log = Logger.getLogger(PetService.class);

	public PetDTO savePet(PetDTO petDTO) throws CustomerNotFound {

		Optional<CustomerEntity> customerEntity = customerRepository.findById(petDTO.getOwnerId());
		if (customerEntity.isPresent()) {

			PetEntity pet = new PetEntity(petDTO.getType(), petDTO.getName(), petDTO.getBirthDate(), petDTO.getNotes(),
					customerEntity.get());
			
			pet =  petRepository.save(pet);

			customerService.addPetToCustomer(pet, customerEntity.get());
			
			return entityToDTO(pet);
		} else {
			log.error("Customer does not exists!");
			throw new CustomerNotFound();
		}
	}

	public PetDTO getPet(Long petId) throws PetNotFound {
		PetDTO petDTO = null;

		Optional<PetEntity> pet = petRepository.findById(petId);
		if (pet.isPresent())
			petDTO = entityToDTO(pet.get());
		else
			throw new PetNotFound();

		return petDTO;
	}

	public List<PetDTO> getAllPets() {
		return petRepository.findAll().stream().map(petEntity -> entityToDTO(petEntity)).collect(Collectors.toList());
	}

	public List<PetDTO> getPetsByOwner(long ownerId) {
		Optional<CustomerEntity> customer = customerRepository.findById(ownerId);

		List<PetDTO> responsePetsList = null;

		if (customer.isPresent()) {
			responsePetsList = petRepository.findAllByCustomer(customer.get()).stream()
					.map(petEntity -> entityToDTO(petEntity)).collect(Collectors.toList());
		} else {
			log.error(ExceptionConstants.CUSTOMER_NOT_FOUND);
		}

		return responsePetsList;
	}

	public static PetDTO entityToDTO(PetEntity petEntity) {
		return new PetDTO(petEntity.getId(), petEntity.getType(), petEntity.getName(), petEntity.getCustomer().getId(),
				petEntity.getBirthDate(), petEntity.getPetNotes());
	}
}
