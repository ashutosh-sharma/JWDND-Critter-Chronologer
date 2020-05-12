package com.udacity.jdnd.course3.critter.pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public PetEntity savePet(PetEntity pet, long ownerId) throws CustomerNotFound {

		Optional<CustomerEntity> customerEntity = customerRepository.findById(ownerId);

		if (customerEntity.isPresent()) {
			pet.setCustomer(customerEntity.get());
			pet = petRepository.save(pet);

			customerService.addPetToCustomer(pet, customerEntity.get());
			return pet;
		} else {
			log.error("Customer does not exists!");
			throw new CustomerNotFound();
		}
	}

	public PetEntity getPet(Long petId) throws PetNotFound {
		return this.petRepository.findById(petId).orElseThrow(() -> new PetNotFound());
	}

	public List<PetEntity> getAllPets() {
		return petRepository.findAll();
	}

	public List<PetEntity> getPetsByOwner(long ownerId) {
		Optional<CustomerEntity> customer = customerRepository.findById(ownerId);

		List<PetEntity> petList = new ArrayList<PetEntity>();

		if (customer.isPresent()) {
			petList = petRepository.findAllByCustomer(customer.get());
		} else {
			log.error(ExceptionConstants.CUSTOMER_NOT_FOUND);
		}

		return petList;

	}

}
