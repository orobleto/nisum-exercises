package com.nisum.exercises.users.settings;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.nisum.exercises.users.entities.Phone;
import com.nisum.exercises.users.entities.User;
import com.nisum.exercises.users.repositories.UserRepository;

@Component
public class DataInitialize implements CommandLineRunner {

	private static Logger logger = LogManager.getLogger();
	@Autowired
	private UserRepository userRepository;

	public void run(String... args) throws Exception {
		users();
	}

	private void users() {
		User user = User.builder().name("Octavio Robleto").email("orobleto@nisum.cl").password("1234").isActive(true)
				.build();
		Set<Phone> phones = Set.of(Phone.builder().countryCode("56").cityCode("9").number("922025551").build());
		user.setPhones(phones);
		userRepository.save(user);
		logger.info(userRepository.findByEmail("orobleto@nisum.cl"));

	}

}
