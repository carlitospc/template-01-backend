package com.emssanar.template.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emssanar.template.dao.ICategoryDao;
import com.emssanar.template.model.Category;
import com.emssanar.template.response.CategoryResponseRest;


@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ICategoryDao categoryDao; 
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {
	
		CategoryResponseRest response = new CategoryResponseRest();
		
		try {
			List<Category> category = (List<Category>) categoryDao.findAll();
			response.getCategoryResponse().setCategory(category);
			response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");
		} catch (Exception e) {
			response.setMetadata("Respuesta NOK", "-1", "Error al consultar");
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		
		try {
			Optional<Category> category = categoryDao.findById(id);
			
			if(category.isPresent()) {
				list.add(category.get());
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("Respuesta OK", "00", "Categoria encontrada");
			} else {
				response.setMetadata("Respuesta NOK", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta NOK", "-1", "Error al consultar por id");
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> save(Category category) {
		
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		
		try {
			Category categorySaved = categoryDao.save(category);
			
			if (categorySaved != null) {
				list.add(categorySaved);
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("Respuesta OK", "00", "Categoria guardada");
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta NOK", "-1", "Error al guardar categoria");
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> update(Category category, long id) {
		
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		
		try {
			Optional <Category> categorySearch = categoryDao.findById(id);
			
			if(categorySearch.isPresent()) {
				categorySearch.get().setName(category.getName());
				categorySearch.get().setDescription(category.getDescription());
				
				Category categoryToUpdate = categoryDao.save(categorySearch.get());
				if(categoryToUpdate != null) {
					list.add(categoryToUpdate);
					response.getCategoryResponse().setCategory(list);
					response.setMetadata("Respuesta OK", "00", "Categoria actualizada");
				} else {
					response.setMetadata("Respuesta NOK", "01", "Categoria no actualizada");
					return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
				
			} else {
				response.setMetadata("Respuesta NOK", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			response.setMetadata("Respuesta NOK", "-1", "Error al actualizar categoria");
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> deleteById(Long id) {

		CategoryResponseRest response = new CategoryResponseRest();
		
		try {
			categoryDao.deleteById(id);
			response.setMetadata("respuesta OK", "00", "Registro eliminado");

		} catch (Exception e) {
			response.setMetadata("Respuesta NOK", "-1", "Error al aliminar");
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
}
