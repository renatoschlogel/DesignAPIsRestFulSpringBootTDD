package br.com.renatoschlogel.libraryapi.api.resource;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.renatoschlogel.libraryapi.api.dto.BookDTO;
import br.com.renatoschlogel.libraryapi.api.exception.ApiErros;
import br.com.renatoschlogel.libraryapi.exception.BusinessException;
import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private BookService bookService;
	
	private ModelMapper modelMapper; 
	
	public BookController( BookService bookService, ModelMapper modelMapper) {
		this.bookService = bookService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody @Valid BookDTO bookDTO) {
		Book book = modelMapper.map(bookDTO, Book.class);
		book = bookService.incluir(book);
		return modelMapper.map(book, BookDTO.class);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErros handleValidationException(MethodArgumentNotValidException exception){
		BindingResult bindingResult = exception.getBindingResult();
		return new ApiErros(bindingResult);
	}
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErros handleBusinessException(BusinessException exception){
		return new ApiErros(exception);
	}
}
