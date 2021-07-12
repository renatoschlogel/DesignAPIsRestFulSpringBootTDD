package br.com.renatoschlogel.libraryapi.api.resource;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renatoschlogel.libraryapi.api.dto.LoanDTO;
import br.com.renatoschlogel.libraryapi.api.dto.ReturnedLoanDTO;
import br.com.renatoschlogel.libraryapi.exception.BusinessException;
import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.model.entity.Loan;
import br.com.renatoschlogel.libraryapi.service.BookService;
import br.com.renatoschlogel.libraryapi.service.LoanService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest( controllers = LoanController.class)
@AutoConfigureMockMvc
public class LoanControllerTest {
	
	static final String LOAN_API = "/api/loans";

	@Autowired
	MockMvc mvc;
	
	@MockBean
	private BookService bookService;

	@MockBean
	private LoanService loanService;
	
	@Test
	@DisplayName("Deve realizar um emprestimo")
	void createLoanTest() throws Exception {
		LoanDTO loanDto = LoanDTO.builder().isbn("123").custumer("Renato").build();
		String json = new ObjectMapper().writeValueAsString(loanDto);
		
		Book book = Book.builder().id(1L).isbn("123").build();
		BDDMockito.given(bookService.getBookByIsbn("123")).willReturn(Optional.of(Book.builder().id(1l).isbn("123").build()));
		
		
		Loan loan = Loan.builder().id(1L).custumer("Renato").book(book).loanDate(LocalDate.now()).build();
		
		BDDMockito.given(loanService.save(Mockito.any(Loan.class))).willReturn(loan);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LOAN_API)
							.accept(MediaType.APPLICATION_JSON)
							.contentType(MediaType.APPLICATION_JSON)
							.content(json);
		
		mvc.perform(request)
			.andExpect( MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().string("1"));
	}
	
	@Test
	@DisplayName("Deve retornar um erro ao tentar emprestar um livro inexistente.")
	void invalidIsbnCreateLoan() throws Exception {

		String isbn = "123";
		LoanDTO loanDto = LoanDTO.builder().isbn(isbn).custumer("Renato").build();
		String json = new ObjectMapper().writeValueAsString(loanDto);
		
		given(bookService.getBookByIsbn(isbn)).willReturn(Optional.empty());
		
		MockHttpServletRequestBuilder request = post(LOAN_API)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest())
						    .andExpect(jsonPath("errors", hasSize(1)))
						    .andExpect(jsonPath("errors[0]").value("Book not found fot passad isbn."))
						    ;
	}
	
	@Test
	@DisplayName("Deve retornar um erro ao tentar emprestar um livro já emprestado")
	void loanedBookErrorOnCreateLoan() throws Exception {

		String isbn = "123";
		LoanDTO loanDto = LoanDTO.builder().isbn(isbn).custumer("Renato").build();
		String json = new ObjectMapper().writeValueAsString(loanDto);
		
		Book book = Book.builder().id(1L).isbn("123").build();
		given(bookService.getBookByIsbn("123")).willReturn(Optional.of(book));
		
		given(loanService.save(Mockito.any(Loan.class))).willThrow(new BusinessException("Book already loaned."));
		
		
		MockHttpServletRequestBuilder request = post(LOAN_API)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request).andExpect(status().isBadRequest())
						    .andExpect(jsonPath("errors", hasSize(1)))
						    .andExpect(jsonPath("errors[0]").value("Book already loaned."))
						    ;
	}

	@Test
	@DisplayName("Deve devolver o livro emprestado")
	void returnBook() throws Exception {
		
		ReturnedLoanDTO retornedLoanDTO = ReturnedLoanDTO.builder().retorned(true).build();
		
		String jsonRetornedLoan = new ObjectMapper().writeValueAsString(retornedLoanDTO);
		
		BDDMockito.given(loanService.findById(1l)).willReturn(Optional.of(Loan.builder().id(1l).build()));
		MockHttpServletRequestBuilder request = patch(LOAN_API.concat("/1")).accept(MediaType.APPLICATION_JSON)
																	  		.contentType(MediaType.APPLICATION_JSON)
																	  		.content(jsonRetornedLoan);
		mvc.perform(request).andExpect(status().isOk());
		BDDMockito.verify(loanService, times(1)).updateReturnedBook(1l, retornedLoanDTO.getRetorned());
	}
	
	
	@Test
	@DisplayName("Deve retornar 404 quando não encontrado o livro a devolver")
	void returnInexistentBook() throws Exception {
		
		ReturnedLoanDTO retornedLoanDTO = ReturnedLoanDTO.builder().retorned(true).build();
		
		String jsonRetornedLoan = new ObjectMapper().writeValueAsString(retornedLoanDTO);
		
		
		MockHttpServletRequestBuilder request = patch(LOAN_API.concat("/1")).accept(MediaType.APPLICATION_JSON)
																	  		.contentType(MediaType.APPLICATION_JSON)
																	  		.content(jsonRetornedLoan);
		mvc.perform(request).andExpect(status().isNotFound());
	}
	
}
