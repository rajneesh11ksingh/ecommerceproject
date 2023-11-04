package com.electronic.store.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electronic.store.Dto.PageableResponse;
import com.electronic.store.Dto.ProductDto;
import com.electronic.store.entities.Category;
import com.electronic.store.entities.Product;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.repo.CategoryRepo;
import com.electronic.store.repo.ProductRepo;
import com.electronic.store.services.ProductService;
import com.electronic.store.utilities.Helper;
@Service
public class ProductServiceImpl implements ProductService {
	
	private ModelMapper mapper;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public ProductDto createProduct(ProductDto dto) {
		// TODO Auto-generated method stub
		Product entity=mapper.map(dto, Product.class);
		Product savedEntity=productRepo.save(entity);
		
		return mapper.map(savedEntity, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(String id, ProductDto dto) {
		// TODO Auto-generated method stub
		Product pr=productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with give id!!"));
		pr.setTitle(dto.getTitle());
		pr.setDescription(dto.getDescription());
		pr.setPrice(dto.getPrice());
		pr.setDiscountedPrice(dto.getDiscountedPrice());
		pr.setQuantity(dto.getQuantity());
		pr.setLive(dto.isLive());
		pr.setStock(dto.isStock());
		Product savedEntity=productRepo.save(pr);
		return mapper.map(savedEntity, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllProduct(int pageNumber ,int pageSize ,String sortBy ,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort =(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page=productRepo.findAll(pageable);
	
		return Helper.getPAgeableResponse(page, ProductDto.class);
	}

	@Override
	public void deleteProduct(String id) {
		// TODO Auto-generated method stub
		Product pr=productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with give id!!"));
		productRepo.delete(pr);

	}

	@Override
	public PageableResponse<ProductDto> getAllProductLive(int pageNumber ,int pageSize ,String sortBy ,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort =(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page=productRepo.findByLiveTrue(pageable);
	
		return Helper.getPAgeableResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String title,int pageNumber ,int pageSize ,String sortBy ,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort =(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page=productRepo.findByTitleContaining(title,pageable);
	
		return Helper.getPAgeableResponse(page, ProductDto.class);
	}

	@Override
	public ProductDto createWithCategory(ProductDto productDto, String category) {
		// TODO Auto-generated method stub
		Category categori =categoryRepo.findById(category).orElseThrow(() -> new ResourceNotFoundException("category not found with given id!!"));
	    Product product=mapper.map(productDto, Product.class);
	    String productId=UUID.randomUUID().toString();
	    product.setId(productId);
	    product.setAddedDate(new Date());
	    product.setCategory(categori);
	    Product saveProduct=productRepo.save(product);
	    return mapper.map(saveProduct, ProductDto.class);
	    
	
	}

	@Override
	public ProductDto updateCategory(String productId, String categoryId) {
		// TODO Auto-generated method stub
		Product product=productRepo.findById(productId).orElseThrow(() ->new ResourceNotFoundException("product with given productId is not found!!"));
		Category categori =categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with given id!!"));
		
		product.setCategory(categori);
		Product savedProduct=productRepo.save(product);
		return mapper.map(savedProduct, ProductDto.class);
	}

}
