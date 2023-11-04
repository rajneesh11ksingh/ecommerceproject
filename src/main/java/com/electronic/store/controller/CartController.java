package com.electronic.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.store.Dto.AddItemToCartRequest;
import com.electronic.store.Dto.CartDto;
import com.electronic.store.exception.ApiResponseMessage;
import com.electronic.store.services.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	  
	//add Item to cart
	 @PostMapping("/{userId}")
      public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId ,@RequestBody AddItemToCartRequest request){
    	  CartDto dto=cartService.addItemToCart(userId, request);
    	  return new ResponseEntity<>(dto ,HttpStatus.OK);
    	 } 
      
     @DeleteMapping("/{userId}/items/{itemId}")
     public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId,@PathVariable int itemId){
    	 cartService.removeItemFromCart(userId, itemId);
    	 ApiResponseMessage response=ApiResponseMessage.builder()
    			                      .message("Item is removed")
    			                       .success(true)
    			                       .status(HttpStatus.OK)
    			                       .build();
    	 return new ResponseEntity<>(response,HttpStatus.OK);
     }
     
     //clear cart
     
     @DeleteMapping("/{userId}")
     public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
    	 cartService.clearCart(userId);
    	 ApiResponseMessage response=ApiResponseMessage.builder()
    			                      .message("Now cart is blanke !!")
    			                       .success(true)
    			                       .status(HttpStatus.OK)
    			                       .build();
    	 return new ResponseEntity<>(response,HttpStatus.OK);
     }
     @GetMapping("/{userId}")
     public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId ){
   	  CartDto dto=cartService.getCartByUser(userId);
   	  return new ResponseEntity<>(dto ,HttpStatus.OK);
   	 } 
}
