package com.electronic.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.store.Dto.CategoryDto;
import com.electronic.store.Dto.PageableResponse;
import com.electronic.store.Dto.ProductDto;
import com.electronic.store.entities.Product;
import com.electronic.store.exception.ApiResponseMessage;
import com.electronic.store.services.CategoryService;
import com.electronic.store.services.ProductService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
		
		CategoryDto dto=categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<>(dto ,HttpStatus.CREATED);
				
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId ,
			 @RequestBody CategoryDto categoryDto){
		
		CategoryDto dto=categoryService.updateCategory(categoryId, categoryDto);
		return new ResponseEntity<CategoryDto>(dto ,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAll(
			@RequestParam(value="pageNumber" ,defaultValue="0" ,required=false)int pageNumber,
			@RequestParam(value="pageSize" ,defaultValue="0" ,required=false)int pageSize,
			@RequestParam(value="sortBy" ,defaultValue="title" ,required=false)String sortBy,
			@RequestParam(value="sortDir" ,defaultValue="asc" ,required=false)String sortDir){
		
		PageableResponse<CategoryDto> response=categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	} 
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(
			@PathVariable String categoryId){
		
		categoryService.deleteCategory(categoryId);
		ApiResponseMessage responseMessage=ApiResponseMessage.builder().message("Category is deleted successfully !!").status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);
	}
	@GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
    	CategoryDto dto=categoryService.getCategory(categoryId);
    	return ResponseEntity.ok(dto);        //another way of returning response
    	
    }
	//cretae product with category
	@PostMapping("/{categoryId}/products")
	public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable("categoryId") String categoryId
			,@RequestBody ProductDto dto){
		ProductDto productWithCategory=productService.createWithCategory(dto, categoryId);
		return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
	}
	//update category of product
	@PutMapping("/{categoryId}/products/{productId}")
	public ResponseEntity<ProductDto> updateCategoryOfProduct(@PathVariable("categoryId") String categoryId
			,@PathVariable String productId){
		ProductDto productDto=productService.updateCategory(productId, categoryId);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
		
	}
	
	
	
}
