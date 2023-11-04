package com.electronic.store.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

@Entity
@Table(name="cart")
public class Cart {

	@Id
	private String cartid;
	
	private Date createdAt;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	
	//to remove duplicate problem we can remove fetch=FetchType.EAGER refer last video in cart module     
	@OneToMany(mappedBy="cart" ,cascade=CascadeType.ALL ,fetch=FetchType.EAGER,orphanRemoval=true)
	private List<CartItem> items=new ArrayList<>();
}
