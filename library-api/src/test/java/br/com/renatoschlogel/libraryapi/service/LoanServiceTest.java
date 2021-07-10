package br.com.renatoschlogel.libraryapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
	
}
