package br.com.renatoschlogel.libraryapi.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.renatoschlogel.libraryapi.api.dto.BookDTO;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create() {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setId(1L);
		bookDTO.setTitle("GO TEAM!");
		bookDTO.setAuthor("Autor");
		bookDTO.setIsbn("123");
		return bookDTO;
	}

}
