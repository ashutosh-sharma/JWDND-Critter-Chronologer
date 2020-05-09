package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entities.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entities.PetEntity;
import com.udacity.jdnd.course3.critter.entities.ScheduleEntity;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

	public List<ScheduleEntity> getAllByPetsContains(PetEntity pet);

	public List<ScheduleEntity> getAllByEmployeesContains(EmployeeEntity employee);
	
	public List<ScheduleEntity> getAllByPetsIn(List<PetEntity> pets);

}