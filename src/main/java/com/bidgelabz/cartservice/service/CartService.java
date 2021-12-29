package com.bidgelabz.cartservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bidgelabz.cartservice.dto.CartDTO;
import com.bidgelabz.cartservice.dto.ResponseDTO;
import com.bidgelabz.cartservice.exception.CartServiceException;
import com.bidgelabz.cartservice.model.Book;
import com.bidgelabz.cartservice.model.Cart;
import com.bidgelabz.cartservice.repository.CartRepository;
import com.bidgelabz.cartservice.util.JwtToken;

@Service
public class CartService implements ICartService{

	@Autowired
	CartRepository cartRepository;
	@Autowired
	JwtToken jwtToken;
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseDTO addToCart(String token, CartDTO cartDto) {
		boolean verify = restTemplate.getForObject("http://BOOK-USER/user/verify?token="+token, Boolean.class);
		if(verify) {		
			Cart cart = modelmapper.map(cartDto, Cart.class);
			Book book = restTemplate.getForObject("http://BOOKSTORE-BOOK/book/getBook/"+cartDto.getBookId()+"?token="+token,Book.class);
			if(book.getId() == cartDto.getBookId()) {
			cart.setUserId(jwtToken.decodeToken(token));
			cartRepository.save(cart);
			return new ResponseDTO("book has add to cart",cart,200);
			}else
				throw new CartServiceException("This book is unavailable");
		}else
			throw new CartServiceException("Access Denied...! please check the login token");
	}

	@Override
	public ResponseDTO getAllCartItem(String token) {
		boolean verify = restTemplate.getForObject("http://BOOK-USER/user/verify?token="+token, Boolean.class);
		if(verify) {
			List<Cart> getCartItems = cartRepository.findAll();
			return new ResponseDTO("book has add to cart",getCartItems,200);
		}else
		throw new CartServiceException("Access Denied...! please check the login token");
}

	@Override
	public ResponseDTO getcartitemsById(String token) {
		long id = jwtToken.decodeToken(token);
		boolean verify = restTemplate.getForObject("http://BOOK-USER/user/verify?token="+token, Boolean.class);
		if(verify) {
			List<Cart> cartList = cartRepository.findAll();
			if(cartList.isEmpty()) {
				throw new CartServiceException("Database is empty");
			}else{
			List<Cart> getCartbyUserId = cartList.stream().filter(order->order.getUserId() == id)
					.collect(Collectors.toList());

			if(getCartbyUserId.isEmpty()) {
				throw new CartServiceException("No Data Present in Database,of This User Id");
			}else 
				return new ResponseDTO("book has add to cart",getCartbyUserId,200);
			}
		}else
		throw new CartServiceException("Access Denied...! please check the login token");
	}

	@Override
	public ResponseDTO updateCartItem(String token, Long id, CartDTO cartDto) {
		boolean verify = restTemplate.getForObject("http://localhost:8082/user/verify?token="+token, Boolean.class);
		if(verify) {
			Optional<Cart> isCartPresent = cartRepository.findById(id);
			if(isCartPresent.isPresent()) {
				isCartPresent.get().setBookId(cartDto.getBookId());
				isCartPresent.get().setQuantity(cartDto.getQuantity());
				cartRepository.save(isCartPresent.get());
				
				return new ResponseDTO("this cart has updated",isCartPresent.get(),200);
			}else
				throw new CartServiceException("this cart id is not present");
		}else
		throw new CartServiceException("Access Denied...! please check the login token");
	}

	@Override
	public void removeCartItem(String token, Long id) {
		boolean verify = restTemplate.getForObject("http://BOOK-USER/user/verify?token="+token, Boolean.class);
		if(verify) {
			Optional<Cart> isCartPresent = cartRepository.findById(id);
			if(isCartPresent.isPresent()) {
				cartRepository.deleteById(id);
			}else
				throw new CartServiceException("this cart id is not present");
		}else
		throw new CartServiceException("Access Denied...! please check the login token");
		
	}

}
