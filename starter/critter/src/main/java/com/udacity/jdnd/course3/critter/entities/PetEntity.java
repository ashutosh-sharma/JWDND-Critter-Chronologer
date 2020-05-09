package com.udacity.jdnd.course3.critter.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.udacity.jdnd.course3.critter.pet.PetType;

@Entity
@Table
public class PetEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private PetType type;

	private String name;

	private LocalDate birthDate;

	private String petNotes;

	@ManyToOne(targetEntity = CustomerEntity.class, optional = false)
	private CustomerEntity customer;
	
	public PetEntity(){
		
	}
	
	public PetType getType() {
		return type;
	}

	public void setType(PetType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPetNotes() {
		return petNotes;
	}

	public void setPetNotes(String petNotes) {
		this.petNotes = petNotes;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public long getId() {
		return id;
	}
	
	public PetEntity(Long id) {
		this.id = id;
	}

	public PetEntity(PetType type, String name, LocalDate birthDate, String petNotes, CustomerEntity customer) {
		super();
		this.type = type;
		this.name = name;
		this.birthDate = birthDate;
		this.petNotes = petNotes;
		this.customer = customer;
	}
	

}
