package org.example.controller;

import org.example.dto.RegisterRequest;
import org.example.dto.UserResponse;
import org.example.model.Cart;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.CartRepository;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserRepository userRepository,
	                      CartRepository cartRepository,
	                      PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/** Admin-only overview of registered users; never exposes password hashes. */
	@GetMapping("/all")
	public List<UserResponse> allUsers() {
		return userRepository.findAll().stream()
				.map(UserResponse::from)
				.toList();
	}

	@PostMapping("/user")
	@Transactional
	public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			return new ResponseEntity<>("Email taken", HttpStatus.CONFLICT);
		}
		if (userRepository.existsByUsername(request.username())) {
			return new ResponseEntity<>("Username taken", HttpStatus.CONFLICT);
		}

		// The role is always USER here; admins are only created through seeding.
		User user = userRepository.save(new User(
				request.username(),
				passwordEncoder.encode(request.password()),
				request.email(),
				Role.USER));
		cartRepository.save(new Cart(user.getId()));

		return new ResponseEntity<>("User created", HttpStatus.CREATED);
	}
}
