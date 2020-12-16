package br.com.renatoschlogel.libraryapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renatoschlogel.libraryapi.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
