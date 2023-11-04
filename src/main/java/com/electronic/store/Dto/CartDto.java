package com.electronic.store.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.electronic.store.entities.Cart;
import com.electronic.store.entities.CartItem;
import com.electronic.store.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

	private String cartid;

	private Date createdAt;

	private UserDto user;

	private List<CartItemDto> items = new ArrayList<>();

}
