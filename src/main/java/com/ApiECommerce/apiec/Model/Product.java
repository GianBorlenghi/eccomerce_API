package com.ApiECommerce.apiec.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
public class Product implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_product;
	
	@Basic
	@NotBlank
	private String title;
	
	@Basic
	private String description;
	
	@Basic
	@NotNull(message="Price cannot be null")
	private double price;
	
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso = ISO.NONE)
	@JsonFormat(shape = JsonFormat.Shape.ANY, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime date_published;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "id_category")
	@JsonBackReference(value = "category - product")
	private Category category;
	
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "id_brand")
	@JsonBackReference(value = "brand - product")
	private Brand brand;
	

	@Column(name = "id_comment")
	@OneToMany(mappedBy="product",cascade = {CascadeType.MERGE, CascadeType.REFRESH })
	@JsonManagedReference(value = "comment - product")
	private List<Comment> commentList;
	
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "id_user")
	@JsonBackReference(value = "user - product")
	private User user;

	@Column(name = "id_file")
	@OneToMany(mappedBy="product",orphanRemoval = true,cascade = CascadeType.ALL)
	@JsonManagedReference(value = "file - product")
	private List<File> files_product;

	public Product() {
		super();
	}

	public Product(Long id_product, @NotBlank String title, String description, double price,
			@Past LocalDateTime date_published, Category category, Brand brand, List<Comment> commentList, User user,
			List<File> files_product) {
		super();
		this.id_product = id_product;
		this.title = title;
		this.description = description;
		this.price = price;
		this.date_published = date_published;
		this.category = category;
		this.brand = brand;
		this.commentList = commentList;
		this.user = user;
		this.files_product = files_product;
	}

	public Long getId_product() {
		return id_product;
	}

	public void setId_product(Long id_product) {
		this.id_product = id_product;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getDate_published() {
		return date_published;
	}

	public void setDate_published(LocalDateTime date_published) {
		this.date_published = date_published;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<File> getFiles_product() {
		return files_product;
	}

	public void setFiles_product(List<File> files_product) {
		this.files_product = files_product;
	}
	
	
	

	


	

}
