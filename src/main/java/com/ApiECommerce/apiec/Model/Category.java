package com.ApiECommerce.apiec.Model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;



@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_category;
	
	@Basic
	private String category;
	
	@Column(name="product_category")
	@OneToMany(mappedBy="category",cascade = CascadeType.ALL)
	@JsonManagedReference(value="category - product")
	private List<Product> products;

	public Category() {
		super();
	}

	public Category(long id_category, String category, List<Product> products) {
		super();
		this.id_category = id_category;
		this.category = category;
		this.products = products;
	}

	public long getId_category() {
		return id_category;
	}

	public void setId_category(long id_category) {
		this.id_category = id_category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	
}
