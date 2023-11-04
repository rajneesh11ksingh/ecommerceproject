package com.electronic.store.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronic.store.Dto.PageableResponse;
import com.electronic.store.Dto.UserDto;
import com.electronic.store.exception.ApiResponseMessage;
import com.electronic.store.exception.ImageResponse;
import com.electronic.store.services.FileService;
import com.electronic.store.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUsers(@Valid @RequestBody UserDto dto){
		UserDto userDto=userService.creatUser(dto);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<PageableResponse<UserDto>> getAllUser(
			@RequestParam(value="pageNumber" , defaultValue= "0" ,required=false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue="10" ,required=false) int pageSize
			,@RequestParam(value="sortBy", defaultValue="name" ,required=false) String sortBy ,
			@RequestParam(value="sortDirection", defaultValue="asc" ,required=false) String sortDirection){
		      return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
	}
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
		userService.deleteUser(userId);
		ApiResponseMessage msg=new ApiResponseMessage("user deleted successfully !!",true,HttpStatus.OK);
		return new ResponseEntity<>(msg ,HttpStatus.OK);
	}
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable String userID ,@RequestBody UserDto userDto){
		return new ResponseEntity<>(userService.updateUser(userDto, userID),HttpStatus.OK);
		
	}
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getUser(@PathVariable String userId){
		return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
	}
	
	@GetMapping("/email/{emailId}")
	public ResponseEntity<UserDto> getByEmail(@PathVariable String emailId){
		return new ResponseEntity<>(userService.getUserById(emailId),HttpStatus.OK);
	}
	@GetMapping("/search/{keyWord}")
	public ResponseEntity<UserDto> getByKeyword(@PathVariable String email){
		return new ResponseEntity<>(userService.getUserById(email),HttpStatus.OK);
	}
	
	
	//upload user image
	
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadImage(@RequestParam("userImage") MultipartFile image ,@PathVariable String userId) throws IOException{
		
		String imageName=fileService.uploadFile(image, imageUploadPath);
		
		ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
		UserDto user=userService.getUserById(userId);
		
		user.setImage(imageName);
		UserDto userDto=userService.updateUser(user, userId);
		
		return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
		
		
	}
	
	//user response
	@GetMapping(value="/image/{userId}")
	public void serveImage(@PathVariable String userId,HttpServletResponse response) throws IOException  {
		UserDto user=userService.getUserById(userId);
		InputStream resource=fileService.getReosurce(imageUploadPath, user.getImage());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
}
