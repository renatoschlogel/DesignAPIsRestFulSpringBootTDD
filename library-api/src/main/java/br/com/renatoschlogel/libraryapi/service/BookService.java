package br.com.renatoschlogel.libraryapi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.renatoschlogel.libraryapi.api.dto.BookDTO;
import br.com.renatoschlogel.libraryapi.model.entity.Book;

public interface BookService {

	Book incluir(Book book);

	Optional<Book> getById(Long id);

	void delete(Book book);

	Book update(Book book);

	Page<Book> find(Book book, Pageable pageable);

	Page<BookDTO> find(Book filter, java.awt.print.Pageable pageRequest);

}
