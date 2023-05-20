package com.nisum.exercises.users.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nisum.exercises.users.entities.ErrorMessage;
import com.nisum.exercises.users.enums.TypeOfMessage;

@SpringBootTest
class ResponseErrorMessageServiceTest {
	private static Logger logger = LogManager.getLogger();
	@Autowired
	private ResponseErrorMessageService messageService;

	@Test
	void testBadUUID() {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.message("No es el tipo de dato requerido: Invalid UUID string: 1").build();
		String message = "";
		try {
			String id = "1";
			UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			logger.error(e);
			message = e.getMessage();
		}
		assertEquals(errorMessage, messageService.getErrorMessage(TypeOfMessage.DATA_TYPE_ERROR, message));
	}

}
