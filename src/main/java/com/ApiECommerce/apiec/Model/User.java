package com.ApiECommerce.apiec.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.NonNull;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Entity
@Table(name = "users")
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_user;
	
	@Basic
	@NotBlank

	private String username;
	
	@Basic
	@NotBlank

	private String password;
	
	@Basic
	@NotBlank
	private String name;
	
	@Basic
	@NotBlank

	private String surname;
	
	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
	private Date date_of_birth;
	
	@Basic
	@Email
	@NotBlank
	private String mail;
	
	@Basic
	@NotBlank
	private String province;
	
	@Basic 
	@NotBlank
	private String city;
	
	@Past
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso = ISO.NONE)
	@JsonFormat(shape = JsonFormat.Shape.ANY, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime last_connection;

	
	@Basic
	private Boolean is_online;

	@Enumerated(EnumType.STRING)
	private Role role;

	
	/*@OneToOne(mappedBy = "user")
    private Product prod;*/
	 		
	@Column(name = "id_product")
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	@JsonManagedReference(value = "user - product")
	private List<Product> product;
	
	public User() {
		super();
	}



	public User(Long id_user, @NotBlank String username, @NotBlank String password, @NotBlank String name,
			@NotBlank String surname, @Past Date date_of_birth, @Email @NotBlank String mail, @NotBlank String province,
			@NotBlank String city, @Past LocalDateTime last_connection, Boolean is_online, Role role,
			List<Product> product) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.date_of_birth = date_of_birth;
		this.mail = mail;
		this.province = province;
		this.city = city;
		this.last_connection = last_connection;
		this.is_online = is_online;
		this.role = role;
		this.product = product;
	}

	

	public Long getId_user() {
		return id_user;
	}



	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSurname() {
		return surname;
	}



	public void setSurname(String surname) {
		this.surname = surname;
	}



	public Date getDate_of_birth() {
		return date_of_birth;
	}



	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}



	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public LocalDateTime getLast_connection() {
		return last_connection;
	}



	public void setLast_connection(LocalDateTime last_connection) {
		this.last_connection = last_connection;
	}



	public Boolean getIs_online() {
		return is_online;
	}



	public void setIs_online(Boolean is_online) {
		this.is_online = is_online;
	}



	public Role getRole() {
		return role;
	}



	public void setRole(Role role) {
		this.role = role;
	}



	public List<Product> getProduct() {
		return product;
	}



	public void setProduct(List<Product> product) {
		this.product = product;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}





	

}