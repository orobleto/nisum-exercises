package com.nisum.exercises.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nisum.exercises.users.entities.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
