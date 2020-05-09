package com.udacity.jdnd.course3.critter.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



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

	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		CustomerEntity customer = new CustomerEntity();
		customer.setName(customerDTO.getName());
		customer.setPhoneNumber(customerDTO.getPhoneNumber());
		
		return entityToDTO(customerRepository.save(customer));
	}

	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream().map(employee -> entityToDTO(employee))
				.collect(Collectors.toList());
	}

	public CustomerDTO getOwnerByPet(long petId) {
		Optional<PetEntity> pet = petRepository.findById(petId);
		CustomerDTO customerResponse = null;
		if (pet.isPresent()) {
			customerResponse = entityToDTO(pet.get().getCustomer());
		} else {
			log.error(ExceptionConstants.PET_NOT_FOUND);
		}
		return customerResponse;

	}

	public static CustomerDTO entityToDTO(CustomerEntity customerEntity) {
		List<Long> pets = new ArrayList<Long>();
		if (customerEntity.getPets() != null) {
			pets = customerEntity.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
		}

		return new CustomerDTO(customerEntity.getId(), customerEntity.getName(), customerEntity.getPhoneNumber(), pets);
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
