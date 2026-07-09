package org.example.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Cart {

	// A cart is created together with its user and shares the user's id.
	@Id
	private Long id;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "cart_products", joinColumns = @JoinColumn(name = "cart_id"))
	@Column(name = "product_id")
	private List<Long> products = new ArrayList<>();

	protected Cart() {
	}

	public Cart(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public List<Long> getProducts() {
		return Collections.unmodifiableList(products);
	}

	public void addProduct(Long productId) {
		products.add(productId);
	}

	public void removeProduct(Long productId) {
		products.remove(productId);
	}

	public void removeAllOccurrences(Long productId) {
		products.removeIf(productId::equals);
	}
}
