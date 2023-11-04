package com.electronic.store.Dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.electronic.store.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	
	
	private String Id;
	private String title;
	
	private String description;
	private int price;
	private int discountedPrice;
	private int quantity;
	private Date addedDate;
	private boolean live;
	private boolean stock;
	private CategoryDto category;

}
