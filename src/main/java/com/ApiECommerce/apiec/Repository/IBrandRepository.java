package com.ApiECommerce.apiec.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ApiECommerce.apiec.Model.Brand;

@Repository
public interface IBrandRepository extends CrudRepository<Brand, Long>{
	
	@Query(value = "SELECT EXISTS(SELECT * FROM brand WHERE brand_name = :brand_name)",nativeQuery = true)
	int findProduct(@Param("brand_name") String brand_name);
}
