package com.ApiECommerce.apiec.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ApiECommerce.apiec.DAO.IBrandDao;
import com.ApiECommerce.apiec.Exception.RequestException;
import com.ApiECommerce.apiec.Model.Brand;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/brand")
@AllArgsConstructor
public class BrandController {

	@Autowired
	private IBrandDao brandDao;
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createBrand(@RequestBody Brand brand){
		
		brandDao.saveBrand(brand);
		return new ResponseEntity<>("Marca creada correctamente",HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllBrand")
	public List<Brand> getAllBrands(){
		return brandDao.getAllBrands();
	}
	
	@PutMapping("/editBrand/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> editBrand(
			@PathVariable(value = "id") Long id_brand,
			@Valid @RequestParam(value = "brand_name",required = true)String brand_name
			){
			
			Brand brand = brandDao.findBrandById(id_brand);
			if(!brand_name.equals(brand.getBrand_name()) && brand_name != "") {
				brand.setBrand_name(brand_name);
				brandDao.saveBrand(brand);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				throw new RequestException("El nombre de la marca que intenta modificar, es el mismo", HttpStatus.INTERNAL_SERVER_ERROR, "P-500");
			}					
	}
	
	@DeleteMapping("/deleteBrand/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteBrand(
			@PathVariable(value="id") Long id){
		
		brandDao.findBrandById(id);
		
		brandDao.deleteBrand(id);
		return new ResponseEntity<>("Marca con id: "+id+" dada de baja.",HttpStatus.OK);

	}
			
	
	
	
}
