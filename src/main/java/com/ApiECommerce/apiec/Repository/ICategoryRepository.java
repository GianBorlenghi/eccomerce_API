package com.ApiECommerce.apiec.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ApiECommerce.apiec.Model.Category;

@Repository
public interface ICategoryRepository extends CrudRepository<Category, Long>{

}
