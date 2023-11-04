package com.electronic.store.services;

import com.electronic.store.Dto.AddItemToCartRequest;
import com.electronic.store.Dto.CartDto;

public interface CartService {
    
	//add items to cart
	//case1: cart for user is not available : we will create the cart 
	//case2: cart available add the item to cart
	CartDto addItemToCart(String userId ,AddItemToCartRequest request);
	
	//remove items from cart
	void removeItemFromCart(String userId,int cartItem);
	
	//remove all items from cart
	void clearCart(String userId);
	
	CartDto getCartByUser(String  userId);
	
}
