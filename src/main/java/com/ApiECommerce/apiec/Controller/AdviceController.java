package com.ApiECommerce.apiec.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ApiECommerce.apiec.Exception.BusinessException;
import com.ApiECommerce.apiec.Exception.UserNotFoundException;
import com.ApiECommerce.apiec.Model.ErrorDTO;
import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.ExpiredJwtException;

import java.util.*;

@ControllerAdvice

public class AdviceController  extends ResponseEntityExceptionHandler{
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,HttpStatus status,WebRequest request){
		Map<String, Object> body = new HashMap<>();
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(x-> x.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("errors dsfdsfdsfdsfdsfdsfdsf", errors);
		System.out.println(errors);
		return new ResponseEntity<Object>("VERY BAD", status);
				}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException ex){
	ErrorDTO errorDTO = new ErrorDTO();
	
		errorDTO.setCode("P-500");
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setTimeStamp(new Date());
		return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorDTO> businessExceptionHandler(BusinessException ex){
		
		ErrorDTO errorDTO = new ErrorDTO();
		
		errorDTO.setCode(ex.getCode());
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setTimeStamp(new Date());
		
		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
		
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDTO> userNotFoundExceptionHandler(UserNotFoundException ex){
		
		ErrorDTO errorDTO = new ErrorDTO();
		
		errorDTO.setCode(ex.getCode());
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setTimeStamp(new Date());
		
		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}
	
	 @ExceptionHandler(value = {ExpiredJwtException.class})
	 public ResponseEntity<ErrorDTO> handleExpiredJwtException(ExpiredJwtException ex) {
		 ErrorDTO errorDTO = new ErrorDTO();
			
		 	errorDTO.setCode("P-403");
			errorDTO.setMessage("Expired token");
			errorDTO.setTimeStamp(new Date());
			
			return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.UNAUTHORIZED);
		 
	 }

}
