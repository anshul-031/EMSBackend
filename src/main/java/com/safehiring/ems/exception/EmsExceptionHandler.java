package com.safehiring.ems.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class EmsExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final List<String> errorList = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
		return this.handleExceptionInternal(ex, errorList, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	protected ResponseEntity<Object> handleUserAlreadyExists(final UserAlreadyExistsException ex, final WebRequest request) {
		log.error(ex.getLocalizedMessage());
		return this.handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(InvalidTokenException.class)
	protected ResponseEntity<Object> handleInvalidTokenExists(final InvalidTokenException ex, final WebRequest request) {
		log.error(ex.getLocalizedMessage());
		return this.handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(InvalidJobOfferException.class)
	protected ResponseEntity<Object> handleInvalidTokenExists(final InvalidJobOfferException ex, final WebRequest request) {
		log.error("message = " + ex.getLocalizedMessage());
		log.error("code = " + ex.getCode());
		return this.handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> globalException(final Exception ex, final WebRequest request) {
		ex.printStackTrace();
		log.error(ex.getLocalizedMessage());
		return this.handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}