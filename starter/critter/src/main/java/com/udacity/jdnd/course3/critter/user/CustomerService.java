package com.udacity.jdnd.course3.critter.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entities.CustomerEntity;
import com.udacity.jdnd.course3.critter.entities.PetEntity;
import com.udacity.jdnd.course3.critter.exceptions.ExceptionConstants;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

@Service
@Transactional
public class CustomerService {

	private static Logger log = Logger.getLogger(CustomerService.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PetRepository petRepository;

	public CustomerEntity saveCustomer(CustomerEntity customer) {
		return customerRepository.save(customer);
	}

	public List<CustomerEntity> getAllCustomers() {
		return customerRepository.findAll();
	}

	public CustomerEntity getOwnerByPet(long petId) {
		Optional<PetEntity> pet = petRepository.findById(petId);
		CustomerEntity customerResponse = null;
		if (pet.isPresent()) {
			customerResponse = pet.get().getCustomer();
		} else {
			log.error(ExceptionConstants.PET_NOT_FOUND);
		}
		return customerResponse;

	}

	public void addPetToCustomer(PetEntity pet, CustomerEntity customer) {
		List<PetEntity> pets = customer.getPets();
		if (pets != null) {
			pets.add(pet);
		} else {
			pets = new ArrayList<PetEntity>();
			pets.add(pet);
		}
		customer.setPets(pets);

		customerRepository.save(customer);
		log.info("Pet added to Customer");

	}
}
