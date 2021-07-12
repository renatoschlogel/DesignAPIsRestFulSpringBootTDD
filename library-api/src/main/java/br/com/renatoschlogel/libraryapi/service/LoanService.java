package br.com.renatoschlogel.libraryapi.service;

import java.util.Optional;

import br.com.renatoschlogel.libraryapi.model.entity.Loan;

public interface LoanService {

	public Loan save(Loan loan);

	public void updateReturnedBook(Long idLoan, Boolean retorned);

	public Optional<Loan> findById(Long id);

}
