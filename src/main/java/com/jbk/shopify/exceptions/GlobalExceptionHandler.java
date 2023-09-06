package com.jbk.shopify.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> methodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		Map<String , String> errorMap=new HashMap<>();
		
		List<FieldError> fieldErrors = ex.getFieldErrors();
		
		for (FieldError fieldError : fieldErrors) {
			
			String field = fieldError.getField();
			
			String defaultMessage = fieldError.getDefaultMessage();
			
			errorMap.put(field, defaultMessage);
		}
		return errorMap;
		
		
	}
	
	@ExceptionHandler(ProductNotExistsException.class)
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public String productNotExistsException(ProductNotExistsException ex) {
		
		return ex.getMessage();
		
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public String rntimeException(RuntimeException ex) {
		//System.out.println("runtime");
		return ex.getMessage();
		
	}
	

}
