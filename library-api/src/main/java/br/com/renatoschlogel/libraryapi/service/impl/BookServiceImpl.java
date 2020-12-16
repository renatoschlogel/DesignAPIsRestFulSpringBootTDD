package br.com.renatoschlogel.libraryapi.service.impl;

import org.springframework.stereotype.Service;

import br.com.renatoschlogel.libraryapi.model.entity.Book;
import br.com.renatoschlogel.libraryapi.model.repository.BookRepository;
import br.com.renatoschlogel.libraryapi.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	BookRepository repository;
	
	public BookServiceImpl(BookRepository repository) {
		this.repository = repository;
	}
		
	@Override
	public Book incluir(Book book) {
		return repository.save(book);
	}

}
