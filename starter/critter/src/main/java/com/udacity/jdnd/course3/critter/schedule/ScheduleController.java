package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.entities.ScheduleEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;

	@PostMapping
	public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
		return entityToDTO(
				scheduleService.saveSchedule(new ScheduleEntity(scheduleDTO.getDate(), scheduleDTO.getActivities()),
						scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
	}

	@GetMapping
	public List<ScheduleDTO> getAllSchedules() {
		return scheduleService.getAllSchedule().stream().map(scheduleEntity -> entityToDTO(scheduleEntity))
				.collect(Collectors.toList());
	}

	@GetMapping("/pet/{petId}")
	public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
		return scheduleService.getScheduleForPet(petId).stream().map(scheduleEntity -> entityToDTO(scheduleEntity))
				.collect(Collectors.toList());
	}

	@GetMapping("/employee/{employeeId}")
	public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
		return scheduleService.getScheduleForEmployee(employeeId).stream()
				.map(scheduleEntity -> entityToDTO(scheduleEntity)).collect(Collectors.toList());
	}

	@GetMapping("/customer/{customerId}")
	public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
		return scheduleService.getScheduleByCustomer(customerId).stream()
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
