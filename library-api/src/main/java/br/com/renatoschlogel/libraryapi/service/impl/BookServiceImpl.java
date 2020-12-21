package br.com.renatoschlogel.libraryapi.service.impl;

import org.springframework.stereotype.Service;

import br.com.renatoschlogel.libraryapi.exception.BusinessException;
import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.model.repository.BookRepository;
import br.com.renatoschlogel.libraryapi.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	BookRepository bookRepository;
	
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
		
	@Override
	public Book incluir(Book book) {
		
		if (bookRepository.existsByIsbn(book.getIsbn())) {
			throw new BusinessException("Isbn já utilizado por outro livro!");
		}
		
		return bookRepository.save(book);
	}

}
