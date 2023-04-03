package com.ApiECommerce.apiec.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ApiECommerce.apiec.Exception.BusinessException;
import com.ApiECommerce.apiec.Model.Category;
import com.ApiECommerce.apiec.Repository.ICategoryRepository;

@Service
public class CategoryDao implements ICategoryDao{

	@Autowired
	private ICategoryRepository categoryRepo;

	@Override
	public Category findCategoryById(Long id) {
		return categoryRepo.findById(id).orElseThrow(
				()->new BusinessException("Category not found","P-404", HttpStatus.NOT_FOUND)
				);
	}
	
	
}
