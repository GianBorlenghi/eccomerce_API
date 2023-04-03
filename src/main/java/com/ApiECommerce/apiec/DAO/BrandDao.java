package com.ApiECommerce.apiec.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ApiECommerce.apiec.Exception.RequestException;
import com.ApiECommerce.apiec.Model.Brand;
import com.ApiECommerce.apiec.Repository.IBrandRepository;

@Service
public class BrandDao implements IBrandDao{
	
	@Autowired
	private IBrandRepository brandRepository;
	
	@Override
	public void saveBrand(Brand brand) {

		int brandExists = brandRepository.findProduct(brand.getBrand_name());
		if(brandExists == 0) {
			brandRepository.save(brand);
		}else {
			throw new RequestException("La marca con el nombre "+brand.getBrand_name()+" ya existe", HttpStatus.INTERNAL_SERVER_ERROR,"P-500");
		}
		
	}

	@Override
	public void deleteBrand(Long id) {

		brandRepository.deleteById(id);
	}

	@Override
	public Brand findBrandById(Long id) {
		return brandRepository
				.findById(id)
				.orElseThrow(()	-> 	new RequestException("Brand not found", HttpStatus.NOT_FOUND, "P-404"));
	}

	@Override
	public List<Brand> getAllBrands() {

		List<Brand> allBrand = (List<Brand>) brandRepository.findAll();
		if(allBrand.isEmpty()) {
			throw new RequestException("Aún no hay marcas cargadas.", HttpStatus.NOT_FOUND, "P-404");
		}
		
		return allBrand;
	}

}
