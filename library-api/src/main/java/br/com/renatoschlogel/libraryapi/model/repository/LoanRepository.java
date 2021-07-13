package br.com.renatoschlogel.libraryapi.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.model.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

	@Query(value = " select case when (count(l.id) > 0) then true else false end"
				 + "   from Loan l "
			     + "  where l.book = :book "
			     + "    and (l.returned is null or l.returned = false) ")
	boolean existsByBookAndNotReturned(@Param("book") Book book);

	Page<Loan> findByBookIsbnOrCustumer(String isbn, String custumer, Pageable pageable);
	
}
