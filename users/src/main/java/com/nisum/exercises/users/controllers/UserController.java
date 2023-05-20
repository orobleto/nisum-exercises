package com.nisum.exercises.users.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.exercises.users.entities.User;
import com.nisum.exercises.users.enums.TypeOfMessage;
import com.nisum.exercises.users.repositories.UserRepository;
import com.nisum.exercises.users.services.ResponseErrorMessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = { "/users" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController implements GenericRestController<User, String> {
	private static Logger logger = LogManager.getLogger();
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResponseErrorMessageService messageService;

	public ResponseEntity<?> findById(String id) {
		logger.debug(id);
		try {
			UUID uuid = UUID.fromString(id);
			
			User user = userRepository.findById(uuid).orElse(null);
			if (null == user) {
				return ResponseEntity.status(200)
						.body(messageService.getErrorMessage(TypeOfMessage.NO_ELEMENTS, id));
			}

			return ResponseEntity.ok(user);
		} catch (IllegalArgumentException e) {
			logger.error(e);
			return ResponseEntity.status(200)
					.body(messageService.getErrorMessage(TypeOfMessage.DATA_TYPE_ERROR, e.getMessage()));
		}
	}

	public ResponseEntity<?> insert(@Valid User user, BindingResult bindingResult) {

		return null;
	}

	public ResponseEntity<?> update(@Valid User user, BindingResult bindingResult) {

		return null;
	}

	public ResponseEntity<?> delete(@Valid User user, BindingResult bindingResult) {

		return null;
	}

	public ResponseEntity<?> findAll() {
		List<User> users = userRepository.findAll();
		logger.debug(users);
		if (null == users || users.isEmpty()) {
			ResponseEntity.status(204).body(messageService.getErrorMessage(TypeOfMessage.NO_ELEMENTS, ""));
		}
		return ResponseEntity.ok(users);
	}

	public ResponseEntity<?> save(User user, BindingResult bindingResult) {

		return null;
	}

}
