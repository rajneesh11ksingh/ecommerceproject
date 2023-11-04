package com.electronic.store.services;

import com.electronic.store.Dto.CategoryDto;
import com.electronic.store.Dto.PageableResponse;

public interface CategoryService {
	
	//create
	
	CategoryDto createCategory(CategoryDto dto);
	
	//update
	
	CategoryDto updateCategory(String categoryId, CategoryDto dto);
	
	//getAllCatgory
	PageableResponse<CategoryDto> getAllCategory(int pageumber,int pageSize,String sortBy ,String sortDir);
	
	CategoryDto getCategory(String categoryId);
	
	//delete
	void deleteCategory(String categoryId);
	
	

}
