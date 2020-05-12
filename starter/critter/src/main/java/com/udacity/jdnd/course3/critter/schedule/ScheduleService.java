package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entities.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entities.PetEntity;
import com.udacity.jdnd.course3.critter.entities.ScheduleEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

@Service
@Transactional
public class ScheduleService {
	@Autowired
	ScheduleRepository scheduleRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	CustomerRepository customerRepository;

	public ScheduleEntity saveSchedule(ScheduleEntity schedule, List<Long> empList, List<Long> petList) {
		List<EmployeeEntity> employees = empList.stream()
				.map(employeeId -> employeeRepository.findById(employeeId).get()).collect(Collectors.toList());
		List<PetEntity> pets = petList.stream().map(petId -> petRepository.findById(petId).get())
				.collect(Collectors.toList());

		return scheduleRepository
				.save(new ScheduleEntity(employees, pets, schedule.getDate(), schedule.getActivities()));
	}

	public List<ScheduleEntity> getAllSchedule() {
		return scheduleRepository.findAll();
	}

	public List<ScheduleEntity> getScheduleForPet(Long petId) {
		return scheduleRepository.getAllByPetsContains(petRepository.getOne(petId));
	}

	public List<ScheduleEntity> getScheduleForEmployee(Long employeeId) {
		return scheduleRepository.getAllByEmployeesContains(employeeRepository.getOne(employeeId));
	}

	public List<ScheduleEntity> getScheduleByCustomer(Long customerId) {
		return scheduleRepository.getAllByPetsIn(customerRepository.getOne(customerId).getPets());
	}

}
