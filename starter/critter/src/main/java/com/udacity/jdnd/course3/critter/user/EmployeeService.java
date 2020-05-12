package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.udacity.jdnd.course3.critter.entities.EmployeeEntity;
import com.udacity.jdnd.course3.critter.exceptions.EmployeeNotFound;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;

	private static Logger log = Logger.getLogger(EmployeeService.class);

	public EmployeeEntity saveEmployee(EmployeeEntity employee) {
		return employeeRepository.save(employee);
	}

	public void setAvailablity(Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
		Optional<EmployeeEntity> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			EmployeeEntity updatedEmployee = employee.get();
			updatedEmployee.setWorkDays(daysAvailable);
			employeeRepository.save(updatedEmployee);
		} else {
			log.error("Employee does not exists");
		}
	}

	public EmployeeEntity getEmployee(Long employeeId) throws EmployeeNotFound{
		return this.employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFound());
	}

	public List<EmployeeEntity> getEmployeesForService(LocalDate requiredDate, Set<EmployeeSkill> requiredSkills) {
		return employeeRepository.getAllByWorkDaysContains(requiredDate.getDayOfWeek()).stream()
				.filter(employee -> employee.getSkills().containsAll(requiredSkills)).collect(Collectors.toList());
	}

}
