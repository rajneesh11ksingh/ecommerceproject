package com.electronic.store.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.electronic.store.Dto.PageableResponse;
import com.electronic.store.Dto.UserDto;
import com.electronic.store.entities.User;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.repo.UserRepository;
import com.electronic.store.services.UserService;
import com.electronic.store.utilities.Helper;
@Component
public class UserServiceImpl implements UserService {
    @Autowired
	private UserRepository userRepository;
	
    @Autowired
	private ModelMapper modelMapper; 
	
    @Value("${user.profile.image.path}")
	private String imageUploadPath;
    
    private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
    
	@Override
	public UserDto creatUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user=dtoToEntity(userDto);
		
		User savedUser=userRepository.save(user);
		UserDto dto=entityToDto(savedUser);
		
		return dto;
	}

	@Override
	public UserDto updateUser(UserDto dto, String userId) {
		// TODO Auto-generated method stub
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with give ID!!"));
		user.setAbout(dto.getAbout());
		user.setGender(dto.getGender());
		user.setName(dto.getName());
		user.setPassword(dto.getPassword());
		user.setImage(dto.getImage());
		
		User saveduser=userRepository.save(user);
		UserDto dtuserDto=entityToDto(saveduser);
		
		return dtuserDto;
	}
	
	
	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with given ID!!"));
		
		//delete user profile image
		String fullPath=imageUploadPath+user.getImage();
		
		try{
			Path path=Paths.get(fullPath);
		    Files.delete(path);
		}
		catch(NoSuchFileException ex) {
			logger.info("userImage not found in folder :{}",ex);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		userRepository.delete(user);

	}

	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber ,int pageSize, String sortBy ,String sortDirection) {
		// TODO Auto-generated method stub
		
		Sort sort=(sortDirection.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending())  : (Sort.by(sortBy).ascending());
		//pageNumber default starts with 0
		Pageable pageable=PageRequest.of(pageNumber, pageSize ,sort );
		Page<User> page=userRepository.findAll(pageable);
		
		
		 PageableResponse<UserDto> response= Helper.getPAgeableResponse(page, UserDto.class);
		 return response;
	}

	@Override
	public UserDto getUserByEmail(String email) {
		// TODO Auto-generated method stub
		User user =userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found with this emailId"));
		return entityToDto(user);
	}

	@Override
	public UserDto getUserById(String userId) {
		// TODO Auto-generated method stub
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with given ID!!"));
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchByKeyword(String keyword) {
		// TODO Auto-generated method stub
		List<User> users=userRepository.findByNameContaining(keyword);
		
		List<UserDto> userDto=users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		
		return userDto;
	}
	
	private User dtoToEntity(UserDto userDto) {
		// TODO Auto-generated method stub
//		User us=new User();
//		us.setUserId(userDto.getUserId());
		return modelMapper.map(userDto, User.class);


	}
	
	private UserDto entityToDto(User savedUser) {
		// TODO Auto-generated method stub
		return modelMapper.map(savedUser, UserDto.class);
	}

}
