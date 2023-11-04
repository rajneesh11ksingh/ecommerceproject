package com.electronic.store.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.electronic.store.Dto.AddItemToCartRequest;

import com.electronic.store.Dto.CartDto;
import com.electronic.store.entities.Cart;
import com.electronic.store.entities.CartItem;
import com.electronic.store.entities.Product;
import com.electronic.store.entities.User;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.repo.CartItemRepository;
import com.electronic.store.repo.CartRepository;
import com.electronic.store.repo.ProductRepo;
import com.electronic.store.repo.UserRepository;
import com.electronic.store.services.CartService;

public class CartServiceimpl implements CartService {
	private ModelMapper mapper;
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
		// TODO Auto-generated method stub
		
		int quantity=request.getQuantity();
		String productId=request.getProductId();
		Product product=productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product with this productId not found!!"));
		User user=userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with this userId not found!!"));
		Cart cart=null;
		try {
			cart =cartRepository.findByUser(user).get();
		}
		catch(NoSuchElementException ex) {
			cart =new Cart();
			cart.setCartid(UUID.randomUUID().toString());
			cart.setCreatedAt(new Date());
		}
		
		//perform cart operations
		//if cart items already present then update
		AtomicReference<Boolean> updated=new AtomicReference<>(false);
		List<CartItem> items=cart.getItems();
		List<CartItem> updatedItems=items.stream().map(item ->{
			
			if(item.getProduct().getId().equals(productId)) {
				//items already present in cart
				item.setQuantity(quantity);
				item.setTotalPrice(quantity*product.getPrice());
				updated.set(true);
				
			}
			
			return item;
		}).collect(Collectors.toList());	
		
		cart.setItems(updatedItems);
		//create items
		
		CartItem cartItem=CartItem.builder()
				.quantity(quantity)
				.totalPrice(quantity*product.getDiscountedPrice())
				.cart(cart)
				.product(product)
				.build();
		cart.getItems().add(cartItem);
		cart.setUser(user);
		Cart updatedCart=cartRepository.save(cart);
		return mapper.map(updatedCart,CartDto.class);
	}

	@Override
	public void removeItemFromCart(String userId, int cartItemId) {
		// TODO Auto-generated method stub
		
     CartItem cartitem=cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("cartitem with this cartitemId not found!!"));
     cartItemRepository.delete(cartitem);
	}

	@Override
	public void clearCart(String userId) {
		// TODO Auto-generated method stub
		
		User user=userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with this usertId not found!!"));
		Cart cart=cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart with this cartId not found!!"));
		cart.getItems().clear();
		cartRepository.save(cart);

	}

	@Override
	public CartDto getCartByUser(String userId) {
		// TODO Auto-generated method stub
		User user=userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with this usertId not found!!"));
		Cart cart=cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart with this cartId not found!!"));
		return mapper.map(cart, CartDto.class);
	}

}
