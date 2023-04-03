package com.ApiECommerce.apiec.DAO;

import com.ApiECommerce.apiec.Model.Product;

public interface IProductDao {

	public void saveProduct(Product product);
	
	public Product findProductById(Long id);
	
	public void deleteProduct(Long id);
	
	public void saveProductAfterUploadImage(Product product);
}
