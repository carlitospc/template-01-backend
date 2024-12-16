package com.emssanar.template.services;

import org.springframework.http.ResponseEntity;

import com.emssanar.template.model.Category;
import com.emssanar.template.response.CategoryResponseRest;

public interface ICategoryService {

	public ResponseEntity<CategoryResponseRest> search();
	public ResponseEntity<CategoryResponseRest> searchById(Long id); 
	public ResponseEntity<CategoryResponseRest> save(Category category); 
	public ResponseEntity<CategoryResponseRest> update(Category category, long id); 
	public ResponseEntity<CategoryResponseRest> deleteById(Long id); 
}
