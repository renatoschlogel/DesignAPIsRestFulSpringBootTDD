package br.com.renatoschlogel.libraryapi.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.renatoschlogel.libraryapi.api.entities.Book;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

	BookService bookService;
	
	@Test
	@DisplayName("Deve salvar um livro")
	void saveBookTest() throws Exception {
		
		Book book = Book.builder().title("Titulo").author("Autor").isbn("123").build();
		
		Book savedBook = bookService.incluir(book);
		
		assertThat(savedBook.getId()).isNotNull();
		assertThat(savedBook.getTitle()).isEqualTo(book.getTitle());
		assertThat(savedBook.getAuthor()).isEqualTo(book.getAuthor());
		assertThat(savedBook.getIsbn()).isEqualTo(book.getIsbn());
	}
}
