package com.electronic.store.Dto;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	
	private String categoryId;
	
    @NotBlank
    @Min(value=4,message="title must be of minimum 4 character!!")
	private String title;
   
    @NotBlank(message="Description required !!")
	private String description;
    
   
	private String coverImage;
}
