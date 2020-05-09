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

	public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
		List<EmployeeEntity> employees = scheduleDTO.getEmployeeIds().stream()
				.map(employeeId -> employeeRepository.findById(employeeId).get()).collect(Collectors.toList());
		List<PetEntity> pets = scheduleDTO.getPetIds().stream().map(petId -> petRepository.findById(petId).get())
				.collect(Collectors.toList());

		return entityToDTO(scheduleRepository
				.save(new ScheduleEntity(employees, pets, scheduleDTO.getDate(), scheduleDTO.getActivities())));
	}

	public List<ScheduleDTO> getAllSchedule() {
		return scheduleRepository.findAll().stream().map(scheduleEntity -> entityToDTO(scheduleEntity))
				.collect(Collectors.toList());
	}

	public List<ScheduleDTO> getScheduleForPet(Long petId) {
		return scheduleRepository.getAllByPetsContains(petRepository.getOne(petId)).stream()
				.map(scheduleEntity -> entityToDTO(scheduleEntity)).collect(Collectors.toList());
	}

	public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {
		return scheduleRepository.getAllByEmployeesContains(employeeRepository.getOne(employeeId)).stream()
				.map(scheduleEntity -> entityToDTO(scheduleEntity)).collect(Collectors.toList());
	}

	public List<ScheduleDTO> getScheduleByPet(Long petId) {
		return scheduleRepository.getAllByPetsContains(petRepository.getOne(petId)).stream()
				.map(scheduleEntity -> entityToDTO(scheduleEntity)).collect(Collectors.toList());
	}

	public List<ScheduleDTO> getScheduleByCustomer(Long customerId) {
		return scheduleRepository.getAllByPetsIn(customerRepository.getOne(customerId).getPets()).stream()
				.map(scheduleEntity -> entityToDTO(scheduleEntity)).collect(Collectors.toList());
	}

	public ScheduleDTO entityToDTO(ScheduleEntity scheduleEntity) {
		List<Long> employeeIds = scheduleEntity.getEmployees().stream().map(employee -> employee.getId())
				.collect(Collectors.toList());
		List<Long> petIds = scheduleEntity.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
		return new ScheduleDTO(scheduleEntity.getId(), employeeIds, petIds, scheduleEntity.getDate(),
				scheduleEntity.getActivities());
	}

}
