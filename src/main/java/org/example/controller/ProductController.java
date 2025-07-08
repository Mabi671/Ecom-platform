package org.example.controller;

import io.jsonwebtoken.Jwt;
import org.example.config.JwtService;
import org.example.model.Product;
import org.example.exception.ProductNotFoundException;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

// tag::constructor[]
@RestController
public class ProductController {

	private final ProductRepository repository;

	@Autowired
	private JwtService jwtService;


	@Autowired
	private UserRepository userRepository;

	ProductController(ProductRepository repository) {

		this.repository = repository;
	}

	@GetMapping("/products")
	public ResponseEntity<?> all() {

		List<List<String>> Products = new ArrayList<>();
		for (Product product : repository.findAll()){
			Products.add(List.of(product.toArray()));
		}
		return ResponseEntity.ok(Products);
	}
	@PostMapping("/products")
	public ResponseEntity<?> newProduct(@RequestBody Product newProduct, @RequestHeader("Authorization") String token) {
		for(Product product: repository.findAll()){
			if (product.getName().equals(newProduct.getName())){
				return new ResponseEntity<>("Product name taken", HttpStatus.CONFLICT);
			}
		}
		repository.save(newProduct);
		return ResponseEntity.ok(null);
	}
	@DeleteMapping("/products/{id}")
	ResponseEntity<?> deleteProduct(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		token = token.substring(7);
		boolean isAdmin = userRepository.findByUsername(jwtService.extractUsername(token)).get().getRole().equals("ADMIN");
		if(!isAdmin){
			return ResponseEntity.unprocessableEntity().build();
		}
		repository.deleteById(id);
		return ResponseEntity.ok(null);
	}
}
