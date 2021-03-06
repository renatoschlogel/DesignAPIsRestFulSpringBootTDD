package br.com.renatoschlogel.libraryapi.api.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.renatoschlogel.libraryapi.api.dto.BookDTO;
import br.com.renatoschlogel.libraryapi.api.dto.LoanDTO;
import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.model.entity.Loan;
import br.com.renatoschlogel.libraryapi.service.BookService;
import br.com.renatoschlogel.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
	
	private BookService bookService;
	
	private LoanService loanService;
	
	private ModelMapper modelMapper; 
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody @Valid BookDTO bookDTO) {
		Book book = modelMapper.map(bookDTO, Book.class);
		book = bookService.incluir(book);
		return modelMapper.map(book, BookDTO.class);
	}

	@GetMapping("{id}")
	public BookDTO get(@PathVariable Long id) {
		return  bookService.getById(id)
				            .map(book -> modelMapper.map(book, BookDTO.class))
				            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Optional<Book> optBook = bookService.getById(id);
		if(!optBook.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		bookService.delete(optBook.get());
	}
	
	@PutMapping("{id}")
	public BookDTO update(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
		return bookService.getById(id)
				          .map(book -> {
				        	  book.setTitle(bookDTO.getTitle());
				        	  book.setAuthor(bookDTO.getAuthor());
				        	  book = bookService.update(book);
				        	  return modelMapper.map(book, BookDTO.class);
				          })
						  .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping
	public Page<BookDTO> find (BookDTO bookDTO, Pageable pageRequest){
		Book filter = modelMapper.map(bookDTO, Book.class);
		Page<Book> result = bookService.find(filter, pageRequest);
		
		List<BookDTO> listBookDTO = result.getContent()
		      .stream()
		      .map(book -> modelMapper.map(book, BookDTO.class))
		      .collect(Collectors.toList());
		
		return new PageImpl<BookDTO>(listBookDTO, pageRequest, result.getTotalElements());
	}
	
	@GetMapping("{id}/loans")
	public Page<LoanDTO> loansByBook(@PathVariable Long id, Pageable pageable){
		Book book = bookService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		Page<Loan> result = loanService.getloansByBook(book, pageable);
		
		List<LoanDTO> listLoanDTO = result.getContent()
		      .stream()
		      .map(loan -> {
		    	  LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
		    	  loanDTO.setBook(modelMapper.map(loan.getBook(), BookDTO.class));
		    	  return loanDTO;
		      })
		      .collect(Collectors.toList());
		
		return new PageImpl<LoanDTO>(listLoanDTO, pageable, result.getTotalElements());
	}
	
}
