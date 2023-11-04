package com.electronic.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.store.Dto.PageableResponse;
import com.electronic.store.Dto.ProductDto;
import com.electronic.store.Dto.UserDto;
import com.electronic.store.exception.ApiResponseMessage;
import com.electronic.store.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/create")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
		ProductDto dto=productService.createProduct(productDto);
		return new ResponseEntity<>(dto ,HttpStatus.OK);
		
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto ,@PathVariable String productId){
		ProductDto updatedDto=productService.updateProduct(productId, productDto);
		return new ResponseEntity<>(updatedDto,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
		productService.deleteProduct(productId);
		ApiResponseMessage apiResponseMessage=ApiResponseMessage.builder().message("Product deleted successfully !!").
				status(HttpStatus.OK).success(true).build();
		
		return new ResponseEntity<>(apiResponseMessage ,HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<PageableResponse<ProductDto>> getAllUser(
			@RequestParam(value="pageNumber" , defaultValue= "0" ,required=false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue="10" ,required=false) int pageSize
			,@RequestParam(value="sortBy", defaultValue="name" ,required=false) String sortBy ,
			@RequestParam(value="sortDirection", defaultValue="asc" ,required=false) String sortDirection){
		      return new ResponseEntity<>(productService.getAllProduct(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
	}
	@GetMapping("/getAllLive")
	public ResponseEntity<PageableResponse<ProductDto>> getAllProductLive(
			@RequestParam(value="pageNumber" , defaultValue= "0" ,required=false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue="10" ,required=false) int pageSize
			,@RequestParam(value="sortBy", defaultValue="name" ,required=false) String sortBy ,
			@RequestParam(value="sortDirection", defaultValue="asc" ,required=false) String sortDirection){
		      return new ResponseEntity<>(productService.getAllProductLive(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
	}
	
	@GetMapping("/getBytitle")
	public ResponseEntity<PageableResponse<ProductDto>> searchByTitle(
			@RequestParam String title,
			@RequestParam(value="pageNumber" , defaultValue= "0" ,required=false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue="10" ,required=false) int pageSize
			,@RequestParam(value="sortBy", defaultValue="name" ,required=false) String sortBy ,
			@RequestParam(value="sortDirection", defaultValue="asc" ,required=false) String sortDirection){
		      return new ResponseEntity<>(productService.searchByTitle(title,pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
	}
	
	
	
	

}
