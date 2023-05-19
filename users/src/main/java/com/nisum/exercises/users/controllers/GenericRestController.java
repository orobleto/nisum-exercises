package com.nisum.exercises.users.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

public interface GenericRestController<E, ID> {

	@GetMapping(path = { "/findById/{id}" })
	ResponseEntity<?> findById(@PathVariable(name = "id") ID id);

	@PostMapping(path = { "/insert" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> insert(@RequestBody @Valid E e, BindingResult bindingResult);

	@PutMapping(path = { "/update" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> update(@RequestBody @Valid E e, BindingResult bindingResult);

	@DeleteMapping(path = { "/delete" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> delete(@RequestBody @Valid E e, BindingResult bindingResult);

	@GetMapping(path = { "/findAll" })
	ResponseEntity<?> findAll();

	ResponseEntity<?> save(E e, BindingResult bindingResult);
}
