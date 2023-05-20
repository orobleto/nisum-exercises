package com.nisum.exercises.users.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.nisum.exercises.users.entities.Phone;

@SpringBootTest
class ValidateDataTypeServiceTest {
	@Autowired
	private ValidateDataTypeService dataTypeService;

	@Value("${regex.password.message}")
	private String passwordMessage;

	@Value("${regex.email.message}")
	private String emailMesssage;

	@Test
	void testBadEmail() {
		String email = "aaaaaaa@dominio";
		assertEquals(emailMesssage, dataTypeService.validateEmail(email));
	}

	@Test
	void testBadPassword() {
		String password = "12345678";
		assertEquals(passwordMessage, dataTypeService.validatePassword(password));

	}

	@Test
	void testGoodEmail() {
		String email = "aaaaaaa@dominio.cl";
		assertEquals(null, dataTypeService.validateEmail(email));
	}

	@Test
	void testGoodPassword() {
		String password = "Aaaa1234";
		assertEquals(null, dataTypeService.validatePassword(password));

	}

	@Test
	void testBadPhone() {
		Phone phone = Phone.builder().countryCode("hh").cityCode("a").number("hggg555").build();
		assertEquals(
				"El codigo de pais [countryCode] debe ser numerico; El codigo de ciudad [cityCode] debe ser numerico; El numero [number] debe ser numerico",
				dataTypeService.validatePhone(phone));

	}

	@Test
	void testGoodPhone() {
		Phone phone = Phone.builder().countryCode("56").cityCode("9").number("22025551").build();
		assertEquals(null, dataTypeService.validatePhone(phone));

	}

}
