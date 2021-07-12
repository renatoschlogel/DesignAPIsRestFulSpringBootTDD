package br.com.renatoschlogel.libraryapi.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.renatoschlogel.libraryapi.exception.BusinessException;
import br.com.renatoschlogel.libraryapi.model.entity.Loan;
import br.com.renatoschlogel.libraryapi.model.repository.LoanRepository;
import br.com.renatoschlogel.libraryapi.service.LoanService;

@Service
public class LoanServiceImpl implements LoanService{
	
	private LoanRepository loanRepository;

	public LoanServiceImpl(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@Override
	public Loan save(Loan loan) {
		
		if(loanRepository.existsByBookAndNotReturned(loan.getBook())) {
			throw new BusinessException("Book already loaned.");
		}
		
		return loanRepository.save(loan);
	}

	@Override
	public void updateReturnedBook(Long idLoan, Boolean retorned) {
		
	}

	@Override
	public Optional<Loan> findById(Long id) {
		
		
		return null;
	}

}
