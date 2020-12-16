package br.com.renatoschlogel.libraryapi.api.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		
		BookDTO bookDTO = BookDTO.builder().title("GO TEAM!")
		                 				   .author("Ken")
		                 				   .isbn("123")
		                 				   .build();
		
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
		
	}
}
