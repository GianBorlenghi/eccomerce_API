package com.ApiECommerce.apiec.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ApiECommerce.apiec.Model.Product;

@Repository
public interface IProductRepository extends CrudRepository<Product, Long>{

	@Query(value = "SELECT COUNT(id_product) FROM product WHERE title = :product_name and id_category =:category and id_brand = :brand",nativeQuery = true)
	int findProduct(@Param("product_name") String product_name, @Param("category") Long category, @Param("brand") Long brand);
}
