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

import com.nisum.exercises.users.entities.ErrorMessage;
import com.nisum.exercises.users.entities.Phone;
import com.nisum.exercises.users.entities.User;
import com.nisum.exercises.users.enums.TypeOfMessage;
import com.nisum.exercises.users.exceptions.ExceptionNisum;
import com.nisum.exercises.users.repositories.UserRepository;
import com.nisum.exercises.users.services.ResponseErrorMessageService;
import com.nisum.exercises.users.services.ValidateDataTypeService;
import com.nisum.exercises.users.utilities.StringUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = { "/users" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController implements GenericRestController<User, String> {
	private static Logger logger = LogManager.getLogger();
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResponseErrorMessageService messageService;

	@Autowired
	private ValidateDataTypeService dataTypeService;

	@Operation(summary = "Obtiene un Usuario por el id(UUID)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "404", description = "Elemento No Encontrado: ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)) }) })

	public ResponseEntity<?> findById(String id) {
		logger.debug(id);
		try {
			UUID uuid = UUID.fromString(id);

			User user = userRepository.findById(uuid).orElse(null);
			if (null == user) {
				return ResponseEntity.status(404).body(messageService.getErrorMessage(TypeOfMessage.NO_ELEMENTS, id));
			}

			return ResponseEntity.ok(user);
		} catch (IllegalArgumentException e) {
			logger.error(e);
			return ResponseEntity.status(200)
					.body(messageService.getErrorMessage(TypeOfMessage.DATA_TYPE_ERROR, e.getMessage()));
		}
	}

	@Operation(summary = "Inserta un usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "400", description = "Errores de Validacion: ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)) }),
			@ApiResponse(responseCode = "409", description = "Usuario ya Registrado: ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)) }) })

	public ResponseEntity<?> insert(@Valid User user, BindingResult bindingResult) {
		logger.debug(user);
		ErrorMessage errorMessage = getValidations(user, bindingResult);
		if (!(null == errorMessage)) {
			return ResponseEntity.status(400).body(errorMessage);
		}

		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			return ResponseEntity.status(409).body(messageService.getErrorMessage(TypeOfMessage.VALIDATION_ERROR,
					user.getEmail().concat(" es un correo ya registrado")));
		}

		try {
			return ResponseEntity.ok(userRepository.save(user));
		} catch (ExceptionNisum e) {
			return ResponseEntity.status(500)
					.body(messageService.getErrorMessage(TypeOfMessage.GENERIC_ERROR, e.getMessage()));
		}
	}

	@Operation(summary = "Actualiza un usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "400", description = "Errores de Validacion: ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)) }),
			@ApiResponse(responseCode = "404", description = "Elemento No Encontrado: ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)) }) })

	public ResponseEntity<?> update(@Valid User user, BindingResult bindingResult) {

		logger.debug(user);
		ErrorMessage errorMessage = getValidations(user, bindingResult);
		if (!(null == errorMessage)) {
			return ResponseEntity.status(400).body(errorMessage);
		}

		if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
			return ResponseEntity.status(404).body(messageService.getErrorMessage(TypeOfMessage.VALIDATION_ERROR,
					user.getEmail().concat(" No se encuentra registrado")));
		}

		try {
			User userDB = userRepository.findByEmail(user.getEmail()).get();
			// no se actualizan estos datos
			user.setId(userDB.getId());
			user.setCreated(userDB.getCreated());
			user.setLastLogin(userDB.getLastLogin());
			return ResponseEntity.ok(userRepository.save(user));
		} catch (ExceptionNisum e) {
			return ResponseEntity.status(500)
					.body(messageService.getErrorMessage(TypeOfMessage.GENERIC_ERROR, e.getMessage()));
		}
	}

	@Operation(summary = "Elimina un usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "404", description = "Elemento No Encontrado: ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)) }) })

	public ResponseEntity<?> delete(@Valid User user, BindingResult bindingResult) {
		if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
			return ResponseEntity.status(404).body(messageService.getErrorMessage(TypeOfMessage.VALIDATION_ERROR,
					user.getEmail().concat(" No se encuentra registrado")));
		}
		try {
			userRepository.delete(userRepository.findByEmail(user.getEmail()).get());
			return ResponseEntity.ok(messageService.getErrorMessage(TypeOfMessage.DELETED_ELEMENT, user.getEmail()));
		} catch (ExceptionNisum e) {
			return ResponseEntity.status(500)
					.body(messageService.getErrorMessage(TypeOfMessage.GENERIC_ERROR, e.getMessage()));
		}
	}

	@Operation(summary = "Obtiene un Usuario por el id(UUID)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class))) }),
			@ApiResponse(responseCode = "404", description = "Elemento No Encontrado: ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)) }) })

	public ResponseEntity<?> findAll() {
		List<User> users = userRepository.findAll();
		logger.debug(users);
		if (null == users || users.isEmpty()) {
			ResponseEntity.status(200).body(messageService.getErrorMessage(TypeOfMessage.NO_ELEMENTS, ""));
		}
		return ResponseEntity.ok(users);
	}

	public ResponseEntity<?> save(User user, BindingResult bindingResult) {

		return null;
	}

	private ErrorMessage getValidations(User user, BindingResult bindingResult) {
		ErrorMessage errorMessage = null;
		String validateEmail = dataTypeService.validateEmail(user.getEmail());
		String validatePassword = dataTypeService.validatePassword(user.getPassword());
		if (bindingResult.hasErrors()) {
			errorMessage = messageService.getErrorMessage(TypeOfMessage.VALIDATION_ERROR, bindingResult);
		}

		if (!StringUtils.isEmpty(validateEmail)) {
			errorMessage = getErrorMessage(errorMessage, validateEmail);
		}

		if (!StringUtils.isEmpty(validatePassword)) {
			errorMessage = getErrorMessage(errorMessage, validatePassword);
		}

		for (Phone phone : user.getPhones()) {
			String validatePhone = dataTypeService.validatePhone(phone);
			if (!StringUtils.isEmpty(validatePhone)) {
				errorMessage = getErrorMessage(errorMessage, phone.toString().concat(": ").concat(validatePhone));
			}
		}

		return errorMessage;
	}

	private ErrorMessage getErrorMessage(ErrorMessage errorMessage, String message) {
		if (!(errorMessage == null)) {
			errorMessage.setMessage(errorMessage.getMessage().concat("; ").concat(message));
			return errorMessage;
		}
		return ErrorMessage.builder().message(message).build();
	}

}
