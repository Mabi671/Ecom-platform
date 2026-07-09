package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler({CartNotFoundException.class, ProductNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String notFoundHandler(RuntimeException ex) {
		return ex.getMessage();
	}
}
