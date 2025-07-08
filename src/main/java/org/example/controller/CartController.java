package org.example.controller;

import org.apache.juli.logging.Log;
import org.example.config.JwtService;
import org.example.dto.LoginResponse;
import org.example.exception.CartNotFoundException;
import org.example.exception.ProductNotFoundException;
import org.example.model.Cart;
import org.example.model.Product;
import org.example.repository.CartRepository;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class CartController {

	private final CartRepository repository;


	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;


	CartController(CartRepository repository) {
		this.repository = repository;
	}


	@GetMapping("cart/all")
	String seeAll(){
		StringBuilder k = new StringBuilder();
		for(Cart cart : repository.findAll()){
			k.append(cart.getId()).append(" ");
		}
		return k.toString();
	}

	@PostMapping("cart/{id}")
	ResponseEntity<?> addToCart(@PathVariable int id, @RequestBody LoginResponse response)
	{
		Long cartId = userRepository.findByUsername(jwtService.extractUsername(response.getToken())).get().getId();
		Cart cart = repository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
		cart.addProduct(id);
		repository.save(cart);
		return ResponseEntity.ok(cartId);
	}
	@DeleteMapping("cart/product/{id}")
	ResponseEntity<?> removeFromCart(@PathVariable int id, @RequestHeader("Authorization") String token)
	{
		token = token.substring(7, token.length() -1);
 		Long cartId = userRepository.findByUsername(jwtService.extractUsername(token)).get().getId();
		Cart cart = repository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
		cart.deleteProduct(id);
		repository.save(cart);
		return ResponseEntity.ok("ACCEPTED");
	}
	@DeleteMapping("/cart/{id}")
	ResponseEntity<?> deleteCart(@PathVariable Long id) {

		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/cart/products")
	ResponseEntity<?> showCartItems(@RequestHeader("Authorization") String token){
		token = token.substring(7, token.length() -1);
		Long cartId = userRepository.findByUsername(jwtService.extractUsername(token)).get().getId();
		Cart cart = repository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
		List<String[]> products = new ArrayList<>();
		List<Integer> product_ids = cart.getProducts();
		for(int product_id : product_ids){
			Product product = productRepository.findById((long) product_id).orElseThrow(() -> new ProductNotFoundException(product_id));
			String[] productData = product.toArray();
			products.add(productData);
		}
		return ResponseEntity.ok(products);
	}


}
