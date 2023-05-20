package com.nisum.exercises.users.services;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.nisum.exercises.users.entities.ErrorMessage;
import com.nisum.exercises.users.enums.TypeOfMessage;

@Service
public class ResponseErrorMessageService {
	private static Logger logger = LogManager.getLogger();
	public ErrorMessage getErrorMessage(TypeOfMessage typeOfMessage, Object object) {
		String message = null;
		if (object instanceof String) {
			message = (String) object;
		} else if (object instanceof BindingResult) {
			BindingResult bindingResult = (BindingResult) object;
			message = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.joining("; "));
		}
		ErrorMessage errorMessage = ErrorMessage.builder().message(typeOfMessage.getMessage().concat(message)).build();
		logger.debug(errorMessage);
		return errorMessage;

	}
}
