package com.electronic.store.services;

import java.util.List;

import com.electronic.store.Dto.PageableResponse;
import com.electronic.store.Dto.ProductDto;

public interface ProductService {
	
	//create
	ProductDto createProduct(ProductDto dto);
	
	
	//Update
	ProductDto updateProduct(String id , ProductDto dto);
	
	//get
	PageableResponse<ProductDto> getAllProduct(int pageNumber ,int pageSize ,String sortBy ,String sortDir);
	
	
	//delete
	void deleteProduct(String id);
	
	PageableResponse<ProductDto> getAllProductLive(int pageNumber ,int pageSize ,String sortBy ,String sortDir);
	
	
	PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber ,int pageSize ,String sortBy ,String sortDir);
	
	//create product with category
	
	ProductDto createWithCategory(ProductDto productDto ,String category);

	//update category of Product
	ProductDto updateCategory(String productId ,String categoryId);
}
