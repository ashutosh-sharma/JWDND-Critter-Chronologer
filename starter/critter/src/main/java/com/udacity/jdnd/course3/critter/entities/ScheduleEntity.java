package com.udacity.jdnd.course3.critter.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Table
@Entity
public class ScheduleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToMany(targetEntity = EmployeeEntity.class)
	private List<EmployeeEntity> employees;

	@ManyToMany(targetEntity = PetEntity.class)
	private List<PetEntity> pets;

	private LocalDate date;

	@ElementCollection
	private Set<EmployeeSkill> activities;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<EmployeeEntity> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeEntity> employees) {
		this.employees = employees;
	}

	public List<PetEntity> getPets() {
		return pets;
	}

	public void setPets(List<PetEntity> pets) {
		this.pets = pets;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Set<EmployeeSkill> getActivities() {
		return activities;
	}

	public void setActivities(Set<EmployeeSkill> activities) {
		this.activities = activities;
	}

	public ScheduleEntity() {

	}

	public ScheduleEntity(List<EmployeeEntity> employees, List<PetEntity> pets, LocalDate date,
			Set<EmployeeSkill> activities) {
		super();
		this.employees = employees;
		this.pets = pets;
		this.date = date;
		this.activities = activities;
	}

}
