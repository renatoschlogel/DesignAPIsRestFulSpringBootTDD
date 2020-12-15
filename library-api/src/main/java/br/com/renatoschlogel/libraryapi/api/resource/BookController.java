package br.com.renatoschlogel.libraryapi.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.renatoschlogel.libraryapi.api.dto.BookDTO;
import br.com.renatoschlogel.libraryapi.api.entities.Book;
import br.com.renatoschlogel.libraryapi.api.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private BookService bookService;
	
	public BookController( BookService bookService) {
		this.bookService = bookService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody BookDTO bookDTO) {
		Book book = Book.builder().title(bookDTO.getTitle()).author(bookDTO.getAuthor()).isbn(bookDTO.getIsbn()).build();
		
		book = bookService.incluir(book);
		
		return BookDTO.builder().id(book.getId()).title(book.getTitle()).author(book.getAuthor()).isbn(book.getIsbn()).build();
	}

}
