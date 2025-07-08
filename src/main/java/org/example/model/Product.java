package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Product {

	private @Id @GeneratedValue Long id;
	private String name;
	private String description;
	private Float price;
	private String image;

	Product(){}

	Product(String name, String description, Float price, String image) {

		this.name = name;
		this.description = description;
		this.price = price;
		this.image = image;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String[] toArray(){
		return new String[]{this.getName(), this.getDescription(), this.getPrice().toString(), this.getId().toString(), this.getImage()};
	}
}
