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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_brand;
	
	@Basic
	@NotBlank
	private String brand_name;
	
	@Column(name = "product_brand")
	@OneToMany(mappedBy="brand",cascade = CascadeType.ALL)
	@JsonManagedReference(value = "brand - product")
	private List<Product> products;

	public Brand() {
		super();
	}

	public Brand(Long id_brand, String brand_name, List<Product> products) {
		super();
		this.id_brand = id_brand;
		this.brand_name = brand_name;
		this.products = products;
	}

	public Long getId_brand() {
		return id_brand;
	}

	public void setId_brand(Long id_brand) {
		this.id_brand = id_brand;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	
	
}
