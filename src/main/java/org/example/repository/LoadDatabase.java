package org.example.repository;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
class LoadDatabase {
	@Value("${admin.password}")
	private String adminPassword;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			/// xd
			if(userRepository.existsById(1L)){return;}
			User admin = new User("admin", passwordEncoder.encode(adminPassword), "admin@gmail.com", "ADMIN");
			userRepository.save(admin);
		};
	}
}
