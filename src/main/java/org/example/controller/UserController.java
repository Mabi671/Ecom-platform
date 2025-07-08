package org.example.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Cart;
import org.example.model.User;
import org.example.repository.CartRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserRepository repository;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	UserController(UserRepository repository) {

		this.repository = repository;
	}
	@GetMapping("/all")
	List<String> showAll()
	{
		List<String> list = new ArrayList<>();
		for(User user : repository.findAll()){
			list.add(user.toString());
		}
		return list;
	}
	@PostMapping("/user")
	ResponseEntity<String> createUser(@RequestBody User newUser)
	{
		for(User user : repository.findAll()){
			if (user.getEmail().equals(newUser.getEmail())){
				return new ResponseEntity<>("Email taken", HttpStatus.CONFLICT);
			}
		}
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        repository.save(newUser);
		cartRepo.save(new Cart(newUser.getId()));
		return new ResponseEntity<>("User created", HttpStatus.ACCEPTED);
	}
}
