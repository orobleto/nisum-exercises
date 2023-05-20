package com.nisum.exercises.users.enums;

public enum TypeOfMessage {
	VALIDATION_ERROR("Error de validación: "), GENERIC_ERROR("Error Generico: "),
	NO_ELEMENTS("No hay elementos que mostrar con: "), DELETED_ELEMENT("Elemento Eliminado: "),
	DATA_TYPE_ERROR("No es el tipo de dato requerido: ");

	private String message;

	private TypeOfMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
