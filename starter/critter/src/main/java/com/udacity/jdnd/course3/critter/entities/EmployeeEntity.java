/**
 * 
 */
package com.udacity.jdnd.course3.critter.entities;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

/**
 * @author ashutosh_sharma
 *
 */
@Entity
@Table
public class EmployeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	// Using set in place of list to keep skills and workDays unique
	@ElementCollection
	private Set<EmployeeSkill> skills;

	@ElementCollection
	private Set<DayOfWeek> workDays;

	public EmployeeEntity() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<EmployeeSkill> getSkills() {
		return skills;
	}

	public void setSkills(Set<EmployeeSkill> skills) {
		this.skills = skills;
	}

	public Set<DayOfWeek> getWorkDays() {
		return workDays;
	}

	public void setWorkDays(Set<DayOfWeek> workDays) {
		this.workDays = workDays;
	}

	public long getId() {
		return id;
	}

	public EmployeeEntity(String name, Set<EmployeeSkill> skills) {
		super();
		this.name = name;
		this.skills = skills;
	}
	
	public EmployeeEntity(Long id, String name, Set<EmployeeSkill> skills) {
		super();
		this.id=id;
		this.name = name;
		this.skills = skills;
	}
}
