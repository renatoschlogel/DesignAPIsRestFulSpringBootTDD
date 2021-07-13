package br.com.renatoschlogel.libraryapi.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.renatoschlogel.libraryapi.api.dto.LoanFilterDTO;
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
	public Loan updateReturnedBook(Long idLoan, Boolean retorned) {
		Loan loan = loanRepository.findById(idLoan).orElseThrow(() -> new BusinessException("Empréstimo não encontrado!") );
		loan.setReturned(retorned);
		return loanRepository.save(loan);
	}

	@Override
	public Optional<Loan> findById(Long id) {
		return loanRepository.findById(id);
	}

	@Override
	public Page<Loan> find(LoanFilterDTO loan, Pageable pageable) {
		return null;
	}

}
