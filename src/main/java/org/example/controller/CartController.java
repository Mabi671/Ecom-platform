package org.example.controller;

import org.example.dto.ProductRows;
import org.example.exception.CartNotFoundException;
import org.example.exception.ProductNotFoundException;
import org.example.model.Cart;
import org.example.model.User;
import org.example.repository.CartRepository;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public CartController(CartRepository cartRepository,
	                      ProductRepository productRepository,
	                      UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	/** Admin-only (enforced in {@code SecurityConfig}). */
	@GetMapping("/all")
	public List<Long> allCartIds() {
		return cartRepository.findAll().stream()
				.map(Cart::getId)
				.toList();
	}

	@PostMapping("/{productId}")
	public ResponseEntity<Void> addToCart(@PathVariable Long productId, Authentication authentication) {
		if (!productRepository.existsById(productId)) {
			throw new ProductNotFoundException(productId);
		}
		Cart cart = cartOf(authentication);
		cart.addProduct(productId);
		cartRepository.save(cart);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Void> removeFromCart(@PathVariable Long productId, Authentication authentication) {
		Cart cart = cartOf(authentication);
		cart.removeProduct(productId);
		cartRepository.save(cart);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/products")
	public List<List<String>> cartItems(Authentication authentication) {
		return cartOf(authentication).getProducts().stream()
				.map(productRepository::findById)
				.flatMap(Optional::stream)
				.map(ProductRows::toRow)
				.toList();
	}

	/** Admin-only (enforced in {@code SecurityConfig}). */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
		if (!cartRepository.existsById(id)) {
			throw new CartNotFoundException(id);
		}
		cartRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	/** Resolves the cart of the authenticated caller instead of trusting client-supplied ids. */
	private Cart cartOf(Authentication authentication) {
		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(authentication.getName()));
		return cartRepository.findById(user.getId())
				.orElseThrow(() -> new CartNotFoundException(user.getId()));
	}
}
