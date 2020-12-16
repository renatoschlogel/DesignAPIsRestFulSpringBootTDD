package br.com.renatoschlogel.libraryapi.api.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;

import br.com.renatoschlogel.libraryapi.exception.BusinessException;

public class ApiErros {
	
	private List<String> errors;

	public ApiErros(BindingResult bindingResult) {
		this.errors = new ArrayList<>();
		bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));
	}

	public ApiErros(BusinessException exception) {
		this.errors = Arrays.asList(exception.getMessage());
	}

	public List<String> getErrors() {
		return errors;
	}
	
}
