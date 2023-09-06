package com.jbk.shopify.exceptions;

public class ProductNotExistsException extends RuntimeException{

	
	public ProductNotExistsException(String msg) {
		super(msg);
	}
}
