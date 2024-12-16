package com.emssanar.template.dao;

import org.springframework.data.repository.CrudRepository;

import com.emssanar.template.model.Category;

public interface ICategoryDao extends CrudRepository<Category, Long> {

}
