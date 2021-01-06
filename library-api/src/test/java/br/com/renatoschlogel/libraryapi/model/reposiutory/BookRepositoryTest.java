package br.com.renatoschlogel.libraryapi.model.reposiutory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.model.repository.BookRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	BookRepository bookRepository;
	
	@Test
	@DisplayName("Deve retornar verdadeiro quando existir um livro na base com o isbn informado")
	void returnTrueWhenIsbnExists() throws Exception {
		
		String isbn = "123";
		
		Book book = Book.builder().title("Clean Code").author("Uncle Bob").isbn(isbn).build();
		
		entityManager.persist(book);
		
		boolean existsByIsbn = bookRepository.existsByIsbn(isbn);
		
		assertThat(existsByIsbn).isTrue();
	}
	
	@Test
	@DisplayName("Deve retornar falso quando não existir um livro na base com o isbn informado")
	void returnFalseWhenIsbnDoesNottExists() throws Exception {
		
		String isbn = "123";
		
		boolean existsByIsbn = bookRepository.existsByIsbn(isbn);
		
		assertThat(existsByIsbn).isFalse();
	}
}
