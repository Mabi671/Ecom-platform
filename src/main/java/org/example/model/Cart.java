package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
	private @Id Long id;
	private final List<Integer> products = new ArrayList<>();
	public Cart(){}

	public Cart(Long id)
	{
		this.id = id;
	}


	public Long getId() {
		return id;
	}
	public List<Integer> getProducts() {
		return products;
	}

	public void addProduct(Integer product) {
		this.products.add(product);
	}
	public void deleteProduct(Integer product) {
		this.products.remove(product);
	}



}
