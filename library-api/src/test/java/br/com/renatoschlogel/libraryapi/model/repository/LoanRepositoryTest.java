package br.com.renatoschlogel.libraryapi.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.model.entity.Book.BookBuilder;
import br.com.renatoschlogel.libraryapi.model.entity.Loan;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LoanRepositoryTest {

	@Autowired
	private TestEntityManager entityManage;

	@Autowired
	private LoanRepository loanRepository;
	
	@Test
	@DisplayName("Deve verificar o livro esta emprestado")
	void testName() throws Exception {
		
		Book availableBook = bookBuilder().build();
		Book borroweBook = bookBuilder().build();
		Loan loan = Loan.builder().book(borroweBook).custumer("Renato").loanDate(LocalDate.now()).build();
		
		entityManage.persist(availableBook);
		entityManage.persist(borroweBook);
		entityManage.persist(loan);
		
		assertThat(loanRepository.existsByBookAndNotReturned(borroweBook)).isTrue();
		assertThat(loanRepository.existsByBookAndNotReturned(availableBook)).isFalse();
	}
	
	private BookBuilder bookBuilder() {
		return Book.builder().title("Clean Code").author("Uncle Bob");
	}
}
