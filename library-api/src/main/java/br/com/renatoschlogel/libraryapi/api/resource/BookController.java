package br.com.renatoschlogel.libraryapi.api.resource;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

	@GetMapping("{id}")
	public BookDTO get(@PathVariable Long id) {
		return  bookService.getById(id)
				            .map(book -> modelMapper.map(book, BookDTO.class))
				            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Optional<Book> optBook = bookService.getById(id);
		if(!optBook.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		bookService.delete(optBook.get());
	}
	
	@PutMapping("{id}")
	public BookDTO update(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
		return bookService.getById(id)
				          .map(book -> {
				        	  book.setTitle(bookDTO.getTitle());
				        	  book.setAuthor(bookDTO.getAuthor());
				        	  book = bookService.update(book);
				        	  return modelMapper.map(book, BookDTO.class);
				          })
						  .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
