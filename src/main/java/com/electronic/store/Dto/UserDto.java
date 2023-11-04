package com.electronic.store.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.electronic.storee.validate.ImageNameValid;

public class UserDto {
   
	
	
	private String userId;
	@Size(message="Password is required!!")
	private String password;
	
	@NotBlank(message="Write Something about yourself!!")
	private String about;
	
	@Size(min=3,max=15 ,message="Invalid Name")
	private String name;
	//@Email(message="Invalid User Email")
	@NotBlank(message="email is required!!")
	@Pattern(regexp="^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message="Invalid user Email")
	private String email;
	@Size(min=4,max=6,message="invalid gender!!")
	private String gender;
	
	@ImageNameValid
	private String image;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
