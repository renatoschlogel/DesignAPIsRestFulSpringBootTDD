package br.com.renatoschlogel.libraryapi.api.resource;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renatoschlogel.libraryapi.api.dto.BookDTO;
import br.com.renatoschlogel.libraryapi.exception.BusinessException;
import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.service.BookService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {
	
	static String BOOK_API = "/api/books";

	@Autowired
	MockMvc mvc;
	
	@MockBean
	BookService bookService; 
	
	@Test
	@DisplayName("Deve criar um livro com sucesso!")
	public void createBookTest() throws Exception {
		
		BookDTO bookDTO = createNewBookDTO();
		
		Book savedBook = Book.builder().title("GO TEAM!")
				   					   .id(1L)
				   					   .author("Ken")
				   					   .isbn("123")
				   					   .build();
		
		BDDMockito.given(bookService.incluir(Mockito.any(Book.class))).willReturn(savedBook);
		
		String json = new ObjectMapper().writeValueAsString(bookDTO);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
			.post(BOOK_API)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(json);
		
		mvc.perform(request)
		   .andExpect(status().isCreated())
		   .andExpect(jsonPath("id").isNotEmpty())
		   .andExpect(jsonPath("title").value(bookDTO.getTitle()))
		   .andExpect(jsonPath("author").value(bookDTO.getAuthor()))
		   .andExpect(jsonPath("isbn").value(bookDTO.getIsbn()))
		   ;
				  
	}

	@Test
	@DisplayName("Deve lançar erro de validação quando não houver dados suficientes para a criação do livro.")
	void createInvalidBookTest() throws Exception {
		String json = new ObjectMapper().writeValueAsString(new BookDTO());
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
		mvc.perform(request).andExpect( status().isBadRequest() )
		                    .andExpect(jsonPath("errors", hasSize(3)));
	}
	
	@Test
	@DisplayName("Deve lançar exceção ao tentar cadastra um livro com isbn já utilizado por outro livro")
	void validateBookCreationWithDuplicatedIsbn() throws Exception {
		BookDTO bookDTO = createNewBookDTO();
		String json = new ObjectMapper().writeValueAsString(bookDTO);
		String mensagemException = "Isbn já utilizado por outro livro!";
		BDDMockito.given(bookService.incluir(Mockito.any(Book.class))).willThrow(new BusinessException(mensagemException));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request).andExpect( status().isBadRequest() )
		                    .andExpect(jsonPath("errors", hasSize(1)))
		                    .andExpect(jsonPath("errors[0]").value(mensagemException) );
	}
	
	
	@Test
	@DisplayName("Deve obter informações de um livro.")
	void getBookDetailsTest() throws Exception {
		
		Long id = 1l;
		
		Book book = Book.builder()
				        .id(id)
						.title("GO TEAM!")
						.author("Ken")
						.isbn("123").build();
		
		BDDMockito.given(bookService.getById(id)).willReturn(Optional.of(book));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BOOK_API + "/" + id)
                              										  .accept(MediaType.APPLICATION_JSON);
		mvc.perform(request)
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("id").value(id))
		   .andExpect(jsonPath("title").value(book.getTitle()))
		   .andExpect(jsonPath("author").value(book.getAuthor()))
		   .andExpect(jsonPath("isbn").value(book.getIsbn()));
	}
	
	@Test
	@DisplayName("Deve retornar resourse not found quando o livro nao for encontrado")
	void bookNotFoundTest() throws Exception {
		
		BDDMockito.given(bookService.getById(Mockito.anyLong())).willReturn(Optional.empty());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BOOK_API + "/" + 1l)
                              										  .accept(MediaType.APPLICATION_JSON);
		mvc.perform(request)
		   .andExpect(status().isNotFound());
		
	}
	
	private BookDTO createNewBookDTO() {
		BookDTO bookDTO = BookDTO.builder().title("GO TEAM!")
		                 				   .author("Ken")
		                 				   .isbn("123")
		                 				   .build();
		return bookDTO;
	}
}
