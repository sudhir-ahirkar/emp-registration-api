/**
 * GlobalExceptionHandler is a centralized exception handling class that handles exceptions
 * thrown by the application. It extends ResponseEntityExceptionHandler to provide custom
 * exception handling for specific exceptions.
 * 
 * <p>This class uses the @ControllerAdvice annotation to handle exceptions globally across
 * the whole application. It defines methods to handle specific exceptions and return appropriate
 * HTTP responses.
 * 
 * <p>Methods:
 * <ul>
 *   <li>{@code resourceNotFoundException(ResourceNotFoundException ex, WebRequest request)}:
 *       Handles ResourceNotFoundException and returns a 404 NOT FOUND response with error details.
 *   <li>{@code handleUnknownSystemException(Exception ex, WebRequest request)}:
 *       Handles generic exceptions and returns a 500 INTERNAL SERVER ERROR response with error details.
 * </ul>
 * 
 * <p>Each method constructs an ErrorDetails object containing the timestamp, error message, and
 * request description, and returns it in the ResponseEntity with the appropriate HTTP status.
 * 
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 * @see org.springframework.web.bind.annotation.ControllerAdvice
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 */
package com.tcs.registration.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUnknownSystemException(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
