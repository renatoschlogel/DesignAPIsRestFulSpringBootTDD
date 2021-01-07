package br.com.renatoschlogel.libraryapi.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.renatoschlogel.libraryapi.exception.BusinessException;
import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.model.repository.BookRepository;
import br.com.renatoschlogel.libraryapi.service.BookService;
import br.com.renatoschlogel.libraryapi.service.impl.BookServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

	BookService bookService;
	
	@MockBean
	BookRepository bookRepository;
	
	@BeforeEach
	public void setUp() {
		this.bookService = new BookServiceImpl(bookRepository);
	}
	
	@Test
	@DisplayName("Deve salvar um livro")
	void saveBookTest() throws Exception {
		
		Book book = createValidBook();
		Book bookReturn = Book.builder().id(1L).title("Titulo").author("Autor").isbn("123").build();
		Mockito.when(bookRepository.existsByIsbn(book.getIsbn())).thenReturn(false);
		
		Mockito.when(bookRepository.save(book)).thenReturn(bookReturn);
		
		Book savedBook = bookService.incluir(book);
		
		assertThat(savedBook.getId()).isNotNull();
		assertThat(savedBook.getTitle()).isEqualTo(bookReturn.getTitle());
		assertThat(savedBook.getAuthor()).isEqualTo(bookReturn.getAuthor());
		assertThat(savedBook.getIsbn()).isEqualTo(bookReturn.getIsbn());
	}

	@Test
	@DisplayName("Deve lançar erro de negócio ao tentar salvar um livro com isbn duplicado")
	void shouldNotSaveABookWithDuplicateIsbn() throws Exception {
		Book book = createValidBook();
		
		Mockito.when(bookRepository.existsByIsbn(book.getIsbn())).thenReturn(true);
		
		Throwable exception = Assertions.catchThrowable(() -> bookService.incluir(book) );
		
		assertThat(exception).isInstanceOf(BusinessException.class)
		                     .hasMessage("Isbn já utilizado por outro livro!");
		Mockito.verify(bookRepository, Mockito.never()).save(book);
	}
	
	@Test
	@DisplayName("Deve obter um livro por id")
	void getByIdTest() throws Exception {
		Long id = 1l;
		
		Book book = createValidBook();
		book.setId(id);
		
		Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
		
		Optional<Book> optBook = bookService.getById(id);
	
		assertThat(optBook.isPresent()).isTrue();
		Book bookAvail = optBook.get();
		assertThat(bookAvail.getId()).isEqualTo(id);
		assertThat(bookAvail.getTitle()).isEqualTo(book.getTitle());
		assertThat(bookAvail.getAuthor()).isEqualTo(book.getAuthor());
		assertThat(bookAvail.getIsbn()).isEqualTo(book.getIsbn());
		
	}
	
	@Test
	@DisplayName("Deve retornar vazio quando nao encontrar o livro na base")
	void bookNotFoundByIdTest() throws Exception {
		Long id = 1l;
		
		Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<Book> optBook = bookService.getById(id);
	
		assertThat(optBook.isPresent()).isFalse();
	}
	
	
	private Book createValidBook() {
		return Book.builder().title("Titulo").author("Autor").isbn("123").build();
	}
	
}
