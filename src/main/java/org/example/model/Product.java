package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Product {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(length = 2000)
	private String description;

	@Column(nullable = false)
	private BigDecimal price;

	private String image;

	protected Product() {
	}

	public Product(String name, String description, BigDecimal price, String image) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}
}
