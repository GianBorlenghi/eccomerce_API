package com.ApiECommerce.apiec.DAO;

import java.util.List;

import com.ApiECommerce.apiec.Model.Brand;

public interface IBrandDao {

	public void saveBrand(Brand brand);
	
	public void deleteBrand(Long id);
	
	public Brand findBrandById(Long id);
	
	public List<Brand> getAllBrands();
	
	
}
