package org.example.config;

import org.example.model.Cart;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.CartRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class DataInitializer {

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository,
	                               CartRepository cartRepository,
	                               PasswordEncoder passwordEncoder,
	                               @Value("${admin.username}") String adminUsername,
	                               @Value("${admin.password}") String adminPassword,
	                               @Value("${admin.email}") String adminEmail) {
		return args -> {
			if (userRepository.existsByUsername(adminUsername)) {
				return;
			}
			User admin = userRepository.save(
					new User(adminUsername, passwordEncoder.encode(adminPassword), adminEmail, Role.ADMIN));
			cartRepository.save(new Cart(admin.getId()));
		};
	}
}
