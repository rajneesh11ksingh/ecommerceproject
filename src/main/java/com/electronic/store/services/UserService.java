package com.electronic.store.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.electronic.store.Dto.PageableResponse;
import com.electronic.store.Dto.UserDto;
@Service
public interface UserService {

	UserDto creatUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto,String userId);
	
	void deleteUser(String userId);
	
	PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy ,String sortDirection);
	
	UserDto getUserByEmail(String email);
	
	UserDto getUserById(String userId);
	
	List<UserDto> searchByKeyword(String keyword);
}
