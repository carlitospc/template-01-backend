package com.emssanar.template.response;

import java.util.List;

import com.emssanar.template.model.Category;

import lombok.Data;

@Data
public class CategoryResponse {
	
	private List<Category> category;
}
