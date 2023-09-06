package com.jbk.shopify.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jbk.shopify.model.Product;

@Component
public class ValidateObject {
	
	
	
	public static Map<String, String> validateProduct(Product product) {
		Map<String, String> errorMap=new HashMap<>();
		
		if(product.getProductName()!=null) {
			if(!product.getProductName().matches("^[a-zA-Z]+[a-zA-Z0-9]*$")) {
				errorMap.put("Product Name", "Product should valid");
			}
			
		}else {
			errorMap.put("Product Name", "Product should be not null");
		}
		
		
		if(product.getProductQty()<=0) {
			errorMap.put("Product QTY", "Product qty should be greater than 0" );
		}
		
		if(product.getProductPrice()<=0) {
			errorMap.put("Product Price", "Product price should be greater than 0" );
		}
			
		
		return errorMap;
		
		
	}

}
