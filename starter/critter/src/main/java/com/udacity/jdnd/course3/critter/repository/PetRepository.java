package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entities.CustomerEntity;
import com.udacity.jdnd.course3.critter.entities.PetEntity;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
	public List<PetEntity> findAllByCustomer(CustomerEntity customer);
}
