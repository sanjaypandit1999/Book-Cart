package com.bidgelabz.cartservice.dto;

import lombok.Data;

@Data
public class CartDTO {

	private long userId;
	private long bookId;
	private int quantity;
}
