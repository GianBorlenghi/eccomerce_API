package com.ApiECommerce.apiec.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_file;
	
	@Basic
	private String file_name;
	
	@Basic
	private String url;
	
	@Basic
	private String public_id;
		
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "id_product")
	@JsonBackReference(value = "file - product")
	private Product product;

	public File() {
		super();
	}

	public File(Long id_file, String file_name, String url, Product product,String public_id) {
		super();
		this.id_file = id_file;
		this.file_name = file_name;
		this.url = url;
		this.product = product;
		this.public_id = public_id;
	}

	public File(String filename, String url2) {
		this.file_name = filename;
		this.url = url2;
	}

	public Long getId_file() {
		return id_file;
	}

	public void setId_file(Long id_file) {
		this.id_file = id_file;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getPublic_id() {
		return public_id;
	}

	public void setPublic_id(String public_id) {
		this.public_id = public_id;
	}

	




	  
	
}
