package com.bidgelabz.cartservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	private Long Id;

	private String bookName;
	private String bookAuthor;
	private String bookdescription;
	private String bookLogo;
	private double bookPrice;
	private int bookQuantity;
}
