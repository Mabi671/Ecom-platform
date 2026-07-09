package org.example.controller;

import org.example.dto.ProductRequest;
import org.example.dto.ProductRows;
import org.example.exception.ProductNotFoundException;
import org.example.model.Product;
import org.example.repository.CartRepository;
import org.example.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductRepository productRepository;
	private final CartRepository cartRepository;

	public ProductController(ProductRepository productRepository, CartRepository cartRepository) {
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
	}

	@GetMapping
	public List<List<String>> all() {
		return productRepository.findAll().stream()
				.map(ProductRows::toRow)
				.toList();
	}

	/** Admin-only (enforced in {@code SecurityConfig}). */
	@PostMapping
	public ResponseEntity<String> create(@RequestBody ProductRequest request) {
		if (productRepository.existsByName(request.name())) {
			return new ResponseEntity<>("Product name taken", HttpStatus.CONFLICT);
		}
		productRepository.save(new Product(
				request.name(), request.description(), request.price(), request.image()));
		return new ResponseEntity<>("Product created", HttpStatus.CREATED);
	}

	/** Admin-only (enforced in {@code SecurityConfig}). Also removes the product from every cart. */
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!productRepository.existsById(id)) {
			throw new ProductNotFoundException(id);
		}
		cartRepository.removeProductFromAllCarts(id);
		productRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
