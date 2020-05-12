package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.entities.CustomerEntity;
import com.udacity.jdnd.course3.critter.entities.EmployeeEntity;
import com.udacity.jdnd.course3.critter.exceptions.EmployeeNotFound;
import com.udacity.jdnd.course3.critter.exceptions.ExceptionConstants;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into
 * separate user and customer controllers would be fine too, though that is not
 * part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static Logger log = Logger.getLogger(UserController.class);

	@Autowired
	CustomerService customerService;

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/customer")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
		return entityToDTO(
				customerService.saveCustomer(new CustomerEntity(customerDTO.getName(), customerDTO.getPhoneNumber())));
	}

	@GetMapping("/customer")
	public List<CustomerDTO> getAllCustomers() {
		return customerService.getAllCustomers().stream().map(customer -> entityToDTO(customer))
				.collect(Collectors.toList());
	}

	@GetMapping("/customer/pet/{petId}")
	public CustomerDTO getOwnerByPet(@PathVariable long petId) {
		return entityToDTO(customerService.getOwnerByPet(petId));
	}

	@PostMapping("/employee")
	public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return entityToDTO(employeeService.saveEmployee(
				new EmployeeEntity(employeeDTO.getName(), employeeDTO.getSkills(), employeeDTO.getDaysAvailable())));
	}

	// Converted from @PostMapping to @GetMapping - wrong endpoint in provided
	// postman requests
	@GetMapping("/employee/{employeeId}")
	public EmployeeDTO getEmployee(@PathVariable long employeeId) {
		EmployeeDTO employee = new EmployeeDTO();
		try {
			employee = entityToDTO(employeeService.getEmployee(employeeId));
		} catch (EmployeeNotFound e) {
			e.printStackTrace();
			log.error(ExceptionConstants.EMPLOYEE_NOT_FOUND);
		}
		return employee;
	}

	@PutMapping("/employee/{employeeId}")
	public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
		employeeService.setAvailablity(daysAvailable, employeeId);
	}

	@GetMapping("/employee/availability")
	public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
		return employeeService.getEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills()).stream()
				.map(employee -> entityToDTO(employee)).collect(Collectors.toList());
	}

	public static CustomerDTO entityToDTO(CustomerEntity customerEntity) {
		List<Long> pets = new ArrayList<Long>();
		if (customerEntity.getPets() != null) {
			pets = customerEntity.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
		}

		return new CustomerDTO(customerEntity.getId(), customerEntity.getName(), customerEntity.getPhoneNumber(), pets);
	}

	public static EmployeeDTO entityToDTO(EmployeeEntity employeeEntity) {
		return new EmployeeDTO(employeeEntity.getId(), employeeEntity.getName(), employeeEntity.getSkills(),
				employeeEntity.getWorkDays());
	}
}
