package com.bidgelabz.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bidgelabz.cartservice.dto.CartDTO;
import com.bidgelabz.cartservice.dto.ResponseDTO;
import com.bidgelabz.cartservice.service.ICartService;

@RestController
@RequestMapping("/cartservice")
public class CartController {
@Autowired
ICartService cartService;

@PostMapping("/addtocart")
	public ResponseEntity<ResponseDTO> addBookToCart(@RequestHeader  String token,@RequestBody CartDTO cartDto) {
	ResponseDTO  respDTO = cartService.addToCart(token,cartDto);
	return new ResponseEntity<ResponseDTO>( respDTO, HttpStatus.OK);
	}
@GetMapping("/getallcartitems")
	public ResponseEntity<ResponseDTO>getAllCartItem(@RequestHeader  String token){
	ResponseDTO  respDTO=cartService.getAllCartItem(token);
		return new ResponseEntity<ResponseDTO>( respDTO,HttpStatus.OK);
	}

@GetMapping("/getcartitemsbyid")
public ResponseEntity<ResponseDTO> getcartitemsById(@RequestHeader String token){
	ResponseDTO resDto = cartService.getcartitemsById(token);
	return new ResponseEntity<ResponseDTO>(resDto,HttpStatus.OK);
}

@PutMapping("/updatecartitem/{id}")
	public ResponseEntity<ResponseDTO> updateCartItem(@RequestHeader String token ,@PathVariable Long id, @RequestBody CartDTO cartDto) {
	ResponseDTO respDTO = cartService.updateCartItem(token, id, cartDto);;
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}

	@DeleteMapping("/deletecartitem/{id}")
	public ResponseEntity<ResponseDTO> removeCartItem(@RequestHeader String token,@PathVariable Long id) {
		cartService.removeCartItem(token, id);
		ResponseDTO respDTO = new ResponseDTO("Deleted Book Details with id : ", id,200);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}
	
}
