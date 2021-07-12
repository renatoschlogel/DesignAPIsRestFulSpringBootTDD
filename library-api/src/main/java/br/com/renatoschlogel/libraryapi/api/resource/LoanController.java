package br.com.renatoschlogel.libraryapi.api.resource;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.renatoschlogel.libraryapi.api.dto.LoanDTO;
import br.com.renatoschlogel.libraryapi.api.dto.ReturnedLoanDTO;
import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.model.entity.Loan;
import br.com.renatoschlogel.libraryapi.service.BookService;
import br.com.renatoschlogel.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController{
	
	private final LoanService loanService;
	private final BookService bookService;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Long create(@RequestBody LoanDTO loanDTO) {
		
		Book book = bookService.getBookByIsbn(loanDTO.getIsbn())
							   .orElseThrow(() -> new ResponseStatusException( HttpStatus.BAD_REQUEST, "Book not found fot passad isbn."));
		
		Loan loan = Loan.builder().book(book)
								  .custumer(loanDTO.getCustumer())
								  .loanDate(LocalDate.now())
								  .build();
		loan = loanService.save(loan);
		
		return loan.getId();
	}
	
	@PatchMapping("{id}")
	public void returnedBook(@PathVariable Long id, @RequestBody ReturnedLoanDTO returnedLoanDTO) {
		Loan loan = loanService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		loanService.updateReturnedBook(loan.getId(), returnedLoanDTO.getRetorned());
	}

}
