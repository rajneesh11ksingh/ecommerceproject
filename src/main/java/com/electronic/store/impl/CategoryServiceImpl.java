package com.electronic.store.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electronic.store.Dto.CategoryDto;
import com.electronic.store.Dto.PageableResponse;
import com.electronic.store.entities.Category;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.repo.CategoryRepo;
import com.electronic.store.services.CategoryService;
import com.electronic.store.utilities.Helper;
@Service
public class CategoryServiceImpl implements CategoryService {
	
	private ModelMapper mapper;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public CategoryDto createCategory(CategoryDto dto) {
		// TODO Auto-generated method stub
		Category category=mapper.map(dto, Category.class);
		Category savedCategory=categoryRepo.save(category);
		
		return mapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(String categoryId, CategoryDto dto) { 
		// TODO Auto-generated method stub
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found Exception !!"));
	
		category.setTitle(dto.getTitle());
		category.setDescription(dto.getDescription());
	    category.setCoverImage(dto.getCoverImage());
	    
		Category savedCat=categoryRepo.save(category);
		
		return mapper.map(savedCat, CategoryDto.class);
	}

	@Override
	public PageableResponse<CategoryDto> getAllCategory(int pageumber,int pageSize,String sortBy ,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageumber, pageSize,sort);
		Page<Category> page=categoryRepo.findAll(pageable);
		
		PageableResponse<CategoryDto> pageableResponse=Helper.getPAgeableResponse(page, CategoryDto.class);
		
		return pageableResponse;
	}

	@Override
	public CategoryDto getCategory(String categoryId) {
		// TODO Auto-generated method stub
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found Exception !!"));
		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategory(String categoryId) {
		// TODO Auto-generated method stub
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found Exception !!"));
		categoryRepo.delete(category);

	}

}
