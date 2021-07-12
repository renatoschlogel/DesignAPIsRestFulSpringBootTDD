package br.com.renatoschlogel.libraryapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

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
import br.com.renatoschlogel.libraryapi.model.entity.Loan;
import br.com.renatoschlogel.libraryapi.model.repository.LoanRepository;
import br.com.renatoschlogel.libraryapi.service.impl.LoanServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LoanServiceTest {
	
	private LoanService loanService;
	
	@MockBean
	private LoanRepository loanRepository;
	
	@BeforeEach
	public void setUp() {
		loanService = new LoanServiceImpl(loanRepository);
	}

	@Test
	@DisplayName("Deve salvar um emprestimo")
	void saveLoan() throws Exception {
		Book book = Book.builder().build();
		String custumer = "Renato";
		Loan loan = Loan.builder().book(book)
								  .custumer(custumer)
								  .loanDate(LocalDate.now())
								  .build();
		
		Loan loanExpected = Loan.builder().id(1l)
				  .book(book)
				  .custumer(custumer)
				  .loanDate(LocalDate.now())
				  .build();
		
		when(loanRepository.save(loan)).thenReturn(loanExpected);
		
		Loan loanSalved = loanService.save(loan);
		
		assertThat(loan).isNotNull();
		assertThat(loanSalved.getId()).isEqualTo(1l);
		assertThat(loanSalved.getBook()).isEqualTo(book);
		
	}
	
	@Test
	@DisplayName("Deve lançar exceção ao tentar emprestar um livro já emprestado")
	void bookAlreadyBorrowed() throws Exception {
		Book book = Book.builder().build();
		String custumer = "Renato";
		Loan loan = Loan.builder().book(book)
								  .custumer(custumer)
								  .loanDate(LocalDate.now())
								  .build();
		when(loanRepository.existsByBookAndNotReturned(book)).thenReturn(true);
		
		Throwable exception = catchThrowable(() -> loanService.save(loan));
		assertThat(exception).isInstanceOf(BusinessException.class)
							 .hasMessage("Book already loaned.");
		
		verify(loanRepository, never()).save(loan);
	}
	
	@Test
	@DisplayName("Deve encontrar o empréstimo pelo id")
	void getLoanById() throws Exception {
		
		Loan loan = Loan.builder().id(1l)
								  .book(Book.builder().build())
								  .custumer("Renato")
								  .loanDate(LocalDate.now())
								  .build();
		
		when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
		
		Optional<Loan> optLoan = loanService.findById(loan.getId());

		assertThat(optLoan.isPresent()).isTrue();
		assertThat(optLoan.get()).isEqualTo(loan);
		
		Mockito.verify(loanRepository, times(1)).findById(loan.getId());
		
	}
	
}
