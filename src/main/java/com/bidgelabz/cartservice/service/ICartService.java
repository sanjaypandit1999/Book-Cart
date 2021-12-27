package com.bidgelabz.cartservice.service;

import org.springframework.stereotype.Service;

import com.bidgelabz.cartservice.dto.CartDTO;
import com.bidgelabz.cartservice.dto.ResponseDTO;

@Service
public interface ICartService {

	ResponseDTO addToCart(String token, CartDTO cartDto);

	ResponseDTO getAllCartItem(String token);

	ResponseDTO getcartitemsById(String token);

	ResponseDTO updateCartItem(String token, Long id, CartDTO cartDto);

	void removeCartItem(String token, Long id);

}
