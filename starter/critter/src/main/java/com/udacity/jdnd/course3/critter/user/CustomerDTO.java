package com.udacity.jdnd.course3.critter.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the form that customer request and response data takes. Does not
 * map to the database directly.
 */
public class CustomerDTO {
	@JsonIgnore
	private long id;
	private String name;
	private String phoneNumber;
	private String notes;
	private List<Long> petIds;

	public CustomerDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Long> getPetIds() {
		return petIds;
	}

	public void setPetIds(List<Long> petIds) {
		this.petIds = petIds;
	}

	public CustomerDTO(String name, String phoneNumber) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public CustomerDTO(Long id, String name, String phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public CustomerDTO(Long id, String name, String phoneNumber, List<Long> petIds) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.petIds = petIds;
	}
	
}