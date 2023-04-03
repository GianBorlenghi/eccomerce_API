package com.ApiECommerce.apiec.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ApiECommerce.apiec.Exception.BusinessException;
import com.ApiECommerce.apiec.Exception.RequestException;
import com.ApiECommerce.apiec.Model.Product;
import com.ApiECommerce.apiec.Repository.IProductRepository;

import jakarta.transaction.Transactional;


@Service
public class ProductDao implements IProductDao{

	@Autowired
	private IProductRepository productRepo;
	
	@Override
	@Transactional
	public void saveProduct(Product product) {
		
		int productExist = productRepo.findProduct(product.getTitle(), product.getCategory().getId_category(),product.getBrand().getId_brand());

		if(productExist == 0) {
			productRepo.save(product);
		}else {
			throw new BusinessException("Product already exists", "P-500", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

	@Override
	public Product findProductById(Long id) {

		return productRepo.findById(id)
				.orElseThrow( () -> new RequestException("Producto con id "+id+" no existe.",HttpStatus.NOT_FOUND,"P-404" ));
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
		
			productRepo.deleteById(id);	
		}



	@Override
	@Transactional
	public void saveProductAfterUploadImage(Product product) {

		productRepo.save(product);
		
	}
	


}
